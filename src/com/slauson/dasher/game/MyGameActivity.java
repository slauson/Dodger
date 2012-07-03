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

	private Button resumeButton, optionsButton, quitButton;

	private boolean running, quitting;


	// Handler for updating debug text
	// http://stackoverflow.com/questions/5097267/error-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-view
	//Handler mHandler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		myGameView = (MyGameView)findViewById(R.id.myGameView);

		// set myForeground to use transparent background
		//myForeground.setZOrderOnTop(true);
		//myForeground.getHolder().setFormat(PixelFormat.TRANSPARENT);

		myAccelerometer = new MyAccelerometer(this);

		running = true;
		quitting = false;

		// resume button
		resumeButton = (Button)findViewById(R.id.gameResumeButton);
		resumeButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				unpauseGame();
			}
		});
		resumeButton.setVisibility(View.GONE);

		// options button
		optionsButton = (Button)findViewById(R.id.gameOptionsButton);
		optionsButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent StartGameIntent = new Intent(MyGameActivity.this, OptionsMenu.class);
				startActivity(StartGameIntent);
			}
		});
		optionsButton.setVisibility(View.GONE);

		// quit button
		quitButton = (Button)findViewById(R.id.gameQuitButton);
		quitButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				quitting = true;
				myGameView.togglePause(false);
				Intent StartGameIntent = new Intent(MyGameActivity.this, MainMenu.class);
				startActivity(StartGameIntent);
			}
		});
		quitButton.setVisibility(View.GONE);

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
			if (running) {
				pauseGame();
			} else {
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
		resumeButton.setVisibility(View.VISIBLE);
		optionsButton.setVisibility(View.VISIBLE);
		quitButton.setVisibility(View.VISIBLE);
		
		running = false;
		myGameView.togglePause(true);
	}

	void unpauseGame() {
		resumeButton.setVisibility(View.GONE);
		optionsButton.setVisibility(View.GONE);
		quitButton.setVisibility(View.GONE);
		
		running = true;
		myGameView.togglePause(false);
	}
}