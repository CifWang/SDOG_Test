package transLB;


import java.text.DecimalFormat;

import transLB.TimeFormat;
import transLB.BCalculate;
import IGRF.Igrf;

import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

public class LCalculate {
	public final double R_earth=6371.393;
	private double time;
	private final MWNumericArray distance_up=new MWNumericArray(Double.valueOf(90e3),MWClassID.DOUBLE);
	private final MWNumericArray distance_down=new MWNumericArray(Double.valueOf(-90e3),MWClassID.DOUBLE);
	private final MWNumericArray nsteps=new MWNumericArray(Double.valueOf(90e3),MWClassID.DOUBLE);
	//private Object[][] out;
	//private float[] result;
	private double LValue;
	private double B_equator;
	
	public LCalculate(double t,float latst,float lonst,float altst) throws MWException{
		TimeFormat tf=new TimeFormat(t);
		String timestr=tf.getTimestr();
		String coord="geodetic";
		MWNumericArray latstArray=new MWNumericArray(Float.valueOf(latst));
		MWNumericArray lonstArray=new MWNumericArray(Float.valueOf(lonst));
		MWNumericArray altstArray=new MWNumericArray(Float.valueOf(altst));
		
		MWNumericArray distance;
		if(latst>=0){
			distance=distance_down;
		}else{distance=distance_up;}
		
		Object[] in={timestr,latstArray,lonstArray,altstArray,coord,distance,nsteps};
		
		Igrf ig=new Igrf();
		Object[] out=ig.igrfline2L(3, in);
		double[] outd=new double[out.length];
		float[] outf=new float[out.length];
		for(int i=0;i<out.length;i++){
			outd[i]=Double.parseDouble(out[i].toString());
			outf[i]=Float.parseFloat(out[i].toString());
			//System.out.println(outd[i]);
		}
		
		DecimalFormat df=new DecimalFormat(".0");
		String Lvalue=df.format(outd[2]/R_earth);
		this.LValue=Double.parseDouble(Lvalue);
		
		BCalculate bc=new BCalculate(t,outf[0],outf[1],outf[2]);
		this.B_equator=bc.getBTotal();
	}
	
	public double getBe(){
		return this.B_equator;
	}
	
	public double getLValue(){
		return this.LValue;
	}
	
	/**
	public static void main(String[] args){
		double time=7.3689e+05;
		try {
			LCalculate lc=new LCalculate(time,60,180,0);
			//double L=lc.getLValue();
			//System.out.println(L);
			double b=lc.getBe();
			System.out.println(b);
		} catch (MWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}

