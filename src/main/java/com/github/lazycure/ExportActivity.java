package main.java.com.github.lazycure;

import java.util.Collections;
import java.util.List;

import main.java.com.github.lazycure.activities.Activity;
import main.java.com.github.lazycure.db.DatabaseHandler;

import main.java.com.github.lazycure.R;

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
	private final String XLS_EXTENSION = ".xls";
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
				exportTimeLog();
				finish();
			}
		});
	}
	
	public void exportTimeLog(){
		fileType = (RadioGroup)findViewById(R.id.fileType);
		switch (fileType.getCheckedRadioButtonId()){
			case R.id.radio0:
				exportTimeLogAsXLS();
	            break;
			case R.id.radio1:
				exportDBasPlainText();
				break;
		}
	}
	
	public void exportDBasPlainText(){
		String activitiesList = EMPTY_STRING;
		String today = Time.getYYYYMMDD(Time.getCurrentDate());
		String filename = today + TXT_EXTENSION;
		List<Activity> activities = db.getAllActivities();
        OutputManager.updateActivitiesWithStartTime(activities);
		//Reverse the activities order
        Collections.reverse(activities);
		//Print out the activities
        activitiesList = OutputManager.formatActivitiesList(activities);
		if (Writer.writeFile(TIME_LOGS_DIRECTORY_NAME, filename, activitiesList)){
			Toast.makeText(context, "Saved as Plain Text", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(context, "Cannot save the file", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void exportTimeLogAsXLS() {
		String today = Time.getYYYYMMDD(Time.getCurrentDate());
		String filename = today + XLS_EXTENSION;
		List<Activity> activities = db.getAllActivities();
		OutputManager.updateActivitiesWithStartTime(activities);
		//Reverse the activities order
        Collections.reverse(activities);
        if (Writer.writeActivitiesInXLS(TIME_LOGS_DIRECTORY_NAME, filename, activities)){
			Toast.makeText(context, "Saved as XLS file", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(context, "Cannot save the file", Toast.LENGTH_SHORT).show();
		}
		Writer.writeActivitiesInXLS(TIME_LOGS_DIRECTORY_NAME, filename, activities);
	}

}
