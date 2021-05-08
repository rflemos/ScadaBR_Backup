package br.org.scadabr.rt.dataSource.asciiFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ajile.jem.SystemOut;
import com.serotonin.mango.rt.dataImage.DataPointRT;
import com.serotonin.mango.rt.dataImage.PointValueTime;
import com.serotonin.mango.rt.dataImage.SetPointSource;
import com.serotonin.mango.rt.dataImage.types.MangoValue;
import com.serotonin.mango.rt.dataImage.types.NumericValue;
import com.serotonin.mango.rt.dataSource.DataSourceRT;
import com.serotonin.mango.rt.dataSource.PollingDataSource;
import com.serotonin.web.i18n.LocalizableMessage;

import br.org.scadabr.vo.dataSource.asciiFile.ASCIIFileDataSourceVO;
import br.org.scadabr.vo.dataSource.asciiFile.ASCIIFilePointLocatorVO;
import etherip.EtherNetIP;
import etherip.data.CipException;
import etherip.types.CIPData;

public class ASCIIFileDataSource extends PollingDataSource {
	
	
	
	private final Log LOG = LogFactory.getLog(ASCIIFileDataSource.class);
	public static final int POINT_READ_EXCEPTION_EVENT = 1;
	public static final int DATA_SOURCE_EXCEPTION_EVENT = 2;
	public static final int POINT_WRITE_EXCEPTION_EVENT = 3;
	private final ASCIIFileDataSourceVO<?> vo;
	
	private String regex;
	private EtherNetIP clp;

	public ASCIIFileDataSource(ASCIIFileDataSourceVO<?> vo) {
		super(vo);
		this.vo = vo;
		setPollingPeriod(vo.getUpdatePeriodType(), vo.getUpdatePeriods(), vo.isQuantize());
	}

	@Override
	protected void doPoll(long time) {
		
		
			if(this.clp == null) {
				try {
				this.clp = new EtherNetIP(vo.getFilePath(),0);
				this.clp.connectTcp();		
				System.out.println("tentativa de conectar");
				}
				catch (TimeoutException e) {
					this.clp= null;
					raiseEvent(DATA_SOURCE_EXCEPTION_EVENT, time, true,
							new LocalizableMessage("event.exception2", vo.getName(), "Not possible connect clp"));
					
				} catch (Exception e) {
					this.clp= null;
					raiseEvent(DATA_SOURCE_EXCEPTION_EVENT, time, true,
							new LocalizableMessage("event.exception2", vo.getName(), "Error in connection clp"));
				}
				
			}
		else {
			for (DataPointRT dataPoint : dataPoints) {
				try {
					ASCIIFilePointLocatorVO dataPointVO = dataPoint.getVO().getPointLocator();
					MangoValue value;
					value = getValue(dataPointVO);
					long timestamp = time;
					

					dataPoint.updatePointValue(new PointValueTime(value, timestamp));
				} catch (Exception e) {
					this.clp = null;
					raiseEvent(POINT_READ_EXCEPTION_EVENT, time, true,
							new LocalizableMessage("event.exception2", vo.getName(), e.getMessage()));
					e.printStackTrace();
				}

			}
				
			}
			
			
		}
		//excetion for desconection
		
		
		
		
		
		
		
	
	
	private CIPData getClpData(String tag) {

		try {
			CIPData cipData = clp.readTag(tag);
			return cipData;

		} catch (CipException e) {
			throw new RuntimeException("Tag reading error " + e.getMessage());
			
			
		} catch (TimeoutException e) {
			try {
				clp.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("Loss of Connection");
		} catch (Exception e) {
			throw new RuntimeException("Unexpected tag reading error " +  e.getMessage() );
		}

	}
	

	private MangoValue getValue(ASCIIFilePointLocatorVO point) throws Exception {
		return new NumericValue ((double) getClpData(point.getValueRegex()).getNumber(0));
			
		
		

		
	}

	private long getTimestamp(ASCIIFilePointLocatorVO point, String arquivo) throws Exception {
		long timestamp = new Date().getTime();
		String dataFormat = point.getTimestampFormat();
		String tsRegex = point.getTimestampRegex();
		Pattern tsPattern = Pattern.compile(tsRegex);
		Matcher tsMatcher = tsPattern.matcher(arquivo);

		boolean found = false;
		while (tsMatcher.find()) {
			found = true;
			String tsValue = tsMatcher.group();
			timestamp = new SimpleDateFormat(dataFormat).parse(tsValue).getTime();
		}

		if (!found) {
			throw new Exception("Timestamp string not found (regex: " + tsRegex + ")");
		}

		return timestamp;
	}

	@Override
	public void setPointValue(DataPointRT dataPoint, PointValueTime valueTime, SetPointSource source) {
		System.out.print("rafael faine");

	}
	
	
	@Override
	public void initialize() {
		try {
			this.clp = new EtherNetIP(vo.getFilePath(),0);
			clp.connectTcp();
			
			returnToNormal(DATA_SOURCE_EXCEPTION_EVENT,
					System.currentTimeMillis());
		}

		catch (TimeoutException e) {
			this.clp= null;
			e.printStackTrace();
			
			
		} catch (Exception e) {
			this.clp= null;
			e.printStackTrace();
			
		}

	
	
        super.initialize();
        
        
     }
	@Override
     public void terminate() {
		super.terminate();
		try {
			if(this.clp != null)
			this.clp.close();
		} catch (Exception e) {
			System.err.println("Not possible to disconnect clp: " + e.getMessage());
		}
		
    	 
     }

	@Override
	public void addDataPoint(DataPointRT dataPoint) {
		if (dataPoint.getPointValue() != null) {
			ASCIIFilePointLocatorRT locator = dataPoint.getPointLocator();
			locator.setCurrentValue(dataPoint.getPointValue().getValue());
		}

		super.addDataPoint(dataPoint);
	}
	
	
	
		

}
