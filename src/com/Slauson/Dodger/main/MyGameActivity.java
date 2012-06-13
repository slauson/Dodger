package com.slauson.dodger.main;

import com.slauson.dodger.main.R;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MyGameActivity extends Activity {
	
	private MyGameView myGameView;
	
	private MyAccelerometer myAccelerometer;
	
	// constants
	private boolean ACCELEROMETER_ENABLED = false;
	
	// Handler for updating debug text
	// http://stackoverflow.com/questions/5097267/error-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-view
	//Handler mHandler;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myGameView = (MyGameView)findViewById(R.id.myview);
        
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
    	
    	if (ACCELEROMETER_ENABLED) {
    		myAccelerometer.registerListener();
    	}
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	myGameView.MyGameSurfaceView_OnPause();
    	
    	if (ACCELEROMETER_ENABLED) {
    		myAccelerometer.unregisterListener();
    	}
    }
    
    void updateAccelerometer(float tx, float ty) {
    	myGameView.updateAccelerometer(tx, ty);
    }
}