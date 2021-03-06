package main.java.com.github.lazycure;

import java.util.Date;
import java.util.List;

import main.java.com.github.lazycure.activities.Activity;

/*
 * Prepare data for being displayed
 */
public class OutputManager {
	
	public final static String EMPTY_STRING = "";
	public final static String ACTIVITY_PREFIX = "	";
	public final static String DELIMITER = " ";
	public final static String DAY_SEPARATION_LINE = "=====";
	public final static String THREE_DOTS = "...";
	public final static int SYMBOLS_TO_KEEP = 25;
	public static int lastDayNumber;
	
	public static String formatActivitiesList(List<Activity> activities){
		StringBuffer buffer = new StringBuffer();
		String activitiesList = EMPTY_STRING;
		for (int i=0; i<activities.size(); i++) {
			buffer.append(formatActivityString(activities.get(i)));
			buffer.append("\n");
			if (i<activities.size()-1){
				if (!isOnSameDay(activities.get(i), activities.get(i+1))){
					buffer.append(getSeparationString(activities.get(i)));
					buffer.append("\n");
				}
			}
		}
		activitiesList = buffer.toString();
		return activitiesList;
	}

	public static String formatActivityString(Activity activity){
		StringBuffer buffer = new StringBuffer();
		buffer.append(Time.formatWithDefaultTimeZone(activity.getStartTime()));
		buffer.append(DELIMITER);
		buffer.append(activity.getName());
		buffer.append(DELIMITER);
		buffer.append(Time.formatWithDay(activity.getDuration()));
		buffer.append(DELIMITER);
		buffer.append(Time.formatWithDefaultTimeZone(activity.getFinishTime()));
		return buffer.toString();
	}

	public static List<Activity> updateActivitiesWithStartTime(List<Activity> activities){
        //Update missing start time records for all activities
		for (int i=1;i<activities.size();i++){
			if (activities.get(i-1).getFinishTime() != null){
				activities.get(i).setStartTime(activities.get(i-1).getFinishTime());
			}
		}
		return activities;
	}

	public static int getFinishDayNumber(Activity activity){
		int dayNumber = activity.getFinishTime().getDay();
		return dayNumber;
	}
	
	/**
	 * compare days of 2 activities
	 * @param activity1
	 * @param activity2
	 * @return true or false
	 */
	public static boolean isOnSameDay(Activity activity1, Activity activity2){
		return getFinishDayNumber(activity1) == getFinishDayNumber(activity2);
	}
	
	/**
	 * return separation line to be displayed between activities from different days
	 * @param activity
	 * @return string
	 */
	public static String getSeparationString(Activity activity) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(ACTIVITY_PREFIX + DAY_SEPARATION_LINE);
		Date previousActivityDate = activity.getFinishTime();
		buffer.append(Time.getYYYYMMDD(previousActivityDate));
		buffer.append(DAY_SEPARATION_LINE);
		return buffer.toString();
	}

	/**
	 * Crop the activity name in order to fit one row on the screen
	 * @param activityName
	 * @param symbolsToKeep
	 * @return cropped string
	 */
	public static String cropName(String activityName) {
		String newName = activityName;
		if (activityName.length() > SYMBOLS_TO_KEEP - THREE_DOTS.length()) {
			newName = activityName.substring(0, SYMBOLS_TO_KEEP - THREE_DOTS.length());
			newName = newName.concat(THREE_DOTS);
		}
		return newName;
	}
}
