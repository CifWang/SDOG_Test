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
	 * @param Q ������
	 */
	//public static String Q;
	
	public Geocoor2Polar(){}
	
	public Geocoor2Polar(double latitude, double longitude){
		this(latitude,longitude,1);
	}
	
	/**
	 * ��������ľ�γ�ȣ��ж����ĸ��˷��������Ҫת����[0,90]��Χ
	 * @param latitude �����γ�ȣ�[-90,90]
	 * @param longitude ����ľ��ȣ�[0,360]
	 * @param height �߶ȣ���L����ȵ�
	 */
	public Geocoor2Polar(double latitude, double longitude, double height){
		this.lat=latitude;
		this.lon=longitude;
		this.h=height;
	}
	
	/**
	 * ת���õ��ĵ�һ���޵�������
	 * @return ����������
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