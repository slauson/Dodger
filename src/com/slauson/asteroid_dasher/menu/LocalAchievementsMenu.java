package com.slauson.asteroid_dasher.menu;

import com.slauson.asteroid_dasher.status.Achievement;
import com.slauson.asteroid_dasher.status.Achievements;

import android.os.Bundle;

public class LocalAchievementsMenu extends AchievementsMenu {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	achievementsLoaded = true;
    	
    	super.onCreate(savedInstanceState);

    	// add local achievements
    	for(Achievement achievement : Achievements.localAchievements) {
    		addAchievement(achievement);
    	}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
