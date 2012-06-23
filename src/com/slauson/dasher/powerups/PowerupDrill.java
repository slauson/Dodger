package com.slauson.dasher.powerups;

import com.slauson.dasher.main.MyGameView;
import com.slauson.dasher.objects.Asteroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Drill powerup that splits asteroids in half
 * @author Josh Slauson
 *
 */
public class PowerupDrill extends ActivePowerup {

	private static final int SPEED = 10;
	private static final float WEIGHTED_DISTANCE_X_FACTOR = 2;
	private static final float MAX_DIR_CHANGE = 0.05f;
	private static final float CONE_CHECK_X_FACTOR = 1.5f;
	
	private int direction;
	
	private Asteroid nextAsteroid;
	private float nextDistance;
		
	public PowerupDrill(Bitmap bitmap, float x, float y, int duration, int direction) {
		super(bitmap, x, y);
		
		this.direction = direction;
		
		nextAsteroid = null;
		nextDistance = 0;

		dirY = 1;
		dirX = 0;
		
		activate(duration);
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
			nextAsteroid = null;
		}
		
		// get weighted distance
		float distanceX = Math.abs(x - asteroid.getX());
		float distanceY = Math.abs(y - asteroid.getY());
		
		// cone check
		if (CONE_CHECK_X_FACTOR*distanceX > distanceY) {
			return;
		}
		
		float weightedDistance = (float)Math.sqrt(Math.pow(WEIGHTED_DISTANCE_X_FACTOR*distanceX, 2) + Math.pow(distanceY, 2));

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
		
		// update direction based on next asteroid
		if (nextAsteroid != null) {
			float distanceX = nextAsteroid.getX() - x;
			float distanceY = nextAsteroid.getY() - y;
			
			float distanceAbsoluteValue = Math.abs(distanceX) + Math.abs(distanceY);
			
			float actualDirX = distanceX/(distanceAbsoluteValue)/2;
			//float actualDirY = 0.5f + distanceY/(distanceAbsoluteValue)/2;
			
			float dirChangeX = actualDirX - dirX;
			if (Math.abs(dirChangeX) < MAX_DIR_CHANGE) {
				dirX = actualDirX;
			} else if (dirChangeX > 0) {
				dirX += MAX_DIR_CHANGE;
			} else {
				dirX -= MAX_DIR_CHANGE;
			}
			
			dirY = 1.0f - Math.abs(dirX);
		}
		
		if (direction == MyGameView.DIRECTION_NORMAL) {
			y -= dirY*SPEED*speedModifier;
		} else {
			y += dirY*SPEED*speedModifier;
		}
		
		x += dirX*SPEED*speedModifier;
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		// rotate if needed
		if (direction == MyGameView.DIRECTION_REVERSE) {
			canvas.save();
			canvas.rotate(180, x, y);
		}
		
		// rotate based on direction
		canvas.save();
		canvas.rotate(90*dirX, x, y);
		
		canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
		
		canvas.restore();
		
		// unrotate
		if (direction == MyGameView.DIRECTION_REVERSE) {
			canvas.restore();
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
		if (direction == MyGameView.DIRECTION_NORMAL) {
			return super.isActive() && y + height/2 > 0;
		} else {
			return super.isActive() && y - height/2 < MyGameView.canvasHeight;
		}
	}
}
