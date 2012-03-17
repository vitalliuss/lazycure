package com.github.lazycure;

import java.util.ArrayList;
import com.github.lazycure.activities.Activity;
import android.app.Application;

public class LazyCureApplication extends Application {

	private ArrayList<Activity> currentActivities;
	
	@Override
	public void onCreate() {
		super.onCreate();
		if (null == currentActivities) {
			currentActivities = new ArrayList<Activity>();
		}
	}

	public void setCurrentActivities(ArrayList<Activity> currentActivities) {
		this.currentActivities = currentActivities;
	}

	public ArrayList<Activity> getCurrentActivities() {
		return currentActivities;
	}
	
	public void addActivity(Activity a) {
		assert(null != a);
		if (null == currentActivities) {
			currentActivities = new ArrayList<Activity>();
		}
		currentActivities.add(a);
	}
	
}
