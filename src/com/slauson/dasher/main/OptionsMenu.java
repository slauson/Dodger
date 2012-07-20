package com.slauson.dasher.main;

import com.slauson.dasher.R;
import com.slauson.dasher.status.Configuration;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OptionsMenu extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//add the prefernces.xml layout
		addPreferencesFromResource(R.xml.configuration);
		
		setContentView(R.layout.options_menu);
		
		// reset data button
		Button resetData = (Button)findViewById(R.id.optionsMenuResetDataButton);
		
		resetData.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO
			}
		});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			
		Configuration.load(preferences);
	}
}