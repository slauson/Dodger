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
	 * Sets up surface view
	 */
	public void MyGameSurfaceView_OnResume() {
		
		surfaceHolder = getHolder();
		getHolder().addCallback(this);
		
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
	 * Draw on surface view
	 */
	public void draw() {
		
		Canvas canvas = null;

		try {
			canvas = surfaceHolder.lockCanvas();
			
			synchronized (surfaceHolder) {
				game.draw(canvas);
			}
		} finally {
			if (canvas != null) {
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
	public void setGame(MyGame game) {
		this.game = game;
	}
}
