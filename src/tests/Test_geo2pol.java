package tests;
/**
 * 测试转换到第一象限坐标
 * @author xcc
 *
 */
import sdog_essentials.Geocoor2Polar;
public class Test_geo2pol {
	public static void main(String[] args){
		Geocoor2Polar geo=new Geocoor2Polar(190.0,-60.0,1.5);
		
		double[] v=new double[3];
		v=geo.PolarCoor();
		System.out.println(v[0]);
		System.out.println(v[1]);
		System.out.println(v[2]);
	}

}