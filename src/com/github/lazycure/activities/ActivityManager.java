package com.github.lazycure.activities;

import java.util.List;
import android.content.Context;
import android.util.Log;
import com.github.lazycure.LazyCureApplication;
import com.github.lazycure.OutputManager;
import com.github.lazycure.Time;
import com.github.lazycure.db.DatabaseHandler;

public class ActivityManager {
	
	private static Context context = LazyCureApplication.getAppContext();
	private static DatabaseHandler db = new DatabaseHandler(context);
	
	public static void createFirstTestActivity(){
		db.addActivity(new Activity(OutputManager.TEST_ACTIVITY_NAME, null, Time.getCurrentDate()));
	}
	
	public static boolean activityListIsEmpty(){
		boolean isEmpty = false;
		int activitiesCount = db.getActivitiesCount();
		Log.d("DB", "Activities count: " + activitiesCount);
		if (activitiesCount < 1){
			isEmpty = true;
		}
		return isEmpty;
	}
	
	public static void addActivity(String activityName) {
		if (activityName.length() != 0){
			if (!activityName.equalsIgnoreCase(getLastActivity().getName())){
				Log.d("Insert" , "Inserting [" + activityName + "] with finish date: " + Time.getCurrentDate().toString());
		        db.addActivity(new Activity(activityName, null, Time.getCurrentDate()));
			}
			else{
				continueLatestActivity();
			}
		}
	}

	public static void removeActivity(Activity activity){
		db.removeActivity(activity);
	}

	public static void continueLatestActivity(){
		Activity latestActivity = getLastActivity();
		removeActivity(latestActivity);
		addActivity(latestActivity.getName());
	}

	public static Activity getLastActivity(){
		List<Activity> activities = db.getAllActivities();
		return activities.get(activities.size()-1);
	}
}
