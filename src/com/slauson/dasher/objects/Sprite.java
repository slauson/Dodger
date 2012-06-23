package com.slauson.dasher.objects;

import com.slauson.dasher.main.MyGameView;

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
		x = x + (dirX*speed*speedModifier);
		y = y + (MyGameView.gravity*dirY*speed*speedModifier);
	}

}
