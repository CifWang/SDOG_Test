package tests;
/**
 * 
 * @author cifengwang
 *
 */
import java.io.File;
import java.io.IOException;

import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.ma2.InvalidRangeException;

public class NOAAReadTest3 {
	private NetcdfFile ncFile=null;
	
	public static final String[] variable = { "ELEC_0DEG_40", "ELEC_90DEG_40", "ELEC_0DEG_130", "ELEC_90DEG_130",
		"ELEC_0DEG_287", "ELEC_90DEG_287", "ELEC_0DEG_612", "ELEC_90DEG_612", "PRO_0DEG_39", "PRO_90DEG_39",
		"PRO_0DEG_115", "PRO_90DEG_115", "PRO_0DEG_332", "PRO_90DEG_332", "PRO_0DEG_1105", "PRO_90DEG_1105",
		"PRO_0DEG_2723", "PRO_90DEG_2723", "PRO_0DEG_6174", "PRO_90DEG_6174" };
	
	private static final String[] ncVars=new String[3+variable.length];//数据+lat,lon,alt，没有time
	
	private double[] times=null;//时间times是单独列开的，放的时间
	//private float[] alts=null;
	private float[][] values=new float[ncVars.length][];
	
	private static final int ncStep=10;
	
	private int ncIndex=0;
	private int ncLen=0;
	private int valueIndex=-1;
	public int readIndex=-1;
	
	static{
		//ncVars[0]="TIME";
		ncVars[0]="LAT";
		ncVars[1]="LON";
		ncVars[2]="ALT";
		for(int i=0;i<variable.length;i++){
			ncVars[i+3]=variable[i];
			
		}
		
	}
	
	public NOAAReadTest3(String filePath){
		try{
			ncFile=NetcdfFile.open(filePath);
			Variable time=ncFile.findVariable("TIME");
			//Variable latitude=ncFile.findVariable(ncVars[0]);
			ncLen=time.getShape(0);

			
			readToBuffer();
			//System.out.println(times.length);
			//System.out.println(ncLen);
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 读记录
	 * values[0][],是lat
	 * values[1][],是lon
	 * values[2][],是alt
	 * values[3-22],是20个变量
	 * times[]是时间
	 */
	private void readToBuffer(){
		try{
			int readLen=ncIndex+ncStep<ncLen? ncStep: ncLen-ncIndex;
			int[] origin={ncIndex},size={readLen};
			//一次都读进去的话，数据文件太大，内存溢出。所以用了readToBuffer(),一次读一万条记录，读完扔到数据库里扔掉
			//int[] origin={0},size={ncLen};
			Variable time=ncFile.findVariable("TIME");
			times=(double[]) time.read(origin,size).get1DJavaArray(double.class);
			//System.out.println(times.length);
			
			for(int i=0;i<ncVars.length;i++){
				Variable v=ncFile.findVariable(ncVars[i]);
				values[i]=(float[])v.read(origin,size).get1DJavaArray(float.class);
			}
			
			/**
			for(int i=0;i<times.length;i++){
				System.out.println(times[i]);
			}
			
			
			Variable alt=ncFile.findVariable("ALT");
			alts=(float[]) alt.read(origin,size).get1DJavaArray(float.class);
			for(int i=0;i<alts.length;i++){
				System.out.println(alts[i]);
			}*/
			
			ncIndex+=readLen;
			valueIndex=-1;//valueIndex：已经读到buffer中的记录的index
			//System.out.println(values[0].length);
			//System.out.println(ncIndex);
		
		}catch (IOException e){
			e.printStackTrace();
		}catch (InvalidRangeException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断下一条记录还有没有
	 * ncLen记录一共有多少条，times.length，目前读了有多少条
	 * @return
	*/ 

	public boolean next(){
		readIndex++;
		if(readIndex>=ncLen)
			return false;
		valueIndex++;
		if(valueIndex>=times.length){
			readToBuffer();
			valueIndex++;
		}
		return true;
	}
	
	protected void finalize(){
		try{
			ncFile.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public double[] getTimes(){
		return this.times;
	}
	
	public float[] getLat(){
		return this.values[0];
	}
	
	public float[] getLon(){
		return this.values[1];
	}
	
	public float[] getAlt(){
		return this.values[2];
	}
	
	/**
	public static void main(String[] args){
		String filePath="D:/SatelliteData/METOP-1/metop1_poes-sem2s_fluxes-2sec_20130101000001_20130101011013.nc";
		NOAAReadTest3 t3=new NOAAReadTest3(filePath);
		//System.out.println(t3);
		double[] times=t3.getTimes();
		float[] lats=t3.getLat();
		float[] lons=t3.getLon();
		float[] alts=t3.getAlt();
		for(int i=0;i<times.length;i++){
			System.out.println(times[i]+"   "+lats[i]+"   "+lons[i]+"   "+alts[i]);
		}
	}*/

}
