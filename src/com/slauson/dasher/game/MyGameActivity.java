package com.slauson.dasher.game;

import com.slauson.dasher.R;
import com.slauson.dasher.main.Configuration;
import com.slauson.dasher.main.Instructions;
import com.slauson.dasher.main.MainMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * Game activity
 * @author Josh Slauson
 *
 */
public class MyGameActivity extends Activity {
	
	private MyGameView myGameView;
	
	private MyAccelerometer myAccelerometer;
	
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

    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	myGameView.MyGameSurfaceView_OnResume();
    	
    	if (Configuration.controlType == Configuration.CONTROL_ACCELEROMETER) {
    		myAccelerometer.registerListener();
    	}
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	myGameView.MyGameSurfaceView_OnPause();
    	
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

		myGameView.keyUp(keyCode, event);

		switch(keyCode) {
		// pause game when menu/back/search is pressed
		case KeyEvent.KEYCODE_MENU:
		case KeyEvent.KEYCODE_BACK:
		case KeyEvent.KEYCODE_SEARCH:

			// TODO: change this to bring up paused menu
			Intent mainMenuIntent = new Intent(MyGameActivity.this, MainMenu.class);
			startActivity(mainMenuIntent);
			break;
		}

		return true;
	}
    
    void updateAccelerometer(float tx, float ty) {
    	myGameView.updateAccelerometer(tx, ty);
    }
}