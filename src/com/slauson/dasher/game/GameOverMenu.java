package com.slauson.dasher.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.slauson.dasher.R;
import com.slauson.dasher.main.HighScoresMenu;
import com.slauson.dasher.main.LocalAchievementsMenu;
import com.slauson.dasher.main.MainMenu;
import com.slauson.dasher.main.PointDetailsMenu;
import com.slauson.dasher.main.StatisticsMenu;
import com.slauson.dasher.main.UpgradesMenu;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.LocalStatistics;

public class GameOverMenu extends Activity {
	
	// buttons
	private Button gameOverMenuRetryButton, gameOverMenuStatisticsButton, gameOverMenuUpgradesButton, gameOverMenuQuitButton;
	
	// text views
	private TextView gameOverSummaryTime, gameOverSummaryPoints, gameOverSummaryAchievements;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over_menu);

		setupButtons();
	}
	
	private void setupButtons() {
		
		// survival time
		gameOverSummaryTime = (TextView)findViewById(R.id.gameOverSummaryTime);
		gameOverSummaryTime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, HighScoresMenu.class);
				startActivity(intent);
			}
		});
		gameOverSummaryTime.setText(LocalStatistics.timePlayed + "");
		
		// points earned
		gameOverSummaryPoints = (TextView)findViewById(R.id.gameOverSummaryPoints);
		gameOverSummaryPoints.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, PointDetailsMenu.class);
				startActivity(intent);
			}
		});
		
		// calculate points
		int points = LocalStatistics.getPoints();
		// TODO: add achievement points
		
		gameOverSummaryPoints.setText(points + " points earned");
		
		// achievements unlocked
		gameOverSummaryAchievements = (TextView)findViewById(R.id.gameOverSummaryAchievements);
		gameOverSummaryAchievements.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, LocalAchievementsMenu.class);
				startActivity(intent);
			}
		});
		
		// calculate number of achievements unlocked
		int numAchievements = Achievements.localAchievements.size();
		
		gameOverSummaryAchievements.setText(numAchievements + " achievements unlocked");
		
		// retry button
		gameOverMenuRetryButton = (Button)findViewById(R.id.gameOverMenuRetryButton);
		gameOverMenuRetryButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, MyGameActivity.class);
				startActivity(intent);
			}
		});
		
		// statistics button
		gameOverMenuStatisticsButton = (Button)findViewById(R.id.gameOverMenuStatisticsButton);
		gameOverMenuStatisticsButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, StatisticsMenu.class);
				startActivity(intent);
			}
		});
		
		// upgrades button
		gameOverMenuUpgradesButton = (Button)findViewById(R.id.gameOverMenuUpgradesButton);
		gameOverMenuUpgradesButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, UpgradesMenu.class);
				startActivity(intent);
			}
		});

		// quit button
		gameOverMenuQuitButton = (Button)findViewById(R.id.gameOverMenuQuitButton);
		gameOverMenuQuitButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, MainMenu.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		
		// TODO: do something better 
		// override back button so that user cannot go back to game
		switch(keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		
		// let other buttons go through
		super.onKeyUp(keyCode, event);
		return true;
	}

}