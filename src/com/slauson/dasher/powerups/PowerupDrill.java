package com.slauson.dasher.powerups;

import com.slauson.dasher.game.MyGameView;
import com.slauson.dasher.objects.Asteroid;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.Upgrades;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.FloatMath;

/**
 * Drill powerup that splits asteroids in half
 * @author Josh Slauson
 *
 */
public class PowerupDrill extends ActivePowerup {

	// constants
	private static final int SPEED = 200;
	private static final float WEIGHTED_DISTANCE_X_FACTOR = 2;
	private static final float CONE_CHECK_X_FACTOR = 1.5f;
	private static final int TELEPORT_DURATION = 500;
	
	private static final float MAX_DIR_CHANGE_0 = 0.05f;
	private static final float MAX_DIR_CHANGE_1 = 0.10f;
	private static final float MAX_DIR_CHANGE_2 = 0.15f;
	private static final float MAX_DIR_CHANGE_3 = 0.25f;
	
	private static final int DURATION = 10000;
	
	private int direction;
	private RectF rectDest;
	
	private Asteroid nextAsteroid;
	private float nextDistance;
	
	private int teleportDuration;

	// upgrades
	private float maxDirChange;
	private boolean hasTeleport;
		
	public PowerupDrill(Bitmap bitmap, float x, float y, int direction, int level) {
		super(bitmap, x, y);
		
		this.direction = direction;
		
		nextAsteroid = null;
		nextDistance = 0;

		dirY = 1;
		dirX = 0;
		
		rectDest = new RectF(-width/2, -height/2, width/2, height/2);
		
		// calculate maximum direction change
		switch(level) {
		case Upgrades.DRILL_UPGRADE_SEEK_1:
			maxDirChange = MAX_DIR_CHANGE_1;
			break;
		case Upgrades.DRILL_UPGRADE_SEEK_2:
			maxDirChange = MAX_DIR_CHANGE_2;
			break;
		case Upgrades.DRILL_UPGRADE_SEEK_3:
		case Upgrades.DRILL_UPGRADE_TELEPORT:
			maxDirChange = MAX_DIR_CHANGE_3;
			break;
		default:
			maxDirChange = MAX_DIR_CHANGE_0;
			break;
		}
		
		hasTeleport = level >= Upgrades.DRILL_UPGRADE_TELEPORT;
		teleportDuration = 0;
		
		activate(DURATION);
	}

	@Override
	public void alterAsteroid(Asteroid asteroid) {

		// ignore asteroids past drill, off screen, or non normal
		if (asteroid.getStatus() != Asteroid.STATUS_NORMAL ||
				!asteroid.onScreen() ||
				direction == MyGameView.DIRECTION_NORMAL && asteroid.getY() + asteroid.getHeight()/2 <= y - height/2 ||
				direction == MyGameView.DIRECTION_REVERSE && asteroid.getY() - asteroid.getHeight()/2 >= y + height/2)
		{
			// reset next asteroid
			if (asteroid.equals(nextAsteroid)) {
				nextAsteroid = null;
			}

			return;
		}
		
		if (checkBoxCollision(asteroid)) {
			asteroid.splitUp();
			numAffectedAsteroids++;
			nextAsteroid = null;
		}
		
		// get weighted distance
		float distanceX = Math.abs(x - asteroid.getX());
		float distanceY = Math.abs(y - asteroid.getY());
		
		// cone check
		if (CONE_CHECK_X_FACTOR*distanceX > distanceY) {
			return;
		}
		
		float weightedDistance = FloatMath.sqrt((float)Math.pow(WEIGHTED_DISTANCE_X_FACTOR*distanceX, 2) + (float)Math.pow(distanceY, 2));

		// update distance for next asteroid
		if (asteroid.equals(nextAsteroid)) {
			nextDistance = weightedDistance;
		}
		// otherwise check if its closer
		else if (weightedDistance < nextDistance || nextAsteroid == null) {
			nextDistance = weightedDistance;
			nextAsteroid = asteroid;
		}
	}
	
	@Override
	public void update(float speedModifier) {
		
		long timeElapsed = System.currentTimeMillis() - lastUpdateTime;
		lastUpdateTime = System.currentTimeMillis();

		if (teleportDuration > 0) {
			teleportDuration -= timeElapsed;
			
			// reset drill position
			if (hasTeleport && teleportDuration <= TELEPORT_DURATION/2) {

				// pick random x coordinate
				x = width/2 + (MyGameView.canvasWidth - width)*MyGameView.random.nextFloat();
				
				// position drill back on opposite side
				if (direction == MyGameView.DIRECTION_NORMAL) {
					y = MyGameView.canvasHeight - height/2; 
				} else {
					y = height/2;
				}
				
				System.out.println("RESET drill to " + (int)x + ", " + (int)y);
				
				hasTeleport = false;
			}
			return;
		}
		
		float timeModifier = 1.f*timeElapsed/1000;
		
		// update direction based on next asteroid
		if (nextAsteroid != null) {
			float distanceX = nextAsteroid.getX() - x;
			float distanceY = nextAsteroid.getY() - y;
			
			float distanceAbsoluteValue = Math.abs(distanceX) + Math.abs(distanceY);
			
			float actualDirX = distanceX/(distanceAbsoluteValue)/2;
			//float actualDirY = 0.5f + distanceY/(distanceAbsoluteValue)/2;
			
			float dirChangeX = actualDirX - dirX;
			if (Math.abs(dirChangeX) < maxDirChange) {
				dirX = actualDirX;
			} else if (dirChangeX > 0) {
				dirX += maxDirChange;
			} else {
				dirX -= maxDirChange;
			}
			
			dirY = 1.0f - Math.abs(dirX);
		}
		
		if (direction == MyGameView.DIRECTION_NORMAL) {
			y -= dirY*SPEED*timeModifier*speedModifier;
		} else {
			y += dirY*SPEED*timeModifier*speedModifier;
		}
		
		x += dirX*SPEED*timeModifier*speedModifier;
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

		// translate to drill's position
		canvas.save();
		canvas.translate(x, y);

		// rotate if needed
		if (direction == MyGameView.DIRECTION_REVERSE) {
			canvas.save();
			canvas.rotate(180, 0, 0);
		}
		
		// rotate based on direction
		if (direction == MyGameView.DIRECTION_NORMAL) {
			canvas.rotate(90*dirX, 0, 0);
		} else {
			canvas.rotate(-90*dirX, 0, 0);
		}

		// scale if teleporting
		if (teleportDuration > 0) {
			
			float factor = Math.abs(TELEPORT_DURATION/2 - teleportDuration)/(1.f*TELEPORT_DURATION/2);
			
			System.out.println("Draw teleport: " + factor);
			
			rectDest.set(-width/2*factor, -height/2*factor, width/2*factor, height/2*factor);
			
			canvas.drawBitmap(bitmap, null, rectDest, paint);
		} else {
			canvas.drawBitmap(bitmap, -width/2, - height/2, paint);
		}
		
		// unrotate
		if (direction == MyGameView.DIRECTION_REVERSE) {
			canvas.restore();
		}
		
		canvas.restore();
		
		// restore alpha
		if (remainingDuration() < FADE_OUT_DURATION) {
			paint.setAlpha(255);
		}
	}
	
	public void switchDirection() {
		if (direction == MyGameView.DIRECTION_NORMAL) {
			direction = MyGameView.DIRECTION_REVERSE;
		} else {
			direction = MyGameView.DIRECTION_NORMAL;
		}
	}
	
	@Override
	public boolean isActive() {
		
		// check time component
		if (!super.isActive()) {
			
			// drill use max time achievement
			Achievements.unlockLocalAchievement(Achievements.localDrillUseMaximumTime);
			
			return false;
		}
		
		if (teleportDuration > 0) {
			return true;
		}
		
		if (direction == MyGameView.DIRECTION_NORMAL) {
			if (hasTeleport) {
				return y - height/2 > 0;
			} else {
				return y + height/2 > 0;
			}
		} else {
			if (hasTeleport) {
				return y + height/2 < MyGameView.canvasHeight;
			} else {
				return y - height/2 < MyGameView.canvasHeight;
			}
		}
	}
	
	/**
	 * Teleports drill to other side of screen at random x position
	 */
	public void teleport() {
		
		System.out.println("TELEPORT");
		
		nextAsteroid = null;
		nextDistance = 0;

		teleportDuration = TELEPORT_DURATION;
	}
	
	/**
	 * Checks local drill-related achievements
	 */
	@Override
	public void checkAchievements() {
		if (numAffectedAsteroids > Achievements.LOCAL_DESTROY_ASTEROIDS_NUM_1 &&
				!Achievements.localDestroyAsteroidsWithDrill1.getValue())
		{
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithDrill1);
		}
		
		if (numAffectedAsteroids > Achievements.LOCAL_DESTROY_ASTEROIDS_NUM_2 &&
				!Achievements.localDestroyAsteroidsWithDrill2.getValue())
		{
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithDrill2);
		}
		
		if (numAffectedAsteroids > Achievements.LOCAL_DESTROY_ASTEROIDS_NUM_3 &&
				!Achievements.localDestroyAsteroidsWithDrill3.getValue())
		{
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithDrill3);
		}
	}
	
	/**
	 * Returns true if drill has teleport ability
	 * @return true if drill has teleport ability
	 */
	public boolean hasTeleport() {
		return hasTeleport;
	}

}
