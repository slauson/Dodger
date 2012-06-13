package com.Slauson.Dodger;

import java.util.Iterator;
import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class Player extends Sprite {
	
	private int startX, startY;
	
	private long startTime;
	
	private int goX, goY;
	
	private int shipType = SHIP_NORMAL;
	
	private Asteroid magnetAsteroid = null;
	
	private Bitmap[] bitmaps = new Bitmap[NUM_SHIPS];
	private Bitmap[] smallBitmaps = new Bitmap[NUM_SHIPS];
	
	// single powerups
	private PowerupDisco powerupDisco = new PowerupDisco();
	private PowerupDrill powerupDrill = new PowerupDrill();
	private PowerupSlow powerupSlow = new PowerupSlow();
	private PowerupSmall powerupSmall = new PowerupSmall();
	
	// multiple powerups
	private LinkedList<PowerupMagnet> powerupMagnets = new LinkedList<PowerupMagnet>();
	private LinkedList<PowerupWhiteHole> powerupWhiteHoles = new LinkedList<PowerupWhiteHole>();
	
	/**
	 * Constants
	 */
	private static final int MAX_SPEED = 25;
	private static final int POWERUP_TIME = 15000;
	
	private static final int NUM_SHIPS = 4;
	
	private static final int SHIP_NORMAL = 0;
	private static final int SHIP_DRILL = 1;
	private static final int SHIP_MAGNET = 2;
	private static final int SHIP_DRILL_AND_MAGNET = 3;
	
	private static final int POWERUP_Y_OFFSET = -8;
	
	private static final int DRILL_Y_OFFSET = -16;
	private static final int DRILL_WIDTH = 22;
	private static final int DRILL_HEIGHT = 18;
	
	private static final int MAGNET_Y_OFFSET = -20 - 25;
	private static final int MAGNET_WIDTH = 50;
	private static final int MAGNET_HEIGHT = 50;
	
	
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
	
	public void setupBitmaps(Bitmap bitmapDrill, Bitmap bitmapMagnet, Bitmap bitmapDrillAndMagnet) {
		bitmaps[SHIP_DRILL] = bitmapDrill;
		bitmaps[SHIP_MAGNET] = bitmapMagnet;
		bitmaps[SHIP_DRILL_AND_MAGNET] = bitmapDrillAndMagnet;
		
		smallBitmaps[SHIP_DRILL] = Bitmap.createScaledBitmap(bitmapDrill, bitmapDrill.getWidth()/2, bitmapDrill.getHeight()/2, false);
		smallBitmaps[SHIP_MAGNET] = Bitmap.createScaledBitmap(bitmapMagnet, bitmapMagnet.getWidth()/2, bitmapMagnet.getHeight()/2, false);
		smallBitmaps[SHIP_DRILL_AND_MAGNET] = Bitmap.createScaledBitmap(bitmapDrillAndMagnet, bitmapDrillAndMagnet.getWidth()/2, bitmapDrillAndMagnet.getHeight()/2, false);
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
		
		// update magnet asteroid
		if (magnetAsteroid != null) {
			
			// check if magnet asteroid got reset
			if (magnetAsteroid.getY() < 0) {
				magnetAsteroid = null;
			} else {
			
				int xDiff = getMagnetX() - magnetAsteroid.x;
				int yDiff = getMagnetY() - (magnetAsteroid.y + (int)(magnetAsteroid.height/2));
				
				if (xDiff + yDiff > 0) {
					magnetAsteroid.dirX = xDiff / (xDiff + yDiff);
					magnetAsteroid.dirY = yDiff / (xDiff + yDiff);
				} else {
					magnetAsteroid.dirX = 0;
					magnetAsteroid.dirY = 0;
				}
			}
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
	
	public boolean checkMagnetCollision(Sprite other) {
		if (isSmallActive()) {
			return Math.abs(x - other.x) <= MAGNET_WIDTH/4 + other.width/2 && Math.abs(y + MAGNET_Y_OFFSET/2 - other.y) <= MAGNET_HEIGHT/4 + other.height/2;
		} else {
			return Math.abs(x - other.x) <= MAGNET_WIDTH/2 + other.width/2 && Math.abs(y + MAGNET_Y_OFFSET - other.y) <= MAGNET_HEIGHT/2 + other.height/2;
		}
	}
	
	public int getMagnetX() {
		return x;
	}
	
	public int getMagnetY() {
		if (isSmallActive()) {
			return y - (int)(height/4) + POWERUP_Y_OFFSET/2;
		} else {
			return y - (int)(height/2) + POWERUP_Y_OFFSET;
		}
	}
}