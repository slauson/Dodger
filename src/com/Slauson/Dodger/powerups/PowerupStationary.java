package com.slauson.dodger.powerups;


import com.slauson.dodger.objects.Asteroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Powerup that is stationary on the screen
 * @author josh
 *
 */
public abstract class PowerupStationary extends Powerup {
	
	protected Bitmap bitmap;
	
	protected float x, y;
	protected int width, height;
	
	protected int numHits;
	
	protected int maxHits;
	
	public PowerupStationary(Bitmap bitmap, float x, float y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		this.numHits = 0;
		this.maxHits = 5;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
	}
	
	@Override
	public boolean isActive() {
		return super.isActive() && numHits < maxHits;
	}
	
	public void update() {
		// do nothing by default
	}
	
	public abstract void alterAsteroid(Asteroid asteroid);

}
