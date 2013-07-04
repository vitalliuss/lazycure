package main.java.com.github.lazycure.ui;

import java.util.List;

import main.java.com.github.lazycure.LazyCureApplication;
import main.java.com.github.lazycure.Strings;
import main.java.com.github.lazycure.Time;
import main.java.com.github.lazycure.activities.Activity;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class TimeLogManager extends ActivitiesTableManager {

	private TableLayout timeLogTable = null;
	private Context context = LazyCureApplication.getAppContext();

	public TimeLogManager(Context parent) {
		super(parent);
	}

	/**
	 * show table of activities
	 * 
	 * @param activities
	 *            activities list
	 */
	@Override
	public void showTable(List<Activity> activities) {
		if (this.timeLogTable != null) {
			this.timeLogTable.removeAllViews();
			this.buildHeader();
			for (int i = 0; i < activities.size(); i++) {
				showActivity(activities.get(i));
			}
		}
	}
	
	/**
	 * @param activities activities list
	 * @param activitiesNumberToShow number of last activities to show
	 */
	@Override
	public void showTable(List<Activity> activities, int activitiesNumberToShow) {
		if (this.timeLogTable != null) {
			this.timeLogTable.removeAllViews();
			this.buildHeader();
			for (int i = 0; i < activities.size(); i++) {
				if (i<activitiesNumberToShow-1) {
					showActivity(activities.get(i));
				}
			}
		}
	}

	/**
	 * Create first row with columns names
	 */
	public void buildHeader() {

		TableRow tr = new TableRow(context);
		tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		// create header cells
		TextView labelStart = createTextViewCell(Strings.START);
		TextView labelName = createTextViewCell(Strings.NAME);
		TextView labelDuration = createTextViewCell(Strings.DURATION);
		TextView labelEnd = createTextViewCell(Strings.FINISH);

		// add header to the row
		tr.addView(labelStart);
		tr.addView(labelName);
		tr.addView(labelDuration);
		tr.addView(labelEnd);

		this.timeLogTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

	}

	/**
	 * show activity in table layout by adding a row
	 * 
	 * @param activity
	 */
	@Override
	public void showActivity(Activity activity) {
		TableRow tr = this.createRow(activity);
		this.timeLogTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	}

	/**
	 * create TableRow for the activity
	 * 
	 * @param activity
	 * @return TableRow
	 */
	@Override
	public TableRow createRow(Activity activity) {
		// create a new row for an activity
		TableRow tr = new TableRow(context);
		tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		// create activities properties cells
		TextView activityStart = createTextViewCell(Time.formatWithDefaultTimeZone(activity
				.getStartTime()));
		TextView activityName = createTextViewCell(activity.getName());
		TextView activityDuration = createTextViewCell(Time
				.formatWithDay(activity.getDuration()));
		TextView activityEnd = createTextViewCell(Time.formatWithDefaultTimeZone(activity
				.getFinishTime()));

		// add activity properties views to the row
		tr.addView(activityStart);
		tr.addView(activityName);
		tr.addView(activityDuration);
		tr.addView(activityEnd);
		return tr;
	}

	/**
	 * set TableLayout to work with
	 * 
	 * @param table
	 */
	@Override
	public void setTable(TableLayout table) {
		this.timeLogTable = table;
	}

}
