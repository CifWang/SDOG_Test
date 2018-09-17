package transLB;

import com.mathworks.toolbox.javabuilder.MWException;

import transLB.BCalculate;
import transLB.LCalculate;

public class LBtransit {
	private double LValue;
	private double BValue;
	
	public LBtransit(double time,float lat,float lon,float alt)throws MWException{
		BCalculate bc=new BCalculate(time,lat,lon,alt);
		LCalculate lc=new LCalculate(time,lat,lon,alt);
		double L=lc.getLValue();
		double B=bc.getBValue();
		this.LValue=L;
		this.BValue=B;
		//System.out.println(L+" "+B);
	}
	
	public double[] LBCoord(){
		double[] LB={this.LValue,this.BValue};
		return LB;
	}
	
	
	public static void main(String[] args){
		double time=7.3689e+05;
		try {
			LBtransit lb=new LBtransit(time,60,180,0);
			double[] LB=lb.LBCoord();
			for(int i=0;i<LB.length;i++){
				System.out.print(LB[i]);
				System.out.print(" ");
			}
		} catch (MWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
