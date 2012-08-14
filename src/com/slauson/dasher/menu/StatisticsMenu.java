package com.slauson.dasher.menu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.slauson.dasher.R;
import com.slauson.dasher.other.Util;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.GlobalStatistics;
import com.slauson.dasher.status.Statistics;
import com.slauson.dasher.status.Upgrades;

public class StatisticsMenu extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.statistics_menu);
    	
    	Statistics globalStatistics = GlobalStatistics.getInstance();
    	
    	// add uses statistics
    	TextView usesDash = (TextView)findViewById(R.id.statisticsMenuDashUses);
    	TextView usesSmall = (TextView)findViewById(R.id.statisticsMenuSmallUses);
    	TextView usesSlow = (TextView)findViewById(R.id.statisticsMenuSlowUses);
    	TextView usesInvulnerability = (TextView)findViewById(R.id.statisticsMenuInvulnerabilityUses);
    	TextView usesDrill = (TextView)findViewById(R.id.statisticsMenuDrillUses);
    	TextView usesMagnet = (TextView)findViewById(R.id.statisticsMenuMagnetUses);
    	TextView usesBlackHole = (TextView)findViewById(R.id.statisticsMenuBlackHoleUses);
    	TextView usesBumper = (TextView)findViewById(R.id.statisticsMenuBumperUses);
    	TextView usesBomb = (TextView)findViewById(R.id.statisticsMenuBombUses);
    	TextView usesTotal = (TextView)findViewById(R.id.statisticsMenuTotalUses);
		
    	usesDash.setText("" + globalStatistics.usesDash);
    	usesSmall.setText("" + globalStatistics.usesSmall);
    	usesSlow.setText("" + globalStatistics.usesSlow);
    	usesInvulnerability.setText("" + globalStatistics.usesInvulnerability);
    	usesDrill.setText("" + globalStatistics.usesDrill);
    	usesMagnet.setText("" + globalStatistics.usesMagnet);
    	usesBlackHole.setText("" + globalStatistics.usesBlackHole);
    	usesBumper.setText("" + globalStatistics.usesBumper);
    	usesBomb.setText("" + globalStatistics.usesBomb);
    	
    	// total
		usesTotal.setText("" + globalStatistics.getTotalUses());
		
    	// add asteroids destroyed statistics
    	TextView asteroidsDestroyedDash = (TextView)findViewById(R.id.statisticsMenuDashAsteroidsDestroyed);
    	TextView asteroidsDestroyedSmall = (TextView)findViewById(R.id.statisticsMenuSmallAsteroidsDestroyed);
    	TextView asteroidsDestroyedSlow = (TextView)findViewById(R.id.statisticsMenuSlowAsteroidsDestroyed);
    	TextView asteroidsDestroyedInvulnerability = (TextView)findViewById(R.id.statisticsMenuInvulnerabilityAsteroidsDestroyed);
    	TextView asteroidsDestroyedDrill = (TextView)findViewById(R.id.statisticsMenuDrillAsteroidsDestroyed);
    	TextView asteroidsDestroyedMagnet = (TextView)findViewById(R.id.statisticsMenuMagnetAsteroidsDestroyed);
    	TextView asteroidsDestroyedBlackHole = (TextView)findViewById(R.id.statisticsMenuBlackHoleAsteroidsDestroyed);
    	TextView asteroidsDestroyedBumper = (TextView)findViewById(R.id.statisticsMenuBumperAsteroidsDestroyed);
    	TextView asteroidsDestroyedBomb = (TextView)findViewById(R.id.statisticsMenuBombAsteroidsDestroyed);
		TextView asteroidsDestroyedTotal = (TextView)findViewById(R.id.statisticsMenuTotalAsteroidsDestroyed);
   	
    	// these are always 0
    	asteroidsDestroyedSmall.setText("0");
    	asteroidsDestroyedSlow.setText("0");
    	asteroidsDestroyedInvulnerability.setText("0");
    	
    	asteroidsDestroyedDash.setText("" + globalStatistics.asteroidsDestroyedByDash);
    	asteroidsDestroyedDrill.setText("" + globalStatistics.asteroidsDestroyedByDrill);
    	asteroidsDestroyedMagnet.setText("" + globalStatistics.asteroidsDestroyedByMagnet);
    	asteroidsDestroyedBlackHole.setText("" + globalStatistics.asteroidsDestroyedByBlackHole);
    	asteroidsDestroyedBumper.setText("" + globalStatistics.asteroidsDestroyedByBumper);
    	asteroidsDestroyedBomb.setText("" + globalStatistics.asteroidsDestroyedByBomb);

		// total
		asteroidsDestroyedTotal.setText("" + globalStatistics.getTotalNumAsteroidsDestroyed());

    	// add time played
    	TextView timePlayed = (TextView)findViewById(R.id.statisticsMenuTimePlayed);
    	timePlayed.setText(globalStatistics.getTimePlayedString(true));
    	
    	// add average time played
    	TextView averageTimePerPlay = (TextView)findViewById(R.id.statisticsMenuAverageTimePlayed);
    	averageTimePerPlay.setText(GlobalStatistics.getAverageTimePerPlayString());
    	
    	// add # of times played
    	TextView timesPlayed = (TextView)findViewById(R.id.statisticsMenuTimesPlayed);
    	timesPlayed.setText("" + globalStatistics.timesPlayed);
    	
    	// add total points earned
    	TextView pointsEarned = (TextView)findViewById(R.id.statisticsMenuPointsEarned);
    	pointsEarned.setText("" + Util.getPointsString(globalStatistics.pointsEarned));

    	// add total points spent
    	TextView pointsSpent = (TextView)findViewById(R.id.statisticsMenuPointsSpent);
    	pointsSpent.setText("" + Util.getPointsString(globalStatistics.pointsSpent));

    	// completion percentage
    	float completion = 0.5f*Upgrades.completionPercentage() + 0.5f*Achievements.completionPercentage();
    	TextView completionPercentage = (TextView)findViewById(R.id.statisticsMenuCompletionPercentage);
    	completionPercentage.setText("" + ((int)(completion*100)) + " %");
    	
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}