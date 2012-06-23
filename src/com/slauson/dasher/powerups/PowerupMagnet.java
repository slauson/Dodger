package com.slauson.dasher.powerups;

import com.slauson.dasher.main.MyGameView;
import com.slauson.dasher.objects.Asteroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Magnet powerup that attracts powerups
 * @author Josh Slauson
 *
 */
public class PowerupMagnet extends ActivePowerup {
	
	private static final int MAX_RANGE = 200;
	
	private int direction;
	
	public PowerupMagnet(Bitmap bitmap, float x, float y, int duration, int direction) {
		super(bitmap, x, y);
		
		this.direction = direction;
		
		activate(duration);
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
		float distanceY;
		
		if (direction == MyGameView.DIRECTION_NORMAL) {
			distanceY = (y - height/2) - (asteroid.getY() + asteroid.getHeight()/2);
		} else {
			distanceY = (y + height/2) - (asteroid.getY() - asteroid.getHeight()/2);
		}
		
		float absDistanceX = Math.abs(distanceX);
		float absDistanceY = Math.abs(distanceY);

		// don't pull asteroids past magnet
		if (direction == MyGameView.DIRECTION_NORMAL && asteroid.getY() + asteroid.getHeight()/2 > y - height/2 ||
				direction == MyGameView.DIRECTION_REVERSE && asteroid.getY() - asteroid.getHeight()/2 < y + height/2) {
			return;
		}
				
		float distance = (float)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
		
		if (distance < MAX_RANGE) {
			
			float holdRange = height/2 + asteroid.getHeight()/2;
			
			// direction directly to asteroid
			float dirX = distanceX/(absDistanceX + absDistanceY);
			float dirY = Math.abs(distanceY/(absDistanceX + absDistanceY));
			
			if (distance < holdRange) {
				distance = 0;
				dirX = 0;
				dirY = 0;
			}
			
			float pullFactor = 1f - (1.0f*distance/MAX_RANGE);
			
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