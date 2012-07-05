package com.slauson.dasher.main;

import com.slauson.dasher.R;
import com.slauson.dasher.status.Configuration;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Instructions extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.instructions);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}