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
    
    /**
     * Formats the milliseconds value to <dd>H:mm and rounded seconds to minutes
     * @param milliseconds
     * @return string
     */
    public static String formatAndRoundWithDay(long milliseconds) {
      String res = "";
      int secondsOffset = 0;
      milliseconds /= ONE_SECOND;
      int seconds = (int) (milliseconds % SECONDS);
      if (seconds > 30)
    	  secondsOffset = 60;
      milliseconds += secondsOffset;
      milliseconds /= SECONDS;
      int minutes = (int) (milliseconds % MINUTES);
      milliseconds /= MINUTES;
      int hours = (int) (milliseconds % HOURS);
      int days = (int) (milliseconds / HOURS);
      if (days == 0) {
      	 res = String.format("%d:%02d", hours, minutes);
      } else {
      	 res = String.format("%dd%d:%02d", days, hours, minutes);
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
	 * Formats the Date object to be displayed as time in H:mm:ss in device time zone
	 * @param Date object presenting time, f.e. activity start or end
	 * @return formatted short string presenting readable time in user's time zone
	 */
	public static String formatWithDefaultTimeZone(Date dateTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm:ss");
		dateFormat.setTimeZone(TimeZone.getDefault());
		String output = "0:00:00";
		if (dateTime != null) {
			output = dateFormat.format(dateTime);
		}

		return output;
	}
	
	/**
     * Formats the Date object to be displayed as time in H:mm format and rounded to minutes
     * @param Date object presenting time, usually duration
     * @return formatted short string presenting readable rounded time
     */
	public static String formatAndRound(Date dateTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm");
		//required in order to ignore current locale when formatting, just look at the numbers
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		if(dateTime==null)
			dateTime=new Date(0);
		return dateFormat.format(roundSecondsToMinutes(dateTime));
	}
	
	/**
     * Formats the Date object to be displayed as time in H:mm format and rounded to minutes in device time zone
     * @param Date object presenting time, f.e. activity start or end
	 * @return formatted short string presenting readable time in user's time zone
     */
	public static String formatAndRoundWithDefaultTimeZone(Date dateTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm");
		dateFormat.setTimeZone(TimeZone.getDefault());
		String output = "0:00";
		if (dateTime != null) {
			output = dateFormat.format(roundSecondsToMinutes(dateTime));
		}

		return output;
	}
	
	/**
	 * Round seconds in Date object to minutes before formatting into String
	 * @param Date to round
	 * @return Rounded Date object
	 */
	public static Date roundSecondsToMinutes(Date dateTime) {
		if (dateTime.getSeconds() > 30) {
			int minutes = dateTime.getMinutes();
			dateTime.setMinutes(minutes + 1);
		}
		return dateTime;
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
	
	/**
	 * Formats the date value to <dd>H:mm and round seconds to minutes
	 * @param duration
	 * @return string
	 */
	public static String formatAndRoundWithDay(Date duration) {
		return formatAndRoundWithDay(duration.getTime());
	}
	
	/**
	 * Convert hours to minutes
	 * @param hours
	 * @return minutes
	 */
	public static int hoursToMinutes(int hours) {
		return (int) (hours * MINUTES);
	}
	
	/**
	 * Format HH:mm string from minutest count
	 * @param minutesCount
	 * @return time string
	 */
	public static String minutesToHoursAndMunites(int minutesCount) {
		int hours = minutesCount / 60;
		int minutes = minutesCount % 60;
		String result = String.format("%02d:%02d", hours, minutes);
		return result;
	}
	
	/**
	 * Convert hours and minutes to minutes
	 * @param hours
	 * @param minutes
	 * @return minutest total
	 */
	public static int hoursAndMinutestToMinutes(int hours, int minutes) {
		int result = (hours * 60) + minutes;
		return result;
	}
}
