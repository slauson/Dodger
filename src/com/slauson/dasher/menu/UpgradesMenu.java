package com.slauson.dasher.menu;

import com.slauson.dasher.R;
import com.slauson.dasher.status.GlobalStatistics;
import com.slauson.dasher.status.Points;
import com.slauson.dasher.status.Upgrade;
import com.slauson.dasher.status.Upgrades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

public class UpgradesMenu extends Activity {
	
	/** Dialog box id for when player doesn't have enough **/
	private final static int DIALOG_NOT_ENOUGH_POINTS = 0;
	/** Dialog box id for confirming unlocking of powerups **/
	private final static int DIALOG_CONFIRM_POWERUP_UNLOCK = 1;

	/** Dialog box bundle argument for title **/
	private final static String DIALOG_TITLE = "title";
	/** Dialog box bundle argument for points **/
	private final static String DIALOG_POINTS = "points";
	/** Dialog box bundle argument for upgrade id **/
	private final static String DIALOG_CONFIRM_POWERUP_UNLOCK_UPGRADE_ID = "upgradeID";
	/** Dialog box bundle argument for button id **/
	private final static String DIALOG_CONFIRM_POWERUP_BUTTON_ID = "buttonID";
	
	/** Shared preferences editor **/
	private SharedPreferences.Editor sharedPreferencesEditor;
	
	/** Points text view **/
	private TextView pointsView;
	
	/** Dash upgrade button **/
	private Button dashButton;
	/** Small upgrade button **/
	private Button smallButton;
	/** Slow upgrade button **/
	private Button slowButton;
	/** Invulnerable upgrade button **/
	private Button invulnerabilityButton;
	/** Drill upgrade button **/
	private Button drillButton;
	/** Magnet upgrade button **/
	private Button magnetButton;
	/** Black hole upgrade button **/
	private Button blackHoleButton;
	/** Bumper upgrade button **/
	private Button bumperButton;
	/** Bomb upgrade button **/
	private Button bombButton;

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.upgrades_menu);
    	
    	// load upgrade resources
    	Upgrades.loadResources(getResources(), getPackageName());
    	
    	// get preferences editor
    	sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
    	
    	// points
    	pointsView = (TextView)findViewById(R.id.upgradesMenuPoints);
    	pointsView.setText(Points.getNumPoints() + " points");
    	
    	dashButton = (Button)findViewById(R.id.upgradesMenuDashButton);
    	toggleButtonColor(dashButton);
		dashButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.dashUpgrade));
				startActivity(intent);
			}
		});
		
    	smallButton = (Button)findViewById(R.id.upgradesMenuSmallButton);
    	toggleButtonColor(smallButton);
		smallButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.smallUpgrade));
				startActivity(intent);
			}			
		});
		
    	// slow powerup
    	slowButton = (Button)findViewById(R.id.upgradesMenuSlowButton);
    	toggleButtonColor(slowButton);
    	slowButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.slowUpgrade));
				startActivity(intent);
			}
		});

    	// invulnerability powerup
    	invulnerabilityButton = (Button)findViewById(R.id.upgradesMenuInvulnerabilityButton);
    	toggleButtonColor(invulnerabilityButton);
		invulnerabilityButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.invulnerabilityUpgrade));
				startActivity(intent);
			}
		});

		
    	// drill powerup
    	drillButton = (Button)findViewById(R.id.upgradesMenuDrillButton);
    	toggleButtonColor(drillButton);
    	drillButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.drillUpgrade));
				startActivity(intent);
			}
    	});

    	// magnet powerup
    	magnetButton = (Button)findViewById(R.id.upgradesMenuMagnetButton);
    	
    	if (Upgrades.magnetUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED) {
    		toggleButtonColor(magnetButton);
    	} else {
    		magnetButton.setText(magnetButton.getText() + "\n" + Upgrades.POINTS_MAGNET_POWERUP + " points");
    	}
    	
    	magnetButton.setOnClickListener(new OnClickListener() {
			
    		public void onClick(View v) {
				
				// check if not unlocked
				if (Upgrades.magnetUpgrade.getLevel() == Upgrades.POWERUP_LOCKED) {

					Bundle bundle = new Bundle();
					bundle.putString(DIALOG_TITLE, Upgrades.magnetUpgrade.getDescription());
					bundle.putInt(DIALOG_POINTS, Upgrades.POINTS_MAGNET_POWERUP);
					bundle.putInt(DIALOG_CONFIRM_POWERUP_UNLOCK_UPGRADE_ID, Upgrades.getUpgradeID(Upgrades.magnetUpgrade));
					bundle.putInt(DIALOG_CONFIRM_POWERUP_BUTTON_ID, R.id.upgradesMenuMagnetButton);

					// check if we have enough points
					if (Points.getNumPoints() < Upgrades.POINTS_MAGNET_POWERUP) {
						showDialog(DIALOG_NOT_ENOUGH_POINTS, bundle);
					} else {
						showDialog(DIALOG_CONFIRM_POWERUP_UNLOCK, bundle);
					}
					return;
				}
				
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.magnetUpgrade));
				startActivity(intent);
			}
		});

		
    	// black hole powerup
    	blackHoleButton = (Button)findViewById(R.id.upgradesMenuBlackHoleButton);
    	
    	if (Upgrades.blackHoleUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED) {
    		toggleButtonColor(blackHoleButton);
    	} else {
    		blackHoleButton.setText(blackHoleButton.getText() + "\n" + Upgrades.POINTS_BLACK_HOLE_POWERUP + " points");
    	}
    	
    	blackHoleButton.setOnClickListener(new OnClickListener() {
    		
			public void onClick(View v) {
				
				// check if not unlocked
				if (Upgrades.blackHoleUpgrade.getLevel() == Upgrades.POWERUP_LOCKED) {

					Bundle bundle = new Bundle();
					bundle.putString(DIALOG_TITLE, Upgrades.blackHoleUpgrade.getDescription());
					bundle.putInt(DIALOG_POINTS, Upgrades.POINTS_BLACK_HOLE_POWERUP);
					bundle.putInt(DIALOG_CONFIRM_POWERUP_UNLOCK_UPGRADE_ID, Upgrades.getUpgradeID(Upgrades.blackHoleUpgrade));
					bundle.putInt(DIALOG_CONFIRM_POWERUP_BUTTON_ID, R.id.upgradesMenuBlackHoleButton);

					// check if we have enough points
					if (Points.getNumPoints() < Upgrades.POINTS_BLACK_HOLE_POWERUP) {
						showDialog(DIALOG_NOT_ENOUGH_POINTS, bundle);
					} else {
						showDialog(DIALOG_CONFIRM_POWERUP_UNLOCK, bundle);
					}
					return;
				}
				
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.blackHoleUpgrade));
				startActivity(intent);
			}
		});

		
    	// bumper powerup
    	bumperButton = (Button)findViewById(R.id.upgradesMenuBumperButton);
    	
    	if (Upgrades.bumperUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED) {
    		toggleButtonColor(bumperButton);
    	} else {
    		bumperButton.setText(bumperButton.getText() + "\n" + Upgrades.POINTS_BUMPER_POWERUP + " points");
    	}
    	
    	bumperButton.setOnClickListener(new OnClickListener() {
    		
			public void onClick(View v) {
				
				// check if not unlocked
				if (Upgrades.bumperUpgrade.getLevel() == Upgrades.POWERUP_LOCKED) {

					Bundle bundle = new Bundle();
					bundle.putString(DIALOG_TITLE, Upgrades.bumperUpgrade.getDescription());
					bundle.putInt(DIALOG_POINTS, Upgrades.POINTS_BUMPER_POWERUP);
					bundle.putInt(DIALOG_CONFIRM_POWERUP_UNLOCK_UPGRADE_ID, Upgrades.getUpgradeID(Upgrades.bumperUpgrade));
					bundle.putInt(DIALOG_CONFIRM_POWERUP_BUTTON_ID, R.id.upgradesMenuBumperButton);

					// check if we have enough points
					if (Points.getNumPoints() < Upgrades.POINTS_BUMPER_POWERUP) {
						showDialog(DIALOG_NOT_ENOUGH_POINTS, bundle);
					} else {
						showDialog(DIALOG_CONFIRM_POWERUP_UNLOCK, bundle);
					}
					return;
				}
				
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.bumperUpgrade));
				startActivity(intent);
			}
		});
		
		
    	// bomb powerup
    	bombButton = (Button)findViewById(R.id.upgradesMenuBombButton);
		
    	if (Upgrades.bombUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED) {
    		toggleButtonColor(bombButton);
    	} else {
    		bombButton.setText(bombButton.getText() + "\n" + Upgrades.POINTS_BOMB_POWERUP + " points");
    	}
    	
    	bombButton.setOnClickListener(new OnClickListener() {
			
    		public void onClick(View v) {
    			
    			// check if not unlocked
				if (Upgrades.bombUpgrade.getLevel() == Upgrades.POWERUP_LOCKED) {

					Bundle bundle = new Bundle();
					bundle.putString(DIALOG_TITLE, Upgrades.bombUpgrade.getDescription());
					bundle.putInt(DIALOG_POINTS, Upgrades.POINTS_BOMB_POWERUP);
					bundle.putInt(DIALOG_CONFIRM_POWERUP_UNLOCK_UPGRADE_ID, Upgrades.getUpgradeID(Upgrades.bombUpgrade));
					bundle.putInt(DIALOG_CONFIRM_POWERUP_BUTTON_ID, R.id.upgradesMenuBombButton);

					// check if we have enough points
					if (Points.getNumPoints() < Upgrades.POINTS_BOMB_POWERUP) {
						showDialog(DIALOG_NOT_ENOUGH_POINTS, bundle);
					} else {
						showDialog(DIALOG_CONFIRM_POWERUP_UNLOCK, bundle);
					}
					return;
				}
				
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.bombUpgrade));
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		// set points
		if (pointsView != null) {
			pointsView.setText(Points.getNumPoints() + " points");
		}
	}
	
	@Override
	public Dialog onCreateDialog(int id, Bundle args) {

		// get args
		final String title = args.getString(DIALOG_TITLE);
		final int points = args.getInt(DIALOG_POINTS);
		
		Dialog dialog = null;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		switch(id) {
		case DIALOG_NOT_ENOUGH_POINTS:
			alertDialogBuilder
				.setTitle("Not Enough Points")
				.setMessage("You need at least " + points + " to unlock the " + title + " powerup.")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						removeDialog(DIALOG_NOT_ENOUGH_POINTS);
					}
				});
			
			dialog = alertDialogBuilder.create();

			break;
		case DIALOG_CONFIRM_POWERUP_UNLOCK:

			// get more args
			final int powerupUnlockUpgradeID = args.getInt(DIALOG_CONFIRM_POWERUP_UNLOCK_UPGRADE_ID);
			final int powerupUnlockButtonID = args.getInt(DIALOG_CONFIRM_POWERUP_BUTTON_ID);
			
			alertDialogBuilder
				.setTitle("Confirm Powerup")
				.setMessage("Are you sure you want to unlock the " + title + " powerup for " + points + " points?")
				
				// do nothing when user selects no 
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						removeDialog(DIALOG_CONFIRM_POWERUP_UNLOCK);
					}
				})
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// set upgrade level
						Upgrade upgrade = Upgrades.getUpgrade(powerupUnlockUpgradeID);
						
						// this should never happen...
						if (upgrade == null) {
							System.out.println("Null upgrade for id " + powerupUnlockUpgradeID);
							return;
						}

						upgrade.setLevel(Upgrades.POWERUP_UNLOCKED);

						// update button text
						Button button = (Button)findViewById(powerupUnlockButtonID);
						button.setText(button.getText().toString().replaceAll("-.*", ""));
						toggleButtonColor(button);
						toggleButtonText(button);
						
						// update points
						Points.update(-points);
						
						// update points spent
						GlobalStatistics.getInstance().pointsSpent += points;
						
						// update points textview
				    	pointsView.setText("You have " + Points.getNumPoints() + " points");
				    	
				    	// save state
				    	upgrade.save(sharedPreferencesEditor);
				    	Points.save(sharedPreferencesEditor);
				    	GlobalStatistics.save(sharedPreferencesEditor);
				    	sharedPreferencesEditor.commit();
				    	
				    	// remove dialog so that we can change message/title
				    	removeDialog(DIALOG_CONFIRM_POWERUP_UNLOCK);
					}
				});

			
			dialog = alertDialogBuilder.create();
			break;
		default:
			dialog = null;
		}
		
		return dialog;
	}

	/**
	 * Sets button text to be black and sets the row background color to black
	 * @param button button to toggle
	 */
	public void toggleButtonColor(Button button) {
		button.setTextColor(Color.BLACK);
		((TableRow)button.getParent()).setBackgroundColor(Color.WHITE);
	}
	
	/**
	 * Modifies button text to remove cost of powerup
	 * @param button button to toggle
	 */
	public void toggleButtonText(Button button) {
		String text = button.getText().toString();
		
		text = text.replaceAll("\n.*", "");
		button.setText(text);
	}
}