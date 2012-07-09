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
import com.slauson.dasher.main.LocalStatisticsMenu;
import com.slauson.dasher.main.UpgradesMenu;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.GlobalStatistics;
import com.slauson.dasher.status.HighScores;
import com.slauson.dasher.status.LocalStatistics;
import com.slauson.dasher.status.Points;


public class GameOverMenu extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over_menu);

		setupButtons();
	}
	
	private void setupButtons() {

		// update statistics
		updateStatistics();

		// check/update achievements
		checkAchievements();
		updateAchievements();
		
		// update high scores
		updateHighScores();
		
		// calculate points
		int points = 0;
		points += Points.POINTS_TIME_PLAYED*LocalStatistics.timePlayed;
		points += Points.POINTS_ASTEROIDS_DESTROYED*LocalStatistics.getTotalNumAsteroidsDestroyed();
		points += Points.POINTS_ACHIEVEMENT*Achievements.localAchievements.size();

		// update points
		updatePoints(points);

		// calculate number of achievements
		int numAchievements = Achievements.localAchievements.size();
		
		
		// survival time
		TextView gameOverSummaryTime = (TextView)findViewById(R.id.gameOverSummaryTime);
		gameOverSummaryTime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, HighScoresMenu.class);
				startActivity(intent);
			}
		});
		gameOverSummaryTime.setText(LocalStatistics.timePlayed + "");
		
		// points earned
		TextView gameOverSummaryPoints = (TextView)findViewById(R.id.gameOverSummaryPoints);
		gameOverSummaryPoints.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, PointDetailsMenu.class);
				startActivity(intent);
			}
		});
		gameOverSummaryPoints.setText(points + " points earned");
		
		// achievements unlocked
		TextView gameOverSummaryAchievements = (TextView)findViewById(R.id.gameOverSummaryAchievements);
		gameOverSummaryAchievements.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, LocalAchievementsMenu.class);
				startActivity(intent);
			}
		});
		gameOverSummaryAchievements.setText(numAchievements + " achievements unlocked");
		
		// retry button
		Button gameOverMenuRetryButton = (Button)findViewById(R.id.gameOverMenuRetryButton);
		gameOverMenuRetryButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, MyGameActivity.class);
				startActivity(intent);
			}
		});
		
		// statistics button
		Button gameOverMenuStatisticsButton = (Button)findViewById(R.id.gameOverMenuStatisticsButton);
		gameOverMenuStatisticsButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, LocalStatisticsMenu.class);
				startActivity(intent);
			}
		});
		
		// upgrades button
		Button gameOverMenuUpgradesButton = (Button)findViewById(R.id.gameOverMenuUpgradesButton);
		gameOverMenuUpgradesButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(GameOverMenu.this, UpgradesMenu.class);
				startActivity(intent);
			}
		});

		// quit button
		Button gameOverMenuQuitButton = (Button)findViewById(R.id.gameOverMenuQuitButton);
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
	
	/**
	 * Checks for any non-in-game achievements
	 */
	private void checkAchievements() {
		// playtime
		if (LocalStatistics.timePlayed > Achievements.LOCAL_PLAYTIME_1) {
			Achievements.unlockLocalAchievement(Achievements.localPlaytime1);
		}
		if (LocalStatistics.timePlayed > Achievements.LOCAL_PLAYTIME_2) {
			Achievements.unlockLocalAchievement(Achievements.localPlaytime2);
		}
		if (LocalStatistics.timePlayed > Achievements.LOCAL_PLAYTIME_3) {
			Achievements.unlockLocalAchievement(Achievements.localPlaytime3);
		}
		
		Achievements.checkGlobalAchievements();
	}
	
	/**
	 * Updates statistics
	 */
	private void updateStatistics() {
		GlobalStatistics.update();
		GlobalStatistics.save(getPreferences(MODE_PRIVATE).edit());
	}

	/**
	 * Updates achievements
	 */
	private void updateAchievements() {
		Achievements.save(getPreferences(MODE_PRIVATE).edit());
	}
	
	/**
	 * Updates points
	 */
	private void updatePoints(int points) {
		Points.update(points);
		Points.save(getPreferences(MODE_PRIVATE).edit());
	}

	/**
	 * Updates high scores
	 */
	private void updateHighScores() {
		HighScores.update(LocalStatistics.timePlayed);
		HighScores.save(getPreferences(MODE_PRIVATE).edit());
	}
}