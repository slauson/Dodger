package com.slauson.dodger.objects;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Sprite {
	
	protected float width, height;
	protected float x, y;
	protected float dirX, dirY;
	protected float speed;
	
	public Sprite(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		this.dirX = 0;
		this.dirY = 0;
		this.speed = 0;
	}

	/**
	 * Abstract methods to be defined in subclasses 
	 */
	public abstract void draw(Canvas canvas, Paint paint);
	
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
	public boolean checkBoxCollision(Sprite other) {
		
		// quick check
		if (Math.abs(x - other.x) > width/2 + other.width/2 || Math.abs(y - other.y) > height/2 + other.height/2) {
			return false;
		}
		
		return true;
		
		/*
		// check top right corner
		if (x + width/2 <= other.x + other.width/2 &&
				x + width/2 >= other.x - other.width/2 &&
				y - height/2 <= other.y + other.height/2 &&
				y - height/2 >= other.y - other.height/2)
		{
			return true;
		}
		
		// check top left corner
		if (x - width/2 <= other.x + other.width/2 &&
				x - width/2 >= other.x - other.width/2 &&
				y - height/2 <= other.y + other.height/2 &&
				y - height/2 >= other.y - other.height/2)
		{
			return true;
		}
		
		// check bottom right corner
		if (x + width/2 <= other.x + other.width/2 &&
				x + width/2 >= other.x - other.width/2 &&
				y + height/2 <= other.y + other.height/2 &&
				y + height/2 >= other.y - other.height/2)
		{
			return true;
		}
		
		// check bottom left corner
		if (x - width/2 <= other.x + other.width/2 &&
				x - width/2 >= other.x - other.width/2 &&
				y + height/2 <= other.y + other.height/2 &&
				y + height/2 >= other.y - other.height/2)
		{
			return true;
		}
		
		return false;
		*/
	}
	
	/**
	 * Updates position based on direction, speed
	 */
	public void update() {
		x = x + (dirX*speed);
		y = y + (dirY*speed);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getDirX() {
		return dirX;
	}
	
	public void setDirX(float dirX) {
		this.dirX = dirX;
	}
	
	public float getDirY() {
		return dirY;
	}
	
	public void setDirY(float dirY) {
		this.dirY = dirY;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
