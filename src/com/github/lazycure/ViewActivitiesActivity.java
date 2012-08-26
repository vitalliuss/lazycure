package com.github.lazycure;

import java.util.Collections;
import java.util.List;

import com.github.lazycure.activities.Activity;
import com.github.lazycure.activities.ActivityManager;
import com.github.lazycure.db.DatabaseHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
	    long duration = System.currentTimeMillis() - start;
	    String text = Time.millisToShortDHMS(duration);
	    timeLabel.setText(text);
	    mRedrawHandler.sleep(Time.ONE_SECOND);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.export:
	        	Intent export = new Intent();
	        	export.setClass(LazyCureApplication.getAppContext(), ExportActivity.class);
	        	startActivity(export);
	        	break;
	        case R.id.settings:
	        	Toast.makeText(this, "Not implemented yet", Toast.LENGTH_LONG).show();
	            break;
	        case R.id.about:
	        	Intent about = new Intent();
	        	about.setClass(LazyCureApplication.getAppContext(), AboutActivity.class);
	        	startActivity(about);
	        	break;
	    }
	    return true;
	}

	private void showActivities() {
		if (ActivityManager.activityListIsEmpty()){
			ActivityManager.createFirstTestActivity();
		}
        List<Activity> activities = db.getAllActivities();
        OutputManager.UpdateActivitiesWithStartTime(activities);
		//Reverse the activities order
        Collections.reverse(activities);
		//Print out the activities
        String activitiesList = OutputManager.FormatActivitiesList(activities);
		activityText.setMovementMethod(new ScrollingMovementMethod());
		activityText.setText(activitiesList);
		if (activities.size() != 0){
			//Set the latest activity time for timeLabel
			lastActivity = activities.get(0).getFinishTime().getTime();
        }
		else{
			lastActivity = System.currentTimeMillis();
		}
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
	
	private void addActivity() {
		String activityName = activityNameEditText.getText().toString();
		ActivityManager.addActivity(activityName);
		clearInput();
		showActivities();
	}
	
	protected void clearInput() {
		activityNameEditText.setText("");
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(activityNameEditText.getWindowToken(), 0);
	}
}