package com.slauson.dodger.objects;

import com.slauson.dodger.main.MyGameView;
import com.slauson.dodger.powerups.PowerupDisco;
import com.slauson.dodger.powerups.PowerupDrill;
import com.slauson.dodger.powerups.PowerupSlow;
import com.slauson.dodger.powerups.PowerupSmall;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Player extends Sprite {
	
	private float startX, startY;
	
	private long startTime;
	
	private float goX, goY;
	
	private int shipType = SHIP_NORMAL;

	private Bitmap[] bitmaps = new Bitmap[NUM_SHIP_TYPES];
	private Bitmap[] smallBitmaps = new Bitmap[NUM_SHIP_TYPES];
	
	// powerups
	private PowerupDisco powerupDisco = new PowerupDisco();
	private PowerupDrill powerupDrill = new PowerupDrill();
	private PowerupSlow powerupSlow = new PowerupSlow();
	private PowerupSmall powerupSmall = new PowerupSmall();
	
	private int move = MOVE_NONE;

	/**
	 * Constants
	 */
	public static final int MAX_SPEED = 25;
	public static final int POWERUP_TIME = 15000;
	
	public static final int NUM_SHIP_TYPES = 2;
	
	public static final int SHIP_NORMAL = 0;
	public static final int SHIP_DRILL = 1;
	
	public static final int POWERUP_Y_OFFSET = -8;
	
	public static final int DRILL_Y_OFFSET = -16;
	public static final int DRILL_WIDTH = 22;
	public static final int DRILL_HEIGHT = 18;	
	
	private static final int MOVE_NONE = 0;
	private static final int MOVE_LEFT = 1;
	private static final int MOVE_RIGHT = 2;
	
	private static final float BUTTON_MOVE_FACTOR = 4f;
	private static final float BUTTON_MIN_SPEED = 2f;
	
	private static final float SLOW_BLUR_FACTOR = 3f;
	
	public Player(Bitmap bitmap, float x, float y) {
		super(x, y, bitmap.getWidth(), bitmap.getHeight());
		
		this.startX = x;
		this.startY = y;
		
		this.goX = x;
		this.goY = y;
		
		this.startTime = System.currentTimeMillis();
		
		bitmaps[SHIP_NORMAL] = bitmap;
		smallBitmaps[SHIP_NORMAL] = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/2, bitmap.getHeight()/2, false);
		
		//this.bitmap.eraseColor(Color.BLUE);
	}
	
	public void setupBitmaps(Bitmap bitmapDrill) {
		bitmaps[SHIP_DRILL] = bitmapDrill;
		
		smallBitmaps[SHIP_DRILL] = Bitmap.createScaledBitmap(bitmapDrill, bitmapDrill.getWidth()/2, bitmapDrill.getHeight()/2, false);
	}
	
	public void reset() {
		//x = startX;
		//y = startY;
		
		this.startTime = System.currentTimeMillis();
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		
		// if small powerup is active, draw resized bitmap
		if (isSmallActive()) {
			// poor man's blur effect
			if (speed != 0 && isSlowActive()) {
				canvas.drawBitmap(smallBitmaps[shipType], x-width/2 - SLOW_BLUR_FACTOR*dirX, y-height/2 - SLOW_BLUR_FACTOR*dirY, null);
			}

			canvas.drawBitmap(smallBitmaps[shipType], x-width/4, y-height/4, null);
		}
		// draw normal bitmap
		else {
			
			// poor man's blur effect
			if (speed != 0 && isSlowActive()) {
				canvas.drawBitmap(bitmaps[shipType], x-width/2 - SLOW_BLUR_FACTOR*dirX, y-height/2 - SLOW_BLUR_FACTOR*dirY, null);
			}
			canvas.drawBitmap(bitmaps[shipType], x-width/2, y-height/2, null);
		}
	}

	@Override
	public void update() {
		
		System.out.println(x + "," + y + " -> " + goX + "," + goY);
		
		// touch based controls
		if (MyGameView.controlMode == MyGameView.CONTROL_TOUCH) {
			// damn floating point arithmetic
			if (Math.abs(goX - x) > 1) {
				speed = MAX_SPEED;

				// need to move right
				if (goX > x) {
					dirX = 1;
					
					if (goX - x < MAX_SPEED) {
						speed = goX - x;
					}
				}
				// need to move left
				else {
					dirX = -1;
					
					if (x - goX < MAX_SPEED) {
						speed = x - goX;
					}
				}
			} else {
				speed = 0;
			}
		}
		// key based controls
		else if (MyGameView.controlMode == MyGameView.CONTROL_BUTTONS) {
			if (move != MOVE_NONE) {
				if (speed < MAX_SPEED) {
					speed += BUTTON_MOVE_FACTOR;
					
					if (speed > MAX_SPEED) {
						speed = MAX_SPEED;
					}
				}
			}
		}
		
		x = x + (dirX*speed);
		//y = y + (dirY*speed);
		
		// update ship type
		shipType = SHIP_NORMAL;
		
		if (isDrillActive()) {
			shipType = SHIP_DRILL;
		}
		
		// update ship's y position if powerup is active
		if (shipType != SHIP_NORMAL) {
			y = startY + POWERUP_Y_OFFSET;
		}
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
	
	public void setGoX(int goX) {
		this.goX = goX;
	}
	
	public void setGoY(int goY) {
		this.goY = goY;
	}
	
	public void activatePowerup(int type) {
		
		switch(type) {
		case MyGameView.POWERUP_DISCO:
			powerupDisco.activate(POWERUP_TIME);
			break;
		case MyGameView.POWERUP_DRILL:
			powerupDrill.activate(POWERUP_TIME);
			break;
		case MyGameView.POWERUP_SLOW:
			powerupSlow.activate(POWERUP_TIME);
			break;
		case MyGameView.POWERUP_SMALL:
			powerupSmall.activate(POWERUP_TIME);
			break;
		}
	}
	
	public boolean isDiscoActive() {
		return powerupDisco.isActive();
	}

	public boolean isDrillActive() {
		return powerupDrill.isActive();
	}
	
	public boolean isSlowActive() {
		return powerupSlow.isActive();
	}
	
	public boolean isSmallActive() {
		return powerupSmall.isActive();
	}
	
	public boolean checkDrillCollision(Sprite other) {
		if (isSmallActive()) {
			return Math.abs(x - other.x) <= DRILL_WIDTH/4 + other.width/2 && Math.abs(y + DRILL_Y_OFFSET/2 - other.y) <= DRILL_HEIGHT/4 + other.height/2;
		} else {
			return Math.abs(x - other.x) <= DRILL_WIDTH/2 + other.width/2 && Math.abs(y + DRILL_Y_OFFSET - other.y) <= DRILL_HEIGHT/2 + other.height/2;
		}
	}
	
	@Override
	public boolean checkBoxCollision(Sprite other) {
		if (isSmallActive()) {
			return Math.abs(x - other.x) <= width/4 + other.width/2 && Math.abs(y - other.y) <= width/4 + other.height/2;
		} else {
			return Math.abs(x - other.x) <= width/2 + other.height/2 && Math.abs(y - other.y) <= height/2 + other.height/2;
		}
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void moveLeft() {
		
		if (speed == 0 || move != MOVE_LEFT) {
			speed = BUTTON_MIN_SPEED;
		}
		
		dirX = -1;
		
		move = MOVE_LEFT;
	}
	
	public void moveRight() {
		if (speed == 0 || move != MOVE_RIGHT) {
			speed = BUTTON_MIN_SPEED;
		}
		
		dirX = 1;
		
		move = MOVE_RIGHT;
	}
	
	public void moveStop() {
		dirX = 0;
		move = MOVE_NONE;
	}
}