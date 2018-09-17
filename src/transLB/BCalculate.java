package transLB;

import java.text.DecimalFormat;

import IGRF.Igrf;

import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import transLB.TimeFormat;
import transLB.LCalculate;
public class BCalculate {
	private double time;
	private float lat;
	private float alt;
	private float lon;
	private double[] B;
	double BTotal;
	private double BValue;
	
	public BCalculate(double t,float latitude,float longitude,float altitude) throws MWException{
		this.time=t;
		this.lat=latitude;
		this.lon=longitude;
		String coord="geodetic";
		
		TimeFormat tf=new TimeFormat(t);
		String timestr=tf.getTimestr();
		
		MWNumericArray latArray=new MWNumericArray(Float.valueOf(lat));
		MWNumericArray lonArray=new MWNumericArray(Float.valueOf(lon));
		MWNumericArray altArray=new MWNumericArray(Float.valueOf(alt));
		
		Igrf ig=new Igrf();
		Object[] result=ig.igrf(3, timestr,latArray,lonArray,altArray,coord);
		double[] b=new double[result.length];
		for(int i=0;i<b.length;i++){
			b[i]=Double.parseDouble(result[i].toString());
		}
		
		this.B=b;
		double bValue=(float) Math.sqrt(Math.pow(this.B[0], 2)+Math.pow(this.B[1], 2)+Math.pow(this.B[2], 2));
		this.BTotal=bValue;
	}
	
	public double getBTotal(){
		return this.BTotal;
	}
	
	public double getBValue() throws MWException{
		LCalculate lc=new LCalculate(this.time,this.lat,this.lon,this.alt);
		double Be=lc.getBe();
		//System.out.println(Be);
		DecimalFormat df=new DecimalFormat(".00");
		String Bvalue=df.format(this.BTotal/Be);
		this.BValue=Double.parseDouble(Bvalue);
		//System.out.println(this.BValue);
		return this.BValue;
	}
	
	/**
	public static void main(String[] args){
		double time=7.3689e+05;
		BCalculate bc;
		try {
			bc = new BCalculate(time,-60,180,0);
			double b=bc.getBValue();
			//double b=bc.getBTotal();
			System.out.println(b);
		} catch (MWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

}