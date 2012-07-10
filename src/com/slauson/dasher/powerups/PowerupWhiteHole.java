package com.slauson.dasher.powerups;

import java.util.ArrayList;

import com.slauson.dasher.game.MyGameView;
import com.slauson.dasher.objects.Asteroid;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.Upgrades;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * White hole powerup that sucks in asteroids
 * @author Josh Slauson
 *
 */
public class PowerupWhiteHole extends ActivePowerup {

	// constants
	private static final int MAX_RANGE = 100;
	private static final int SUCK_RANGE = 50;
	private static final int ROTATION_SPEED = 10;
	private static final int ASTEROID_SPEED = 100;
	
	private static final int DURATION_0 = 5000;
	private static final int DURATION_1 = 10000;
	private static final int DURATION_2 = 15000;
	private static final int DURATION_3 = 20000;

	private static final int QUASAR_ASTEROID_SPEED = 500;
	
	private int rotation;
	private boolean hasQuasar;
	private ArrayList<Asteroid> asteroids;
	
	public PowerupWhiteHole(Bitmap bitmap, float x, float y, int level) {
		super(bitmap, x, y);
		
		rotation = 0;
		asteroids = new ArrayList<Asteroid>();
		
		// get duration
		switch(level) {
		case Upgrades.WHITE_HOLE_UPGRADE_INCREASED_DURATION_1:
			activate(DURATION_1);
			break;
		case Upgrades.WHITE_HOLE_UPGRADE_INCREASED_DURATION_2:
			activate(DURATION_2);
			break;
		case Upgrades.WHITE_HOLE_UPGRADE_INCREASED_DURATION_3:
		case Upgrades.WHITE_HOLE_UPGRADE_QUASAR:
			activate(DURATION_3);
			break;
		default:
			activate(DURATION_0);
			break;
		}
		
		hasQuasar = level >= Upgrades.WHITE_HOLE_UPGRADE_QUASAR;
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
					asteroids.add(asteroid);
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
				asteroid.setSpeed(ASTEROID_SPEED);
				
				numAffectedAsteroids++;
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
	
	public void destroy() {
		
		// if has quasar, shoot out the remaining asteroids
		if (hasQuasar) {
			for (Asteroid asteroid : asteroids) {
				
				asteroid.setDirX(-1 + 2*MyGameView.random.nextFloat());
				
				if (MyGameView.random.nextBoolean()) {
					asteroid.setDirY(1 - Math.abs(asteroid.getDirX()));
				} else {
					asteroid.setDirY(-1 + Math.abs(asteroid.getDirX()));
				}
				
				asteroid.setSpeed(QUASAR_ASTEROID_SPEED);
				asteroid.fadeOut();
			}
		}
		// otherwise just reset the asteroids
		else {
			for (Asteroid asteroid : asteroids) {
				asteroid.reset();
			}
		}
		
		asteroids.clear();
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
		if (numAffectedAsteroids > Achievements.LOCAL_DESTROY_ASTEROIDS_NUM_1 &&
				!Achievements.localDestroyAsteroidsWithWhiteHole1.getValue())
		{
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithWhiteHole1);
		}
		
		if (numAffectedAsteroids > Achievements.LOCAL_DESTROY_ASTEROIDS_NUM_2 &&
				!Achievements.localDestroyAsteroidsWithWhiteHole2.getValue())
		{
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithWhiteHole2);
		}
		
		if (numAffectedAsteroids > Achievements.LOCAL_DESTROY_ASTEROIDS_NUM_3 &&
				!Achievements.localDestroyAsteroidsWithWhiteHole3.getValue())
		{
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithWhiteHole3);
		}
	}
	
	public boolean hasQuasar() {
		return hasQuasar;
	}

}
