package com.slauson.dasher.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.slauson.dasher.R;
import com.slauson.dasher.status.LocalStatistics;

public class LocalStatisticsMenu extends Activity {

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
		
		usesDash.setText("" + LocalStatistics.usesDash);
		usesSmall.setText("" + LocalStatistics.usesSmall);
		usesSlow.setText("" + LocalStatistics.usesSlow);
		usesInvulnerability.setText("" + LocalStatistics.usesInvulnerability);
		usesDrill.setText("" + LocalStatistics.usesDrill);
		usesMagnet.setText("" + LocalStatistics.usesMagnet);
		usesWhiteHole.setText("" + LocalStatistics.usesWhiteHole);
		usesBumper.setText("" + LocalStatistics.usesBumper);
		usesBomb.setText("" + LocalStatistics.usesBomb);
		
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
		
		asteroidsDestroyedDash.setText("" + LocalStatistics.asteroidsDestroyedByDash);
		asteroidsDestroyedDrill.setText("" + LocalStatistics.asteroidsDestroyedByDrill);
		asteroidsDestroyedMagnet.setText("" + LocalStatistics.asteroidsDestroyedByMagnet);
		asteroidsDestroyedWhiteHole.setText("" + LocalStatistics.asteroidsDestroyedByWhiteHole);
		asteroidsDestroyedBumper.setText("" + LocalStatistics.asteroidsDestroyedByBumper);
		asteroidsDestroyedBomb.setText("" + LocalStatistics.asteroidsDestroyedByBomb);
	
		// add time played
		TextView localTimePlayed = (TextView)findViewById(R.id.statisticsMenuTimePlayed);
		localTimePlayed.setText(LocalStatistics.getTimePlayedString());
	}
}
