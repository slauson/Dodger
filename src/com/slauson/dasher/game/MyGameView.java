package com.slauson.dasher.game;

import com.slauson.dasher.status.Configuration;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Main game view
 * 
 * Adapted from here: http://android-coding.blogspot.com/2012/01/create-surfaceview-game-step-by-step.html
 * 
 * @author Josh Slauson
 *
 */
public class MyGameView extends SurfaceView implements SurfaceHolder.Callback {

	private MyGame game;
	
	/** Holder for surface **/
	private SurfaceHolder surfaceHolder;
	
	/** Game thread **/
	private MyGameThread myGameThread;
	
	public MyGameView(Context context) {
		super(context);
	}
	
	public MyGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public MyGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		
		// initialize game
		game.init(getWidth(), getHeight());
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Sets up surface view and game thread
	 */
	public void MyGameSurfaceView_OnResume() {
		
		surfaceHolder = getHolder();
		getHolder().addCallback(this);
		
		// Create and start background Thread
		myGameThread = new MyGameThread(this);
		myGameThread.setRunning(true);
		myGameThread.start();
		
	}
	
	/**
	 * Pauses game thread
	 */
	public void MyGameSurfaceView_OnPause() {
		// Kill the background thread
		boolean retry = true;
		myGameThread.setRunning(false);
		
		while (retry) {
			try {
				myGameThread.join();
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		// only move when touch controls are being used and when ship in normal or invulnerability status
		if (Configuration.controlType != Configuration.CONTROL_TOUCH) {
			return false;
		}
		
		game.touchEvent(event);
		return true;
	}

	/**
	 * Update surface view
	 * @return true on success, false otherwise
	 */
	public boolean updateSurfaceView() {
		
		Canvas canvas = null;

		try {
			canvas = surfaceHolder.lockCanvas();
			
			synchronized (surfaceHolder) {
				game.updateStates();
				game.draw(canvas);
			}
		} finally {
			if (canvas != null) {
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
		
		return true;
	}
	
	public void setGame(MyGame game) {
		this.game = game;
	}
}
