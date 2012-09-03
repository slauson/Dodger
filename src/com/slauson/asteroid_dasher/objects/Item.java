package com.slauson.asteroid_dasher.objects;

import com.slauson.asteroid_dasher.game.Game;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * An item to be displayed on the screen
 * @author Josh Slauson
 *
 */
public abstract class Item {
	
	protected float width, height;
	protected float x, y;
	protected float dirX, dirY;
	protected float speed;
	
	protected long lastUpdateTime;
	
	public Item(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		this.dirX = 0;
		this.dirY = 0;
		this.speed = 0;
		
		this.lastUpdateTime = System.currentTimeMillis();
	}

	/**
	 * Returns true if sprite's bounding box contains given point
	 * @param px point's x coordinate
	 * @param py point's y coordinate
	 * @return true if point is contained in box
	 */
	public boolean checkBoxContains(float px, float py) {
		// quick check
		if (Math.abs(x - px) > width/2 || Math.abs(y - py) > height/2) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Returns true if sprite's bounding box intersects given sprite's bounding box
	 * @param other Sprite to check
	 * @return true if bounding boxes intersect
	 */
	public boolean checkBoxCollision(Item other) {
		
		// quick check
		if (Math.abs(x - other.x) > width/2 + other.width/2 || Math.abs(y - other.y) > height/2 + other.height/2) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Set item's x value
	 * @param x x value to set to
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Set item's y value
	 * @param y y value to set to
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns item's x position
	 * @return item's x position
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Returns item's y position
	 * @return item's y position
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Returns item's width
	 * @return item's width
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * Returns item's height
	 * @return item's height
	 */
	public float getHeight() {
		return height;
	}
	
	/**
	 * Returns item's x direction
	 * @return item's x direction
	 */
	public float getDirX() {
		return dirX;
	}
	
	/**
	 * Sets item's x direction
	 * @param dirX direction to set to
	 */
	public void setDirX(float dirX) {
		this.dirX = dirX;
	}
	
	/**
	 * Returns item's y direction
	 * @return item's y direction
	 */
	public float getDirY() {
		return dirY;
	}
	
	/**
	 * Sets item's y direction
	 * @param dirY direction to set to
	 */
	public void setDirY(float dirY) {
		this.dirY = dirY;
	}
	
	/**
	 * Returns item's speed
	 * @return item's speed
	 */
	public float getSpeed() {
		return speed;
	}
	
	/**
	 * Sets item's speed
	 * @param speed speed to set to
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns true if this item is currently on the screen
	 * @return true if this item is currently on the screen
	 */
	public boolean onScreen() {
		return y + height/2 > 0 && y - height/2 < Game.canvasHeight;
	}
	
	/**
	 * Resets last update time to current time (useful for pause menu)
	 */
	public void resetUpdateTime() {
		lastUpdateTime = System.currentTimeMillis();
	}
	
	/**
	 * Returns elapsed time since last update
	 * Also updates lastUpdateTime
	 * @return elapsed time since last update
	 */
	public long getElapsedTime() {
		long timeElapsed = System.currentTimeMillis() - lastUpdateTime;
		lastUpdateTime = System.currentTimeMillis();
		
		// we don't want to drop more than 1 frame
		if (timeElapsed > 2*Game.maxSleepTime) {
			timeElapsed = 2*Game.maxSleepTime;
		}
		
		return timeElapsed;
	}
	
	/**
	 * Returns next y position of asteroid
	 * @param speedModifier
	 * @param timeModifier
	 * @return next y position
	 */
	public float getNextY(float speedModifier, float timeModifier) {

		// only use gravity when direction is positive
		if (dirY > 0) {
			return y + (Game.gravity*dirY*speed*timeModifier*speedModifier);
		} else {
			// otherwise use direction
			// NOTE: this allows items to stay moving in opposite direction during a dash
			// after the dash is completed, their directions are set positive again
			if (Game.direction == Game.DIRECTION_NORMAL) {
				return y + (1*dirY*speed*timeModifier*speedModifier);
			} else {
				return y + (-1*dirY*speed*timeModifier*speedModifier);
			}
		}
	}

	/**
	 * Abstract methods to be defined in subclasses 
	 */
	/**
	 * Draws item on screen using the given canvas and paint
	 * @param canvas canvas to draw item onto
	 * @param paint paint to use to draw item
	 */
	public abstract void draw(Canvas canvas, Paint paint);
	
	/**
	 * Update's item's state
	 */
	public abstract void update();
	
}
