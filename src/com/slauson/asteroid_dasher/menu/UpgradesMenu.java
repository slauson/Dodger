package com.slauson.asteroid_dasher.menu;

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
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.slauson.asteroid_dasher.R;
import com.slauson.asteroid_dasher.other.Util;
import com.slauson.asteroid_dasher.status.GlobalStatistics;
import com.slauson.asteroid_dasher.status.Points;
import com.slauson.asteroid_dasher.status.Upgrade;
import com.slauson.asteroid_dasher.status.Upgrades;

public class UpgradesMenu extends Activity {

	/** Dialog box id for when player doesn't have enough **/
	private final static int DIALOG_NOT_ENOUGH_POINTS = 0;
	/** Dialog box id for confirming unlocking of powerups **/
	private final static int DIALOG_CONFIRM_POWERUP_UNLOCK = 1;
	/** Dialog box id for minimum number of powerups picked **/
	private final static int DIALOG_MINIMUM_POWERUPS_SELECTED = 2;

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
	/** Other upgrade button **/
	private Button otherButton;

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

		// dash upgrades
		dashButton = (Button)findViewById(R.id.upgradesMenuDashButton);
		toggleButtonColor(dashButton, true);
		dashButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.dashUpgrade));
				startActivity(intent);
			}
		});

		// other upgrades
		otherButton = (Button)findViewById(R.id.upgradesMenuOtherButton);
		toggleButtonColor(otherButton, true);
		otherButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.otherUpgrade));
				startActivity(intent);
			}
		});

		// small powerup
		smallButton = (Button)findViewById(R.id.upgradesMenuSmallButton);
		toggleButtonColor(smallButton, Upgrades.smallUpgrade.isEnabled());
		smallButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.smallUpgrade));
				startActivity(intent);
			}
		});

		smallButton.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER) {
					if (Upgrades.smallUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(smallButton, Upgrades.smallUpgrade.toggleEnabled());
						Upgrades.smallUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
				return true;
			}
		});

		ImageView smallIcon = (ImageView)findViewById(R.id.upgradesMenuSmallIcon);
		smallIcon.setClickable(true);
		smallIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER) {
					if (Upgrades.smallUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(smallButton, Upgrades.smallUpgrade.toggleEnabled());
						Upgrades.smallUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
			}
		});

		// slow powerup
		slowButton = (Button)findViewById(R.id.upgradesMenuSlowButton);
		toggleButtonColor(slowButton, Upgrades.slowUpgrade.isEnabled());
		slowButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.slowUpgrade));
				startActivity(intent);
			}
		});

		slowButton.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER) {
					if (Upgrades.slowUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(slowButton, Upgrades.slowUpgrade.toggleEnabled());
						Upgrades.slowUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
				return true;
			}
		});

		ImageView slowIcon = (ImageView)findViewById(R.id.upgradesMenuSlowIcon);
		slowIcon.setClickable(true);
		slowIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER) {
					if (Upgrades.slowUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(slowButton, Upgrades.slowUpgrade.toggleEnabled());
						Upgrades.slowUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
			}
		});

		// invulnerability powerup
		invulnerabilityButton = (Button)findViewById(R.id.upgradesMenuInvulnerabilityButton);
		toggleButtonColor(invulnerabilityButton, Upgrades.invulnerabilityUpgrade.isEnabled());
		invulnerabilityButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.invulnerabilityUpgrade));
				startActivity(intent);
			}
		});

		invulnerabilityButton.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER) {
					if (Upgrades.invulnerabilityUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(invulnerabilityButton, Upgrades.invulnerabilityUpgrade.toggleEnabled());
						Upgrades.invulnerabilityUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
				return true;
			}
		});

		ImageView invulnerabilityIcon = (ImageView)findViewById(R.id.upgradesMenuInvulnerabilityIcon);
		invulnerabilityIcon.setClickable(true);
		invulnerabilityIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER) {
					if (Upgrades.invulnerabilityUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(invulnerabilityButton, Upgrades.invulnerabilityUpgrade.toggleEnabled());
						Upgrades.invulnerabilityUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
			}
		});

		// drill powerup
		drillButton = (Button)findViewById(R.id.upgradesMenuDrillButton);
		toggleButtonColor(drillButton, Upgrades.drillUpgrade.isEnabled());
		drillButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
				intent.putExtra(Upgrades.UPGRADE_KEY, Upgrades.getUpgradeID(Upgrades.drillUpgrade));
				startActivity(intent);
			}
		});

		drillButton.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER) {
					if (Upgrades.drillUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(drillButton, Upgrades.drillUpgrade.toggleEnabled());
						Upgrades.drillUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
				return true;
			}
		});

		ImageView drillIcon = (ImageView)findViewById(R.id.upgradesMenuDrillIcon);
		drillIcon.setClickable(true);
		drillIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER) {
					if (Upgrades.drillUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(drillButton, Upgrades.drillUpgrade.toggleEnabled());
						Upgrades.drillUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
			}
		});

		// magnet powerup
		magnetButton = (Button)findViewById(R.id.upgradesMenuMagnetButton);

		if (Upgrades.magnetUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED) {
			toggleButtonColor(magnetButton, Upgrades.magnetUpgrade.isEnabled());
		} else {
			magnetButton.setText(magnetButton.getText() + "\n" + Util.getPointsString(Upgrades.POINTS_MAGNET_POWERUP) + " points");
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

		magnetButton.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (Upgrades.magnetUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED &&
						Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER)
				{
					if (Upgrades.magnetUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(magnetButton, Upgrades.magnetUpgrade.toggleEnabled());
						Upgrades.magnetUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
				return true;
			}
		});

		ImageView magnetIcon = (ImageView)findViewById(R.id.upgradesMenuMagnetIcon);
		magnetIcon.setClickable(true);
		magnetIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (Upgrades.magnetUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED &&
						Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER)
				{
					if (Upgrades.magnetUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(magnetButton, Upgrades.magnetUpgrade.toggleEnabled());
						Upgrades.magnetUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
			}
		});


		// black hole powerup
		blackHoleButton = (Button)findViewById(R.id.upgradesMenuBlackHoleButton);

		if (Upgrades.blackHoleUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED) {
			toggleButtonColor(blackHoleButton, Upgrades.blackHoleUpgrade.isEnabled());
		} else {
			blackHoleButton.setText(blackHoleButton.getText() + "\n" + Util.getPointsString(Upgrades.POINTS_BLACK_HOLE_POWERUP) + " points");
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

		blackHoleButton.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (Upgrades.blackHoleUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED &&
						Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER)
				{
					if (Upgrades.blackHoleUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(blackHoleButton, Upgrades.blackHoleUpgrade.toggleEnabled());
						Upgrades.blackHoleUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
				return true;
			}
		});

		ImageView blackHoleIcon = (ImageView)findViewById(R.id.upgradesMenuBlackHoleIcon);
		blackHoleIcon.setClickable(true);
		blackHoleIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (Upgrades.blackHoleUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED &&
						Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER)
				{
					if (Upgrades.blackHoleUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(blackHoleButton, Upgrades.blackHoleUpgrade.toggleEnabled());
						Upgrades.blackHoleUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
			}
		});



		// bumper powerup
		bumperButton = (Button)findViewById(R.id.upgradesMenuBumperButton);

		if (Upgrades.bumperUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED) {
			toggleButtonColor(bumperButton, Upgrades.bumperUpgrade.isEnabled());
		} else {
			bumperButton.setText(bumperButton.getText() + "\n" + Util.getPointsString(Upgrades.POINTS_BUMPER_POWERUP) + " points");
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

		bumperButton.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (Upgrades.bumperUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED &&
						Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER)
				{
					if (Upgrades.bumperUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(bumperButton, Upgrades.bumperUpgrade.toggleEnabled());
						Upgrades.bumperUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
				return true;
			}
		});

		ImageView bumperIcon = (ImageView)findViewById(R.id.upgradesMenuBumperIcon);
		bumperIcon.setClickable(true);
		bumperIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (Upgrades.bumperUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED &&
						Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER)
				{
					if (Upgrades.bumperUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(bumperButton, Upgrades.bumperUpgrade.toggleEnabled());
						Upgrades.bumperUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
			}
		});

		// bomb powerup
		bombButton = (Button)findViewById(R.id.upgradesMenuBombButton);

		if (Upgrades.bombUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED) {
			toggleButtonColor(bombButton, Upgrades.bombUpgrade.isEnabled());
		} else {
			bombButton.setText(bombButton.getText() + "\n" + Util.getPointsString(Upgrades.POINTS_BOMB_POWERUP) + " points");
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

		bombButton.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (Upgrades.bombUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED &&
						Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER)
				{
					if (Upgrades.bombUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(bombButton, Upgrades.bombUpgrade.toggleEnabled());
						Upgrades.bombUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
				return true;
			}
		});

		ImageView bombIcon = (ImageView)findViewById(R.id.upgradesMenuBombIcon);
		bombIcon.setClickable(true);
		bombIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (Upgrades.bombUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED &&
						Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER)
				{
					if (Upgrades.bombUpgrade.isEnabled() &&
							Upgrades.getNumPowerupsEnabled() == Upgrades.POWERUP_PICKER_MIN_POWERUPS)
					{
						showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
					} else {
						toggleButtonColor(bombButton, Upgrades.bombUpgrade.toggleEnabled());
						Upgrades.bombUpgrade.save(sharedPreferencesEditor);
						sharedPreferencesEditor.commit();
					}
				}
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

		Dialog dialog = null;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		switch(id) {
		case DIALOG_NOT_ENOUGH_POINTS:

			// get args
			final String title = args.getString(DIALOG_TITLE);

			alertDialogBuilder
			.setTitle("Not Enough Points")
			.setMessage("You do not have enough points to unlock the " + title + " powerup.")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					removeDialog(DIALOG_NOT_ENOUGH_POINTS);
				}
			});

			dialog = alertDialogBuilder.create();

			break;
		case DIALOG_CONFIRM_POWERUP_UNLOCK:

			// get args
			final String unlockTitle = args.getString(DIALOG_TITLE);
			final int points = args.getInt(DIALOG_POINTS);
			final int powerupUnlockUpgradeID = args.getInt(DIALOG_CONFIRM_POWERUP_UNLOCK_UPGRADE_ID);
			final int powerupUnlockButtonID = args.getInt(DIALOG_CONFIRM_POWERUP_BUTTON_ID);

			alertDialogBuilder
			.setTitle("Confirm Powerup")
			.setMessage("Are you sure you want to unlock the " + unlockTitle + " powerup for " + Util.getPointsString(points) + " points?")

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
						return;
					}

					upgrade.setLevel(Upgrades.POWERUP_UNLOCKED);

					// update button text
					Button button = (Button)findViewById(powerupUnlockButtonID);
					button.setText(button.getText().toString().replaceAll("-.*", ""));
					toggleButtonColor(button, upgrade.isEnabled());
					toggleButtonText(button);

					// update points
					Points.update(-points);

					// update points spent
					GlobalStatistics.getInstance().pointsSpent += points;

					// update points textview
					pointsView.setText(Points.getNumPoints() + " points");

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
		case DIALOG_MINIMUM_POWERUPS_SELECTED:
			alertDialogBuilder
			.setTitle("Unable to Deselect")
			.setMessage("You need to have at least " + Upgrades.POWERUP_PICKER_MIN_POWERUPS + " powerups enabled.")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					removeDialog(DIALOG_NOT_ENOUGH_POINTS);
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
	public void toggleButtonColor(Button button, boolean white) {

		if (white) {
			button.setTextColor(Color.BLACK);
			((TableRow)button.getParent()).setBackgroundColor(Color.WHITE);
		} else {
			button.setTextColor(Color.WHITE);
			((TableRow)button.getParent()).setBackgroundColor(Color.BLACK);
		}
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