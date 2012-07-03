package com.slauson.dasher.main;

import com.slauson.dasher.R;
import com.slauson.dasher.game.MyGameActivity;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.Configuration;
import com.slauson.dasher.status.GlobalStatistics;
import com.slauson.dasher.status.HighScores;
import com.slauson.dasher.status.Upgrades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Main menu
 * 
 * adapted from here: http://www.droidnova.com/creating-game-menus-in-android,518.html
 * 
 * @author Josh Slauson
 *
 */
public class MainMenu extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		// start button
		Button startButton = (Button)findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent StartGameIntent = new Intent(MainMenu.this, MyGameActivity.class);
				startActivity(StartGameIntent);
			}
		});
		
		// how to play button
		Button instructionsButton = (Button)findViewById(R.id.instructionsButton);
		instructionsButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent StartGameIntent = new Intent(MainMenu.this, Instructions.class);
				startActivity(StartGameIntent);
			}
		});

		// options button
		Button optionsButton = (Button)findViewById(R.id.optionsButton);
		optionsButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent StartGameIntent = new Intent(MainMenu.this, OptionsMenu.class);
				startActivity(StartGameIntent);
			}
		});
		
		// load configuration
		loadConfiguration();
		
		// load saved state
		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
		
		// load achievements
		Achievements.load(preferences);
		
		// load upgrades
		Upgrades.load(preferences);
		
		// load stats
		GlobalStatistics.load(preferences);
		
		// load high scores
		HighScores.load(preferences);
	}

	private void loadConfiguration() {
		System.out.println("loadConfiguration");
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		Configuration.load(preferences);
	}
}