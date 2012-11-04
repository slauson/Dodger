package com.slauson.asteroid_dasher.menu;

import com.slauson.asteroid_dasher.other.Util;
import com.slauson.asteroid_dasher.status.Achievements;
import com.slauson.asteroid_dasher.status.LocalStatistics;
import com.slauson.asteroid_dasher.status.Options;
import com.slauson.asteroid_dasher.status.Points;
import com.slauson.asteroid_dasher.status.Statistics;
import com.slauson.asteroid_dasher.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PointDetailsMenu extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.point_details_menu);
    	
    	Statistics localStatistics = LocalStatistics.getInstance();
    	
    	// calculate number of achievements
    	int numAchievements = Achievements.localAchievements.size();
    	
    	if (Options.demoVersion) {
    		numAchievements = 0;
    	}
    	
    	// add points added
    	TextView pointsAddedTime = (TextView)findViewById(R.id.pointDetailsMenuAddedPointsTime);
    	TextView pointsAddedAsteroidsDestroyed = (TextView)findViewById(R.id.pointDetailsMenuAddedPointsAsteroidsDestroyed);
    	TextView pointsAddedAchievements = (TextView)findViewById(R.id.pointDetailsMenuAddedPointsAchievements);
    	TextView pointsAddedTotal = (TextView)findViewById(R.id.pointDetailsMenuTotalPoints);
    	
    	pointsAddedTime.setText("+" + Util.getPointsString(Points.POINTS_TIME_PLAYED*localStatistics.timePlayed));
    	pointsAddedAsteroidsDestroyed.setText("+" + Util.getPointsString(Points.POINTS_ASTEROIDS_DESTROYED*localStatistics.getTotalNumAsteroidsDestroyed()));
    	pointsAddedAchievements.setText("+" + Util.getPointsString(Points.POINTS_ACHIEVEMENT*numAchievements));
    	pointsAddedTotal.setText("+" + Util.getPointsString(Points.POINTS_TIME_PLAYED*localStatistics.timePlayed + Points.POINTS_ASTEROIDS_DESTROYED*localStatistics.getTotalNumAsteroidsDestroyed() + Points.POINTS_ACHIEVEMENT*numAchievements));
    	
    	// add details
    	TextView pointsAddedTimeDescription = (TextView)findViewById(R.id.pointDetailsMenuAddedPointsTimeDescription);
    	TextView pointsAddedAsteroidsDestroyedDescription = (TextView)findViewById(R.id.pointDetailsMenuAddedPointsAsteroidsDestroyedDescription);
    	TextView pointsAddedAchievementsDescription = (TextView)findViewById(R.id.pointDetailsMenuAddedPointsAchievementsDescription);
    	
    	if (localStatistics.timePlayed == 1) {
    		pointsAddedTimeDescription.setText("(" + localStatistics.timePlayed + " second survived)");
    	} else {
    		pointsAddedTimeDescription.setText("(" + localStatistics.timePlayed + " seconds survived)");
    	}
    	
    	if (localStatistics.getTotalNumAsteroidsDestroyed() == 1) {
    		pointsAddedAsteroidsDestroyedDescription.setText("(" + localStatistics.getTotalNumAsteroidsDestroyed() + " asteroid destroyed)");
    	} else {
    		pointsAddedAsteroidsDestroyedDescription.setText("(" + localStatistics.getTotalNumAsteroidsDestroyed() + " asteroids destroyed)");
    	}
    	
    	if (numAchievements == 1) {
        	pointsAddedAchievementsDescription.setText("(" + numAchievements + " achievement unlocked)");
		} else {
	    	pointsAddedAchievementsDescription.setText("(" + numAchievements + " achievements unlocked)");
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
