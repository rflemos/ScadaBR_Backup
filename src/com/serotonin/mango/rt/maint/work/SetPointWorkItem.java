/*
    Mango - Open Source M2M - http://mango.serotoninsoftware.com
    Copyright (C) 2006-2011 Serotonin Software Technologies Inc.
    @author Matthew Lohbihler
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.serotonin.mango.rt.maint.work;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.serotonin.mango.Common;
import com.serotonin.mango.db.dao.DataPointDao;
import com.serotonin.mango.rt.RTException;
import com.serotonin.mango.rt.dataImage.PointValueTime;
import com.serotonin.mango.rt.dataImage.SetPointSource;
import com.serotonin.mango.vo.DataPointVO;

/**
 * @author Matthew Lohbihler
 */
public class SetPointWorkItem implements WorkItem {
	private static final ThreadLocal<List<String>> threadLocal = new ThreadLocal<List<String>>();
	private static final int MAX_RECURSION = 10;
	private static Log LOG = LogFactory.getLog(SetPointWorkItem.class);

	private final int targetPointId;
	private final PointValueTime pvt;
	private final SetPointSource source;
	private final List<String> sourceIds;
	private DataPointDao dpdao = new DataPointDao();
	private DataPointVO targetVo;

	public SetPointWorkItem(int targetPointId, PointValueTime pvt, SetPointSource source) {
		this.targetPointId = targetPointId;
		this.pvt = pvt;
		this.source = source;
		this.targetVo = dpdao.getDataPoint(targetPointId);
		LOG.debug("Creating SPWorkItem - source: " + source.getSetPointSourceId() + ", target: " + targetVo.getName()
				+ "with value: " + pvt.getStringValue() + "@" + pvt.getTime());

		if (threadLocal.get() == null)
			sourceIds = new ArrayList<String>();
		else
			sourceIds = threadLocal.get();
	}

	@Override
	public void execute() {
		LOG.debug("Executing SPWorkItem, target: " + targetVo.getName());

		String sourceId = Integer.toString(source.getSetPointSourceType()) + "-"
				+ Integer.toString(source.getSetPointSourceId());

		// Check if we've reached the maximum number of hits for this point
		int count = 0;
		for (String id : sourceIds) {
			if (id.equals(sourceId))
				count++;
		}

		if (count > MAX_RECURSION) {
			source.raiseRecursionFailureEvent();
			LOG.debug("Max recursions!");
			return;
		}

		sourceIds.add(sourceId);
		threadLocal.set(sourceIds);
		try {
			Common.ctx.getRuntimeManager().setDataPointValue(targetPointId, pvt, source);
		} catch (RTException rte) {
			LOG.debug("Exception while setting point value to dp: " + targetVo.getName() + ", message: "
					+ rte.getMessage());
		} finally {
			LOG.debug("Removing thread");
			threadLocal.remove();
		}
		LOG.debug("Done!");
	}

	@Override
	public int getPriority() {
		return WorkItem.PRIORITY_HIGH;
	}
}
