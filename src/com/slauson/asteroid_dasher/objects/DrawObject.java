package com.slauson.asteroid_dasher.objects;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;

import com.slauson.asteroid_dasher.game.Game;

/**
 * Object to be drawn on the screen
 * @author Josh Slauson
 *
 */
public abstract class DrawObject extends Item {

	protected float[] points;
	
	protected ArrayList<LineSegment> lineSegments;
	
	protected Bitmap bitmap;
	
	protected int status;
	protected long timeCounter;
	
	protected RectF rectDest;
	protected Rect rectSrc;

	// constants
	public static final int STATUS_NORMAL = 0;
	public static final int STATUS_INVISIBLE = 1;
	public static final int STATUS_BREAKING_UP = 2;
	public static final int STATUS_DISAPPEARING = 3;
	public static final int STATUS_FADING_OUT = 4;
	public static final int STATUS_SPLITTING_UP = 5;
	public static final int STATUS_INVULNERABILITY = 6;
	public static final int STATUS_HELD_IN_PLACE = 7;
	public static final int STATUS_NEEDS_RESET = 8;

	// used by MyGame to schedule game over message
	protected static final int BREAKING_UP_DURATION = 5000;
	
	protected static final float BREAKING_UP_FACTOR = 0.5f;
	protected static final float BREAKING_UP_MOVE = 40;
	protected static final float DISAPPEARING_FACTOR = 0.125f;
	protected static final int FADING_OUT_DURATION = 1000;
	protected static final int SPLITTING_UP_DURATION = 1000;
	protected static final float SPLITTING_UP_OFFSET = 20;
	
	protected static final float STROKE_WIDTH = 2;

	public DrawObject(float x, float y, int width, int height) {
		super(x, y, width, height);
		
		status = STATUS_NORMAL;
		timeCounter = 0;
	}
	
	/**
	 * Draws points to bitmap
	 */
	protected void drawPointsToBitmap(boolean highQuality) {
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(STROKE_WIDTH);
		
		// snowflake mode
		if (Game.gameMode == Game.GAME_MODE_SNOWFLAKE && this instanceof Asteroid) {
			paint.setStrokeWidth(width/2);
		}
		
		// use anti-aliasing, dithering if high quality
		if (highQuality) {
			paint.setAntiAlias(true);
			paint.setDither(true);
		}
		
		// clear canvas
		canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		canvas.translate(bitmap.getWidth()/2, bitmap.getHeight()/2);
		
		canvas.drawLines(points, paint);
	}

	// abstract methods to be defined in subclasses
	public abstract void reset();
	public abstract void breakup();

	/**
	 * Returns status of drawable object
	 * @return status of drawable object
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Returns relative size according to screen width
	 * @param factor percentage of screen size
	 * @return relative size according to screen width
	 */
	public int getRelativeWidthSize(float factor) {
		return (int)(Game.canvasWidth*factor);
	}
	
	/**
	 * Returns relative size according to screen height
	 * @param factor percentage of screen size
	 * @return relative size according to screen height
	 */
	public int getRelativeHeightSize(float factor) {
		return (int)(Game.canvasHeight*factor);
	}
	
	/**
	 * Cleans up bitmap.
	 */
	public void cleanup() {
		if (bitmap != null) {
			bitmap.recycle();
		}
	}
}
