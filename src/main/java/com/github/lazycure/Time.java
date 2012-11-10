package main.java.com.github.lazycure;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * formats Time values
 *
 */
public class Time {
	public final static long ONE_SECOND = 1000;//milliseconds
    public final static long SECONDS = 60;

    public final static long ONE_MINUTE = ONE_SECOND * SECONDS;
    public final static long MINUTES = 60;

    public final static long ONE_HOUR = ONE_MINUTE * MINUTES;
    public final static long HOURS = 24;

    public final static long ONE_DAY = ONE_HOUR * HOURS;
    
    public static Date getCurrentDate(){
		Date date = new Date();
		return date;
	}
    
    /**
     * Formats the milliseconds value to <dd>H:mm:ss
     * @param milliseconds
     * @return string
     */
    public static String formatWithDay(long milliseconds) {
      String res = "";
      milliseconds /= ONE_SECOND;
      int seconds = (int) (milliseconds % SECONDS);
      milliseconds /= SECONDS;
      int minutes = (int) (milliseconds % MINUTES);
      milliseconds /= MINUTES;
      int hours = (int) (milliseconds % HOURS);
      int days = (int) (milliseconds / HOURS);
      if (days == 0) {
      	 res = String.format("%d:%02d:%02d", hours, minutes, seconds);
      } else {
      	 res = String.format("%dd%d:%02d:%02d", days, hours, minutes, seconds);
      }
      return res;
	}
    
    public static String getYYYYMMDD(Date date){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	String dateString = dateFormat.format(date);
		return dateString;
    }
    
    /**
     * Converts date into ISO 8601 string
     * @param date
     * @return String date in ISO 8601 format http://www.iso.org/iso/en/prods-services/popstds/datesandtime.html
     */
    public static String getYYYYMMddHHmmss(Date date){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String dateString = dateFormat.format(date);
		return dateString;
    }

    /**
     * Formats the Date object to be displayed as time in H:mm:ss format
     * @param Date object presenting time, usually duration
     * @return formatted short string presenting readable time
     */
	public static String format(Date dateTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm:ss");
		//required in order to ignore current locale when formatting, just look at the numbers
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		if(dateTime==null)
			dateTime=new Date(0);
		return dateFormat.format(dateTime);
	}

	/**
	 * Formats duration to be displayed as time in H:mm:ss format
	 * @param milliseconds in milliseconds
	 * @return formatted short string presenting readable time
	 */
	public static String format(long milliseconds) {
		return format(new Date(milliseconds));
	}

	/**
	 * Formats the date value to <dd>H:mm:ss
	 * @param duration
	 * @return string
	 */
	public static String formatWithDay(Date duration) {
		return formatWithDay(duration.getTime());
	}
}
