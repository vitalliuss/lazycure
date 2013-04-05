package main.java.com.github.lazycure;

import java.util.Collections;
import java.util.List;

import main.java.com.github.lazycure.activities.Activity;
import main.java.com.github.lazycure.db.DatabaseHandler;
import main.java.com.github.lazycure.ui.ActivitiesTableManager;
import main.java.com.github.lazycure.ui.TimeLogManager;

import main.java.com.github.lazycure.R;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class TimeLogActivity extends LazyCureActivity {

	DatabaseHandler db = new DatabaseHandler(this);
	private TableLayout timeLogTable = null;
	private Context context = LazyCureApplication.getAppContext();
	private ActivitiesTableManager activitiesTableManager = new ActivitiesTableManager(context);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timelog);

		setUpViews();
	}

	private void setUpViews() {
		timeLogTable = (TableLayout) findViewById(R.id.timeLogTable);

		List<Activity> activities = db.getAllActivities();
		OutputManager.updateActivitiesWithStartTime(activities);
		// Reverse the activities order
		Collections.reverse(activities);
		//Remove test activity
        activitiesTableManager.removeTestActivity(activities);
		// Print out the activities
		TimeLogManager timeLog = new TimeLogManager(context);
		timeLog.setTable(timeLogTable);
		timeLog.showTable(activities, 500);

	}

}
