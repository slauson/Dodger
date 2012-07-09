package com.slauson.dasher.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.slauson.dasher.R;
import com.slauson.dasher.status.GlobalStatistics;

public class StatisticsMenu extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.statistics_menu);
    	
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
    	
    	usesDash.setText("" + GlobalStatistics.usesDash);
    	usesSmall.setText("" + GlobalStatistics.usesSmall);
    	usesSlow.setText("" + GlobalStatistics.usesSlow);
    	usesInvulnerability.setText("" + GlobalStatistics.usesInvulnerability);
    	usesDrill.setText("" + GlobalStatistics.usesDrill);
    	usesMagnet.setText("" + GlobalStatistics.usesMagnet);
    	usesWhiteHole.setText("" + GlobalStatistics.usesWhiteHole);
    	usesBumper.setText("" + GlobalStatistics.usesBumper);
    	usesBomb.setText("" + GlobalStatistics.usesBomb);
    	
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
    	
    	asteroidsDestroyedDash.setText("" + GlobalStatistics.asteroidsDestroyedByDash);
    	asteroidsDestroyedDrill.setText("" + GlobalStatistics.asteroidsDestroyedByDrill);
    	asteroidsDestroyedMagnet.setText("" + GlobalStatistics.asteroidsDestroyedByMagnet);
    	asteroidsDestroyedWhiteHole.setText("" + GlobalStatistics.asteroidsDestroyedByWhiteHole);
    	asteroidsDestroyedBumper.setText("" + GlobalStatistics.asteroidsDestroyedByBumper);
    	asteroidsDestroyedBomb.setText("" + GlobalStatistics.asteroidsDestroyedByBomb);

    	// add time played
    	TextView localTimePlayed = (TextView)findViewById(R.id.statisticsMenuTimePlayed);
    	localTimePlayed.setText(GlobalStatistics.getTimePlayedString());
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
