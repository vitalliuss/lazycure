package com.github.lazycure;

import java.util.Collections;
import java.util.List;

import com.github.lazycure.activities.Activity;
import com.github.lazycure.db.DatabaseHandler;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ExportActivity extends LazyCureActivity {
	
	private Button exportButton;
	private RadioGroup fileType;
	private Context context = LazyCureApplication.getAppContext();
	private String EMPTY_STRING = "";
	private final String APP_DIRECTORY_NAME = "LazyCure";
	private final String TIME_LOGS_DIRECTORY_NAME = APP_DIRECTORY_NAME + "/TimeLogs";
	private final String TXT_EXTENSION = ".txt";
	private final String XML_EXTENSION = ".xml";
	private final String TIMELOG_EXTENSION = ".timelog";
	DatabaseHandler db = new DatabaseHandler(this);
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export);
        
        setUpViews();
    }
	
	private void setUpViews() {
		exportButton = (Button)findViewById(R.id.exportButton);
		exportButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ExportTimeLog();
				finish();
			}
		});
	}
	
	public void ExportTimeLog(){
		fileType = (RadioGroup)findViewById(R.id.fileType);
		switch (fileType.getCheckedRadioButtonId()){
			case R.id.radio0:
				Toast.makeText(this, "Not implemented yet", Toast.LENGTH_LONG).show();
	            break;
			case R.id.radio1:
				Toast.makeText(this, "Not implemented yet", Toast.LENGTH_LONG).show();
	            break;
			case R.id.radio2:
				ExportDBasPlainText();
				break;
		}
	}
	
	public void ExportDBasPlainText(){
		String activitiesList = EMPTY_STRING;
		String today = Time.GetYYYYMMDD(Time.getCurrentDate());
		String filename = today + TXT_EXTENSION;
		List<Activity> activities = db.getAllActivities();
        OutputManager.UpdateActivitiesWithStartTime(activities);
		//Reverse the activities order
        Collections.reverse(activities);
		//Print out the activities
        activitiesList = OutputManager.FormatActivitiesList(activities);
		if (Writer.WriteFile(TIME_LOGS_DIRECTORY_NAME, filename, activitiesList)){
			Toast.makeText(context, "Saved as Plain Text", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(context, "Cannot save the file", Toast.LENGTH_SHORT).show();
		}
	}

}
