package com.slauson.asteroid_dasher.menu;

import com.slauson.asteroid_dasher.status.HighScores;
import com.slauson.asteroid_dasher.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HighScoresMenu extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.high_scores_menu);
    	
    	// populate high score scores
    	TextView entry1Score = (TextView)findViewById(R.id.highScoresMenuEntry1Score);
    	TextView entry2Score = (TextView)findViewById(R.id.highScoresMenuEntry2Score);
    	TextView entry3Score = (TextView)findViewById(R.id.highScoresMenuEntry3Score);
    	TextView entry4Score = (TextView)findViewById(R.id.highScoresMenuEntry4Score);
    	TextView entry5Score = (TextView)findViewById(R.id.highScoresMenuEntry5Score);
    	
    	entry1Score.setText("1) " + HighScores.getHighScore(1).getScoreString());
    	entry2Score.setText("2) " + HighScores.getHighScore(2).getScoreString());
    	entry3Score.setText("3) " + HighScores.getHighScore(3).getScoreString());
    	entry4Score.setText("4) " + HighScores.getHighScore(4).getScoreString());
    	entry5Score.setText("5) " + HighScores.getHighScore(5).getScoreString());
    	
    	// populate high score times
    	TextView entry1Time = (TextView)findViewById(R.id.highScoresMenuEntry1Time);
    	TextView entry2Time = (TextView)findViewById(R.id.highScoresMenuEntry2Time);
    	TextView entry3Time = (TextView)findViewById(R.id.highScoresMenuEntry3Time);
    	TextView entry4Time = (TextView)findViewById(R.id.highScoresMenuEntry4Time);
    	TextView entry5Time = (TextView)findViewById(R.id.highScoresMenuEntry5Time);
    	
    	entry1Time.setText(HighScores.getHighScore(1).getDateString());
    	entry2Time.setText(HighScores.getHighScore(2).getDateString());
    	entry3Time.setText(HighScores.getHighScore(3).getDateString());
    	entry4Time.setText(HighScores.getHighScore(4).getDateString());
    	entry5Time.setText(HighScores.getHighScore(5).getDateString());

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}