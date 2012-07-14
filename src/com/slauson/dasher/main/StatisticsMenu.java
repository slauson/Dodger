package com.slauson.dasher.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.slauson.dasher.R;
import com.slauson.dasher.status.GlobalStatistics;
import com.slauson.dasher.status.Statistics;

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
    	TextView usesWhiteHole = (TextView)findViewById(R.id.statisticsMenuWhiteHoleUses);
    	TextView usesBumper = (TextView)findViewById(R.id.statisticsMenuBumperUses);
    	TextView usesBomb = (TextView)findViewById(R.id.statisticsMenuBombUses);
    	
    	usesDash.setText("" + globalStatistics.usesDash);
    	usesSmall.setText("" + globalStatistics.usesSmall);
    	usesSlow.setText("" + globalStatistics.usesSlow);
    	usesInvulnerability.setText("" + globalStatistics.usesInvulnerability);
    	usesDrill.setText("" + globalStatistics.usesDrill);
    	usesMagnet.setText("" + globalStatistics.usesMagnet);
    	usesWhiteHole.setText("" + globalStatistics.usesWhiteHole);
    	usesBumper.setText("" + globalStatistics.usesBumper);
    	usesBomb.setText("" + globalStatistics.usesBomb);
    	
    	// add asteroids destroyed statistics
    	TextView asteroidsDestroyedDash = (TextView)findViewById(R.id.statisticsMenuDashAsteroidsDestroyed);
    	TextView asteroidsDestroyedSmall = (TextView)findViewById(R.id.statisticsMenuSmallAsteroidsDestroyed);
    	TextView asteroidsDestroyedSlow = (TextView)findViewById(R.id.statisticsMenuSlowAsteroidsDestroyed);
    	TextView asteroidsDestroyedInvulnerability = (TextView)findViewById(R.id.statisticsMenuInvulnerabilityAsteroidsDestroyed);
    	TextView asteroidsDestroyedDrill = (TextView)findViewById(R.id.statisticsMenuDrillAsteroidsDestroyed);
    	TextView asteroidsDestroyedMagnet = (TextView)findViewById(R.id.statisticsMenuMagnetAsteroidsDestroyed);
    	TextView asteroidsDestroyedWhiteHole = (TextView)findViewById(R.id.statisticsMenuWhiteHoleAsteroidsDestroyed);
    	TextView asteroidsDestroyedBumper = (TextView)findViewById(R.id.statisticsMenuBumperAsteroidsDestroyed);
    	TextView asteroidsDestroyedBomb = (TextView)findViewById(R.id.statisticsMenuBombAsteroidsDestroyed);
    	
    	// these are always 0
    	asteroidsDestroyedSmall.setText("0");
    	asteroidsDestroyedSlow.setText("0");
    	asteroidsDestroyedInvulnerability.setText("0");
    	
    	asteroidsDestroyedDash.setText("" + globalStatistics.asteroidsDestroyedByDash);
    	asteroidsDestroyedDrill.setText("" + globalStatistics.asteroidsDestroyedByDrill);
    	asteroidsDestroyedMagnet.setText("" + globalStatistics.asteroidsDestroyedByMagnet);
    	asteroidsDestroyedWhiteHole.setText("" + globalStatistics.asteroidsDestroyedByWhiteHole);
    	asteroidsDestroyedBumper.setText("" + globalStatistics.asteroidsDestroyedByBumper);
    	asteroidsDestroyedBomb.setText("" + globalStatistics.asteroidsDestroyedByBomb);

    	// add time played
    	TextView timePlayed = (TextView)findViewById(R.id.statisticsMenuTimePlayed);
    	timePlayed.setText(globalStatistics.getTimePlayedString());
    	
    	// add average time played
    	TextView averageTimePerPlay = (TextView)findViewById(R.id.statisticsMenuAverageTimePlayed);
    	averageTimePerPlay.setText(GlobalStatistics.getAverageTimePerPlayString());
    	
    	// add # of times played
    	TextView timesPlayed = (TextView)findViewById(R.id.statisticsMenuTimesPlayed);
    	timesPlayed.setText("" + globalStatistics.timesPlayed);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
