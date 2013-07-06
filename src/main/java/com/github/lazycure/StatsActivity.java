package main.java.com.github.lazycure;

import java.util.List;

import main.java.com.github.lazycure.activities.Activity;
import main.java.com.github.lazycure.db.DatabaseHandler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatsActivity extends LazyCureActivity {

	private Button backButton;
	private TextView list;
	
	private DatabaseHandler db = new DatabaseHandler(this);

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        
        setUpViews();
        publishTopActivities();
    }
	
	private void publishTopActivities() {
		list = (TextView)findViewById(R.id.stats_list);
		List<Activity> activities = db.getTopActivities(10);
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<activities.size(); i++) {
			sb.append(String.valueOf(i+1));
			sb.append(". ");
			sb.append(activities.get(i).getName());
			sb.append("\n");
		}
		list.setText(sb.toString());
		
	}

	private void setUpViews() {
		backButton = (Button)findViewById(R.id.stats_backButton);
		
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
}
