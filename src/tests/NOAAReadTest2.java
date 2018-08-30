package tests;
/**
 * 
 * @author xcc
 *
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import sdog_essentials.Geocoor2Polar;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.ma2.Array;
import ucar.ma2.ArrayFloat;
import ucar.ma2.InvalidRangeException;



public class NOAAReadTest2 {
	public static final String[] sat = { "NOAA15", "NOAA18", "NOAA19", "METOP1", "METOP2" };
	public static final String instrument = "MEPED";
	
	public static final String[] variable = { "ELEC_0DEG_40", "ELEC_90DEG_40", "ELEC_0DEG_130", "ELEC_90DEG_130",
		"ELEC_0DEG_287", "ELEC_90DEG_287", "ELEC_0DEG_612", "ELEC_90DEG_612", "PRO_0DEG_39", "PRO_90DEG_39",
		"PRO_0DEG_115", "PRO_90DEG_115", "PRO_0DEG_332", "PRO_90DEG_332", "PRO_0DEG_1105", "PRO_90DEG_1105",
		"PRO_0DEG_2723", "PRO_90DEG_2723", "PRO_0DEG_6174", "PRO_90DEG_6174" };
	
	public static final HashMap<String, Integer> varIndex=new HashMap<>();
	
	//所有23个变量的名字
	private static final String[] ncVars = new String[3 + variable.length];
	private static NetcdfFile ncFile=null;
	private float[][] values=new float[ncVars.length][];
	
	private int ncLen=0;
	private int ncIndex=0;
	
	private static final int ncStep=10000;
	
	private int readIndex=-1;   //读列，读记录
	private int valueIndex=-1;  //读行，读变量
	
	private double [] times=null;
	
	static{
		ncVars[0] = "LAT";
		ncVars[1] = "LON";
		ncVars[2] = "ALT";
		for (int i = 0; i < variable.length; i++) {
			ncVars[i + 3] = variable[i];
			varIndex.put(variable[i], i+3);   //给20个非时空变量做索引，key=变量名字，value=从3到23
		}
	}
	
	public void readToBuffer() throws InvalidRangeException{
		try{
			int readLen=ncIndex+ncStep<ncLen? ncStep:ncLen-ncIndex;
			int [] origin={ncIndex}, size={readLen};
			Variable time=ncFile.findVariable("TIME");
			times=(double [])time.read(origin,size).get1DJavaArray(double.class);
			for (int i=0;i<ncVars.length;i++){
				Variable v=ncFile.findVariable(ncVars[i]);
				values[i]=(float[])v.read(origin,size).get1DJavaArray(float.class);
			}
			ncIndex+=readLen;
			valueIndex=-1;
		}catch (IOException e){
			e.printStackTrace();
		}
	}

}
