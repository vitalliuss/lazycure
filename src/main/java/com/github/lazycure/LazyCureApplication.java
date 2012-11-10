package main.java.com.github.lazycure;

import java.util.ArrayList;

import main.java.com.github.lazycure.activities.Activity;
import android.app.Application;
import android.content.Context;

public class LazyCureApplication extends Application {

	private ArrayList<Activity> currentActivities;
	private static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		if (null == currentActivities) {
			currentActivities = new ArrayList<Activity>();
		}
		LazyCureApplication.context = getApplicationContext();
	}

	public static Context getAppContext() {
        return LazyCureApplication.context;
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
