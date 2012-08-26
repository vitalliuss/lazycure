package com.github.lazycure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity {
	
	private Button backButton;
	private TextView aboutText;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        
        setUpViews();
    }
	
	private void setUpViews() {
		backButton = (Button)findViewById(R.id.backButton);
		aboutText = (TextView)findViewById(R.id.aboutText);
		
		aboutText.setText(readRawTextFile(LazyCureApplication.getAppContext(), R.raw.about));
				
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	public static String readRawTextFile(Context ctx, int resId)
    {
         InputStream inputStream = ctx.getResources().openRawResource(resId);
         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
         String line;
         StringBuilder text = new StringBuilder();
         try {
	           while (( line = reader.readLine()) != null) {
	               text.append(line);
	               text.append('\n');
             }
       } catch (IOException e) {
           return null;
       }
         return text.toString();
    }
}
