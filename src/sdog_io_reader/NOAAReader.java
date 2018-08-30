package sdog_io_reader;
/**
 * 
 * @author xcc
 *
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import sdog_io.FileReader;
import sdog_essentials.Geocoor2Polar;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class NOAAReader implements FileReader{
	private NetcdfFile ncFile = null;

	public static final String[] sat = { "NOAA15", "NOAA18", "NOAA19", "METOP1", "METOP2" };

	public static final String instrument = "MEPED";

	public static final String[] variable = { "ELEC_0DEG_40", "ELEC_90DEG_40", "ELEC_0DEG_130", "ELEC_90DEG_130",
			"ELEC_0DEG_287", "ELEC_90DEG_287", "ELEC_0DEG_612", "ELEC_90DEG_612", "PRO_0DEG_39", "PRO_90DEG_39",
			"PRO_0DEG_115", "PRO_90DEG_115", "PRO_0DEG_332", "PRO_90DEG_332", "PRO_0DEG_1105", "PRO_90DEG_1105",
			"PRO_0DEG_2723", "PRO_90DEG_2723", "PRO_0DEG_6174", "PRO_90DEG_6174" };
	
	private static final HashMap<String, Integer> varIndex = new HashMap<>();

	private float[][] values = new float[ncVars.length][];
	private static final String[] ncVars = new String[3 + variable.length];
	private double[] times=null;
	
	private int ncLen=0;
	
	private String satellite;
	
	private int valueIndex=-1;
	

	static {
		ncVars[0] = "LAT";
		ncVars[1] = "LON";
		ncVars[2] = "ALT";
		for (int i = 0; i < variable.length; i++) {
			ncVars[i + 3] = variable[i];
			varIndex.put(variable[i], i + 3);
		}
	}
	
	public NOAAReader(String filePath){
		try{
			ncFile=NetcdfFile.open(filePath);
			Variable time=ncFile.findVariable(ncVars[0]);
			ncLen=time.getShape(0);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public Geocoor2Polar PolarCoor(){
		Geocoor2Polar gp=new Geocoor2Polar(values[0][valueIndex],values[1][valueIndex],values[2][valueIndex]);
		return gp;
	}
	
	public long getTime(){
		return (long)(times[valueIndex]/1000);
		
	}
	
	public String getSatellite(){
		return satellite;
	}
	
	public String getInstrument(){
		return instrument;
	}
	
	public String[] getVariables(){
		return variable;
	}
	
	public double getValue(String variable){
		int vi=varIndex.get(variable);
		return values[vi][valueIndex];
	}
	

}
