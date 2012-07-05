package com.slauson.dasher.objects;

import java.util.ArrayList;

/**
 * Object to be drawn on the screen
 * @author Josh Slauson
 *
 */
public abstract class DrawObject extends Item {

	protected float[] points;
	protected float[] altPoints;
	
	protected ArrayList<LineSegment> lineSegments;
	
	protected int status;
	protected long timeCounter;

	// constants
	public static final int STATUS_NORMAL = 0;
	public static final int STATUS_INVISIBLE = 1;
	public static final int STATUS_BREAKING_UP = 2;
	public static final int STATUS_DISAPPEARING = 3;
	public static final int STATUS_FADING_OUT = 4;
	public static final int STATUS_SPLITTING_UP = 5;
	public static final int STATUS_INVULNERABLE = 6;

	// used by MyGameView to schedule game over message
	public static final int BREAKING_UP_DURATION = 2500;
	
	protected static final float BREAKING_UP_MOVE = 20;
	protected static final float DISAPPEARING_FACTOR = 0.25f;
	protected static final int FADING_OUT_DURATION = 1000;
	protected static final int SPLITTING_UP_DURATION = 2000;
	protected static final float SPLITTING_UP_OFFSET = 50;
	
	public DrawObject(float x, float y, int width, int height) {
		super(x, y, width, height);
		
		status = STATUS_NORMAL;
		timeCounter = 0;
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
}
