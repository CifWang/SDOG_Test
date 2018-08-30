package sdog_essentials;
/**
 * changes geo-coordinate to polar coordinate
 * @author xcc
 *
 */
public class Geocoor2Polar {
	/**
	 * latitude and longitude in Polar System based on every octant
	 * @param L represents L value in BL coordinate
	 * @param lon in degree, range[0,90]
	 * @param lat in degree, rang[0,90]
	 */
	public double lat;
	public double lon;
	public double h;
	
	/**
	 * @param Q 象限码
	 */
	//public static String Q;
	
	public Geocoor2Polar(){}
	
	public Geocoor2Polar(double latitude, double longitude){
		this(latitude,longitude,1);
	}
	
	/**
	 * 根据输入的经纬度，判断在哪个八分体里，并且要转换到[0,90]范围
	 * @param latitude 输入的纬度，[-90,90]
	 * @param longitude 输入的经度，[0,360]
	 * @param height 高度，与L是相等的
	 */
	public Geocoor2Polar(double latitude, double longitude, double height){
		this.lat=latitude;
		this.lon=longitude;
		this.h=height;
	}
	
	/**
	 * 转换得到的第一象限的球坐标
	 * @return 球坐标数组
	 */
	public double[] PolarCoor(){
		
		double[] Pcoor=new double[3];
		Pcoor[1]=lon%90;
		Pcoor[2]=h;
		if(lat>=0){
			Pcoor[0]=lat;
		}
		else{
			Pcoor[0]=-lat;
		}
		return Pcoor;
	}

}