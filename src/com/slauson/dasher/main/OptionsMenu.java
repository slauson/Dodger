package com.slauson.dasher.main;

import com.slauson.dasher.R;
import com.slauson.dasher.status.Configuration;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class OptionsMenu extends PreferenceActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//add the prefernces.xml layout
		addPreferencesFromResource(R.xml.configuration);
		
		setContentView(R.layout.options_menu);
	}
	
	@Override
	public void onPause() {
		super.onPause();

		// do this in onPause instead of onDestroy because onDestroy gets called after the previous activity's onResume
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		Configuration.load(preferences);
	}
}