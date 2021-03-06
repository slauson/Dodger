package com.slauson.asteroid_dasher.powerups;

import com.slauson.asteroid_dasher.game.Game;
import com.slauson.asteroid_dasher.objects.Asteroid;
import com.slauson.asteroid_dasher.status.Achievements;
import com.slauson.asteroid_dasher.status.Upgrades;

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
	private static final float ASTEROID_SPEED_FACTOR = 0.25f;
	
	private static final int DURATION_0 = 10000;
	private static final int DURATION_1 = 15000;
	private static final int DURATION_2 = 20000;
	private static final int DURATION_3 = 30000;
	
	// semi-constant
	private static float ASTEROID_SPEED = 100;
	
	// "constants" (want to make sure Game.canvasWidth is initialized)
	private static float rangePull = -1;
	private static float rangeSuck = -1;

	private int rotation;
	private boolean hasQuasar;
	
	private Bitmap twinkle;
	
	public PowerupBlackHole(Bitmap bitmap, Bitmap twinkle, float x, float y, int level) {
		super(bitmap, x, y);
		
		rotation = 0;
		
		ASTEROID_SPEED = Game.canvasWidth*ASTEROID_SPEED_FACTOR;
		
		// init ranges if not already initialized
		if (rangePull < 0 || rangeSuck < 0) {
			rangePull = Game.canvasWidth*RANGE_PULL_FACTOR;
			rangeSuck = Game.canvasWidth*RANGE_SUCK_FACTOR;
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
		
		if (hasQuasar) {
			this.twinkle = twinkle;
		} else {
			this.twinkle = null;
		}
	}
	
	/**
	 * Alters asteroids direction based on distance from magnet
	 * @param asteroid asteroid to modify
	 */
	public void alterAsteroid(Asteroid asteroid) {
		
		if (asteroid.getStatus() != Asteroid.STATUS_NORMAL && asteroid.getStatus() != Asteroid.STATUS_DISAPPEARING) {
			return;
		}
	
		// get distance from asteroid to black hole
		float distanceX = x - asteroid.getX();
		float distanceY = y - asteroid.getY();
		
		float absDistanceX = Math.abs(distanceX);
		float absDistanceY = Math.abs(distanceY);

		int distance = (int)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

		// in pull range
		if (distance < rangePull) {
			
			// direction from asteroid to black hole
			float dirX = 1.0f*distanceX/(absDistanceX + absDistanceY);
			float dirY = 1.0f*distanceY/(absDistanceX + absDistanceY);
			
			/**
			 * We want to blend the normal, pull, and suck movement here
			 * 0: 0 pull, 0 normal, 1 suck
			 * suck range: .5 pull, 0 normal, .5 suck
			 * pull range: 0 pull, 1 normal, 0 suck
			 */
			float normalFactor, pullFactor, suckFactor;
			
			suckFactor = (rangePull-distance)/rangePull;
			
			// in suck range
			if (distance < rangeSuck) {
				
				normalFactor = 0;
				pullFactor = 0.5f*(distance/rangeSuck);
				
				// Asteroid will only update factor if its smaller
				asteroid.setFactor(1.0f * distance / rangeSuck);
				
				// suck asteroid into black hole
				if (asteroid.getStatus() != Asteroid.STATUS_DISAPPEARING) {
					asteroid.disappear();
					numAffectedAsteroids++;
					
					asteroid.setSpeed(ASTEROID_SPEED);
				}
			}
			// not in suck range
			else {
				float factor = (((rangePull - rangeSuck) - (rangePull - distance))/(rangePull - rangeSuck));
				normalFactor = 1.0f*factor;
				pullFactor = 0.5f - 0.5f*factor;
			}
			
			float asteroidDirX, asteroidDirY;

			if (Game.direction == Game.DIRECTION_REVERSE) {
				dirY *= -1;
				asteroidDirX = normalFactor*asteroid.getDirX() + pullFactor*dirX + suckFactor*(0.25f*dirX - 0.75f*dirY);
				asteroidDirY = normalFactor*asteroid.getDirY() + pullFactor*dirY + suckFactor*(0.25f*dirY + 0.75f*dirX);
			} else {
				asteroidDirX = normalFactor*asteroid.getDirX() + pullFactor*dirX + suckFactor*(0.25f*dirX + 0.75f*dirY);
				asteroidDirY = normalFactor*asteroid.getDirY() + pullFactor*dirY + suckFactor*(0.25f*dirY - 0.75f*dirX);
			}
				
			asteroid.setDirX(asteroidDirX);
			asteroid.setDirY(asteroidDirY);
		}
	}
	
	@Override
	public void update(float speedModifier) {
		rotation += ROTATION_SPEED*speedModifier;
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		
		long remainingDuration = remainingDuration();
		
		// fade out
		if (remainingDuration < FADE_OUT_DURATION && Game.gameStatus == Game.STATUS_RUNNING) {
			int alpha = (int)(255*(1.f*remainingDuration/FADE_OUT_DURATION));
			
			if (alpha < 0) {
				alpha = 0;
			}
			paint.setAlpha(alpha);
			
		}
		
		// rotate
		canvas.save();
		canvas.rotate(rotation, x, y);
		
		canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
		
		// unrotate
		canvas.restore();
		
		// restore alpha
		if (remainingDuration < FADE_OUT_DURATION && Game.gameStatus == Game.STATUS_RUNNING) {

			if (twinkle != null) {
				paint.setAlpha(255 - paint.getAlpha());
				canvas.drawBitmap(twinkle, x - twinkle.getWidth()/2, y - twinkle.getHeight()/2, paint);
			}
			
			paint.setAlpha(255);
		}
	}
	
	public void checkAchievements() {
		if (numAffectedAsteroids >= Achievements.LOCAL_DESTROY_ASTEROIDS_BLACK_HOLE_NUM_1) {
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithBlackHole1);
		}
		
		if (numAffectedAsteroids >= Achievements.LOCAL_DESTROY_ASTEROIDS_BLACK_HOLE_NUM_2) {
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithBlackHole2);
		}
		
		if (numAffectedAsteroids >= Achievements.LOCAL_DESTROY_ASTEROIDS_BLACK_HOLE_NUM_3) {
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
