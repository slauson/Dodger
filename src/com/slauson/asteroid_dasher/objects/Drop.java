package com.slauson.asteroid_dasher.objects;

import com.slauson.asteroid_dasher.game.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Drop which gives the player a powerup
 * @author Josh Slauson
 *
 */
public class Drop extends Sprite {

	// type of powerup granted
	private int type;
	
	private int invisibleTime;
	
	// constants
	private static final float DROP_SPEED_FACTOR = 0.125f;
	private static final int INVISIBLE_TIME = 1000;
	
	public Drop(Bitmap bitmap, float x, float y, int type) {
		super(bitmap, x, y);
		
		this.type = type;
		this.dirY = 1;
		this.speed = Game.canvasHeight*DROP_SPEED_FACTOR;
		this.invisibleTime = INVISIBLE_TIME;
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		
		if (onScreen()) {
			if (invisibleTime > 0) {
				paint.setAlpha((int)(255f*(INVISIBLE_TIME - invisibleTime)/INVISIBLE_TIME));
			}
			canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
			
			if (invisibleTime > 0) {
				paint.setAlpha(255);
			}
		}
	}

	@Override
	public void update(float speedModifier) {
		
		long timeElapsed = getElapsedTime();
		float timeModifier = 1.f*timeElapsed/1000;
		
		if (invisibleTime > 0) {
			invisibleTime -= timeElapsed;
		}
		
		x = x + (dirX*speed*timeModifier*speedModifier);
		y = getNextY(speedModifier, timeModifier);
	}

	/**
	 * Returns type of powerup drop is for
	 * @return type of powerup drop is for
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Returns true if this powerup is visible
	 * @return true if this powerup is visible
	 */
	public boolean isVisible() {
		return invisibleTime <= 0;
	}
}
