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
		smallButton.setOnClickListener(new UpgradeSelection(Upgrades.getUpgradeID(Upgrades.smallUpgrade), 0, R.id.upgradesMenuSmallButton));
		smallButton.setOnLongClickListener(new PowerupSelection(Upgrades.smallUpgrade, smallButton));

		ImageView smallIcon = (ImageView)findViewById(R.id.upgradesMenuSmallIcon);
		smallIcon.setClickable(true);
		smallIcon.setOnClickListener(new PowerupSelection(Upgrades.smallUpgrade, smallButton));

		// slow powerup
		slowButton = (Button)findViewById(R.id.upgradesMenuSlowButton);
		toggleButtonColor(slowButton, Upgrades.slowUpgrade.isEnabled());
		slowButton.setOnClickListener(new UpgradeSelection(Upgrades.getUpgradeID(Upgrades.slowUpgrade), 0, R.id.upgradesMenuSlowButton));
		slowButton.setOnLongClickListener(new PowerupSelection(Upgrades.slowUpgrade, slowButton));

		ImageView slowIcon = (ImageView)findViewById(R.id.upgradesMenuSlowIcon);
		slowIcon.setClickable(true);
		slowIcon.setOnClickListener(new PowerupSelection(Upgrades.slowUpgrade, slowButton));

		// invulnerability powerup
		invulnerabilityButton = (Button)findViewById(R.id.upgradesMenuInvulnerabilityButton);
		toggleButtonColor(invulnerabilityButton, Upgrades.invulnerabilityUpgrade.isEnabled());
		invulnerabilityButton.setOnClickListener(new UpgradeSelection(Upgrades.getUpgradeID(Upgrades.invulnerabilityUpgrade), 0, R.id.upgradesMenuInvulnerabilityButton));
		invulnerabilityButton.setOnLongClickListener(new PowerupSelection(Upgrades.invulnerabilityUpgrade, invulnerabilityButton));

		ImageView invulnerabilityIcon = (ImageView)findViewById(R.id.upgradesMenuInvulnerabilityIcon);
		invulnerabilityIcon.setClickable(true);
		invulnerabilityIcon.setOnClickListener(new PowerupSelection(Upgrades.invulnerabilityUpgrade, invulnerabilityButton));

		// drill powerup
		drillButton = (Button)findViewById(R.id.upgradesMenuDrillButton);
		toggleButtonColor(drillButton, Upgrades.drillUpgrade.isEnabled());
		drillButton.setOnClickListener(new UpgradeSelection(Upgrades.getUpgradeID(Upgrades.drillUpgrade), 0, R.id.upgradesMenuDrillButton));
		drillButton.setOnLongClickListener(new PowerupSelection(Upgrades.drillUpgrade, drillButton));

		ImageView drillIcon = (ImageView)findViewById(R.id.upgradesMenuDrillIcon);
		drillIcon.setClickable(true);
		drillIcon.setOnClickListener(new PowerupSelection(Upgrades.drillUpgrade, drillButton));

		// magnet powerup
		magnetButton = (Button)findViewById(R.id.upgradesMenuMagnetButton);

		if (Upgrades.magnetUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED) {
			toggleButtonColor(magnetButton, Upgrades.magnetUpgrade.isEnabled());
		} else {
			magnetButton.setText(magnetButton.getText() + "\n" + Util.getPointsString(Upgrades.POINTS_MAGNET_POWERUP) + " points");
		}

		magnetButton.setOnClickListener(new UpgradeSelection(Upgrades.getUpgradeID(Upgrades.magnetUpgrade), Upgrades.POINTS_MAGNET_POWERUP, R.id.upgradesMenuMagnetButton));
		magnetButton.setOnLongClickListener(new PowerupSelection(Upgrades.magnetUpgrade, magnetButton));

		ImageView magnetIcon = (ImageView)findViewById(R.id.upgradesMenuMagnetIcon);
		magnetIcon.setClickable(true);
		magnetIcon.setOnClickListener(new PowerupSelection(Upgrades.magnetUpgrade, magnetButton));

		// black hole powerup
		blackHoleButton = (Button)findViewById(R.id.upgradesMenuBlackHoleButton);

		if (Upgrades.blackHoleUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED) {
			toggleButtonColor(blackHoleButton, Upgrades.blackHoleUpgrade.isEnabled());
		} else {
			blackHoleButton.setText(blackHoleButton.getText() + "\n" + Util.getPointsString(Upgrades.POINTS_BLACK_HOLE_POWERUP) + " points");
		}

		blackHoleButton.setOnClickListener(new UpgradeSelection(Upgrades.getUpgradeID(Upgrades.blackHoleUpgrade), Upgrades.POINTS_BLACK_HOLE_POWERUP, R.id.upgradesMenuBlackHoleButton));
		blackHoleButton.setOnLongClickListener(new PowerupSelection(Upgrades.blackHoleUpgrade, blackHoleButton));

		ImageView blackHoleIcon = (ImageView)findViewById(R.id.upgradesMenuBlackHoleIcon);
		blackHoleIcon.setClickable(true);
		blackHoleIcon.setOnClickListener(new PowerupSelection(Upgrades.blackHoleUpgrade, blackHoleButton));

		// bumper powerup
		bumperButton = (Button)findViewById(R.id.upgradesMenuBumperButton);

		if (Upgrades.bumperUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED) {
			toggleButtonColor(bumperButton, Upgrades.bumperUpgrade.isEnabled());
		} else {
			bumperButton.setText(bumperButton.getText() + "\n" + Util.getPointsString(Upgrades.POINTS_BUMPER_POWERUP) + " points");
		}

		bumperButton.setOnClickListener(new UpgradeSelection(Upgrades.getUpgradeID(Upgrades.bumperUpgrade), Upgrades.POINTS_BUMPER_POWERUP, R.id.upgradesMenuBumperButton));
		bumperButton.setOnLongClickListener(new PowerupSelection(Upgrades.bumperUpgrade, bumperButton));

		ImageView bumperIcon = (ImageView)findViewById(R.id.upgradesMenuBumperIcon);
		bumperIcon.setClickable(true);
		bumperIcon.setOnClickListener(new PowerupSelection(Upgrades.bumperUpgrade, bumperButton));

		// bomb powerup
		bombButton = (Button)findViewById(R.id.upgradesMenuBombButton);

		if (Upgrades.bombUpgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED) {
			toggleButtonColor(bombButton, Upgrades.bombUpgrade.isEnabled());
		} else {
			bombButton.setText(bombButton.getText() + "\n" + Util.getPointsString(Upgrades.POINTS_BOMB_POWERUP) + " points");
		}

		bombButton.setOnClickListener(new UpgradeSelection(Upgrades.getUpgradeID(Upgrades.bombUpgrade), Upgrades.POINTS_BOMB_POWERUP, R.id.upgradesMenuBombButton));
		bombButton.setOnLongClickListener(new PowerupSelection(Upgrades.bombUpgrade, bombButton));

		ImageView bombIcon = (ImageView)findViewById(R.id.upgradesMenuBombIcon);
		bombIcon.setClickable(true);
		bombIcon.setOnClickListener(new PowerupSelection(Upgrades.bombUpgrade, bombButton));

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
	
	/**
	 * For handling upgrade selection
	 * @author josh
	 *
	 */
	public class UpgradeSelection implements OnClickListener {

		private int upgradeId;
		private int points;
		private int buttonId;
		
		public UpgradeSelection(int upgradeId, int points, int buttonId) {
			this.upgradeId = upgradeId;
			this.points = points;
			this.buttonId = buttonId;
		}
		
		public void onClick(View v) {
			// check if not unlocked
			if (Upgrades.getUpgrade(upgradeId).getLevel() == Upgrades.POWERUP_LOCKED) {

				Bundle bundle = new Bundle();
				bundle.putString(DIALOG_TITLE, Upgrades.getUpgrade(upgradeId).getDescription());
				bundle.putInt(DIALOG_POINTS, points);
				bundle.putInt(DIALOG_CONFIRM_POWERUP_UNLOCK_UPGRADE_ID, upgradeId);
				bundle.putInt(DIALOG_CONFIRM_POWERUP_BUTTON_ID, buttonId);

				// check if we have enough points
				if (Points.getNumPoints() < points) {
					showDialog(DIALOG_NOT_ENOUGH_POINTS, bundle);
				} else {
					showDialog(DIALOG_CONFIRM_POWERUP_UNLOCK, bundle);
				}
				return;
			}

			Intent intent = new Intent(UpgradesMenu.this, UpgradesSubMenu.class);
			intent.putExtra(Upgrades.UPGRADE_KEY, upgradeId);
			startActivity(intent);
		}
	}
	
	/**
	 * For handling powerup selection.
	 * @author josh
	 *
	 */
	public class PowerupSelection implements OnClickListener, OnLongClickListener {

		private Upgrade upgrade;
		private Button button;
		
		public PowerupSelection(Upgrade upgrade, Button button) {
			this.upgrade = upgrade;
			this.button = button;
		}
		public boolean onLongClick(View v) {
			togglePowerup();
			return false;
		}

		public void onClick(View v) {
			togglePowerup();
		}
		
		private void togglePowerup() {
			if (upgrade.getLevel() >= Upgrades.POWERUP_UNLOCKED &&
					Upgrades.otherUpgrade.getLevel() >= Upgrades.OTHER_POWERUP_PICKER)
			{
				if (upgrade.isEnabled() && Upgrades.getNumPowerupsEnabled() <= Upgrades.POWERUP_PICKER_MIN_POWERUPS) {
					showDialog(DIALOG_MINIMUM_POWERUPS_SELECTED);
				} else {
					toggleButtonColor(button, upgrade.toggleEnabled());
					upgrade.save(sharedPreferencesEditor);
					sharedPreferencesEditor.commit();
				}
			}
		}
	}
}