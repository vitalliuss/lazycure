package main.java.com.github.lazycure;

import java.util.Date;

import main.java.com.github.lazycure.activities.Activity;
import main.java.com.github.lazycure.activities.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class SplitActivity extends LazyCureActivity {
	
	private EditText firstActivity;
	private EditText secondActivity;
	private TimePicker timePicker;
	private Button splitButton;
	private String inputString = null;
	private String separator = ",";
	private String separationPrefix = "\\";
	private String EMPTY = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent= getIntent();
		this.inputString = intent.getStringExtra("inputString");
		this.separator = intent.getStringExtra("separator");
		setUpViews();
	}
	
	private void setUpViews(){
		//Log.d("Split", "Splitting: " + inputString);
		String separationRegex = separationPrefix.concat(separator);
		String[] activities = inputString.split(separationRegex);
		
		String first = EMPTY;
		String second = EMPTY;
		try {
			first = activities[0];
			Log.d("Split", "First: " + first);
			second = activities[1];
			Log.d("Split", "Second: " + second);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setContentView(R.layout.split);
		
		firstActivity = (EditText) findViewById(R.id.split_first_activity);
		secondActivity = (EditText) findViewById(R.id.split_second_activity);
		
		splitButton = (Button) findViewById(R.id.split_button);
		firstActivity.setText(first);
		secondActivity.setText(second);
		timePicker = (TimePicker) findViewById(R.id.split_timer);
		//Remove AM/PM switcher
		timePicker.setIs24HourView(true);

		Activity lastActivity = ActivityManager.getLastActivity();
		final Date lastActivityFinishTime = lastActivity.getFinishTime();
		//Log.d("Split", "lastActivityFinishTime: " + lastActivity.getFinishTime().toString());
		Date now = Time.getCurrentDate();
		//Log.d("Split", "Time.getCurrentDate(): " + now.toString());
		
		//Calculate total duration without time zone offset
		long totalDuration = now.getTime() - lastActivityFinishTime.getTime() + (now.getTimezoneOffset()*60*1000);
		//Create a total duration date
		Date totalDurationDate = new Date(totalDuration);
		
		//Log.d("Split", "Total duration: " + new Date(totalDuration));
		//Log.d("Split", "Total duration hours: " + new Date(totalDuration).getHours());
		//Log.d("Split", "Total duration minutes: " + new Date(totalDuration).getMinutes());
		
		//Get the total duration time
		final int hour = totalDurationDate.getHours();
		final int minute = totalDurationDate.getMinutes();
		final int totalMinutes = Time.hoursAndMinutestToMinutes(hour, minute);
		
		//Set the total time to window title
		String totalTimeString = Time.minutesToHoursAndMunites(totalMinutes);
		this.setTitle(getString(R.string.title_split) + " - " + totalTimeString);
		
		//Set the time for TimePicker
		int halfMinutes = totalMinutes / 2;
		String halfTimeString = Time.minutesToHoursAndMunites(halfMinutes);
		int halfTimeHour = Integer.parseInt(halfTimeString.split("\\:")[0]);
		int halfTimeMinute = Integer.parseInt(halfTimeString.split("\\:")[1]);
		
		timePicker.setCurrentHour(halfTimeHour);
		timePicker.setCurrentMinute(halfTimeMinute);
		
		splitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Get the user set hours and minutes from the time picker
				timePicker.clearFocus();
				int newHour = timePicker.getCurrentHour();
				int newMinute = timePicker.getCurrentMinute();
				//Log.d("Split", "User set hours: " + newHour);
				//Log.d("Split", "User set minute: " + newMinute);
				
				int totalNewMinutes = Time.hoursAndMinutestToMinutes(newHour, newMinute);
				//Log.d("Split", "Total was: " + totalMinutes);
				
				if (totalNewMinutes > totalMinutes) {
					newHour = hour;
					newMinute = minute;
				}
				//Log.d("Split", "Total new: " + totalNewMinutes);
				
				//Date userSetDuration = new Date(60*1000*newMinute + 60*60*1000*newHour);
				long userSetDuration = 60*1000*newMinute + 60*60*1000*newHour;
				//Log.d("Split", "User set duration: " + userSetDuration);
				
				long firstActivityDurationLong = lastActivityFinishTime.getTime() + userSetDuration; 
				//Log.d("Split", "firstActivityDurationLong " + firstActivityDurationLong);
				Date firstActivityDuration = new Date(firstActivityDurationLong);
				//Log.d("Split", "userSetDuration " + userSetDuration);
				
				Editable firstActivityEdited=(Editable)firstActivity.getText(); 
				Editable secondActivityEdited=(Editable)secondActivity.getText(); 
				String firstNewName = firstActivityEdited.toString();
				String secondNewName = secondActivityEdited.toString();
				//Log.d("Split", "First activity duration" + firstActivityDuration.toString());
				ActivityManager.addActivity(firstNewName, firstActivityDuration);
				ActivityManager.addActivity(secondNewName);
				finish();
			}
		});	
		
	}
	
}
