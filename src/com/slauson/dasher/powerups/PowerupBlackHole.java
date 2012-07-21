package com.slauson.dasher.powerups;

import com.slauson.dasher.game.MyGameView;
import com.slauson.dasher.objects.Asteroid;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.Upgrades;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Black hole powerup that sucks in asteroids
 * @author Josh Slauson
 *
 */
public class PowerupBlackHole extends ActivePowerup {

	// constants
	private static final float RANGE_PULL_FACTOR = 0.25f;
	private static final float RANGE_SUCK_FACTOR = 0.125f;
	private static final int ROTATION_SPEED = 10;
	private static final int ASTEROID_SPEED = 100;
	
	private static final int DURATION_0 = 10000;
	private static final int DURATION_1 = 15000;
	private static final int DURATION_2 = 20000;
	private static final int DURATION_3 = 10000;

	// "constants" (want to make sure MyGameView.canvasWidth is initialized)
	private static float rangePull = -1;
	private static float rangeSuck = -1;

	private int rotation;
	private boolean hasQuasar;
	
	public PowerupBlackHole(Bitmap bitmap, float x, float y, int level) {
		super(bitmap, x, y);
		
		rotation = 0;
		
		// init ranges if not already initialized
		if (rangePull < 0 || rangeSuck < 0) {
			rangePull = MyGameView.canvasWidth*RANGE_PULL_FACTOR;
			rangeSuck = MyGameView.canvasWidth*RANGE_SUCK_FACTOR;
		}
		
		// get duration
		switch(level) {
		case Upgrades.BLACK_HOLE_UPGRADE_INCREASED_DURATION_1:
			activate(DURATION_1);
			break;
		case Upgrades.BLACK_HOLE_UPGRADE_INCREASED_DURATION_2:
			activate(DURATION_2);
			break;
		case Upgrades.BLACK_HOLE_UPGRADE_INCREASED_DURATION_3:
		case Upgrades.BLACK_HOLE_UPGRADE_QUASAR:
			activate(DURATION_3);
			break;
		default:
			activate(DURATION_0);
			break;
		}
		
		hasQuasar = level >= Upgrades.BLACK_HOLE_UPGRADE_QUASAR;
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
	
		// get distance from asteroid to black hole
		float distanceX = x - asteroid.getX();
		float distanceY = y - asteroid.getY();
		
		float absDistanceX = Math.abs(distanceX);
		float absDistanceY = Math.abs(distanceY);

		int distance = (int)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
		
		if (distance < rangePull) {
			
			// suck asteroid into black hole
			if (distance < rangeSuck) {
				
				//System.out.println("Asteroid " + asteroid + "(" + distance + ") in suck range of " + this);
				
				if (asteroid.getStatus() != Asteroid.STATUS_DISAPPEARING) {
					asteroid.disappear();
				}
				
				// update factor
				asteroid.setFactor(1.0f * distance / rangeSuck);
				
				// direction from asteroid to black hole
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
				asteroid.setSpeed(ASTEROID_SPEED);
				
				numAffectedAsteroids++;
			} 
			// pull asteroid towards black hole
			else if (asteroid.getStatus() != Asteroid.STATUS_DISAPPEARING) {
			
				// direction from asteroid to black hole
				float dirX = 1.0f*distanceX/(absDistanceX + absDistanceY);
				float dirY = 1.0f*distanceY/(absDistanceX + absDistanceY);
				
				if (MyGameView.direction == MyGameView.DIRECTION_REVERSE) {
					dirY *= -1;
				}
				
				float pullFactor = 1.0f*distance/rangePull;
				
				float asteroidDirX = (1 - pullFactor)*asteroid.getDirX() + (pullFactor)*dirX;
				float asteroidDirY = (1 - pullFactor)*asteroid.getDirY() + (pullFactor)*dirY;
							
				asteroid.setDirX(asteroidDirX);
				asteroid.setDirY(asteroidDirY);
			}
		}
	}
	
	@Override
	public void update(float speedModifier) {
		if (MyGameView.direction == MyGameView.DIRECTION_NORMAL) {
			rotation += ROTATION_SPEED*speedModifier;
		} else {
			rotation -= ROTATION_SPEED*speedModifier;
		}
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		
		// fade out
		if (remainingDuration() < FADE_OUT_DURATION) {
			int alpha = (int)(255*(1.f*remainingDuration()/FADE_OUT_DURATION));
			
			if (alpha < 0) {
				alpha = 0;
			}
			paint.setAlpha(alpha);
		}
		
		// rotate
		canvas.save();
		canvas.rotate(rotation, x, y);
		
		if (MyGameView.direction == MyGameView.DIRECTION_NORMAL) {
			canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
		} else {
			// flip vertically
			canvas.scale(1, -1, x, y);
			canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
		}
		
		// unrotate
		canvas.restore();
		
		// restore alpha
		if (remainingDuration() < FADE_OUT_DURATION) {
			paint.setAlpha(255);
		}
	}
	
	public void checkAchievements() {
		if (numAffectedAsteroids > Achievements.LOCAL_DESTROY_ASTEROIDS_NUM_1) {
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithBlackHole1);
		}
		
		if (numAffectedAsteroids > Achievements.LOCAL_DESTROY_ASTEROIDS_NUM_2) {
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithBlackHole2);
		}
		
		if (numAffectedAsteroids > Achievements.LOCAL_DESTROY_ASTEROIDS_NUM_3) {
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithBlackHole3);
		}
	}
	
	/**
	 * Returns true if the black hole is active
	 * @return true if the black hole is active
	 */
	public boolean isActive() {
		return System.currentTimeMillis() < endingTime;
	}
	
	/**
	 * Returns true if the black hole has the quasar upgrade
	 * @return true if the black hole has the quasar upgrade
	 */
	public boolean hasQuasar() {
		return hasQuasar;
	}
	
	/**
	 * Activates black hole's quasar
	 */
	public void activateQuasar() {
		hasQuasar = false;
	}
}
