package com.slauson.dasher.game;

import com.slauson.dasher.R;
import com.slauson.dasher.main.InstructionsMenu;
import com.slauson.dasher.main.MainMenu;
import com.slauson.dasher.main.OptionsMenu;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.Configuration;
import com.slauson.dasher.status.LocalStatistics;

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

	private Button pauseMenuResumeButton, pauseMenuInstructionsButton, pauseMenuOptionsButton, pauseMenuQuitButton;
	
	private boolean quitting, paused;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_menu);
		
		System.out.println("MyGameActivity onCreate()");
		
		myGameView = (MyGameView)findViewById(R.id.myGameView);
		myGameView.setActivity(this);

		// set myForeground to use transparent background
		//myForeground.setZOrderOnTop(true);
		//myForeground.getHolder().setFormat(PixelFormat.TRANSPARENT);

		myAccelerometer = new MyAccelerometer(this);

		quitting = false;
		paused = false;

		/**
		 * Reset state
		 */
		LocalStatistics.getInstance().reset();
		Achievements.resetLocalAchievements();
		
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
		
		// instructions button
		pauseMenuInstructionsButton = (Button)findViewById(R.id.gamePauseMenuInstructionsButton);
		pauseMenuInstructionsButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MyGameActivity.this, InstructionsMenu.class);
				startActivity(intent);
			}
		});
		pauseMenuInstructionsButton.setVisibility(View.GONE);

		// options button
		pauseMenuOptionsButton = (Button)findViewById(R.id.gamePauseMenuOptionsButton);
		pauseMenuOptionsButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MyGameActivity.this, OptionsMenu.class);
				startActivity(intent);
			}
		});
		pauseMenuOptionsButton.setVisibility(View.GONE);

		// quit button
		pauseMenuQuitButton = (Button)findViewById(R.id.gamePauseMenuQuitButton);
		pauseMenuQuitButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				quitting = true;
				myGameView.togglePause(false);
				Intent intent = new Intent(MyGameActivity.this, MainMenu.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		pauseMenuQuitButton.setVisibility(View.GONE);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myGameView.MyGameSurfaceView_OnResume();
		
		quitting = false;
		
		System.out.println("MyGameActivity onResume()");

		if (Configuration.controlType == Configuration.CONTROL_ACCELEROMETER) {
			myAccelerometer.registerListener();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		myGameView.MyGameSurfaceView_OnPause();
		
		System.out.println("MyGameActivity onPause()");

		
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
			if (!paused) {
				pauseGame();
			} else {
				unpauseGame();
			}
			break;
		}
		
		myGameView.keyUp(keyCode, event);

		return true;
	}
	
	public void gameOver() {
		quitting = true;
		Intent intent = new Intent(MyGameActivity.this, GameOverMenu.class);
		startActivity(intent);
	}

	public void updateAccelerometer(float tx, float ty) {
		myGameView.updateAccelerometer(tx, ty);
	}

	private void pauseGame() {
		pauseMenuResumeButton.setVisibility(View.VISIBLE);
		pauseMenuInstructionsButton.setVisibility(View.VISIBLE);
		pauseMenuOptionsButton.setVisibility(View.VISIBLE);
		pauseMenuQuitButton.setVisibility(View.VISIBLE);
		
		paused = true;
		myGameView.togglePause(true);
	}

	private void unpauseGame() {
		pauseMenuResumeButton.setVisibility(View.GONE);
		pauseMenuInstructionsButton.setVisibility(View.GONE);
		pauseMenuOptionsButton.setVisibility(View.GONE);
		pauseMenuQuitButton.setVisibility(View.GONE);
		
		paused = false;
		myGameView.togglePause(false);
	}
}