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

public class NOAAReadTest {
	
	public static final String[] sat = { "NOAA15", "NOAA18", "NOAA19", "METOP1", "METOP2" };
	public static final String instrument = "MEPED";
	
	//map是个集合对象，key是String类型，value是Integer类型，把数据的属性做索引
	public static final HashMap<String, Integer> varIndex=new HashMap<>();
	
	
	//存放20个变量的名字
	public static final String[] variable = { "ELEC_0DEG_40", "ELEC_90DEG_40", "ELEC_0DEG_130", "ELEC_90DEG_130",
		"ELEC_0DEG_287", "ELEC_90DEG_287", "ELEC_0DEG_612", "ELEC_90DEG_612", "PRO_0DEG_39", "PRO_90DEG_39",
		"PRO_0DEG_115", "PRO_90DEG_115", "PRO_0DEG_332", "PRO_90DEG_332", "PRO_0DEG_1105", "PRO_90DEG_1105",
		"PRO_0DEG_2723", "PRO_90DEG_2723", "PRO_0DEG_6174", "PRO_90DEG_6174" };
	
	
	//所有23个变量的名字
	private static final String[] ncVars = new String[3 + variable.length];
	
	//放变量值
	private float[][] values = new float[ncVars.length][];
	
	private static NetcdfFile ncFile=null;
	
	private int ncLen=0;
	private static final int ncStep=10000;
	private int ncIndex=0;
	
	private int readIndex=-1;
	private int valueIndex=-1;
	
	private double [] alts=null;
	
	//private Array altitudeData;
	
	//前三个是位置信息，LAT,LON,ALT
	static{
		ncVars[0] = "LAT";
		ncVars[1] = "LON";
		ncVars[2] = "ALT";
		for (int i = 0; i < variable.length; i++) {
			ncVars[i + 3] = variable[i];
			varIndex.put(variable[i], i+3);   //给20个非时空变量做索引，key=变量名字，value=从3到23
		}
	}
	
	/**
	 * 打开文件，看看前三个变量的维度
	 * @param filePath
	 * @throws InvalidRangeException 
	 */
	
	public NOAAReadTest(String filePath) throws InvalidRangeException{
		try{
			ncFile=NetcdfFile.open(filePath);
			Variable altitude=ncFile.findVariable(ncVars[2]);
			ncLen=altitude.getShape(0);
			//altitudeData=altitude.read("0:9");
			
		} catch (IOException e){
			e.printStackTrace();
		}
		
		//记得关上文件
		finally{
			if(null!=ncFile)try{
				ncFile.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public boolean next() throws InvalidRangeException{
		while(true){
			readIndex++;
			if(readIndex>=ncLen)
				return false;
			valueIndex++;
			if (valueIndex>=alts.length){
				readToBuffer();
				valueIndex++;
			}
			return true;
		}
	}
	
	/**
	 * 一个数据文件的一个变量，从头读到尾
	 * @throws InvalidRangeException
	 */
	public void readToBuffer() throws InvalidRangeException{
		try{
			//剩余读取的长度，一次读取一万条数据
			int readLen=ncIndex+ncStep < ncLen ? ncStep: ncLen-ncIndex;
			int[] origin={ncIndex},size={readLen};
			Variable altitude=ncFile.findVariable("ALT");
			alts=(double[])altitude.read(origin,size).get1DJavaArray(double.class);
			//TODO get1DJavaArray???
			for (int i=0;i<ncVars.length;i++){
				Variable v=ncFile.findVariable(ncVars[i]);
				values[i]=(float[])v.read(origin,size).get1DJavaArray(float.class);
			}
			ncIndex+=readLen;
			valueIndex=-1;
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	public Array getData(){
		return altitudeData;
	}
	*/
	
	
	/**
	 * 求前三个变量的维度
	 * @return 一维数组，放三个变量的维度
	 */
	/**
	public int[] getDim(){
		int[] ncLen=new int[3];
		ncLen[0]=this.ncLen1;
		ncLen[1]=this.ncLen2;
		ncLen[2]=this.ncLen3;
		return ncLen;
	}
	 * @throws InvalidRangeException */
	
	/**
	public static void main(String[] args) throws InvalidRangeException{
		NOAAReadTest ncFile=new NOAAReadTest("D:/java/SDOG_4D_test/Data/noaa15_poes-sem2s_fluxes-2sec_20130101000235_20130630163258.nc");
		Array data=ncFile.getData();
		System.out.print(data);
	}*/

}