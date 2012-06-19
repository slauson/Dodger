package com.slauson.dodger.objects;

import com.slauson.dodger.main.MyGameView;
import com.slauson.dodger.powerups.Powerup;
import com.slauson.dodger.powerups.PowerupSmall;
import com.slauson.dodger.powerups.PowerupSpin;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Player extends Sprite {
	
	private float startX, startY;
	
	private long startTime;
	
	private float goX;
	
	private float speedX, speedY;
	
	private Bitmap bitmap, smallBitmap;
	
	private int move;
	private boolean inPosition;
	private int direction;

	/**
	 * Constants
	 */
	public static final int MAX_SPEED = 25;
	
	private static final int MOVE_NONE = 0;
	private static final int MOVE_LEFT = 1;
	private static final int MOVE_RIGHT = 2;
	
	private static final float BUTTON_MOVE_FACTOR = 4f;
	private static final float BUTTON_MIN_SPEED = 2f;
	
	private static final float SLOW_BLUR_FACTOR = 3f;
	
	private static final float Y_BOTTOM = MyGameView.canvasHeight - 100;
	private static final float Y_TOP = 32;
	
	private static final float ROTATION_DEGREES = 900f;
	
	private static final int SMALL_DURATION = 10000;
	
	public Player(Bitmap bitmap, float x) {
		super(x, Y_BOTTOM, bitmap.getWidth(), bitmap.getHeight());

		this.bitmap = bitmap;
		smallBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/2, bitmap.getHeight()/2, false);

		startX = x;
		startY = y;
		
		goX = x;
		
		speedX = 0;
		speedY = 0;
		
		move = MOVE_NONE;
		inPosition = true;
		direction = MyGameView.DIRECTION_NORMAL;
		
		startTime = System.currentTimeMillis();
	}
	
	public void reset() {
		//x = startX;
		//y = startY;
		
		this.startTime = System.currentTimeMillis();
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		
		// check if we need to rotate
		if (speedY > 1 || direction == MyGameView.DIRECTION_REVERSE) {
			canvas.save();
			
			float degrees = ROTATION_DEGREES * (Y_BOTTOM - y) / (Y_BOTTOM - Y_TOP);			
			canvas.rotate(degrees, x, y);
		}
		
		// if small powerup is active, draw resized bitmap
		if (MyGameView.powerupSmall.isActive()) {
			System.out.println("DRAW SMALL SHIP");
			// poor man's blur effect
			if (speedX + speedY != 0 && (MyGameView.powerupStop.isActive() || MyGameView.powerupSlow.isActive())) {
				canvas.drawBitmap(smallBitmap, x-width/4 - SLOW_BLUR_FACTOR*dirX, y-height/4 - SLOW_BLUR_FACTOR*dirY, null);
			}
			canvas.drawBitmap(smallBitmap, x-width/4, y-height/4, null);
		}
		// draw normal bitmap
		else {
			// poor man's blur effect
			if (speedX + speedY != 0 && (MyGameView.powerupStop.isActive() || MyGameView.powerupSlow.isActive())) {
				canvas.drawBitmap(bitmap, x-width/2 - SLOW_BLUR_FACTOR*dirX, y-height/2 - SLOW_BLUR_FACTOR*dirY, null);
			}
			canvas.drawBitmap(bitmap, x-width/2, y-height/2, null);
		}
		
		// restore canvas if rotated
		if (speedY > 1 || direction == MyGameView.DIRECTION_REVERSE) {
			canvas.restore();
		}
	}

	@Override
	public void update() {
		
		// touch based controls
		if (MyGameView.controlMode == MyGameView.CONTROL_TOUCH) {
			// damn floating point arithmetic
			if (Math.abs(goX - x) > 1) {
				speedX = MAX_SPEED;

				// need to move right
				if (goX > x) {
					dirX = 1;
					
					if (goX - x < MAX_SPEED) {
						speedX = goX - x;
					}
				}
				// need to move left
				else {
					dirX = -1;
					
					if (x - goX < MAX_SPEED) {
						speedX = x - goX;
					}
				}
			} else {
				speedX = 0;
			}
		}
		// key based controls
		else if (MyGameView.controlMode == MyGameView.CONTROL_BUTTONS) {
			if (move != MOVE_NONE) {
				if (speedX < MAX_SPEED) {
					speedX += BUTTON_MOVE_FACTOR;
					
					if (speedX > MAX_SPEED) {
						speedX = MAX_SPEED;
					}
				}
			}
		}
		
		x = x + (dirX*speedX);
		
		speedY = 0;
		dirY = 0;
		
		// move from bottom to top
		if (!inPosition) {
			if (direction == MyGameView.DIRECTION_REVERSE && Math.abs(y - Y_TOP) > 1) {
				dirY = -1;
				
				speedY = MAX_SPEED;
				
				if (y - Y_TOP <= MAX_SPEED) {
					speedY = y - Y_TOP;
					inPosition = true;
				}
			}
			// move from top to bottom
			else if (direction == MyGameView.DIRECTION_NORMAL && Math.abs(y - Y_BOTTOM) > 1) {
				dirY = 1;
				
				speedY = MAX_SPEED;
				
				if (Y_BOTTOM - y <= MAX_SPEED) {
					speedY = Y_BOTTOM - y;
					inPosition = true;
				}
			}
		}
		
		y = y + (dirY*speedY);
	}

	public long getStartTime() {
		return startTime;
	}

	public float getStartX() {
		return startX;
	}

	public float getStartY() {
		return startY;
	}
	
	public void setGoX(float goX) {
		this.goX = goX;
	}
	
	@Override
	public boolean checkBoxCollision(Sprite other) {
		if (MyGameView.powerupSmall.isActive()) {
			return Math.abs(x - other.x) <= width/4 + other.width/2 && Math.abs(y - other.y) <= width/4 + other.height/2;
		} else {
			return Math.abs(x - other.x) <= width/2 + other.height/2 && Math.abs(y - other.y) <= height/2 + other.height/2;
		}
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void moveLeft() {
		
		if (speedX == 0 || move != MOVE_LEFT) {
			speedX = BUTTON_MIN_SPEED;
		}
		
		dirX = -1;
		
		move = MOVE_LEFT;
	}
	
	public void moveRight() {
		if (speedX == 0 || move != MOVE_RIGHT) {
			speedX = BUTTON_MIN_SPEED;
		}
		
		dirX = 1;
		
		move = MOVE_RIGHT;
	}
	
	public void moveStop() {
		dirX = 0;
		move = MOVE_NONE;
	}
	
	public void switchDirection() {
		inPosition = false;
		
		if (direction == MyGameView.DIRECTION_NORMAL) {
			direction = MyGameView.DIRECTION_REVERSE;
		} else {
			direction = MyGameView.DIRECTION_NORMAL;
		}
	}
	
	public boolean inPosition() {
		return inPosition;
	}
	
	public int getDirection() {
		return direction;
	}
	
	/**
	 * Returns float between [-1, 1] depending on ship's position
	 * @return gravity
	 */
	public float getGravity() {
		return 1 - 2 * (Y_BOTTOM - y) / (Y_BOTTOM - Y_TOP);
	}
}