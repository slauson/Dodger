package com.slauson.asteroid_dasher.objects;

import com.slauson.asteroid_dasher.game.Game;

import android.graphics.Bitmap;

/**
 * A sprite to be drawn on the screen
 * @author Josh Slauson
 *
 */
public abstract class Sprite extends Item {
	
	protected Bitmap bitmap;
	
	public Sprite(Bitmap bitmap, float x, float y) {
		super(x, y, bitmap.getWidth(), bitmap.getHeight());
		
		this.bitmap = bitmap;
	}
	
	/**
	 * Updates sprite's position
	 */
	public void update() {
		update(1.0f);
	}

	/**
	 * Updates sprite's position based on direction, speed
	 */
	public void update(float speedModifier) {
		
		float timeModifier = 1.f*getElapsedTime()/1000;
		
		x = x + (dirX*speed*timeModifier*speedModifier);
		y = y + (Game.gravity*dirY*speed*timeModifier*speedModifier);
	}
	
	/**
	 * Cleans up bitmap
	 */
	public void cleanup() {
		if (bitmap != null) {
			bitmap.recycle();
		}
	}
}
