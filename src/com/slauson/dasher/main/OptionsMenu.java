package com.slauson.dasher.main;

import com.slauson.dasher.R;

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
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		System.out.println("OptionsMenu: loadConfiguration");
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			
		Configuration.load(preferences);
	}
}