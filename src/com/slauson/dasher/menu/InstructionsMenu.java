package com.slauson.dasher.menu;

import java.util.ArrayList;
import java.util.List;

import com.slauson.dasher.R;
import com.slauson.dasher.game.Game;
import com.slauson.dasher.game.GameActivity;
import com.slauson.dasher.game.GameView;
import com.slauson.dasher.instructions.Automator;
import com.slauson.dasher.instructions.Automator.AUTOMATOR_TYPE;
import com.slauson.dasher.instructions.InstructionScreen;
import com.slauson.dasher.instructions.InstructionScreen.REQUIRED_EVENT_TYPE;
import com.slauson.dasher.instructions.Position;
import com.slauson.dasher.instructions.Position.POSITION_TYPE;
import com.slauson.dasher.objects.Asteroid;
import com.slauson.dasher.objects.Drop;
import com.slauson.dasher.other.GameBaseActivity;
import com.slauson.dasher.status.Configuration;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class InstructionsMenu extends GameBaseActivity {

	// bundle flags
	public static final String BUNDLE_FLAG_TUTORIAL = "tutorial";
	
	private boolean tutorialMode;
	
	private ArrayList<InstructionScreen> instructionScreens;
	
	private int instructionScreenIndex;
	
	private TextView detailsTextView;
	private TextView previousButton;
	private TextView nextButton;
	
	/*
	 * Private constants
	 */
	private static final float ASTEROID_RADIUS_FACTOR = 0.05f;
	private static final float ASTEROID_OFFSET = 100;
	private static final int ASTEROID_DURATION = 5000;

	private static final float DROP_OFFSET = 100;
	private static final int DROP_DURATION = 10000;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.instructions_menu);
    	
    	game = new Game(this, true);
    	
    	// get bundle info to determine if from instructions menu vs tutorial
    	if (savedInstanceState != null) {
    		tutorialMode = savedInstanceState.getBoolean(BUNDLE_FLAG_TUTORIAL);
    	} else {
    		tutorialMode = false;
    	}
    	
    	instructionScreens = new ArrayList<InstructionScreen>();
    	
    	// set to -1 so that we transition to 0
    	instructionScreenIndex = -1;
    	
		gameView = (GameView)findViewById(R.id.instructionsGameView);
		gameView.setHeight(.6f, .2f);
		gameView.setGame(game);
		
		detailsTextView = (TextView)findViewById(R.id.instructionsMenuDetails);
		previousButton = (TextView)findViewById(R.id.instructionsMenuPreviousButton);
		nextButton = (TextView)findViewById(R.id.instructionsMenuNextButton);


		previousButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startNewScreen(false);
			}
		});
		
		nextButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startNewScreen(true);
			}
		});
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		
		switch(keyCode) {
		// pause game when menu/back/search is pressed
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}

		return super.onKeyUp(keyCode, event);
	}
	
	@Override
	public void update() {
		super.update();
		
		// update automators
		if (instructionScreenIndex >= 0 && instructionScreenIndex < instructionScreens.size()) {
			for (Automator automator : instructionScreens.get(instructionScreenIndex).getAutomators()) {
				if (automator.update()) {
					if (automator.getType() == AUTOMATOR_TYPE.DROP) {
						final Drop drop = game.dropPowerup(-1, -DROP_OFFSET, automator.getDropType());
						runOnUiThread(new Runnable() {
						     public void run() {
								detailsTextView.setText(instructionScreens.get(instructionScreenIndex).getDescriptionId(drop.getType()));
						    }
						});
					}
				}
			}
		}
		
		// TODO: check required user interaction
		// use local statistics?

	}

	@Override
	public void gameOver() {
		// TODO: reset ship
	}
	
	@Override
	public void init() {
		
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
		
		instructionScreen = new InstructionScreen(true, false, false, REQUIRED_EVENT_TYPE.AVOID_ASTEROIDS);
		instructionScreen.addDescriptionId(descriptionId);
		instructionScreen.setPlayerStatus(true, false);
		instructionScreen.setPlayerStartX(2*Game.canvasWidth/3);
		instructionScreen.setDropStatus(false);
		
		automator = new Automator(new Asteroid(ASTEROID_RADIUS_FACTOR, 0, ASTEROID_RADIUS_FACTOR, 0), AUTOMATOR_TYPE.ASTEROID);
		automator
			.addPosition(new Position(POSITION_TYPE.RESET, 2*Game.canvasWidth/3, -ASTEROID_OFFSET), 0)
			.addPosition(new Position(POSITION_TYPE.COORDINATE, 2*Game.canvasWidth/3, Game.canvasHeight + ASTEROID_OFFSET), ASTEROID_DURATION);
		instructionScreen.addAutomator(automator);
		
		automator = new Automator(new Asteroid(ASTEROID_RADIUS_FACTOR, 0, ASTEROID_RADIUS_FACTOR, 0), AUTOMATOR_TYPE.ASTEROID);
		automator
			.addPosition(new Position(POSITION_TYPE.DELAY_ONCE, Game.canvasWidth/3, -ASTEROID_OFFSET), ASTEROID_DURATION/2)
			.addPosition(new Position(POSITION_TYPE.RESET, Game.canvasWidth/3, -ASTEROID_OFFSET), 0)
			.addPosition(new Position(POSITION_TYPE.COORDINATE, Game.canvasWidth/3, Game.canvasHeight + ASTEROID_OFFSET), ASTEROID_DURATION);
		instructionScreen.addAutomator(automator);
		
		instructionScreens.add(instructionScreen);
		
		/*
		 * how to get powerups screen
		 */
		descriptionId = R.string.instructions_powerup_screen_description_slow;
		
		instructionScreen = new InstructionScreen(true, true, false, REQUIRED_EVENT_TYPE.ACTIVATE_POWERUPS);
		
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
		instructionScreen.setPlayerStatus(true, false);
		instructionScreen.setPlayerStartX(2*Game.canvasWidth/3);
		
		automator = new Automator(null, AUTOMATOR_TYPE.DROP);
		automator.addPosition(new Position(POSITION_TYPE.RESET, Game.canvasWidth/2, -DROP_OFFSET), DROP_DURATION);
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
		
		instructionScreen = new InstructionScreen(true, true, true, REQUIRED_EVENT_TYPE.DASH_ASTEROIDS);
		instructionScreen.addDescriptionId(descriptionId);
		instructionScreen.setPlayerStatus(false, true);
		instructionScreen.setPlayerStartX(Game.canvasWidth/2);
		instructionScreen.setDropStatus(false);
		
		automator = new Automator(new Asteroid(ASTEROID_RADIUS_FACTOR, 0, ASTEROID_RADIUS_FACTOR, 0), AUTOMATOR_TYPE.ASTEROID);
		automator
			.addPosition(new Position(POSITION_TYPE.RESET, Game.canvasWidth/2, -ASTEROID_OFFSET), 7500)
			.addPosition(new Position(POSITION_TYPE.COORDINATE, Game.canvasWidth/2, Game.canvasHeight + ASTEROID_OFFSET), 5000);
		instructionScreen.addAutomator(automator);
		
		instructionScreens.add(instructionScreen);
		
		// game objective screen
		
		// upgrades screen
		
		// achievements screen
		
		startNewScreen(true);
	}
	
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
		}
		
		// set description
		detailsTextView.setText(instructionScreens.get(instructionScreenIndex).getDescriptionId(0));

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
		
		// setup player status
		game.toggleMove(instructionScreens.get(instructionScreenIndex).getPlayerCanMove());
		game.toggleDash(instructionScreens.get(instructionScreenIndex).getPlayerCanDash());
		game.setPlayerStartX(instructionScreens.get(instructionScreenIndex).getPlayerStartX());
		
		// setup drops
		game.toggleDrops(instructionScreens.get(instructionScreenIndex).getDropsEnabled());
		
		// disable asteroid reset
		game.toggleAsteroidReset(false);
		
		// clear any asteroids
		game.clearAsteroids();
		
		// setup automators
		List<Automator> automators = instructionScreens.get(instructionScreenIndex).getAutomators();
		
		for (Automator automator : automators) {
			if (automator.getType() == AUTOMATOR_TYPE.ASTEROID) {
				game.addAsteroid(((Asteroid)automator.getItem()));
			} else if (automator.getType() == AUTOMATOR_TYPE.DROP) {
				game.dropPowerup(-1, -DROP_OFFSET, automator.getDropType());
			}
		}
		
		
	}
}