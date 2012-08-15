package com.slauson.dasher.menu;

import com.slauson.dasher.R;
import com.slauson.dasher.game.Game;
import com.slauson.dasher.game.GameThread;
import com.slauson.dasher.game.GameView;
import com.slauson.dasher.game.Accelerometer;
import com.slauson.dasher.other.GameBaseActivity;
import com.slauson.dasher.other.GameThreadCallback;

import android.os.Bundle;

public class InstructionsMenu extends GameBaseActivity implements GameThreadCallback {
	
	/** Game **/
	private Game game;

	/** Game view **/
	private GameView gameView;

	/** Game thread **/
	private GameThread gameThread;
	
	/** Accelerometer **/
	private Accelerometer accelerometer;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.instructions_menu);
    	
    	game = new Game(this);
		
		gameView = (GameView)findViewById(R.id.gameView);
		gameView.setGame(game);

		accelerometer = new Accelerometer(this);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void update() {
		// TODO Auto-generated method stub
	}

	public void gameOver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAccelerometer(float tx, float ty) {
		// TODO Auto-generated method stub
		
	}
}