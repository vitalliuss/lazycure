package com.github.lazycure;

import java.util.ArrayList;

import com.github.lazycure.activities.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ViewActivitiesActivity extends LazyCureActivity {

	private Button doneButton;
	private TextView activityText;
	private EditText activityNameEditText;
	private Button cancelButton;

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
		ArrayList<Activity> activities = getStuffApplication().getCurrentActivities();
		StringBuffer sb = new StringBuffer();
		for (Activity t:activities) {
			sb.append(String.format("* %s\n", t.toString()));
		}
		activityText.setText(sb.toString());
	}

	private void setUpViews() {
		doneButton = (Button)findViewById(R.id.done_button);
		activityText = (TextView)findViewById(R.id.activities_list_text);
		activityNameEditText = (EditText)findViewById(R.id.input);
		cancelButton = (Button)findViewById(R.id.cancel_button);
		
		doneButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				addActivity();
			}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				clearInput();
			}
		});
	}
	
	private void addActivity() {
		String activityName = activityNameEditText.getText().toString();
		if (activityName.length() != 0){
			Activity a = new Activity(activityName);
			getStuffApplication().addActivity(a);
		}
		clearInput();
		showActivities();
	}
	
	protected void clearInput() {
		activityNameEditText.setText("");
	}
	
}