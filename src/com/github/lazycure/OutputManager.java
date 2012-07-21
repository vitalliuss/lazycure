package com.github.lazycure;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.util.Log;

import com.github.lazycure.activities.Activity;

public class OutputManager {
	
	public final static String EMPTY_STRING = "";
	public final static String ACTIVITY_PREFIX = "	";
	public final static String DELIMITER = " - ";
	public final static String TEST_ACTIVITY_NAME = "test";
	
	public static String FormatActivitiesList(List<Activity> activities){
		StringBuffer buffer = new StringBuffer();
		String activitiesList = EMPTY_STRING;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat ("HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		for (Activity activity:activities) {
			buffer.append(ACTIVITY_PREFIX);
			buffer.append(activity.getName());
			buffer.append(DELIMITER);
			Date delta = activity.getDuration();
			Log.d("Date" , "Inserting [" + activity.getName() + "] with duration: " + dateFormat.format(delta));
			buffer.append(dateFormat.format(delta));
			buffer.append("\n");
		}
		activitiesList = buffer.toString();
		return activitiesList;
	}
	
	public static List<Activity> UpdateActivitiesWithStartTime(List<Activity> activities){
        //Update missing start time records for all activities
		for (int i=1;i<activities.size();i++){
			if (activities.get(i-1).getFinishTime() != null){
				activities.get(i).setStartTime(activities.get(i-1).getFinishTime());
			}
		}
		return activities;
	}
}
