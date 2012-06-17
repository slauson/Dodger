package com.slauson.dodger.powerups;


import com.slauson.dodger.main.MyGameView;
import com.slauson.dodger.objects.Asteroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class PowerupMagnet extends PowerupStationary {
	
	private static final int MAX_RANGE = 200;
	
	private int direction;
	
	public PowerupMagnet(Bitmap bitmap, float x, float y, int direction) {
		super(bitmap, x, y);
		
		this.direction = direction;
		
		activate(Integer.MAX_VALUE);
	}
	
	/**
	 * Alters asteroids direction based on distance from magnet
	 * Also breaks up asteroid if too close
	 * @param asteroid asteroid to modify
	 */
	public void alterAsteroid(Asteroid asteroid) {
		
		if (asteroid.getStatus() != Asteroid.STATUS_NORMAL) {
			return;
		}
	
		// get distance from asteroid
		float distanceX = x - asteroid.getX();
		float distanceY = y - asteroid.getY(); // negative
		
		float absDistanceX = Math.abs(distanceX);
		float absDistanceY = Math.abs(distanceY); // positive

		// check if asteroid hit magnet
		if (absDistanceX < width/2 + asteroid.getWidth()/2 && absDistanceY < height/2 + asteroid.getHeight()/2) {
			asteroid.breakup();
			
			numHits++;
		}
		
		// don't pull asteroids past magnet
		if (direction == MyGameView.DIRECTION_NORMAL && asteroid.getY() + asteroid.getHeight()/2 > y - height/2 ||
				direction == MyGameView.DIRECTION_REVERSE && asteroid.getY() - asteroid.getHeight()/2 < y + height/2) {
			return;
		}
				
		int distance = (int)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
		
		if (distance < MAX_RANGE) {
			
			// direction directly to asteroid
			float dirX = 1.0f*distanceX/(absDistanceX + absDistanceY);
			float dirY = Math.abs(1.0f*distanceY/(absDistanceX + absDistanceY));
			
			float pullFactor = 1.0f*distance/MAX_RANGE;
			
			float asteroidDirX = (1 - pullFactor)*asteroid.getDirX() + (pullFactor)*dirX;
			float asteroidDirY = (1 - pullFactor)*asteroid.getDirY() + (pullFactor)*dirY;
						
			asteroid.setDirX(asteroidDirX);
			asteroid.setDirY(asteroidDirY);
		}
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		
		// rotate if needed
		if (direction == MyGameView.DIRECTION_REVERSE) {
			canvas.save();
			canvas.rotate(180, x, y);
		}
		
		canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
		
		// unrotate
		if (direction == MyGameView.DIRECTION_REVERSE) {
			canvas.restore();
		}
	}
}