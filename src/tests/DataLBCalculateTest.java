package tests;

import com.mathworks.toolbox.javabuilder.MWException;

import tests.NOAAReadTest3;
import transLB.LBtransit;

public class DataLBCalculateTest {
	private float[] Latitude;
	private float[] Longitude;
	private float[] Altitude;
	private double[] Time;
	private double[] L;
	private double[] B;
	
	public DataLBCalculateTest(String filePath) throws MWException{
		NOAAReadTest3 NOAAt3=new NOAAReadTest3(filePath);
		this.Latitude=NOAAt3.getLat();
		this.Longitude=NOAAt3.getLon();
		this.Altitude=NOAAt3.getAlt();
		this.Time=NOAAt3.getTimes();
		
		int length=Latitude.length;
		this.L=new double[length];
		this.B=new double[length];
		
		for(int i=0;i<length;i++){
			LBtransit lbt=new LBtransit(this.Time[i],this.Latitude[i],this.Longitude[i],this.Altitude[i]);
			L[i]=lbt.getLcoord();
			B[i]=lbt.getBcoord();
		}
	}
	
	public double[] getNOAAL(){
		return this.L;
	}
	public double[] getNOAAB(){
		return this.B;
	}
	
	
	public static void main(String[] args){
		try {
			DataLBCalculateTest dlb=new DataLBCalculateTest("D:/documents/java/satellitedata/METOP-1/metop1_poes-sem2s_fluxes-2sec_20130101000001_20130101011013.nc");
			double[] L=dlb.getNOAAL();
			double[] B=dlb.getNOAAB();
			for(int i=0;i<L.length;i++){
				System.out.print(L[i]+" "+B[i]);
				System.out.print("\n");
			}
		} catch (MWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}