package br.org.scadabr.vo.dataSource.cip;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import com.serotonin.json.JsonException;
import com.serotonin.json.JsonObject;
import com.serotonin.json.JsonReader;
import com.serotonin.mango.rt.dataSource.DataSourceRT;
import com.serotonin.mango.util.ExportCodes;
import com.serotonin.mango.vo.dataSource.DataSourceVO;
import com.serotonin.mango.vo.dataSource.PointLocatorVO;
import com.serotonin.mango.vo.event.EventTypeVO;
import com.serotonin.web.dwr.DwrResponseI18n;
import com.serotonin.web.i18n.LocalizableMessage;

public class CIPDataSourceVO extends DataSourceVO<DataSourceVO<?>> {

	/**
	 * 
	 */

	/**
	 * 
	 */

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalizableMessage getConnectionDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PointLocatorVO createPointLocator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSourceRT createDataSourceRT() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExportCodes getEventCodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void addEventTypes(List<EventTypeVO> eventTypes) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addPropertiesImpl(List<LocalizableMessage> list) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addPropertyChangesImpl(List<LocalizableMessage> list, DataSourceVO<?> from) {
		// TODO Auto-generated method stub

	}

	public void validate(DwrResponseI18n response) {
		super.validate(response);
	}

	private static final int version = 1;

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeInt(version);
	}

	private void readObject(ObjectInputStream in) throws IOException {
		int ver = in.readInt();
	}

	public void jsonDeserialize(JsonReader reader, JsonObject json) throws JsonException {
		super.jsonDeserialize(reader, json);
	}

	public void jsonSerialize(Map<String, Object> map) {
		super.jsonSerialize(map);
	}

}
