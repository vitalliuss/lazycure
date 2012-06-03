package com.github.lazycure;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.github.lazycure.activities.Activity;
import com.github.lazycure.db.DatabaseHandler;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ViewActivitiesActivity extends LazyCureActivity {

	private long lastActivity = 0;
	private Button doneButton;
	private TextView activityText;
	private TextView timeLabel;
	private EditText activityNameEditText;
	DatabaseHandler db = new DatabaseHandler(this);
	private RefreshHandler mRedrawHandler = new RefreshHandler();
		class RefreshHandler extends Handler {
		    @Override
		    public void handleMessage(Message msg) {
		    	ViewActivitiesActivity.this.updateTimer(lastActivity);
		    }

		    public void sleep(long delayMillis) {
		      this.removeMessages(0);
		      sendMessageDelayed(obtainMessage(0), delayMillis);
		    }
		  };

	private void updateTimer(long baseMillis){
		long start = lastActivity;
	    long millis = System.currentTimeMillis() - start;
	    int seconds = (int) millis / 1000;
	    int minutes = seconds / 60;
	    int hours = minutes / 60;
	    seconds = seconds % 60;

	    String text = String.format("%d:%02d:%02d", hours, minutes, seconds);
	    timeLabel.setText(text);
	    mRedrawHandler.sleep(1000);
	  }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setUpViews();
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		showActivities();
	}

	private void showActivities() {
		String prefix = "> ";
		String delimiter = " - ";
		SimpleDateFormat ft = new SimpleDateFormat ("HH:mm:ss");
		ft.setTimeZone(TimeZone.getTimeZone("UTC"));
        List<Activity> activities = db.getAllActivities();
        Log.d("List: " , "Activities size: " + activities.size());
        //Update missing time records for all activities
		StringBuffer sb = new StringBuffer();
		for (int i=1;i<activities.size();i++){
			if (activities.get(i-1).getFinishTime() != null){
				activities.get(i).setStartTime(activities.get(i-1).getFinishTime());
			}
		}
		//Reverse the activities order
        Collections.reverse(activities);
        if (activities.size() != 0){
			//Set the latest activity time for timeLabel
			lastActivity = activities.get(0).getFinishTime().getTime();
			Log.d("Date: " , "lastActivity finish time is [" + activities.get(0).getFinishTime() +"]");
        }
		else{
			lastActivity = System.currentTimeMillis();
		}
        Log.d("Date: " , "lastActivity value is [" + lastActivity +"]");
		//Print out the activities
		for (Activity t:activities) {
			sb.append(prefix);
			sb.append(t.getName().toString());
			sb.append(delimiter);
			if (t.getFinishTime() != null && t.getStartTime() != null){
				Log.d("Date: " , "Activity [" + t.getName().toString() + "]:" +
						" Start time  " + ft.format(t.getStartTime()) +
						", Finish time: [" + ft.format(t.getFinishTime()));
				Date delta = new Date(t.getFinishTime().getTime() - t.getStartTime().getTime());
				Log.d("Date: " , "Inserting [" + t.getName().toString() + "] with duration: " + ft.format(delta));
				sb.append(ft.format(delta));
			}
			sb.append("\n");
		}
		activityText.setMovementMethod(new ScrollingMovementMethod());
		activityText.setText(sb.toString());
		updateTimer(lastActivity);
	}

	private void setUpViews() {
		doneButton = (Button)findViewById(R.id.done_button);
		activityText = (TextView)findViewById(R.id.activities_list_text);
		activityNameEditText = (EditText)findViewById(R.id.input);
		timeLabel = (TextView) this.findViewById(R.id.timeLabel);
		
		doneButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				addActivity();
			}
		});

		clearInput();
	}
	
	public Date getCurrentDate(){
		Date date = new Date();
		return date;
	}
	
	private void addActivity() {
		String activityName = activityNameEditText.getText().toString();
		if (activityName.length() != 0){
			Log.d("Insert: " , "Inserting [" + activityName + "] with finish date: " + getCurrentDate().toString());
	        db.addActivity(new Activity(activityName, null, getCurrentDate()));
		}
		clearInput();
		showActivities();
	}
	
	protected void clearInput() {
		activityNameEditText.setText("");
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(activityNameEditText.getWindowToken(), 0);
	}
	
}