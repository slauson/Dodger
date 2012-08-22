package com.slauson.dasher.menu;

import java.util.ArrayList;

import com.slauson.dasher.R;
import com.slauson.dasher.game.Game;
import com.slauson.dasher.game.GameActivity;
import com.slauson.dasher.game.GameView;
import com.slauson.dasher.instructions.InstructionScreen;
import com.slauson.dasher.instructions.InstructionScreen.REQUIRED_EVENT_TYPE;
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
		
    	// set up screens
		setupScreens();
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
		
		// TODO: update automators
		
		// TODO: check required user interaction
		// use local statistics?

	}

	@Override
	public void gameOver() {
		// TODO: reset ship
	}
	
	private void setupScreens() {
		
		int descriptionId;
		InstructionScreen instructionScreen;
		
		// how to move screen
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
		
		instructionScreen = new InstructionScreen(descriptionId, true, false, false, REQUIRED_EVENT_TYPE.AVOID_ASTEROIDS);
		instructionScreen.setPlayerStatus(true, false);
		
		instructionScreens.add(instructionScreen);
		
		// how to dash screen
		
		// how to get powerups screen
		
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
		detailsTextView.setText(instructionScreens.get(instructionScreenIndex).getDescriptionId());

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
		
		// setup automators
		
	}
}