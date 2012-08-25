package com.slauson.dasher.menu;

import com.slauson.dasher.R;
import com.slauson.dasher.game.GameActivity;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.Configuration;
import com.slauson.dasher.status.Debugging;
import com.slauson.dasher.status.GlobalStatistics;
import com.slauson.dasher.status.HighScores;
import com.slauson.dasher.status.Points;
import com.slauson.dasher.status.Upgrades;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main menu
 * 
 * adapted from here: http://www.droidnova.com/creating-game-menus-in-android,518.html
 * 
 * @author Josh Slauson
 *
 */
public class MainMenu extends PaidDialogBaseMenu {
	
	/** Duration of short toast notification **/
	private static final int TOAST_LENGTH_SHORT = 2000;
	
	private static final CharSequence[] GAME_MODES = {"Basic", "Normal", "Hard"};
	
	/** Button for starting game or high scores **/
	private Button startHighScoresButton;
	/** Button for instructions or achievements **/
	private Button instructionsAchievementsButton;
	/** Button for options or statistics **/
	private Button optionsStatisticsButton;
	/** Button for more or upgrades **/
	private Button moreUpgradesButton;
	/** Button for quit or back **/
	private Button quitBackButton;
	
	/** True when more options are being shown **/
	private boolean showingMore;
	
	/** Time back button must be pressed again by to quit game **/
	private long backButtonQuitEndTime;
	
	/** Game mode dialog **/
	private static final int DIALOG_GAME_MODE = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		showingMore = false;
		backButtonQuitEndTime = 0;
		
		// debugging menu
		TextView title = (TextView)findViewById(R.id.mainMenuTitle);
		title.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this, DebuggingMenu.class);
				startActivity(intent);
			}
		});
		

		// start/high scores button
		startHighScoresButton = (Button)findViewById(R.id.mainMenuStartHighScoresButton);
		startHighScoresButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (!showingMore) {
					// if first time playing, show tutorial
					if (GlobalStatistics.getInstance().timesPlayed == 0) {
						Intent intent = new Intent(MainMenu.this, InstructionsMenu.class);
						intent.putExtra(InstructionsMenu.BUNDLE_FLAG_TUTORIAL, true);
						startActivity(intent);
					} else {
						Intent intent = new Intent(MainMenu.this, GameActivity.class);
						startActivity(intent);
					}
				} else {
					Intent intent = new Intent(MainMenu.this, HighScoresMenu.class);
					startActivity(intent);
				}
			}
		});
		startHighScoresButton.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				if (!showingMore) {
					// show dialog with different options
					showDialog(DIALOG_GAME_MODE);
				}
				return true;
			}
		});
		
		
		// instructions/achievements button
		instructionsAchievementsButton = (Button)findViewById(R.id.mainMenuInstructionsAchievementsButton);
		instructionsAchievementsButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (!showingMore) {
					Intent intent = new Intent(MainMenu.this, InstructionsMenu.class);
					startActivity(intent);
				} else {
					// check if free version
					if (Configuration.freeVersion) {
						Bundle bundle = new Bundle();
						bundle.putInt(DIALOG_EXTRA_PAID_FEATURE, R.string.menu_achievements);
						showDialog(DIALOG_PAID_VERSION, bundle);
					} else {
						Intent intent = new Intent(MainMenu.this, AchievementsMenu.class);
						startActivity(intent);
					}
				}
			}
		});

		// options/statistics button
		optionsStatisticsButton = (Button)findViewById(R.id.mainMenuOptionsStatisticsButton);
		optionsStatisticsButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (!showingMore) {
					Intent intent = new Intent(MainMenu.this, OptionsMenu.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(MainMenu.this, StatisticsMenu.class);
					startActivity(intent);
				}
			}
		});
		
		// more/upgrades button
		moreUpgradesButton = (Button)findViewById(R.id.mainMenuMoreUpgradesButton);
		moreUpgradesButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (!showingMore) {
					toggleShowMore();
				} else {
					// check if free version
					if (Configuration.freeVersion) {
						Bundle bundle = new Bundle();
						bundle.putInt(DIALOG_EXTRA_PAID_FEATURE, R.string.menu_upgrades);
						showDialog(DIALOG_PAID_VERSION, bundle);
					} else {
						Intent intent = new Intent(MainMenu.this, UpgradesMenu.class);
						startActivity(intent);
					}
				}
			}
		});
				
		// quit/back/debug button
		quitBackButton = (Button)findViewById(R.id.mainMenuQuitBackButton);
		quitBackButton.setOnClickListener(new OnClickListener() {
			
			// quit application
			// adapted from here: http://stackoverflow.com/questions/2964310/quitting-application-in-android
			public void onClick(View v) {
				if (!showingMore) {
					android.os.Process.killProcess(android.os.Process.myPid());
				} else {
					toggleShowMore();
				}
			}
		});
		
		System.out.println("MainMenu::onCreate()");
		
		// load saved state
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		// load configuration
		loadConfiguration(sharedPreferences);
				
		// load achievements
		if (!Achievements.initialized()) {
			Achievements.load(sharedPreferences);
		}
		
		// load upgrades
		if (!Upgrades.initialized()) {
			Upgrades.load(sharedPreferences);
		}
		
		// load points
		if (!Points.initialized()) {
			Points.load(sharedPreferences);
		}
		
		// load stats
		if (!GlobalStatistics.initialized()) {
			GlobalStatistics.load(sharedPreferences);
		}
		
		// load high scores
		if (!HighScores.initialized()) {
			HighScores.load(sharedPreferences);
		}
		
		// load debugging info
		if (!Debugging.initialized()) {
			Debugging.load(sharedPreferences);
		}
	}
	
	@Override
	public void onResume() {
		
		super.onResume();
		
		System.out.println("MainMenu::onResume()");
		
	}
	
	@Override
	public Dialog onCreateDialog(int id, Bundle args) {

		Dialog dialog = super.onCreateDialog(id, args);
		if (dialog != null) {
			return dialog;
		}
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		switch(id) {
		case DIALOG_GAME_MODE:
			alertDialogBuilder
				.setTitle("Select Game Mode")
				.setItems(GAME_MODES, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						removeDialog(DIALOG_GAME_MODE);
						
						// if first time playing, show tutorial
						if (GlobalStatistics.getInstance().timesPlayed == 0) {
							Intent intent = new Intent(MainMenu.this, InstructionsMenu.class);
							intent.putExtra(InstructionsMenu.BUNDLE_FLAG_TUTORIAL, true);
							startActivity(intent);
						} else {
							int gameMode = GameActivity.GAME_MODE_NORMAL;
							switch(which) {
							case 0:
								if (Configuration.freeVersion) {
									Bundle bundle = new Bundle();
									bundle.putInt(DIALOG_EXTRA_PAID_FEATURE, R.string.menu_game_modes);
									showDialog(DIALOG_PAID_VERSION, bundle);
									return;
								}
								gameMode = GameActivity.GAME_MODE_BASIC;
								break;
							case 1:
								gameMode = GameActivity.GAME_MODE_NORMAL;
								break;
							case 2:
								if (Configuration.freeVersion) {
									Bundle bundle = new Bundle();
									bundle.putInt(DIALOG_EXTRA_PAID_FEATURE, R.string.menu_game_modes);
									showDialog(DIALOG_PAID_VERSION, bundle);
									return;
								}
								gameMode = GameActivity.GAME_MODE_HARD;
								break;
							}
							
							Intent intent = new Intent(MainMenu.this, GameActivity.class);
							intent.putExtra(GameActivity.BUNDLE_FLAG_GAME_MODE, gameMode);
							startActivity(intent);
						}
					}
				});
			dialog = alertDialogBuilder.create();
			break;
		default:
			break;
		}
			
		return dialog;
	}

	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		
		// TODO: do something better 
		// override back button so that user cannot go back to game
		switch(keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// go back to "main" main menu
			if (showingMore) {
				toggleShowMore();
			} else {
				if (System.currentTimeMillis() < backButtonQuitEndTime) {
					android.os.Process.killProcess(android.os.Process.myPid());
				} else {
					
					Toast.makeText(this, "Press again to quit", Toast.LENGTH_SHORT).show();
					
					backButtonQuitEndTime = System.currentTimeMillis() + TOAST_LENGTH_SHORT;
				}
			}
			return true;
		}
		
		// let other buttons go through
		super.onKeyUp(keyCode, event);
		return true;
	}

	/**
	 * Loads configuration
	 * @param sharedPreferences shared preferences to load from
	 */
	private void loadConfiguration(SharedPreferences sharedPreferences) {
		Configuration.load(sharedPreferences);
	}
	
	/**
	 * Toggles more options
	 */
	private void toggleShowMore() {
		if (showingMore) {
			
			startHighScoresButton.setText(R.string.menu_start);
			instructionsAchievementsButton.setText(R.string.menu_instructions);
			optionsStatisticsButton.setText(R.string.menu_options);
			moreUpgradesButton.setText(R.string.menu_more);
			quitBackButton.setText(R.string.menu_quit);
			
			showingMore = false;
		} else {
			
			startHighScoresButton.setText(R.string.menu_high_scores);
			instructionsAchievementsButton.setText(R.string.menu_achievements);
			optionsStatisticsButton.setText(R.string.menu_statistics);
			moreUpgradesButton.setText(R.string.menu_upgrades);
			quitBackButton.setText(R.string.menu_back);
			
			showingMore = true;
		}
	}
}