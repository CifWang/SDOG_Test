package sdog_io;
/**
 * 
 * @author xcc
 *
 */

import java.util.Map;
import sdog_essentials.Geocoor2Polar;
public interface FileReader {
	
	//public boolean next();
	
	/**
	 * 数据时间信息
	 * @return 时间
	 */
	public long getTime();
	
	public Geocoor2Polar PolarCoor();
	
	public String getSatellite();
	
	public String getInstrument();
	
	public String[] getVariables();
	
	//public String getFamily(String variable);
	
	//public Map<String, Object> getAttribute(String variable);
	
	public double getValue(String variable);

}

