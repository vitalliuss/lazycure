package com.github.lazycure;

public class Time {
	public final static long ONE_SECOND = 1000;
    public final static long SECONDS = 60;

    public final static long ONE_MINUTE = ONE_SECOND * 60;
    public final static long MINUTES = 60;

    public final static long ONE_HOUR = ONE_MINUTE * 60;
    public final static long HOURS = 24;

    public final static long ONE_DAY = ONE_HOUR * 24;
    
    /**
     * converts time (in milliseconds) to human-readable format
     *  "<dd:>hh:mm:ss"
     */
    public static String millisToShortDHMS(long duration) {
      String res = "";
      duration /= ONE_SECOND;
      int seconds = (int) (duration % SECONDS);
      duration /= SECONDS;
      int minutes = (int) (duration % MINUTES);
      duration /= MINUTES;
      int hours = (int) (duration % HOURS);
      int days = (int) (duration / HOURS);
      if (days == 0) {
        res = String.format("%02d:%02d:%02d", hours, minutes, seconds);
      } else {
        res = String.format("%dd%02d:%02d:%02d", days, hours, minutes, seconds);
      }
      return res;
    }

}
