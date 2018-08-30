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
	 * ����ʱ����Ϣ
	 * @return ʱ��
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

