package com.slauson.dasher.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.slauson.dasher.R;
import com.slauson.dasher.status.LocalStatistics;
import com.slauson.dasher.status.GlobalStatistics;


public class StatisticsMenu extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.statistics_menu);
    	
    	// add local statistics
    	TextView localStatisticsDash = (TextView)findViewById(R.id.statisticsMenuDashLastPlaythrough);
    	TextView localStatisticsDrill = (TextView)findViewById(R.id.statisticsMenuDrillLastPlaythrough);
    	TextView localStatisticsMagnet = (TextView)findViewById(R.id.statisticsMenuMagnetLastPlaythrough);
    	TextView localStatisticsWhiteHole = (TextView)findViewById(R.id.statisticsMenuWhiteHoleLastPlaythrough);
    	TextView localStatisticsBumper = (TextView)findViewById(R.id.statisticsMenuBumperLastPlaythrough);
    	TextView localStatisticsBomb = (TextView)findViewById(R.id.statisticsMenuBombLastPlaythrough);
    	
    	localStatisticsDash.setText("" + LocalStatistics.asteroidsDestroyedByDash);
    	localStatisticsDrill.setText("" + LocalStatistics.asteroidsDestroyedByDrill);
    	localStatisticsMagnet.setText("" + LocalStatistics.asteroidsDestroyedByMagnet);
    	localStatisticsWhiteHole.setText("" + LocalStatistics.asteroidsDestroyedByWhiteHole);
    	localStatisticsBumper.setText("" + LocalStatistics.asteroidsDestroyedByBumper);
    	localStatisticsBomb.setText("" + LocalStatistics.asteroidsDestroyedByBomb);
    	
    	// add global statistics
    	TextView globalStatisticsDash = (TextView)findViewById(R.id.statisticsMenuDashOverall);
    	TextView globalStatisticsDrill = (TextView)findViewById(R.id.statisticsMenuDrillOverall);
    	TextView globalStatisticsMagnet = (TextView)findViewById(R.id.statisticsMenuMagnetOverall);
    	TextView globalStatisticsWhiteHole = (TextView)findViewById(R.id.statisticsMenuWhiteHoleOverall);
    	TextView globalStatisticsBumper = (TextView)findViewById(R.id.statisticsMenuBumperOverall);
    	TextView globalStatisticsBomb = (TextView)findViewById(R.id.statisticsMenuBombOverall);
    	
    	globalStatisticsDash.setText("" + GlobalStatistics.asteroidsDestroyedByDash);
    	globalStatisticsDrill.setText("" + GlobalStatistics.asteroidsDestroyedByDrill);
    	globalStatisticsMagnet.setText("" + GlobalStatistics.asteroidsDestroyedByMagnet);
    	globalStatisticsWhiteHole.setText("" + GlobalStatistics.asteroidsDestroyedByWhiteHole);
    	globalStatisticsBumper.setText("" + GlobalStatistics.asteroidsDestroyedByBumper);
    	globalStatisticsBomb.setText("" + GlobalStatistics.asteroidsDestroyedByBomb);

    	// add local time played
    	TextView localTimePlayed = (TextView)findViewById(R.id.statisticsMenuTimePlayedLastPlaythrough);
    	localTimePlayed.setText("" + LocalStatistics.timePlayed);
    	
    	// add global time played
    	TextView globalTimePlayed = (TextView)findViewById(R.id.statisticsMenuTimePlayedOverall);
    	globalTimePlayed.setText("" + GlobalStatistics.timePlayed);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
