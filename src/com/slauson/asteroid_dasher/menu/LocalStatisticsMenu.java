package com.slauson.asteroid_dasher.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.slauson.asteroid_dasher.status.LocalStatistics;
import com.slauson.asteroid_dasher.status.Statistics;
import com.slauson.asteroid_dasher.R;

public class LocalStatisticsMenu extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.statistics_menu);
    	
    	Statistics localStatistics = LocalStatistics.getInstance();

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
		
		usesDash.setText("" + localStatistics.usesDash);
		usesSmall.setText("" + localStatistics.usesSmall);
		usesSlow.setText("" + localStatistics.usesSlow);
		usesInvulnerability.setText("" + localStatistics.usesInvulnerability);
		usesDrill.setText("" + localStatistics.usesDrill);
		usesMagnet.setText("" + localStatistics.usesMagnet);
		usesBlackHole.setText("" + localStatistics.usesBlackHole);
		usesBumper.setText("" + localStatistics.usesBumper);
		usesBomb.setText("" + localStatistics.usesBomb);
		
		// total
		usesTotal.setText("" + 
				(localStatistics.usesDash +
				localStatistics.usesSmall +
				localStatistics.usesSlow +
				localStatistics.usesInvulnerability +
				localStatistics.usesDrill +
				localStatistics.usesMagnet +
				localStatistics.usesBlackHole +
				localStatistics.usesBumper +
				localStatistics.usesBomb));
				
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
		
		asteroidsDestroyedDash.setText("" + localStatistics.asteroidsDestroyedByDash);
		asteroidsDestroyedDrill.setText("" + localStatistics.asteroidsDestroyedByDrill);
		asteroidsDestroyedMagnet.setText("" + localStatistics.asteroidsDestroyedByMagnet);
		asteroidsDestroyedBlackHole.setText("" + localStatistics.asteroidsDestroyedByBlackHole);
		asteroidsDestroyedBumper.setText("" + localStatistics.asteroidsDestroyedByBumper);
		asteroidsDestroyedBomb.setText("" + localStatistics.asteroidsDestroyedByBomb);
	
		// total
		asteroidsDestroyedTotal.setText("" + 
				(localStatistics.asteroidsDestroyedByDash +
				localStatistics.asteroidsDestroyedByDrill +
				localStatistics.asteroidsDestroyedByMagnet +
				localStatistics.asteroidsDestroyedByBlackHole +
				localStatistics.asteroidsDestroyedByBumper +
				localStatistics.asteroidsDestroyedByBomb));

		// hide time played, average time played, times played, points earned, points spent, completion percentage
		((TableRow)findViewById(R.id.statisticsMenuTimePlayedRow)).setVisibility(View.GONE);
		((TableRow)findViewById(R.id.statisticsMenuAverageTimePlayedRow)).setVisibility(View.GONE);
		((TableRow)findViewById(R.id.statisticsMenuTimesPlayedRow)).setVisibility(View.GONE);
		((TableRow)findViewById(R.id.statisticsMenuPointsEarnedRow)).setVisibility(View.GONE);
		((TableRow)findViewById(R.id.statisticsMenuPointsSpentRow)).setVisibility(View.GONE);
		((TableRow)findViewById(R.id.statisticsMenuCompletionPercentageRow)).setVisibility(View.GONE);
	}
}
