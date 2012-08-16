package com.slauson.dasher.menu;

import com.slauson.dasher.R;
import com.slauson.dasher.game.GameView;
import com.slauson.dasher.other.GameBaseActivity;

import android.os.Bundle;
import android.view.KeyEvent;

public class InstructionsMenu extends GameBaseActivity {

	// bundle flags
	public static final String BUNDLE_FLAG_TUTORIAL = "tutorial"; 
	
	// TODO: add stuff
	
	private boolean tutorialMode;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.instructions_menu);
    	
    	if (savedInstanceState != null) {
    		tutorialMode = savedInstanceState.getBoolean(BUNDLE_FLAG_TUTORIAL);
    	} else {
    		tutorialMode = false;
    	}
    	
		gameView = (GameView)findViewById(R.id.instructionsGameView);
		gameView.setHeight(.6f, .2f);
		gameView.setGame(game);

    	// TODO: get bundle info to determine if from instructions menu vs tutorial
    	
    	// TODO: set up screens, automators
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
	}

	public void gameOver() {
		// TODO: reset ship
	}
}