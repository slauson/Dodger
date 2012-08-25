package com.slauson.dasher.menu;

import java.util.ArrayList;
import java.util.List;

import com.slauson.dasher.R;
import com.slauson.dasher.game.Game;
import com.slauson.dasher.game.GameActivity;
import com.slauson.dasher.game.GameView;
import com.slauson.dasher.instructions.Automator;
import com.slauson.dasher.instructions.Automator.AutomatorType;
import com.slauson.dasher.instructions.InstructionScreen;
import com.slauson.dasher.instructions.InstructionScreen.RequiredEventType;
import com.slauson.dasher.instructions.Position;
import com.slauson.dasher.instructions.Position.PositionType;
import com.slauson.dasher.objects.Asteroid;
import com.slauson.dasher.objects.Drop;
import com.slauson.dasher.other.GameBaseActivity;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.Configuration;
import com.slauson.dasher.status.LocalStatistics;
import com.slauson.dasher.status.Upgrades;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class InstructionsMenu extends GameBaseActivity {

	// bundle flags
	public static final String BUNDLE_FLAG_TUTORIAL = "tutorial";
	
	/** True if in tutorial mode **/
	private boolean tutorialMode;
	
	/** List of instruction screens **/
	private ArrayList<InstructionScreen> instructionScreens;
	/** Current instruction screen index **/
	private int instructionScreenIndex;
	
	/** True if menu has been initialized **/
	private boolean initialized;
	
	private TextView detailsTextView;
	private TextView screenStatusTextView;
	private TextView previousButton;
	private TextView nextButton;
	private TextView upgradesButton;
	
	/*
	 * Public constants
	 */
	public static final float ASTEROID_RADIUS_FACTOR = 0.05f;
	
	/*
	 * Private constants
	 */
	private static final int ASTEROID_DURATION = 5000;

	private static final float DROP_OFFSET = 100;
	private static final int DROP_DURATION = 10000;
	
	private static final int REQUIREMENT_AVOID_ASTEROIDS_NUM = 5;
	private static final int REQUIREMENT_DASH_ASTEROIDS_NUM = 2;
	private static final int REQUIREMENT_ACTIVATE_POWERUPS_NUM = 2;
	private static final int REQUIREMENT_SURVIVE_NUM = 30;
	private static final int REQUIREMENT_PURCHASE_UPGRADE_NUM = 1;
	
	private static final int DIALOG_REQUIREMENT_NOT_COMPLETED = 0;

	private static final String REQUIREMENT_NOT_COMPLETED_TITLE = "Goal Not Completed";
	private static final String REQUIREMENT_NOT_COMPLETED_MESSAGE = "Hit Ok to keep trying, Skip to move on, or Skip All to start the game.";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.instructions_menu);
    	
    	Game.reset();
    	game = new Game(this, true);
    	
    	// get bundle info to determine if from instructions menu vs tutorial
    	Bundle extras = getIntent().getExtras();
    	if (extras != null) {
    		tutorialMode = extras.getBoolean(BUNDLE_FLAG_TUTORIAL);
    	} else {
    		tutorialMode = false;
    	}
    	
    	instructionScreens = new ArrayList<InstructionScreen>();

    	// change title
    	if (tutorialMode) {
    		((TextView)findViewById(R.id.instructionsMenuTitle)).setText(R.string.instructions_tutorial);
    	}
    	
    	// set to -1 so that we transition to 0
    	instructionScreenIndex = -1;
    	
		gameView = (GameView)findViewById(R.id.instructionsGameView);
		gameView.setHeight(.6f, .2f);
		gameView.setGame(game);
		
		detailsTextView = (TextView)findViewById(R.id.instructionsMenuDetails);
		screenStatusTextView = (TextView)findViewById(R.id.instructionsMenuScreenStatus);
		previousButton = (TextView)findViewById(R.id.instructionsMenuPreviousButton);
		nextButton = (TextView)findViewById(R.id.instructionsMenuNextButton);
		upgradesButton = (TextView)findViewById(R.id.instructionsMenuUpgradesButton);


		previousButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startNewScreen(false);
			}
		});
		
		nextButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (tutorialMode && !instructionScreens.get(instructionScreenIndex).getRequirementCompleted()) {
					game.togglePause(true);
					showDialog(DIALOG_REQUIREMENT_NOT_COMPLETED, null);
				} else {
					startNewScreen(true);
				}
			}
		});
		
		upgradesButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(InstructionsMenu.this, UpgradesMenu.class);
				startActivity(intent);
			}
		});
		
		initialized = false;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if (game.isInitialized()) {
			game.togglePause(false);
		}
		
		// update completion number if we are checking for upgrades
		if (instructionScreenIndex >= 0 && instructionScreens.get(instructionScreenIndex).getEventType() == RequiredEventType.PURCHASE_UPGRADE &&
				Upgrades.getNumUpgradesPurchased() > 0)
		{
			instructionScreens.get(instructionScreenIndex).incrementCompletionNum();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		game.togglePause(true);
		
		// reset achievements
		Achievements.resetLocalAchievements();
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		
		switch(keyCode) {
		// pause game when menu/back/search is pressed
		case KeyEvent.KEYCODE_BACK:
			if (instructionScreenIndex > 0) {
				startNewScreen(false);
			} else {
				finish();
			}
			break;
		}

		return super.onKeyUp(keyCode, event);
	}
	
	@Override
	public Dialog onCreateDialog(int id, Bundle args) {

		Dialog dialog = null;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		switch(id) {
		case DIALOG_REQUIREMENT_NOT_COMPLETED:
			alertDialogBuilder
				.setTitle(REQUIREMENT_NOT_COMPLETED_TITLE)
				.setMessage(getRequirementText() + "\n" + REQUIREMENT_NOT_COMPLETED_MESSAGE)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						removeDialog(DIALOG_REQUIREMENT_NOT_COMPLETED);
						game.togglePause(false);
					}
				})
				.setNeutralButton("Skip", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						removeDialog(DIALOG_REQUIREMENT_NOT_COMPLETED);
						startNewScreen(true);
						game.togglePause(false);
					}
				})
				.setNegativeButton("Skip All", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						removeDialog(DIALOG_REQUIREMENT_NOT_COMPLETED);
						// go to game
						if (tutorialMode) {
							Intent intent = new Intent(InstructionsMenu.this, GameActivity.class);
							startActivity(intent);
						}
						// go back
						else {
							finish();
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
	public void update() {
		super.update();
		
		// update automators
		if (instructionScreenIndex < 0 || instructionScreenIndex >= instructionScreens.size()) {
			return;
		}
		
		for (Automator automator : instructionScreens.get(instructionScreenIndex).getAutomators()) {
			if (automator.update()) {
				// create drop
				if (automator.getType() == AutomatorType.DROP) {
					final Drop drop = game.dropPowerup(-1, -DROP_OFFSET, automator.getDropType());
					runOnUiThread(new Runnable() {
						public void run() {
							// update details
							instructionScreens.get(instructionScreenIndex).setDescriptionIdIndex(drop.getType());
							setDetailsText();
					    }
					});
				} 
				// move asteroid to player's x coordinate
				else if (automator.getType() == AutomatorType.ASTEROID) {
					automator.getItem().setX(game.getPlayerX());
				}
			}
		}
		
		if (tutorialMode) {
			// check requirements
			RequiredEventType requirement = instructionScreens.get(instructionScreenIndex).getEventType();
			
			// avoiding asteroids requirement
			if (requirement == RequiredEventType.AVOID_ASTEROIDS) {
				for (Automator automator : instructionScreens.get(instructionScreenIndex).getAutomators()) {
					if (automator.getType() == AutomatorType.ASTEROID) {
						if (Game.direction == Game.DIRECTION_NORMAL) {
							// toggle flag when asteroid is reset
							if (automator.getItem().getY() < 0) {
								instructionScreens.get(instructionScreenIndex).toggleFlag(true);
							}
							// mark as avoided when asteroid goes off other side of screen
							else if (automator.getItem().getY() > Game.canvasHeight &&
									instructionScreens.get(instructionScreenIndex).getFlag() &&
									((Asteroid)automator.getItem()).getStatus() == Asteroid.STATUS_NORMAL)
							{
								instructionScreens.get(instructionScreenIndex).toggleFlag(false);
								instructionScreens.get(instructionScreenIndex).incrementCompletionNum();
							}
						} else {
							// toggle flag when asteroid is reset
							if (automator.getItem().getY() > Game.canvasHeight) {
								instructionScreens.get(instructionScreenIndex).toggleFlag(true);
							}
							// mark as avoided when asteroid goes off other side of screen
							else if (automator.getItem().getY() < 0 &&
									instructionScreens.get(instructionScreenIndex).getFlag() &&
									((Asteroid)automator.getItem()).getStatus() == Asteroid.STATUS_NORMAL)
							{
								instructionScreens.get(instructionScreenIndex).toggleFlag(false);
								instructionScreens.get(instructionScreenIndex).incrementCompletionNum();
							}
						}
					}
				}
			}
			// dash through asteroids requirement
			else if (requirement == RequiredEventType.DASH_ASTEROIDS) {
				if (LocalStatistics.getInstance().asteroidsDestroyedByDash > instructionScreens.get(instructionScreenIndex).getCompletionNum()) {
					instructionScreens.get(instructionScreenIndex).incrementCompletionNum();
				}
			}
			// activating powerups requirement
			else if (requirement == RequiredEventType.ACTIVATE_POWERUPS) {
				if (LocalStatistics.getInstance().getTotalUses() > instructionScreens.get(instructionScreenIndex).getCompletionNum()) {
					instructionScreens.get(instructionScreenIndex).incrementCompletionNum();
				}
			} else if (requirement == RequiredEventType.SURVIVE) {
				long duration = System.currentTimeMillis() - instructionScreens.get(instructionScreenIndex).getLastResetTime();
				
				if (((int)(duration/1000)) > instructionScreens.get(instructionScreenIndex).getCompletionNum()) {
					instructionScreens.get(instructionScreenIndex).incrementCompletionNum();
				}
				
			} else if (requirement == RequiredEventType.PURCHASE_UPGRADE) {
				
			}
			
			// check if requirement completion needs to be updated
			if (instructionScreens.get(instructionScreenIndex).getCompletionUpdate()) {
				runOnUiThread(new Runnable() {
					public void run() {
						// update details
						setDetailsText();
				    }
				});
			}
		}
	}

	@Override
	public void gameOver() {
		// reset last reset time
		instructionScreens.get(instructionScreenIndex).resetLastResetTime();
		
		// reset completion number in survive mode
		if (instructionScreens.get(instructionScreenIndex).getEventType() == RequiredEventType.SURVIVE) {
			instructionScreens.get(instructionScreenIndex).resetCompletionNum();
		}
	}
	
	@Override
	public void init() {
	
		// only initialize once, since this gets called from Game.java on surface view creation
		if (initialized) {
			return;
		}
		
		int descriptionId;
		InstructionScreen instructionScreen;
		Automator automator;
		
		/*
		 * how to move screen
		 */
		switch(Configuration.controlType) {
		case Configuration.CONTROL_ACCELEROMETER:
			descriptionId = R.string.instructions_move_screen_description_accelerometer;
			break;
		case Configuration.CONTROL_KEYBOARD:
			descriptionId = R.string.instructions_move_screen_description_keyboard;
			break;
		case Configuration.CONTROL_TOUCH:
		default:
			descriptionId = R.string.instructions_move_screen_description_touch;
			break;
		}
		
		instructionScreen = new InstructionScreen(R.string.instructions_move_screen_description_more,
				R.string.instructions_move_screen_description_requirement, REQUIREMENT_AVOID_ASTEROIDS_NUM,
				true, false, false, RequiredEventType.AVOID_ASTEROIDS);
		instructionScreen.addDescriptionId(descriptionId);
		instructionScreen.setPlayerStatus(true, true, false);
		instructionScreen.setPlayerStartX(Game.canvasWidth/2);
		instructionScreen.setDropStatus(false);
		
		automator = new Automator(new Asteroid(ASTEROID_RADIUS_FACTOR, 0, ASTEROID_RADIUS_FACTOR, 0), AutomatorType.ASTEROID);
		automator
			.addPosition(new Position(PositionType.RESET_PLAYER_X, Game.canvasWidth/2, -2*automator.getItem().getHeight()), 0)
			.addPosition(new Position(PositionType.COORDINATE, -1, Game.canvasHeight + 2*automator.getItem().getHeight()), ASTEROID_DURATION);
		instructionScreen.addAutomator(automator);
		
		instructionScreens.add(instructionScreen);
		
		/*
		 * how to get powerups screen
		 */
		instructionScreen = new InstructionScreen(R.string.instructions_powerup_screen_description_more,
				R.string.instructions_powerup_screen_description_requirement, REQUIREMENT_ACTIVATE_POWERUPS_NUM,
				true, true, false, RequiredEventType.ACTIVATE_POWERUPS);
		
		// these must match the constants in Game.java
		instructionScreen.addDescriptionId(R.string.instructions_powerup_screen_description_small); // since powerup ids start at 1
		instructionScreen.addDescriptionId(R.string.instructions_powerup_screen_description_small);
		instructionScreen.addDescriptionId(R.string.instructions_powerup_screen_description_slow);
		instructionScreen.addDescriptionId(R.string.instructions_powerup_screen_description_invulnerability);
		instructionScreen.addDescriptionId(R.string.instructions_powerup_screen_description_drill);
		instructionScreen.addDescriptionId(R.string.instructions_powerup_screen_description_magnet);
		instructionScreen.addDescriptionId(R.string.instructions_powerup_screen_description_black_hole);
		instructionScreen.addDescriptionId(R.string.instructions_powerup_screen_description_bumper);
		instructionScreen.addDescriptionId(R.string.instructions_powerup_screen_description_bomb);
		instructionScreen.setPlayerStatus(true, true, false);
		instructionScreen.setPlayerStartX(Game.canvasWidth/2);
		
		automator = new Automator(null, AutomatorType.DROP);
		automator.addPosition(new Position(PositionType.RESET, Game.canvasWidth/2, -DROP_OFFSET), DROP_DURATION);
		instructionScreen.addAutomator(automator);
		
		instructionScreens.add(instructionScreen);
		
		/*
		 * how to dash screen
		 */
		switch(Configuration.controlType) {
		case Configuration.CONTROL_ACCELEROMETER:
			descriptionId = R.string.instructions_dash_screen_description_accelerometer;
			break;
		case Configuration.CONTROL_KEYBOARD:
			descriptionId = R.string.instructions_dash_screen_description_keyboard;
			break;
		case Configuration.CONTROL_TOUCH:
		default:
			descriptionId = R.string.instructions_dash_screen_description_touch;
			break;
		}
		
		instructionScreen = new InstructionScreen(R.string.instructions_dash_screen_description_more,
				R.string.instructions_dash_screen_description_requirement, REQUIREMENT_DASH_ASTEROIDS_NUM,
				true, true, false, RequiredEventType.DASH_ASTEROIDS);
		instructionScreen.addDescriptionId(descriptionId);
		instructionScreen.setPlayerStatus(true, false, true);
		instructionScreen.setPlayerStartX(Game.canvasWidth/2);
		instructionScreen.setDropStatus(false);
		
		automator = new Automator(new Asteroid(ASTEROID_RADIUS_FACTOR, 0, ASTEROID_RADIUS_FACTOR, 0), AutomatorType.ASTEROID);
		automator
			.addPosition(new Position(PositionType.RESET, Game.canvasWidth/2, -2*automator.getItem().getHeight()), 7500)
			.addPosition(new Position(PositionType.COORDINATE, -1, Game.canvasHeight + 2*automator.getItem().getHeight()), ASTEROID_DURATION);
		instructionScreen.addAutomator(automator);
		
		instructionScreens.add(instructionScreen);
		
		/*
		 * game objective screen
		 */
		instructionScreen = new InstructionScreen(R.string.instructions_objective_screen_description_more,
				R.string.instructions_objective_screen_description_requirement, REQUIREMENT_SURVIVE_NUM,
				true, true, !tutorialMode, RequiredEventType.SURVIVE);
		instructionScreen.addDescriptionId(R.string.instructions_objective_screen_description);
		instructionScreen.setPlayerStartX(Game.canvasWidth/2);
		
		automator = new Automator(new Asteroid(ASTEROID_RADIUS_FACTOR, 0, ASTEROID_RADIUS_FACTOR, 0), AutomatorType.ASTEROID);
		automator
			.addPosition(new Position(PositionType.RESET_PLAYER_X, Game.canvasWidth/2, -2*automator.getItem().getHeight()), 0)
			.addPosition(new Position(PositionType.COORDINATE, -1, Game.canvasHeight + 2*automator.getItem().getHeight()), ASTEROID_DURATION);
		instructionScreen.addAutomator(automator);
		
		automator = new Automator(new Asteroid(ASTEROID_RADIUS_FACTOR, 0, ASTEROID_RADIUS_FACTOR, 0), AutomatorType.ASTEROID);
		automator
			.addPosition(new Position(PositionType.RESET, Game.canvasWidth/4, -2*automator.getItem().getHeight()), 0)
			.addPosition(new Position(PositionType.DELAY_RANDOM, -1, -1), ASTEROID_DURATION/4)
			.addPosition(new Position(PositionType.COORDINATE, -1, Game.canvasHeight + 2*automator.getItem().getHeight()), ASTEROID_DURATION);
		instructionScreen.addAutomator(automator);
		
		automator = new Automator(new Asteroid(ASTEROID_RADIUS_FACTOR, 0, ASTEROID_RADIUS_FACTOR, 0), AutomatorType.ASTEROID);
		automator
			.addPosition(new Position(PositionType.RESET, 2*Game.canvasWidth/4, -2*automator.getItem().getHeight()), 0)
			.addPosition(new Position(PositionType.DELAY_RANDOM, -1, -1), ASTEROID_DURATION/4)
			.addPosition(new Position(PositionType.COORDINATE, -1, Game.canvasHeight + 2*automator.getItem().getHeight()), ASTEROID_DURATION);
		instructionScreen.addAutomator(automator);
		
		automator = new Automator(new Asteroid(ASTEROID_RADIUS_FACTOR, 0, ASTEROID_RADIUS_FACTOR, 0), AutomatorType.ASTEROID);
		automator
			.addPosition(new Position(PositionType.RESET, 3*Game.canvasWidth/4, -2*automator.getItem().getHeight()), 0)
			.addPosition(new Position(PositionType.DELAY_RANDOM, -1, -1), ASTEROID_DURATION/4)
			.addPosition(new Position(PositionType.COORDINATE, -1, Game.canvasHeight + 2*automator.getItem().getHeight()), ASTEROID_DURATION);
		instructionScreen.addAutomator(automator);
	
		instructionScreens.add(instructionScreen);
		
		/*
		 * upgrades screen
		 */
		// only add in tutorial mode
		if (tutorialMode) {
			instructionScreen = new InstructionScreen(R.string.instructions_upgrades_screen_description_more,
					R.string.instructions_upgrades_screen_description_requirement, REQUIREMENT_PURCHASE_UPGRADE_NUM,
					true, true, true, RequiredEventType.PURCHASE_UPGRADE);
			instructionScreen.addDescriptionId(R.string.instructions_upgrades_screen_description);
			instructionScreen.setPlayerStartX(Game.canvasWidth/2);
			instructionScreen.setPlayerStatus(false, false, true);
			instructionScreen.setUpgradesButtonVisibility(View.VISIBLE);
			
			instructionScreens.add(instructionScreen);
		}
		
		initialized = true;
		startNewScreen(true);
	}
	
	/**
	 * Goes to next instruction screen
	 * @param next true if going to next screen, otherwise previous
	 */
	private void startNewScreen(boolean next) {

		if (next) {
			instructionScreenIndex++;
		} else {
			instructionScreenIndex--;
		}
		
		// check if we are at end
		if (instructionScreenIndex >= instructionScreens.size()) {

			// go to game
			if (tutorialMode) {
				Intent intent = new Intent(this, GameActivity.class);
				startActivity(intent);
			}
			// go back
			else {
				finish();
			}
			return;
		}
		
		// check if we are past beginning
		if (instructionScreenIndex < 0) {
			instructionScreenIndex = 0;
			return;
		}
		
		// reset statistics used for requirements
		if (tutorialMode) {
	    	LocalStatistics.getInstance().reset();
		}
		
		// reset last reset time
		instructionScreens.get(instructionScreenIndex).resetLastResetTime();
		
		// set description
		setDetailsText();

		// set instruction screen status
		screenStatusTextView.setText((instructionScreenIndex + 1) + "/" + instructionScreens.size());
		
		// set previous button text
		if (instructionScreens.get(instructionScreenIndex).getPreviousButtonEnabled()) {
			previousButton.setText(R.string.instructions_previous);
		} else {
			previousButton.setText("");
		}
		
		// set next button text
		if (instructionScreens.get(instructionScreenIndex).getFinishButtonEnabled()) {
			nextButton.setText(R.string.instructions_finish);
		} else {
			nextButton.setText(R.string.instructions_next);
		}
		
		// toggle upgrades button
		upgradesButton.setVisibility(instructionScreens.get(instructionScreenIndex).getUpgradesButtonVisibility());
		
		// setup player status
		game.togglePlayerVisible(instructionScreens.get(instructionScreenIndex).getPlayerVisible());
		game.toggleMove(instructionScreens.get(instructionScreenIndex).getPlayerCanMove());
		game.toggleDash(instructionScreens.get(instructionScreenIndex).getPlayerCanDash());
		game.setPlayerStartX(instructionScreens.get(instructionScreenIndex).getPlayerStartX());
		
		// setup drops
		game.toggleDrops(instructionScreens.get(instructionScreenIndex).getDropsEnabled());
		
		// clear any asteroids
		game.clearAsteroids();
		
		// setup automators
		List<Automator> automators = instructionScreens.get(instructionScreenIndex).getAutomators();
		
		for (Automator automator : automators) {
			if (automator.getType() == AutomatorType.ASTEROID) {
				game.addAsteroid(((Asteroid)automator.getItem()));
			} else if (automator.getType() == AutomatorType.DROP) {
				game.dropPowerup(-1, -DROP_OFFSET, automator.getDropType());
			}
		}
	}
	
	/**
	 * Sets details text of instruction screen
	 */
	private void setDetailsText() {
		String details = getString(instructionScreens.get(instructionScreenIndex).getDescriptionId());
		
		// only update in tutorial mode if forced or requirement status has been updated
		if (tutorialMode) {
			details += "\n" + String.format(getString(instructionScreens.get(instructionScreenIndex).getDescriptionRequirementId(),
					instructionScreens.get(instructionScreenIndex).getRequirementNum())) +
					instructionScreens.get(instructionScreenIndex).getCompletionStatusString();
		} else {
			details += "\n" + getString(instructionScreens.get(instructionScreenIndex).getDescriptionDefaultId());
		}
		detailsTextView.setText(details);
	}
	
	/**
	 * Returns requirement text for instruction screen
	 * @return requirement text for instruction screen
	 */
	private String getRequirementText() {
		return String.format(getString(instructionScreens.get(instructionScreenIndex).getDescriptionRequirementId(),
				instructionScreens.get(instructionScreenIndex).getRequirementNum())) +
				instructionScreens.get(instructionScreenIndex).getCompletionStatusString();
	}
}