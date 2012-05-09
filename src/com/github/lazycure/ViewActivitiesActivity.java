package com.github.lazycure;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.github.lazycure.activities.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
		String prefix = "> ";
		String delimiter = " - ";
		SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
		ArrayList<Activity> activities = getStuffApplication().getCurrentActivities();
		StringBuffer sb = new StringBuffer();
		for (Activity t:activities) {
			sb.append(prefix);
			sb.append(t.getName().toString());
			sb.append(delimiter);
			sb.append(ft.format(t.getStartTime()));
			sb.append("\n");
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
	
	public Date getCuttentDate(){
		Date date = new Date();
		return date;
	}
	
	private void addActivity() {
		String activityName = activityNameEditText.getText().toString();
		if (activityName.length() != 0){
			Activity a = new Activity(activityName, getCuttentDate(), null);
			getStuffApplication().addActivity(a);
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