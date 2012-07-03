package com.slauson.dasher.powerups;

import com.slauson.dasher.game.MyGameView;
import com.slauson.dasher.objects.Asteroid;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.GlobalStatistics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.FloatMath;

/**
 * Magnet powerup that attracts powerups
 * @author Josh Slauson
 *
 */
public class PowerupMagnet extends ActivePowerup {
	
	private static final int MAX_RANGE = 100;
	
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
				
		float distance = FloatMath.sqrt((float)Math.pow(distanceX, 2) + (float)Math.pow(distanceY, 2));
		
		if (distance < MAX_RANGE) {
			
			float holdRange = height/2 + asteroid.getHeight()/2;
			
			// direction directly to asteroid
			float dirX = distanceX/(absDistanceX + absDistanceY);
			float dirY = Math.abs(distanceY/(absDistanceX + absDistanceY));
			
			if (distance < holdRange) {
				distance = 0;
				dirX = 0;
				dirY = 0;
				
				GlobalStatistics.asteroidsDestroyedByMagnet++;
				numAffectedAsteroids++;
			}
			
			float pullFactor = 0.5f - (0.5f*distance/MAX_RANGE);
			
			float asteroidDirX = (1 - pullFactor)*asteroid.getDirX() + (pullFactor)*dirX;
			float asteroidDirY = (1 - pullFactor)*asteroid.getDirY() + (pullFactor)*dirY;
						
			asteroid.setDirX(asteroidDirX);
			asteroid.setDirY(asteroidDirY);
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
		
		// restore alpha
		if (remainingDuration() < FADE_OUT_DURATION) {
			paint.setAlpha(255);
		}
	}
	
	public void checkAchievements() {
		if (numAffectedAsteroids > Achievements.LOCAL_DESTROY_ASTEROIDS_NUM_1 &&
				!Achievements.localDestroyAsteroidsWithMagnet1.getValue())
		{
			Achievements.localDestroyAsteroidsWithMagnet1.setValue(true);
		}
		
		if (numAffectedAsteroids > Achievements.LOCAL_DESTROY_ASTEROIDS_NUM_2 &&
				!Achievements.localDestroyAsteroidsWithMagnet2.getValue())
		{
			Achievements.localDestroyAsteroidsWithMagnet2.setValue(true);
		}
		
		if (numAffectedAsteroids > Achievements.LOCAL_DESTROY_ASTEROIDS_NUM_3 &&
				!Achievements.localDestroyAsteroidsWithMagnet3.getValue())
		{
			Achievements.localDestroyAsteroidsWithMagnet3.setValue(true);
		}
	}
}