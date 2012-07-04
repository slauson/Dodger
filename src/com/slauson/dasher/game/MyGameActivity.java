package com.slauson.dasher.game;

import com.slauson.dasher.R;
import com.slauson.dasher.main.MainMenu;
import com.slauson.dasher.main.OptionsMenu;
import com.slauson.dasher.status.Configuration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Game activity
 * @author Josh Slauson
 *
 */
public class MyGameActivity extends Activity {

	private MyGameView myGameView;

	private MyAccelerometer myAccelerometer;

	private Button pauseMenuResumeButton, pauseMenuOptionsButton, pauseMenuQuitButton;

	private Button gameOverMenuRetryButton, gameOverMenuStatisticsButton, gameOverMenuUpgradesButton, gameOverMenuQuitButton;
	
	private int mode;
	
	private boolean quitting;
	
	private int MODE_RUNNING = 0;
	private int MODE_PAUSED = 1;
	private int MODE_GAME_OVER = 2;


	// Handler for updating debug text
	// http://stackoverflow.com/questions/5097267/error-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-view
	//Handler mHandler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		myGameView = (MyGameView)findViewById(R.id.myGameView);
		myGameView.setActivity(this);

		// set myForeground to use transparent background
		//myForeground.setZOrderOnTop(true);
		//myForeground.getHolder().setFormat(PixelFormat.TRANSPARENT);

		myAccelerometer = new MyAccelerometer(this);

		mode = MODE_RUNNING;
		quitting = false;

		
		/**
		 * Setup pause menu
		 */
		
		// resume button
		pauseMenuResumeButton = (Button)findViewById(R.id.gamePauseMenuResumeButton);
		pauseMenuResumeButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				unpauseGame();
			}
		});
		pauseMenuResumeButton.setVisibility(View.GONE);

		// options button
		pauseMenuOptionsButton = (Button)findViewById(R.id.gamePauseMenuOptionsButton);
		pauseMenuOptionsButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent StartGameIntent = new Intent(MyGameActivity.this, OptionsMenu.class);
				startActivity(StartGameIntent);
			}
		});
		pauseMenuOptionsButton.setVisibility(View.GONE);

		// quit button
		pauseMenuQuitButton = (Button)findViewById(R.id.gamePauseMenuQuitButton);
		pauseMenuQuitButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				quitting = true;
				myGameView.togglePause(false);
				Intent StartGameIntent = new Intent(MyGameActivity.this, MainMenu.class);
				startActivity(StartGameIntent);
			}
		});
		pauseMenuQuitButton.setVisibility(View.GONE);

		/**
		 * Setup game over menu
		 */
		// retry button
		gameOverMenuRetryButton = (Button)findViewById(R.id.gameGameOverMenuRetryButton);
		gameOverMenuRetryButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mode = MODE_GAME_OVER;
				hideGameOverMenu();
				myGameView.reset();
				myGameView.togglePause(false);
			}
		});
		gameOverMenuRetryButton.setVisibility(View.GONE);
		
		// statistics button
		gameOverMenuStatisticsButton = (Button)findViewById(R.id.gameGameOverMenuStatisticsButton);
		gameOverMenuStatisticsButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO
			}
		});
		gameOverMenuStatisticsButton.setVisibility(View.GONE);
		
		// upgrades button
		gameOverMenuUpgradesButton = (Button)findViewById(R.id.gameGameOverMenuUpgradesButton);
		gameOverMenuUpgradesButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO
			}
		});
		gameOverMenuUpgradesButton.setVisibility(View.GONE);

		// quit button
		gameOverMenuQuitButton = (Button)findViewById(R.id.gameGameOverMenuQuitButton);
		gameOverMenuQuitButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				quitting = true;
				hideGameOverMenu();
				myGameView.togglePause(false);
				Intent StartGameIntent = new Intent(MyGameActivity.this, MainMenu.class);
				startActivity(StartGameIntent);
			}
		});
		gameOverMenuQuitButton.setVisibility(View.GONE);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myGameView.MyGameSurfaceView_OnResume();
		
		quitting = false;
		
		System.out.println("game onResume()");

		if (Configuration.controlType == Configuration.CONTROL_ACCELEROMETER) {
			myAccelerometer.registerListener();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		myGameView.MyGameSurfaceView_OnPause();
		
		System.out.println("game onPause(): " + quitting);
		
		if (!quitting) {
			pauseGame();
		}

		if (Configuration.controlType == Configuration.CONTROL_ACCELEROMETER) {
			myAccelerometer.unregisterListener();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		myGameView.keyDown(keyCode, event);
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		
		switch(keyCode) {
		// pause game when menu/back/search is pressed
		case KeyEvent.KEYCODE_MENU:
		case KeyEvent.KEYCODE_BACK:
		case KeyEvent.KEYCODE_SEARCH:
			if (mode == MODE_RUNNING) {
				pauseGame();
			} else if (mode == MODE_PAUSED){
				unpauseGame();
			}
			break;
		}
		
		myGameView.keyUp(keyCode, event);

		return true;
	}

	void updateAccelerometer(float tx, float ty) {
		myGameView.updateAccelerometer(tx, ty);
	}

	void pauseGame() {
		pauseMenuResumeButton.setVisibility(View.VISIBLE);
		pauseMenuOptionsButton.setVisibility(View.VISIBLE);
		pauseMenuQuitButton.setVisibility(View.VISIBLE);
		
		mode = MODE_PAUSED;
		myGameView.togglePause(true);
	}

	void unpauseGame() {
		pauseMenuResumeButton.setVisibility(View.GONE);
		pauseMenuOptionsButton.setVisibility(View.GONE);
		pauseMenuQuitButton.setVisibility(View.GONE);
		
		mode = MODE_RUNNING;
		myGameView.togglePause(false);
	}
	
	void hideGameOverMenu() {
		gameOverMenuRetryButton.setVisibility(View.GONE);
		gameOverMenuStatisticsButton.setVisibility(View.GONE);
		gameOverMenuUpgradesButton.setVisibility(View.GONE);
		gameOverMenuQuitButton.setVisibility(View.GONE);
	}
	
	public void showGameOverMenu() {
		gameOverMenuRetryButton.setVisibility(View.VISIBLE);
		gameOverMenuStatisticsButton.setVisibility(View.VISIBLE);
		gameOverMenuUpgradesButton.setVisibility(View.VISIBLE);
		gameOverMenuQuitButton.setVisibility(View.VISIBLE);

		mode = MODE_GAME_OVER;
		myGameView.togglePause(true);
	}
}