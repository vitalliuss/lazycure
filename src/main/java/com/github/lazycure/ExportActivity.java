package main.java.com.github.lazycure;

import java.util.Collections;
import java.util.List;

import main.java.com.github.lazycure.activities.Activity;
import main.java.com.github.lazycure.db.DatabaseHandler;
import main.java.com.github.lazycure.ui.ActivitiesTableManager;

import main.java.com.github.lazycure.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ExportActivity extends LazyCureActivity {
	
	private Button exportButton;
	private RadioGroup fileType;
	private TextView exportLocation;
	private Context context = LazyCureApplication.getAppContext();
	private String EMPTY_STRING = "";
	private final String APP_DIRECTORY_NAME = "LazyCure";
	private final String TIME_LOGS_DIRECTORY_NAME = APP_DIRECTORY_NAME + "/TimeLogs";
	private final String TXT_EXTENSION = ".txt";
	private final String XLS_EXTENSION = ".xls";
	private final String TIMELOG_EXTENSION = ".timelog";
	private String EXPORT_LOCATION = TIME_LOGS_DIRECTORY_NAME;
	
	DatabaseHandler db = new DatabaseHandler(this);
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export);
        
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        EXPORT_LOCATION = sharedPrefs.getString("export_location", TIME_LOGS_DIRECTORY_NAME);

        setUpViews();
    }
	
	private List<Activity> getActivitiesForPrint() {
		ActivitiesTableManager activitiesTableManager = new ActivitiesTableManager(this);
		List<Activity> activities = db.getAllActivities();
		activitiesTableManager.removeTestActivity(activities);
        OutputManager.updateActivitiesWithStartTime(activities);
		//Reverse the activities order
        Collections.reverse(activities);
        //Remove test activity
        activitiesTableManager.removeTestActivity(activities);
		return activities;
	}
	
	private void setUpViews() {
		exportButton = (Button)findViewById(R.id.exportButton);
		exportLocation = (TextView)findViewById(R.id.exportLocation);
		
		String hint = getResources().getString(R.string.export_LocationHint);
		exportLocation.setText(EXPORT_LOCATION);
		
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
			case R.id.radio2:
				exportTimeLogAsTimelog();
				break;
		}
	}
	
	public void exportDBasPlainText(){
		String activitiesList = EMPTY_STRING;
		String today = Time.getYYYYMMDD(Time.getCurrentDate());
		String filename = today + TXT_EXTENSION;
		List<Activity> activities = getActivitiesForPrint();
        activitiesList = OutputManager.formatActivitiesList(activities);
		if (Writer.writeFile(EXPORT_LOCATION, filename, activitiesList)){
			Toast.makeText(context, Strings.EXPORTED_AS_PLAINT_TEXT+ " " + EXPORT_LOCATION, Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(context, Strings.EXPORT_FAILED + " " + EXPORT_LOCATION, Toast.LENGTH_SHORT).show();
		}
	}
	
	public void exportTimeLogAsXLS() {
		String today = Time.getYYYYMMDD(Time.getCurrentDate());
		String filename = today + XLS_EXTENSION;
		List<Activity> activities = getActivitiesForPrint();
        if (Writer.writeActivitiesInXLS(EXPORT_LOCATION, filename, activities)){
			Toast.makeText(context, Strings.EXPORTED_AS_XLS+ " " + EXPORT_LOCATION, Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(context, Strings.EXPORT_FAILED+ " " + EXPORT_LOCATION, Toast.LENGTH_SHORT).show();
		}
	}
	
	public void exportTimeLogAsTimelog() {
		String today = Time.getYYYYMMDD(Time.getCurrentDate());
		String filename = today + TIMELOG_EXTENSION;
		List<Activity> activities = getActivitiesForPrint();
        if (Writer.writeActivitiesInTimeLog(EXPORT_LOCATION, filename, activities)){
			Toast.makeText(context, Strings.EXPORTED_AS_TIMELOG+ " " + EXPORT_LOCATION, Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(context, Strings.EXPORT_FAILED+ " " + EXPORT_LOCATION, Toast.LENGTH_SHORT).show();
		}
	}

}
