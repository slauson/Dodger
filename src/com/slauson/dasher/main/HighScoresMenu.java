package com.slauson.dasher.main;

import com.slauson.dasher.R;
import com.slauson.dasher.status.HighScores;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HighScoresMenu extends Activity {
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.high_scores_menu);
    	
    	// debugging
    	HighScores.highScore1.setScore(200);
    	HighScores.highScore2.setScore(175);
    	HighScores.highScore3.setScore(150);
    	HighScores.highScore4.setScore(100);
    	HighScores.highScore5.setScore(50);
    	
    	// populate high score scores
    	TextView entry1Score = (TextView)findViewById(R.id.highScoresMenuEntry1Score);
    	TextView entry2Score = (TextView)findViewById(R.id.highScoresMenuEntry2Score);
    	TextView entry3Score = (TextView)findViewById(R.id.highScoresMenuEntry3Score);
    	TextView entry4Score = (TextView)findViewById(R.id.highScoresMenuEntry4Score);
    	TextView entry5Score = (TextView)findViewById(R.id.highScoresMenuEntry5Score);
    	
    	entry1Score.setText("1) " + HighScores.highScore1.getScoreString());
    	entry2Score.setText("2) " + HighScores.highScore2.getScoreString());
    	entry3Score.setText("3) " + HighScores.highScore3.getScoreString());
    	entry4Score.setText("4) " + HighScores.highScore4.getScoreString());
    	entry5Score.setText("5) " + HighScores.highScore5.getScoreString());
    	
    	// populate high score times
    	TextView entry1Time = (TextView)findViewById(R.id.highScoresMenuEntry1Time);
    	TextView entry2Time = (TextView)findViewById(R.id.highScoresMenuEntry2Time);
    	TextView entry3Time = (TextView)findViewById(R.id.highScoresMenuEntry3Time);
    	TextView entry4Time = (TextView)findViewById(R.id.highScoresMenuEntry4Time);
    	TextView entry5Time = (TextView)findViewById(R.id.highScoresMenuEntry5Time);
    	
    	entry1Time.setText(HighScores.highScore1.getTimeString());
    	entry2Time.setText(HighScores.highScore2.getTimeString());
    	entry3Time.setText(HighScores.highScore3.getTimeString());
    	entry4Time.setText(HighScores.highScore4.getTimeString());
    	entry5Time.setText(HighScores.highScore5.getTimeString());

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}