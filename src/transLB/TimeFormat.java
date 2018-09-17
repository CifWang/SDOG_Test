package transLB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class TimeFormat {
	
	public final String timeZone="Etc/GMT+0";
	private String timestr;
	
	public TimeFormat(double t){
		Date date=new Date((long)t);
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sf.setTimeZone(TimeZone.getTimeZone(timeZone));
		String time=sf.format(date);
		this.timestr=time;
	}
	
	public String getTimestr(){
		return this.timestr;
	}
	
}
