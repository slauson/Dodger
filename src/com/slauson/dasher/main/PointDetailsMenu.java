package com.slauson.dasher.main;

import com.slauson.dasher.R;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.LocalStatistics;
import com.slauson.dasher.status.Points;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PointDetailsMenu extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.point_details_menu);
    	
    	// add points added
    	TextView pointsAddedTime = (TextView)findViewById(R.id.pointDetailsMenuAddedPointsTime);
    	TextView pointsAddedAsteroidsDestroyed = (TextView)findViewById(R.id.pointDetailsMenuAddedPointsAsteroidsDestroyed);
    	TextView pointsAddedAchievements = (TextView)findViewById(R.id.pointDetailsMenuAddedPointsAchievements);
    	TextView pointsAddedTotal = (TextView)findViewById(R.id.pointDetailsMenuTotalPoints);
    	
    	pointsAddedTime.setText("+" + Points.POINTS_TIME_PLAYED*LocalStatistics.timePlayed);
    	pointsAddedAsteroidsDestroyed.setText("+" + Points.POINTS_ASTEROIDS_DESTROYED*LocalStatistics.getTotalNumAsteroidsDestroyed());
    	pointsAddedAchievements.setText("+" + Points.POINTS_ACHIEVEMENT*Achievements.localAchievements.size());
    	pointsAddedTotal.setText("+" + (Points.POINTS_TIME_PLAYED*LocalStatistics.timePlayed + Points.POINTS_ASTEROIDS_DESTROYED*LocalStatistics.getTotalNumAsteroidsDestroyed() + Points.POINTS_ACHIEVEMENT*Achievements.localAchievements.size()));
    	
    	// add details
    	TextView pointsAddedTimeDescription = (TextView)findViewById(R.id.pointDetailsMenuAddedPointsTimeDescription);
    	TextView pointsAddedAsteroidsDestroyedDescription = (TextView)findViewById(R.id.pointDetailsMenuAddedPointsAsteroidsDestroyedDescription);
    	TextView pointsAddedAchievementsDescription = (TextView)findViewById(R.id.pointDetailsMenuAddedPointsAchievementsDescription);
    	
    	pointsAddedTimeDescription.setText("(" + LocalStatistics.timePlayed + " seconds survived)");
    	pointsAddedAsteroidsDestroyedDescription.setText("(" + LocalStatistics.getTotalNumAsteroidsDestroyed() + " asteroids destroyed)");
    	pointsAddedAchievementsDescription.setText("(" + Achievements.localAchievements.size() + " achievements unlocked)");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
