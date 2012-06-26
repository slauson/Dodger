package com.slauson.dasher.powerups;

import com.slauson.dasher.game.MyGameView;
import com.slauson.dasher.objects.Asteroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * White hole powerup that sucks in asteroids
 * @author Josh Slauson
 *
 */
public class PowerupWhiteHole extends ActivePowerup {

	private static final int MAX_RANGE = 200;
	private static final int SUCK_RANGE = 50;
	private static final int ROTATION_SPEED = 10;

	private int rotation;
	
	public PowerupWhiteHole(Bitmap bitmap, float x, float y, int duration) {
		super(bitmap, x, y);
		
		rotation = 0;
		
		activate(duration);
	}
	
	/**
	 * Alters asteroids direction based on distance from magnet
	 * @param asteroid asteroid to modify
	 */
	public void alterAsteroid(Asteroid asteroid) {
		
		if (asteroid.getStatus() != Asteroid.STATUS_NORMAL && asteroid.getStatus() != Asteroid.STATUS_DISAPPEARING) {
			//System.out.println("Asteroid " + asteroid + " not being altered");
			return;
		}
	
		// get distance from asteroid to white hole
		float distanceX = x - asteroid.getX();
		float distanceY = y - asteroid.getY();
		
		float absDistanceX = Math.abs(distanceX);
		float absDistanceY = Math.abs(distanceY);

		int distance = (int)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
		
		if (distance < MAX_RANGE) {
			
			//System.out.println("Asteroid " + asteroid + "(" + distance + ") in max range of " + this);
			
			// suck asteroid into white hole
			if (distance < SUCK_RANGE) {
				
				//System.out.println("Asteroid " + asteroid + "(" + distance + ") in suck range of " + this);
				
				if (asteroid.getStatus() != Asteroid.STATUS_DISAPPEARING) {
					asteroid.disappear();
				}
				
				// update factor
				asteroid.setFactor(1.0f * distance / SUCK_RANGE);
				
				// direction from asteroid to white hole
				float dirX = 1.0f*distanceX/(absDistanceX + absDistanceY);
				float dirY = 1.0f*distanceY/(absDistanceX + absDistanceY);
				
				if (MyGameView.direction == MyGameView.DIRECTION_REVERSE) {
					dirY *= -1;
				}
				
				// get perpendicular to direction
				float asteroidDirX = 0.25f*dirX + 0.75f*dirY;
				float asteroidDirY = 0.25f*dirY - 0.75f*dirX;
								
				asteroid.setDirX(asteroidDirX);
				asteroid.setDirY(asteroidDirY);
			} 
			// pull asteroid towards white hole
			else if (asteroid.getStatus() != Asteroid.STATUS_DISAPPEARING) {
			
				// direction from asteroid to white hole
				float dirX = 1.0f*distanceX/(absDistanceX + absDistanceY);
				float dirY = 1.0f*distanceY/(absDistanceX + absDistanceY);
				
				if (MyGameView.direction == MyGameView.DIRECTION_REVERSE) {
					dirY *= -1;
				}
				
				float pullFactor = 1.0f*distance/MAX_RANGE;
				
				float asteroidDirX = (1 - pullFactor)*asteroid.getDirX() + (pullFactor)*dirX;
				float asteroidDirY = (1 - pullFactor)*asteroid.getDirY() + (pullFactor)*dirY;
							
				asteroid.setDirX(asteroidDirX);
				asteroid.setDirY(asteroidDirY);
			}
		}
	}
	
	@Override
	public void update(float speedModifier) {
		rotation += ROTATION_SPEED*speedModifier;
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		
		// rotate
		canvas.save();
		canvas.rotate(rotation, x, y);
		
		canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
		
		// unrotate
		canvas.restore();
	}
}
