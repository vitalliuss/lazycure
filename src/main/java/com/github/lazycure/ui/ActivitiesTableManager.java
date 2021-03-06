package main.java.com.github.lazycure.ui;

import java.util.List;

import main.java.com.github.lazycure.OutputManager;
import main.java.com.github.lazycure.Strings;
import main.java.com.github.lazycure.Time;
import main.java.com.github.lazycure.activities.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;


/**
 * Manage activities table, create and modify rows, format it
 * @author Mikhail_Subach
 *
 */
public class ActivitiesTableManager {
	private TableLayout activitiesTable = null;
	private Context parent;
	
	public ActivitiesTableManager(Context parent) {
		this.parent = parent;
	}

	/**
	 * show table of activities
	 * @param activities activities list
	 */
	public void showTable(List<Activity> activities) {
		if(this.activitiesTable!=null){
			this.activitiesTable.removeAllViews();
	        for (int i=0; i<activities.size(); i++) {
				showActivity(activities.get(i));
				if (i<activities.size()-1){
					if (!OutputManager.isOnSameDay(activities.get(i), activities.get(i+1))){
						showDaySeparator(activities.get(i));
					}
				}
			}
		}
	}
	
	/**
	 * show table of last N activities
	 * @param activities activities list
	 * @param activitiesNumberToShow number of last activities to show
	 */
	public void showTable(List<Activity> activities, int activitiesNumberToShow) {
		if(this.activitiesTable!=null){
			this.activitiesTable.removeAllViews();
	        for (int i=0; i<activities.size(); i++) {
	        	if (i<activitiesNumberToShow-1) {
	        		showActivity(activities.get(i));
					if (i<activities.size()-1){
						if (!OutputManager.isOnSameDay(activities.get(i), activities.get(i+1))){
							showDaySeparator(activities.get(i));
						}
					}
	        	}
			}
		}
	}

	/**
	 * remove test activity from the list before printing
	 * @param activities
	 */
	public void removeTestActivity(List<Activity> activities) {
		if (activities.size() != 0) {
			int lastPosition = activities.size()-1;
			Activity _last = activities.get(lastPosition);
			if (_last.getName().equalsIgnoreCase(Strings.TEST_ACTIVITY_NAME)) {
				activities.remove(lastPosition);
			}
		}
	}

	/**
	 * show in table day separator line
	 * @param activity the activity from that day
	 */
	private void showDaySeparator(Activity activity) {
		TableRow tr = new TableRow(parent);
		tr.setLayoutParams(new LayoutParams(
			LayoutParams.FILL_PARENT,
			LayoutParams.WRAP_CONTENT));
		// to be displayed in the first column
		TextView empty = createCell();
		// displayed in the central column
		String separator = OutputManager.getSeparationString(activity);
		TextView daySeparator = createTextViewCell(separator);
		tr.addView(empty);
		tr.addView(daySeparator);
		this.activitiesTable.addView(tr);
	}

	/**
	 * show activity in table layout by adding a row
	 * @param activity
	 */
	public void showActivity(Activity activity) {
		TableRow tr = createRow(activity);
		this.activitiesTable.addView(tr,new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
	}

	/**
	 * create TableRow for the activity
	 * @param activity
	 * @return TableRow
	 */
	public TableRow createRow(Activity activity) {
		// create a new row for an activity
		TableRow tr = new TableRow(parent);
		tr.setLayoutParams(new LayoutParams(
			LayoutParams.FILL_PARENT,
			LayoutParams.WRAP_CONTENT));
		
		// create activities properties cells
		TextView activityStart = createTextViewCell(Time.formatAndRoundWithDefaultTimeZone(activity.getStartTime()));
		TextView activityName = createTextViewCell(activity.getName());
		TextView activityDuration = createTextViewCell(Time.formatAndRoundWithDay(activity.getDuration()));
		
		// add activity properties views to the row
		tr.addView(activityStart);
		tr.addView(activityName);
		tr.addView(activityDuration);
		return tr;
	}
	
	/**
	 * Create text view Cell
	 * @param content of the cell
	 * @return TextView object
	 */
	public TextView createTextViewCell(String content){
		TextView view = createCell();
		view.setText(content);
		view.setTextColor(Color.YELLOW);
		return view;
	}

	/**
	 * Create cell
	 * @return View representing a cell
	 */
	public TextView createCell() {
		TextView cell = new TextView(parent);
		cell.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		cell.setPadding(2, 0, 2, 0);
		return cell;
	}

	/**
	 * set TableLayout to work with
	 * @param table
	 */
	public void setTable(TableLayout table) {
		this.activitiesTable = table;
	}
}
