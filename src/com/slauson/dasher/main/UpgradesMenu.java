package com.slauson.dasher.main;

import com.slauson.dasher.R;
import com.slauson.dasher.status.Upgrade;
import com.slauson.dasher.status.Upgrades;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;

public class UpgradesMenu extends Activity {
	
	// dash buttons
	Button dashButton;
	Button dashUpgradeButton1,dashUpgradeButton2, dashUpgradeButton3, dashUpgradeButton4;

	// small buttons
	Button smallButton;
	Button smallUpgradeButton1,smallUpgradeButton2, smallUpgradeButton3, smallUpgradeButton4;

	// slow buttons
	Button slowButton;
	Button slowUpgradeButton1,slowUpgradeButton2, slowUpgradeButton3, slowUpgradeButton4;

	// invulnerability buttons
	Button invulnerabilityButton;
	Button invulnerabilityUpgradeButton1,invulnerabilityUpgradeButton2, invulnerabilityUpgradeButton3, invulnerabilityUpgradeButton4;

	// drill buttons
	Button drillButton;
	Button drillUpgradeButton1,drillUpgradeButton2, drillUpgradeButton3, drillUpgradeButton4;

	// magnet buttons
	Button magnetButton;
	Button magnetUpgradeButton1,magnetUpgradeButton2, magnetUpgradeButton3, magnetUpgradeButton4;

	// black hole buttons
	Button whiteHoleButton;
	Button whiteHoleUpgradeButton1,whiteHoleUpgradeButton2, whiteHoleUpgradeButton3, whiteHoleUpgradeButton4;

	// bumper buttons
	Button bumperButton;
	Button bumperUpgradeButton1,bumperUpgradeButton2, bumperUpgradeButton3, bumperUpgradeButton4;

	// bomb buttons
	Button bombButton;
	Button bombUpgradeButton1,bombUpgradeButton2, bombUpgradeButton3, bombUpgradeButton4;

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.upgrades_menu);
    	
    	// dash ability
    	dashUpgradeButton1 = (Button)findViewById(R.id.upgradesMenuDashUpgrade1Button);
    	dashUpgradeButton2 = (Button)findViewById(R.id.upgradesMenuDashUpgrade2Button);
    	dashUpgradeButton3 = (Button)findViewById(R.id.upgradesMenuDashUpgrade3Button);
    	dashUpgradeButton4 = (Button)findViewById(R.id.upgradesMenuDashUpgrade4Button);
    	
    	dashUpgradeButton1.setOnClickListener(new UpgradeOnClickListener(Upgrades.dashUpgrade, Upgrades.DASH_UPGRADE_REDUCED_RECHARGE_1));
    	dashUpgradeButton2.setOnClickListener(new UpgradeOnClickListener(Upgrades.dashUpgrade, Upgrades.DASH_UPGRADE_REDUCED_RECHARGE_2));
    	dashUpgradeButton3.setOnClickListener(new UpgradeOnClickListener(Upgrades.dashUpgrade, Upgrades.DASH_UPGRADE_REDUCED_RECHARGE_3));
    	dashUpgradeButton4.setOnClickListener(new UpgradeOnClickListener(Upgrades.dashUpgrade, Upgrades.DASH_UPGRADE_MULTIPLE_POWERUPS));
    	
    	Upgrades.dashUpgrade.setLevel(Upgrades.DASH_UPGRADE_REDUCED_RECHARGE_3);
    	
    	dashButton = (Button)findViewById(R.id.upgradesMenuDashButton);
		dashButton.setOnClickListener(new OnClickListener() {
			
			// true when details are shown
			private boolean showDetails = false;
			
			public void onClick(View v) {
				// show details
				if (!showDetails) {
					dashUpgradeButton1.setVisibility(View.VISIBLE);
					dashUpgradeButton2.setVisibility(View.VISIBLE);
					dashUpgradeButton3.setVisibility(View.VISIBLE);
					dashUpgradeButton4.setVisibility(View.VISIBLE);
					showDetails = true;
					
					// check for purchased upgrades
					switch(Upgrades.dashUpgrade.getLevel()) {
					case Upgrades.DASH_UPGRADE_MULTIPLE_POWERUPS:
						dashUpgradeButton4.setTextColor(Color.BLACK);
						((TableRow)dashUpgradeButton4.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.DASH_UPGRADE_REDUCED_RECHARGE_3:
						dashUpgradeButton3.setTextColor(Color.BLACK);
						((TableRow)dashUpgradeButton3.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.DASH_UPGRADE_REDUCED_RECHARGE_2:
						dashUpgradeButton2.setTextColor(Color.BLACK);
						((TableRow)dashUpgradeButton2.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.DASH_UPGRADE_REDUCED_RECHARGE_1:
						dashUpgradeButton1.setTextColor(Color.BLACK);
						((TableRow)dashUpgradeButton1.getParent()).setBackgroundColor(Color.WHITE);
						break;	
					}
				}
				// hide details
				else {
					dashUpgradeButton1.setVisibility(View.GONE);
					dashUpgradeButton2.setVisibility(View.GONE);
					dashUpgradeButton3.setVisibility(View.GONE);
					dashUpgradeButton4.setVisibility(View.GONE);
					showDetails = false;
				}
			}
		});
		
    	// small powerup
    	smallUpgradeButton1 = (Button)findViewById(R.id.upgradesMenuSmallUpgrade1Button);
    	smallUpgradeButton2 = (Button)findViewById(R.id.upgradesMenuSmallUpgrade2Button);
    	smallUpgradeButton3 = (Button)findViewById(R.id.upgradesMenuSmallUpgrade3Button);
    	smallUpgradeButton4 = (Button)findViewById(R.id.upgradesMenuSmallUpgrade4Button);
    	
    	smallUpgradeButton1.setOnClickListener(new UpgradeOnClickListener(Upgrades.smallUpgrade, Upgrades.SMALL_UPGRADE_INCREASED_DURATION_1));
    	smallUpgradeButton2.setOnClickListener(new UpgradeOnClickListener(Upgrades.smallUpgrade, Upgrades.SMALL_UPGRADE_INCREASED_DURATION_2));
    	smallUpgradeButton3.setOnClickListener(new UpgradeOnClickListener(Upgrades.smallUpgrade, Upgrades.SMALL_UPGRADE_INCREASED_DURATION_3));
    	smallUpgradeButton4.setOnClickListener(new UpgradeOnClickListener(Upgrades.smallUpgrade, Upgrades.SMALL_UPGRADE_QUARTER_SIZE));
    	
    	smallButton = (Button)findViewById(R.id.upgradesMenuSmallButton);
		smallButton.setOnClickListener(new OnClickListener() {
			
			// true when details are shown
			private boolean showDetails = false;
			
			public void onClick(View v) {
				// show details
				if (!showDetails) {
					smallUpgradeButton1.setVisibility(View.VISIBLE);
					smallUpgradeButton2.setVisibility(View.VISIBLE);
					smallUpgradeButton3.setVisibility(View.VISIBLE);
					smallUpgradeButton4.setVisibility(View.VISIBLE);
					showDetails = true;
					
					// check for purchased upgrades
					switch(Upgrades.smallUpgrade.getLevel()) {
					case Upgrades.SMALL_UPGRADE_QUARTER_SIZE:
						smallUpgradeButton4.setTextColor(Color.BLACK);
						((TableRow)smallUpgradeButton4.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.SMALL_UPGRADE_INCREASED_DURATION_3:
						smallUpgradeButton3.setTextColor(Color.BLACK);
						((TableRow)smallUpgradeButton3.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.SMALL_UPGRADE_INCREASED_DURATION_2:
						smallUpgradeButton2.setTextColor(Color.BLACK);
						((TableRow)smallUpgradeButton2.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.SMALL_UPGRADE_INCREASED_DURATION_1:
						smallUpgradeButton1.setTextColor(Color.BLACK);
						((TableRow)smallUpgradeButton1.getParent()).setBackgroundColor(Color.WHITE);
						break;
					}
				}
				// hide details
				else {
					smallUpgradeButton1.setVisibility(View.GONE);
					smallUpgradeButton2.setVisibility(View.GONE);
					smallUpgradeButton3.setVisibility(View.GONE);
					smallUpgradeButton4.setVisibility(View.GONE);
					showDetails = false;
				}
			}
		});
		
    	// slow powerup
    	slowUpgradeButton1 = (Button)findViewById(R.id.upgradesMenuSlowUpgrade1Button);
    	slowUpgradeButton2 = (Button)findViewById(R.id.upgradesMenuSlowUpgrade2Button);
    	slowUpgradeButton3 = (Button)findViewById(R.id.upgradesMenuSlowUpgrade3Button);
    	slowUpgradeButton4 = (Button)findViewById(R.id.upgradesMenuSlowUpgrade4Button);
    	
    	slowUpgradeButton1.setOnClickListener(new UpgradeOnClickListener(Upgrades.slowUpgrade, Upgrades.SLOW_UPGRADE_INCREASED_DURATION_1));
    	slowUpgradeButton2.setOnClickListener(new UpgradeOnClickListener(Upgrades.slowUpgrade, Upgrades.SLOW_UPGRADE_INCREASED_DURATION_2));
    	slowUpgradeButton3.setOnClickListener(new UpgradeOnClickListener(Upgrades.slowUpgrade, Upgrades.SLOW_UPGRADE_INCREASED_DURATION_3));
    	slowUpgradeButton4.setOnClickListener(new UpgradeOnClickListener(Upgrades.slowUpgrade, Upgrades.SLOW_UPGRADE_QUARTER_TIME));
    	
    	slowButton = (Button)findViewById(R.id.upgradesMenuSlowButton);
		slowButton.setOnClickListener(new OnClickListener() {
			
			// true when details are shown
			private boolean showDetails = false;
			
			public void onClick(View v) {
				// show details
				if (!showDetails) {
					slowUpgradeButton1.setVisibility(View.VISIBLE);
					slowUpgradeButton2.setVisibility(View.VISIBLE);
					slowUpgradeButton3.setVisibility(View.VISIBLE);
					slowUpgradeButton4.setVisibility(View.VISIBLE);
					showDetails = true;
					
					// check for purchased upgrades
					switch(Upgrades.slowUpgrade.getLevel()) {
					case Upgrades.SLOW_UPGRADE_QUARTER_TIME:
						slowUpgradeButton4.setTextColor(Color.BLACK);
						((TableRow)slowUpgradeButton4.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.SLOW_UPGRADE_INCREASED_DURATION_3:
						slowUpgradeButton3.setTextColor(Color.BLACK);
						((TableRow)slowUpgradeButton3.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.SLOW_UPGRADE_INCREASED_DURATION_2:
						slowUpgradeButton2.setTextColor(Color.BLACK);
						((TableRow)slowUpgradeButton2.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.SLOW_UPGRADE_INCREASED_DURATION_1:
						slowUpgradeButton1.setTextColor(Color.BLACK);
						((TableRow)slowUpgradeButton1.getParent()).setBackgroundColor(Color.WHITE);
						break;
					}
						
				}
				// hide details
				else {
					slowUpgradeButton1.setVisibility(View.GONE);
					slowUpgradeButton2.setVisibility(View.GONE);
					slowUpgradeButton3.setVisibility(View.GONE);
					slowUpgradeButton4.setVisibility(View.GONE);
					showDetails = false;
				}
			}
		});

    	// invulnerability powerup
    	invulnerabilityUpgradeButton1 = (Button)findViewById(R.id.upgradesMenuInvulnerabilityUpgrade1Button);
    	invulnerabilityUpgradeButton2 = (Button)findViewById(R.id.upgradesMenuInvulnerabilityUpgrade2Button);
    	invulnerabilityUpgradeButton3 = (Button)findViewById(R.id.upgradesMenuInvulnerabilityUpgrade3Button);
    	invulnerabilityUpgradeButton4 = (Button)findViewById(R.id.upgradesMenuInvulnerabilityUpgrade4Button);
    	
    	invulnerabilityUpgradeButton1.setOnClickListener(new UpgradeOnClickListener(Upgrades.invulnerabilityUpgrade, Upgrades.INVULNERABILITY_UPGRADE_INCREASED_DURATION_1));
    	invulnerabilityUpgradeButton2.setOnClickListener(new UpgradeOnClickListener(Upgrades.invulnerabilityUpgrade, Upgrades.INVULNERABILITY_UPGRADE_INCREASED_DURATION_2));
    	invulnerabilityUpgradeButton3.setOnClickListener(new UpgradeOnClickListener(Upgrades.invulnerabilityUpgrade, Upgrades.INVULNERABILITY_UPGRADE_INCREASED_DURATION_3));
    	invulnerabilityUpgradeButton4.setOnClickListener(new UpgradeOnClickListener(Upgrades.invulnerabilityUpgrade, Upgrades.INVULNERABILITY_UPGRADE_SLOW_TIME));
    	
    	invulnerabilityButton = (Button)findViewById(R.id.upgradesMenuInvulnerabilityButton);
		invulnerabilityButton.setOnClickListener(new OnClickListener() {
			
			// true when details are shown
			private boolean showDetails = false;
			
			public void onClick(View v) {
				// show details
				if (!showDetails) {
					invulnerabilityUpgradeButton1.setVisibility(View.VISIBLE);
					invulnerabilityUpgradeButton2.setVisibility(View.VISIBLE);
					invulnerabilityUpgradeButton3.setVisibility(View.VISIBLE);
					invulnerabilityUpgradeButton4.setVisibility(View.VISIBLE);
					showDetails = true;
					
					// check for purchased upgrades
					switch(Upgrades.invulnerabilityUpgrade.getLevel()) {
					case Upgrades.INVULNERABILITY_UPGRADE_SLOW_TIME:
						invulnerabilityUpgradeButton4.setTextColor(Color.BLACK);
						((TableRow)invulnerabilityUpgradeButton4.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.INVULNERABILITY_UPGRADE_INCREASED_DURATION_3:
						invulnerabilityUpgradeButton3.setTextColor(Color.BLACK);
						((TableRow)invulnerabilityUpgradeButton3.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.INVULNERABILITY_UPGRADE_INCREASED_DURATION_2:
						invulnerabilityUpgradeButton2.setTextColor(Color.BLACK);
						((TableRow)invulnerabilityUpgradeButton2.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.INVULNERABILITY_UPGRADE_INCREASED_DURATION_1:
						invulnerabilityUpgradeButton1.setTextColor(Color.BLACK);
						((TableRow)invulnerabilityUpgradeButton1.getParent()).setBackgroundColor(Color.WHITE);
						break;
					}
				}
				// hide details
				else {
					invulnerabilityUpgradeButton1.setVisibility(View.GONE);
					invulnerabilityUpgradeButton2.setVisibility(View.GONE);
					invulnerabilityUpgradeButton3.setVisibility(View.GONE);
					invulnerabilityUpgradeButton4.setVisibility(View.GONE);
					showDetails = false;
				}
			}
		});

		
    	// drill powerup
    	drillUpgradeButton1 = (Button)findViewById(R.id.upgradesMenuDrillUpgrade1Button);
    	drillUpgradeButton2 = (Button)findViewById(R.id.upgradesMenuDrillUpgrade2Button);
    	drillUpgradeButton3 = (Button)findViewById(R.id.upgradesMenuDrillUpgrade3Button);
    	drillUpgradeButton4 = (Button)findViewById(R.id.upgradesMenuDrillUpgrade4Button);
    	
    	drillUpgradeButton1.setOnClickListener(new UpgradeOnClickListener(Upgrades.drillUpgrade, Upgrades.DRILL_UPGRADE_SEEK_1));
    	drillUpgradeButton2.setOnClickListener(new UpgradeOnClickListener(Upgrades.drillUpgrade, Upgrades.DRILL_UPGRADE_SEEK_2));
    	drillUpgradeButton3.setOnClickListener(new UpgradeOnClickListener(Upgrades.drillUpgrade, Upgrades.DRILL_UPGRADE_SEEK_3));
    	drillUpgradeButton4.setOnClickListener(new UpgradeOnClickListener(Upgrades.drillUpgrade, Upgrades.DRILL_UPGRADE_TELEPORT));
    	
    	drillButton = (Button)findViewById(R.id.upgradesMenuDrillButton);
		drillButton.setOnClickListener(new OnClickListener() {
			
			// true when details are shown
			private boolean showDetails = false;
			
			public void onClick(View v) {
				// show details
				if (!showDetails) {
					drillUpgradeButton1.setVisibility(View.VISIBLE);
					drillUpgradeButton2.setVisibility(View.VISIBLE);
					drillUpgradeButton3.setVisibility(View.VISIBLE);
					drillUpgradeButton4.setVisibility(View.VISIBLE);
					showDetails = true;
					
					// check for purchased upgrades
					switch(Upgrades.drillUpgrade.getLevel()) {
					case Upgrades.DRILL_UPGRADE_TELEPORT:
						drillUpgradeButton4.setTextColor(Color.BLACK);
						((TableRow)drillUpgradeButton4.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.DRILL_UPGRADE_SEEK_3:
						drillUpgradeButton3.setTextColor(Color.BLACK);
						((TableRow)drillUpgradeButton3.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.DRILL_UPGRADE_SEEK_2:
						drillUpgradeButton2.setTextColor(Color.BLACK);
						((TableRow)drillUpgradeButton2.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.DRILL_UPGRADE_SEEK_1:
						drillUpgradeButton1.setTextColor(Color.BLACK);
						((TableRow)drillUpgradeButton1.getParent()).setBackgroundColor(Color.WHITE);
						break;
					}
				}
				// hide details
				else {
					drillUpgradeButton1.setVisibility(View.GONE);
					drillUpgradeButton2.setVisibility(View.GONE);
					drillUpgradeButton3.setVisibility(View.GONE);
					drillUpgradeButton4.setVisibility(View.GONE);
					showDetails = false;
				}
			}
		});

    	// magnet powerup
    	magnetUpgradeButton1 = (Button)findViewById(R.id.upgradesMenuMagnetUpgrade1Button);
    	magnetUpgradeButton2 = (Button)findViewById(R.id.upgradesMenuMagnetUpgrade2Button);
    	magnetUpgradeButton3 = (Button)findViewById(R.id.upgradesMenuMagnetUpgrade3Button);
    	magnetUpgradeButton4 = (Button)findViewById(R.id.upgradesMenuMagnetUpgrade4Button);
    	
    	magnetUpgradeButton1.setOnClickListener(new UpgradeOnClickListener(Upgrades.magnetUpgrade, Upgrades.MAGNET_UPGRADE_INCREASED_DURATION_1));
    	magnetUpgradeButton2.setOnClickListener(new UpgradeOnClickListener(Upgrades.magnetUpgrade, Upgrades.MAGNET_UPGRADE_INCREASED_DURATION_2));
    	magnetUpgradeButton3.setOnClickListener(new UpgradeOnClickListener(Upgrades.magnetUpgrade, Upgrades.MAGNET_UPGRADE_INCREASED_DURATION_3));
    	magnetUpgradeButton4.setOnClickListener(new UpgradeOnClickListener(Upgrades.magnetUpgrade, Upgrades.MAGNET_UPGRADE_INCREASED_RANGE));
    	
    	magnetButton = (Button)findViewById(R.id.upgradesMenuMagnetButton);
		magnetButton.setOnClickListener(new OnClickListener() {
			
			// true when details are shown
			private boolean showDetails = false;
			
			public void onClick(View v) {
				// show details
				if (!showDetails) {
					magnetUpgradeButton1.setVisibility(View.VISIBLE);
					magnetUpgradeButton2.setVisibility(View.VISIBLE);
					magnetUpgradeButton3.setVisibility(View.VISIBLE);
					magnetUpgradeButton4.setVisibility(View.VISIBLE);
					showDetails = true;
					
					// check for purchased upgrades
					switch(Upgrades.magnetUpgrade.getLevel()) {
					case Upgrades.MAGNET_UPGRADE_INCREASED_RANGE:
						magnetUpgradeButton4.setTextColor(Color.BLACK);
						((TableRow)magnetUpgradeButton4.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.MAGNET_UPGRADE_INCREASED_DURATION_3:
						magnetUpgradeButton3.setTextColor(Color.BLACK);
						((TableRow)magnetUpgradeButton3.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.MAGNET_UPGRADE_INCREASED_DURATION_2:
						magnetUpgradeButton2.setTextColor(Color.BLACK);
						((TableRow)magnetUpgradeButton2.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.MAGNET_UPGRADE_INCREASED_DURATION_1:
						magnetUpgradeButton1.setTextColor(Color.BLACK);
						((TableRow)magnetUpgradeButton1.getParent()).setBackgroundColor(Color.WHITE);
						break;
					}
				}
				// hide details
				else {
					magnetUpgradeButton1.setVisibility(View.GONE);
					magnetUpgradeButton2.setVisibility(View.GONE);
					magnetUpgradeButton3.setVisibility(View.GONE);
					magnetUpgradeButton4.setVisibility(View.GONE);
					showDetails = false;
				}
			}
		});

		
    	// white hole powerup
    	whiteHoleUpgradeButton1 = (Button)findViewById(R.id.upgradesMenuWhiteHoleUpgrade1Button);
    	whiteHoleUpgradeButton2 = (Button)findViewById(R.id.upgradesMenuWhiteHoleUpgrade2Button);
    	whiteHoleUpgradeButton3 = (Button)findViewById(R.id.upgradesMenuWhiteHoleUpgrade3Button);
    	whiteHoleUpgradeButton4 = (Button)findViewById(R.id.upgradesMenuWhiteHoleUpgrade4Button);
    	
    	whiteHoleUpgradeButton1.setOnClickListener(new UpgradeOnClickListener(Upgrades.whiteHoleUpgrade, Upgrades.WHITE_HOLE_UPGRADE_INCREASED_DURATION_1));
    	whiteHoleUpgradeButton2.setOnClickListener(new UpgradeOnClickListener(Upgrades.whiteHoleUpgrade, Upgrades.WHITE_HOLE_UPGRADE_INCREASED_DURATION_2));
    	whiteHoleUpgradeButton3.setOnClickListener(new UpgradeOnClickListener(Upgrades.whiteHoleUpgrade, Upgrades.WHITE_HOLE_UPGRADE_INCREASED_DURATION_3));
    	whiteHoleUpgradeButton4.setOnClickListener(new UpgradeOnClickListener(Upgrades.whiteHoleUpgrade, Upgrades.WHITE_HOLE_UPGRADE_INCREASED_RANGE));
    	
    	whiteHoleButton = (Button)findViewById(R.id.upgradesMenuWhiteHoleButton);
		whiteHoleButton.setOnClickListener(new OnClickListener() {
			
			// true when details are shown
			private boolean showDetails = false;
			
			public void onClick(View v) {
				// show details
				if (!showDetails) {
					whiteHoleUpgradeButton1.setVisibility(View.VISIBLE);
					whiteHoleUpgradeButton2.setVisibility(View.VISIBLE);
					whiteHoleUpgradeButton3.setVisibility(View.VISIBLE);
					whiteHoleUpgradeButton4.setVisibility(View.VISIBLE);
					showDetails = true;
					
					// check for purchased upgrades
					switch(Upgrades.whiteHoleUpgrade.getLevel()) {
					case Upgrades.WHITE_HOLE_UPGRADE_INCREASED_RANGE:
						whiteHoleUpgradeButton4.setTextColor(Color.BLACK);
						((TableRow)whiteHoleUpgradeButton4.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.WHITE_HOLE_UPGRADE_INCREASED_DURATION_3:
						whiteHoleUpgradeButton3.setTextColor(Color.BLACK);
						((TableRow)whiteHoleUpgradeButton3.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.WHITE_HOLE_UPGRADE_INCREASED_DURATION_2:
						whiteHoleUpgradeButton2.setTextColor(Color.BLACK);
						((TableRow)whiteHoleUpgradeButton2.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.WHITE_HOLE_UPGRADE_INCREASED_DURATION_1:
						whiteHoleUpgradeButton1.setTextColor(Color.BLACK);
						((TableRow)whiteHoleUpgradeButton1.getParent()).setBackgroundColor(Color.WHITE);
						break;
					}
				}
				// hide details
				else {
					whiteHoleUpgradeButton1.setVisibility(View.GONE);
					whiteHoleUpgradeButton2.setVisibility(View.GONE);
					whiteHoleUpgradeButton3.setVisibility(View.GONE);
					whiteHoleUpgradeButton4.setVisibility(View.GONE);
					showDetails = false;
				}
			}
		});

		
    	// bumper powerup
    	bumperUpgradeButton1 = (Button)findViewById(R.id.upgradesMenuBumperUpgrade1Button);
    	bumperUpgradeButton2 = (Button)findViewById(R.id.upgradesMenuBumperUpgrade2Button);
    	bumperUpgradeButton3 = (Button)findViewById(R.id.upgradesMenuBumperUpgrade3Button);
    	bumperUpgradeButton4 = (Button)findViewById(R.id.upgradesMenuBumperUpgrade4Button);
    	
    	bumperUpgradeButton1.setOnClickListener(new UpgradeOnClickListener(Upgrades.bumperUpgrade, Upgrades.BUMPER_UPGRADE_INCREASED_DURATION_1));
    	bumperUpgradeButton2.setOnClickListener(new UpgradeOnClickListener(Upgrades.bumperUpgrade, Upgrades.BUMPER_UPGRADE_INCREASED_DURATION_2));
    	bumperUpgradeButton3.setOnClickListener(new UpgradeOnClickListener(Upgrades.bumperUpgrade, Upgrades.BUMPER_UPGRADE_INCREASED_DURATION_3));
    	bumperUpgradeButton4.setOnClickListener(new UpgradeOnClickListener(Upgrades.bumperUpgrade, Upgrades.BUMPER_UPGRADE_INCREASED_SIZE));
    	
    	bumperButton = (Button)findViewById(R.id.upgradesMenuBumperButton);
		bumperButton.setOnClickListener(new OnClickListener() {
			
			// true when details are shown
			private boolean showDetails = false;
			
			public void onClick(View v) {
				// show details
				if (!showDetails) {
					bumperUpgradeButton1.setVisibility(View.VISIBLE);
					bumperUpgradeButton2.setVisibility(View.VISIBLE);
					bumperUpgradeButton3.setVisibility(View.VISIBLE);
					bumperUpgradeButton4.setVisibility(View.VISIBLE);
					showDetails = true;
					
					// check for purchased upgrades
					switch(Upgrades.bumperUpgrade.getLevel()) {
					case Upgrades.BUMPER_UPGRADE_INCREASED_SIZE:
						bumperUpgradeButton4.setTextColor(Color.BLACK);
						((TableRow)bumperUpgradeButton4.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.BUMPER_UPGRADE_INCREASED_DURATION_3:
						bumperUpgradeButton3.setTextColor(Color.BLACK);
						((TableRow)bumperUpgradeButton3.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.BUMPER_UPGRADE_INCREASED_DURATION_2:
						bumperUpgradeButton2.setTextColor(Color.BLACK);
						((TableRow)bumperUpgradeButton2.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.BUMPER_UPGRADE_INCREASED_DURATION_1:
						bumperUpgradeButton1.setTextColor(Color.BLACK);
						((TableRow)bumperUpgradeButton1.getParent()).setBackgroundColor(Color.WHITE);
						break;
					}
				}
				// hide details
				else {
					bumperUpgradeButton1.setVisibility(View.GONE);
					bumperUpgradeButton2.setVisibility(View.GONE);
					bumperUpgradeButton3.setVisibility(View.GONE);
					bumperUpgradeButton4.setVisibility(View.GONE);
					showDetails = false;
				}
			}
		});
		
		
    	// bomb powerup
    	bombUpgradeButton1 = (Button)findViewById(R.id.upgradesMenuBombUpgrade1Button);
    	bombUpgradeButton2 = (Button)findViewById(R.id.upgradesMenuBombUpgrade2Button);
    	bombUpgradeButton3 = (Button)findViewById(R.id.upgradesMenuBombUpgrade3Button);
    	bombUpgradeButton4 = (Button)findViewById(R.id.upgradesMenuBombUpgrade4Button);
    	
    	bombUpgradeButton1.setOnClickListener(new UpgradeOnClickListener(Upgrades.bombUpgrade, Upgrades.BOMB_UPGRADE_NO_EFFECT_DROPS));
    	bombUpgradeButton2.setOnClickListener(new UpgradeOnClickListener(Upgrades.bombUpgrade, Upgrades.BOMB_UPGRADE_NO_EFFECT_POWERUPS));
    	bombUpgradeButton3.setOnClickListener(new UpgradeOnClickListener(Upgrades.bombUpgrade, Upgrades.BOMB_UPGRADE_CAUSE_DROP));
    	bombUpgradeButton4.setOnClickListener(new UpgradeOnClickListener(Upgrades.bombUpgrade, Upgrades.BOMB_UPGRADE_CAUSE_DROPS));
    	
    	bombButton = (Button)findViewById(R.id.upgradesMenuBombButton);
		bombButton.setOnClickListener(new OnClickListener() {
			
			// true when details are shown
			private boolean showDetails = false;
			
			public void onClick(View v) {
				// show details
				if (!showDetails) {
					bombUpgradeButton1.setVisibility(View.VISIBLE);
					bombUpgradeButton2.setVisibility(View.VISIBLE);
					bombUpgradeButton3.setVisibility(View.VISIBLE);
					bombUpgradeButton4.setVisibility(View.VISIBLE);
					showDetails = true;
					
					// check for purchased upgrades
					switch(Upgrades.bombUpgrade.getLevel()) {
					case Upgrades.BOMB_UPGRADE_CAUSE_DROPS:
						bombUpgradeButton4.setTextColor(Color.BLACK);
						((TableRow)bombUpgradeButton4.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.BOMB_UPGRADE_CAUSE_DROP:
						bombUpgradeButton3.setTextColor(Color.BLACK);
						((TableRow)bombUpgradeButton3.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.BOMB_UPGRADE_NO_EFFECT_POWERUPS:
						bombUpgradeButton2.setTextColor(Color.BLACK);
						((TableRow)bombUpgradeButton2.getParent()).setBackgroundColor(Color.WHITE);
					case Upgrades.BOMB_UPGRADE_NO_EFFECT_DROPS:
						bombUpgradeButton1.setTextColor(Color.BLACK);
						((TableRow)bombUpgradeButton1.getParent()).setBackgroundColor(Color.WHITE);
						break;
					}
				}
				// hide details
				else {
					bombUpgradeButton1.setVisibility(View.GONE);
					bombUpgradeButton2.setVisibility(View.GONE);
					bombUpgradeButton3.setVisibility(View.GONE);
					bombUpgradeButton4.setVisibility(View.GONE);
					showDetails = false;
				}
			}
		});



	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	private class UpgradeOnClickListener implements OnClickListener {
		
		private Upgrade upgrade;
		private int type;
		
		public UpgradeOnClickListener(Upgrade upgrade, int type) {
			this.upgrade = upgrade;
			this.type = type;
		}

		public void onClick(View v) {
			// check if upgrade is unlocked
			if (upgrade.getLevel() >= type) {
				return;
			}
			
			// check if player has enough points
			
			// verify selection
			
			// save upgrade
		}
	}
}