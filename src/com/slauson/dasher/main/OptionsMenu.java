package com.slauson.dasher.main;

import com.slauson.dasher.R;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.Configuration;
import com.slauson.dasher.status.GlobalStatistics;
import com.slauson.dasher.status.HighScores;
import com.slauson.dasher.status.Points;
import com.slauson.dasher.status.Upgrades;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class OptionsMenu extends PreferenceActivity {
	
	// dialog box constants
	private final static int DIALOG_CONFIRM_RESET_DATA = 0;
	
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor sharedPreferencesEditor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		sharedPreferencesEditor = sharedPreferences.edit();

		
		//add the prefernces.xml layout
		addPreferencesFromResource(R.xml.configuration);
		
		setContentView(R.layout.options_menu);
		
		// reset data button
		Button resetData = (Button)findViewById(R.id.optionsMenuResetDataButton);
		
		resetData.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DIALOG_CONFIRM_RESET_DATA);
			}
		});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			
		Configuration.load(preferences);
	}
	
	@Override
	public Dialog onCreateDialog(int id) {

		Dialog dialog = null;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		switch(id) {
		case DIALOG_CONFIRM_RESET_DATA:
			
			alertDialogBuilder
			.setTitle("Confirm Data Reset")
			.setMessage("Are you sure you want to reset all data (statistics, points, upgrades, high scores, and achievements)?")
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// do nothing
					removeDialog(DIALOG_CONFIRM_RESET_DATA);
				}
			})
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					
					// reset data
					GlobalStatistics.reset(sharedPreferencesEditor);
					Points.reset(sharedPreferencesEditor);
					Upgrades.reset(sharedPreferencesEditor);
					HighScores.reset(sharedPreferencesEditor);
					Achievements.reset(sharedPreferencesEditor);
					
					// commit reset
		    		sharedPreferencesEditor.commit();
		    		
		    		// display notification
					Toast.makeText(OptionsMenu.this, "Data reset", Toast.LENGTH_SHORT).show();
					
					// remove dialog
					removeDialog(DIALOG_CONFIRM_RESET_DATA);
				}
			});
			dialog = alertDialogBuilder.create();
			break;
		}
		
		return dialog;
	}
}