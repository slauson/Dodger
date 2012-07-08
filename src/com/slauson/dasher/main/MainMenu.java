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
import android.view.KeyEvent;
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
	
	Button startHighScoresButton, instructionsAchievementsButton, optionsStatisticsButton, moreUpgradesButton, quitBackButton;
	
	boolean showingMore;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		showingMore = false;

		// start/high scores button
		startHighScoresButton = (Button)findViewById(R.id.mainMenuStartHighScoresButton);
		startHighScoresButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (!showingMore) {
					Intent intent = new Intent(MainMenu.this, MyGameActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(MainMenu.this, HighScoresMenu.class);
					startActivity(intent);
				}
			}
		});
		
		// instructions/achievements button
		instructionsAchievementsButton = (Button)findViewById(R.id.mainMenuInstructionsAchievementsButton);
		instructionsAchievementsButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (!showingMore) {
					Intent intent = new Intent(MainMenu.this, InstructionsMenu.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(MainMenu.this, AchievementsMenu.class);
					startActivity(intent);
				}
			}
		});

		// options/statistics button
		optionsStatisticsButton = (Button)findViewById(R.id.mainMenuOptionsStatisticsButton);
		optionsStatisticsButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (!showingMore) {
					Intent intent = new Intent(MainMenu.this, OptionsMenu.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(MainMenu.this, StatisticsMenu.class);
					startActivity(intent);
				}
			}
		});
		
		// more/upgrades button
		moreUpgradesButton = (Button)findViewById(R.id.mainMenuMoreUpgradesButton);
		moreUpgradesButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (!showingMore) {
					toggleShowMore();
				} else {
					Intent intent = new Intent(MainMenu.this, UpgradesMenu.class);
					startActivity(intent);
				}
			}
		});
				
		// quit/back button
		quitBackButton = (Button)findViewById(R.id.mainMenuQuitBackButton);
		quitBackButton.setOnClickListener(new OnClickListener() {
			
			// quit application
			// adapted from here: http://stackoverflow.com/questions/2964310/quitting-application-in-android
			public void onClick(View v) {
				if (!showingMore) {
					android.os.Process.killProcess(android.os.Process.myPid());
				} else {
					toggleShowMore();
				}
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
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		
		// TODO: do something better 
		// override back button so that user cannot go back to game
		switch(keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// go back to "main" main menu
			if (showingMore) {
				toggleShowMore();
			}
			return true;
		}
		
		// let other buttons go through
		super.onKeyUp(keyCode, event);
		return true;
	}

	private void loadConfiguration() {
		System.out.println("loadConfiguration");
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		Configuration.load(preferences);
	}
	
	private void toggleShowMore() {
		if (showingMore) {
			
			startHighScoresButton.setText(R.string.menu_start);
			instructionsAchievementsButton.setText(R.string.menu_instructions);
			optionsStatisticsButton.setText(R.string.menu_options);
			moreUpgradesButton.setText(R.string.menu_more);
			quitBackButton.setText(R.string.menu_quit);
			
			showingMore = false;
		} else {
			
			startHighScoresButton.setText(R.string.menu_high_scores);
			instructionsAchievementsButton.setText(R.string.menu_achievements);
			optionsStatisticsButton.setText(R.string.menu_statistics);
			moreUpgradesButton.setText(R.string.menu_upgrades);
			quitBackButton.setText(R.string.menu_back);
			
			showingMore = true;
		}
	}
}