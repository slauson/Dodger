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
	
	private int startX, startY;
	
	private long startTime;
	
	private int goX, goY;
	
	private int shipType = SHIP_NORMAL;

	private Bitmap[] bitmaps = new Bitmap[NUM_SHIP_TYPES];
	private Bitmap[] smallBitmaps = new Bitmap[NUM_SHIP_TYPES];
	
	// powerups
	private PowerupDisco powerupDisco = new PowerupDisco();
	private PowerupDrill powerupDrill = new PowerupDrill();
	private PowerupSlow powerupSlow = new PowerupSlow();
	private PowerupSmall powerupSmall = new PowerupSmall();
	
	/**
	 * Constants
	 */
	private static final int MAX_SPEED = 25;
	private static final int POWERUP_TIME = 15000;
	
	private static final int NUM_SHIP_TYPES = 2;
	
	private static final int SHIP_NORMAL = 0;
	private static final int SHIP_DRILL = 1;
	
	private static final int POWERUP_Y_OFFSET = -8;
	
	private static final int DRILL_Y_OFFSET = -16;
	private static final int DRILL_WIDTH = 22;
	private static final int DRILL_HEIGHT = 18;	
	
	public Player(Bitmap bitmap, int x, int y) {
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
			canvas.drawBitmap(smallBitmaps[shipType], x-width/4, y-height/4, null);
		}
		// draw normal bitmap
		else {
			
			if (isSlowActive()) {
				canvas.drawBitmap(bitmaps[shipType], x-width/2 - 10*speed*dirX, y-height/2 - 10*speed*dirY, null);
			}
			canvas.drawBitmap(bitmaps[shipType], x-width/2, y-height/2, null);
		}
	}

	@Override
	public void update() {
		if (goX != x) {
			if (goX > x) {
				x += MAX_SPEED;
				
				if (x > goX) {
					x = goX;
				}
			} else {
				x -= MAX_SPEED;
				
				if (x < goX) {
					x = goX;
				}
			}
		}
		
		if (goY != y) {
			if (goY > y) {
				y += MAX_SPEED;
				
				if (y > goY) {
					y = goY;
				}
			} else {
				y-= MAX_SPEED;
				
				if (y < goY) {
					y = goY;
				}
			}
		}
		x = x + (int)(dirX*speed);
		y = y + (int)(dirY*speed);
		
		// update ship type
		shipType = SHIP_NORMAL;
		
		if (isDrillActive()) {
			shipType = SHIP_DRILL;
		}
		
		// update ship's height
		if (shipType != SHIP_NORMAL) {
			y += POWERUP_Y_OFFSET;
		}
	}

	public long getStartTime() {
		return startTime;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}
	
	public void setGoX(int goX) {
		this.goX = goX;
	}
	
	public void setGoY(int goY) {
		this.goY = goY;
	}
	
	public void activatePowerup(int type) {
		
		System.out.println("Activate powerup " + type);
		
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
	
	public void setX(int x) {
		this.x = x;
	}
}