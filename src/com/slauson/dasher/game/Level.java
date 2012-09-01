package com.slauson.dasher.game;

/**
 * Level in the game.
 * @author Josh Slauson
 *
 */
public class Level {
	
	private int level;
	private long levelStartTime;
	
	private boolean progression;
	
	/** Starting number of asteroids **/
	private static final int NUM_ASTEROIDS_BASE = 10;
	/** Number of asteroids to add each level **/
	private static final int NUM_ASTEROIDS_INCREMENT = 2;
	/** Maximum number of asteroids **/
	private static final int NUM_ASTEROIDS_MAX = 50;
	
	/** Starting radius of asteroids in comparison to canvas width **/
	private static final float ASTEROID_RADIUS_FACTOR_MIN = 0.05f;
	/** Asteroid radius increase each level **/
	private static final float ASTEROID_RADIUS_FACTOR_INCREMENT = 0.0025f;
	/** Maximum radius of asteroids in comparison to canvas width **/
	private static final float ASTEROID_RADIUS_FACTOR_MAX = 0.1f;
	
	/** Minimum possible speed of asteroids in comparison to canvas height **/
	private static final float ASTEROID_SPEED_FACTOR_MIN = 0.1f;
	/** Minimum asteroid speed increase each level **/
	private static final float ASTEROID_SPEED_FACTOR_MAX = 0.1f;
	/** Maximum asteroid speed increase each level **/
	private static final float ASTEROID_SPEED_FACTOR_MAX_INCREMENT = 0.005f;
	/** Limit for maximum asteroid speed in comparison to canvas height **/
	private static final float ASTEROID_SPEED_FACTOR_LIMIT = 0.25f;
	
	/** Minimum asteroid horizontal movement (0 - 1) **/
	private static final float ASTEROID_HORIZONTAL_MOVEMENT_MIN = 0.0f;
	/** Asteroid horizontal movement increase each level **/
	private static final float ASTEROID_HORIZONTAL_MOVEMENT_INCREMENT = 0.01f;
	/** Maximum asteroid horizontal movement (0 - 1) **/
	private static final float ASTEROID_HORIZONTAL_MOVEMENT_MAX = 0.25f;
	
	/** Level in which asteroids begin having horizontal movement **/
	private static final int ASTEROID_HORIZONTAL_MOVEMENT_LEVEL = 5;
	
	/** Duration of each level **/
	private static final long LEVEL_TIME = 15000;
	
	public Level(int level, boolean progression) {
		this.level = level;
		this.progression = progression;
		levelStartTime = System.currentTimeMillis();
	}
	
	/**
	 * Returns current level
	 * @return current level
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Resets level
	 */
	public void reset() {
		level = 0;
		levelStartTime = System.currentTimeMillis();
	}
	
	/**
	 * Updates level
	 * @return true if level changed
	 */
	public boolean update() {
		
		if (progression && System.currentTimeMillis() - levelStartTime > LEVEL_TIME) {
			levelStartTime = System.currentTimeMillis();
			level++;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns number of asteroids in level
	 * @return number of asteroids in level
	 */
	public int getNumAsteroids() {
		
		int num = NUM_ASTEROIDS_BASE + level*NUM_ASTEROIDS_INCREMENT;
		
		if (num > NUM_ASTEROIDS_MAX) {
			return NUM_ASTEROIDS_MAX;
		}
		
		return num;
	}
	
	/**
	 * Returns asteroid radius factor minimum
	 * @return asteroid radius factor minimum
	 */
	public float getAsteroidRadiusFactorMin() {
		return ASTEROID_RADIUS_FACTOR_MIN;
	}
	
	/**
	 * Returns asteroid radius factor offset
	 * @return asteroid radius factor offset
	 */
	public float getAsteroidRadiusFactorOffset() {
		float num = level*ASTEROID_RADIUS_FACTOR_INCREMENT;
		
		if (ASTEROID_RADIUS_FACTOR_MIN + num > ASTEROID_RADIUS_FACTOR_MAX) {
			return ASTEROID_RADIUS_FACTOR_MAX - ASTEROID_RADIUS_FACTOR_MIN;
		}
		
		return num;
	}
	
	/**
	 * Returns asteroid radius factor maximum
	 * @return asteroid radius factor maximum
	 */
	public float getAsteroidRadiusFactorMax() {
		return ASTEROID_RADIUS_FACTOR_MAX;
	}
	
	/**
	 * Returns asteroid speed factor minimum
	 * @return asteroid speed factor minimum
	 */
	public float getAsteroidSpeedFactorMin() {
		return ASTEROID_SPEED_FACTOR_MIN;
	}
	
	/**
	 * Returns asteroid speed factor offset
	 * @return asteroid speed factor offset
	 */
	public float getAsteroidSpeedFactorOffset() {
		float num = ASTEROID_SPEED_FACTOR_MAX + level*ASTEROID_SPEED_FACTOR_MAX_INCREMENT;
		
		if (num > ASTEROID_SPEED_FACTOR_LIMIT) {
			return ASTEROID_SPEED_FACTOR_LIMIT;
		}
		
		return num;
	}
	
	/**
	 * Returns true if asteroids have horizontal movement
	 * @return true if asteroids have horizontal movement
	 */
	public boolean hasAsteroidHorizontalMovement() {
		return level >= ASTEROID_HORIZONTAL_MOVEMENT_LEVEL;
	}
	
	/**
	 * Returns asteroid horizontal movement offset (0 - 1)
	 * @return asteroid horizontal movement offset (0 - 1)
	 */
	public float getAsteroidHorizontalMovementOffset() {
		float num = (level - ASTEROID_HORIZONTAL_MOVEMENT_LEVEL)*ASTEROID_HORIZONTAL_MOVEMENT_INCREMENT;
		
		if (ASTEROID_HORIZONTAL_MOVEMENT_MIN + num > ASTEROID_HORIZONTAL_MOVEMENT_MAX) {
			return ASTEROID_HORIZONTAL_MOVEMENT_MAX - ASTEROID_HORIZONTAL_MOVEMENT_MIN;
		}
		
		return num;
	}
	
	/**
	 * Adds the given duration to the level start time
	 * @param milliseconds time to add to the level start time
	 */
	public void addToStartTime(long milliseconds) {
		this.levelStartTime += milliseconds;
	}
}