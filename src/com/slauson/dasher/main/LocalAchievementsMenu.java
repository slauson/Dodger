package com.slauson.dasher.main;

import com.slauson.dasher.status.Achievement;
import com.slauson.dasher.status.Achievements;

import android.os.Bundle;

public class LocalAchievementsMenu extends AchievementsMenu {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	achievementsLoaded = true;
    	
    	super.onCreate(savedInstanceState);

    	// add local achievements
    	Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithWhiteHole1);
    	Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithWhiteHole2);
    	
    	for(Achievement achievement : Achievements.localAchievements) {
    		addAchievement(achievement);
    	}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
