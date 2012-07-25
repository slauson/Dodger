package com.slauson.dasher.game;

public class Level {
	
	private int level;
	private long startTime;
	
	private boolean progression;
	
	// constants
	private static final int NUM_ASTEROIDS_BASE = 10;
	private static final int NUM_ASTEROIDS_INCREMENT = 4;
	private static final int NUM_ASTEROIDS_MAX = 50;
	
	private static final float ASTEROID_RADIUS_FACTOR_MIN = 0.02f;
	private static final float ASTEROID_RADIUS_FACTOR_INCREMENT = 0.004f;
	private static final float ASTEROID_RADIUS_FACTOR_MAX = 0.1f;
	
	private static final float ASTEROID_SPEED_FACTOR_MIN = 0.1f;
	private static final float ASTEROID_SPEED_FACTOR_MIN_INCREMENT = 0.005f;
	private static final float ASTEROID_SPEED_FACTOR_MAX = 0.1f;
	private static final float ASTEROID_SPEED_FACTOR_MAX_INCREMENT = 0.01f;
	private static final float ASTEROID_SPEED_FACTOR_LIMIT = 0.3f;
	
	private static final int ASTEROID_HORIZONTAL_MOVEMENT_LEVEL = 4;
	
	private static final long LEVEL_TIME = 15000;
	
	public Level() {
		this(0, true);
	}
	
	public Level(int level) {
		this(level, true);
	}
	
	public Level(int level, boolean progression) {
		this.level = level;
		this.progression = progression;
		startTime = System.currentTimeMillis();
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
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Updates level
	 * @return true if level changed
	 */
	public boolean update() {
		
		if (progression && System.currentTimeMillis() - startTime > LEVEL_TIME) {
			startTime = System.currentTimeMillis();
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
	 * Returns asteroid speed factor minimum
	 * @return asteroid speed factor minimum
	 */
	public float getAsteroidSpeedFactorMin() {
		float num = ASTEROID_SPEED_FACTOR_MIN + level*ASTEROID_SPEED_FACTOR_MIN_INCREMENT;
		
		if (num > ASTEROID_SPEED_FACTOR_LIMIT) {
			return ASTEROID_SPEED_FACTOR_LIMIT;
		}
		
		return num;
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
	 * Adds the given duration to the level start time
	 * @param milliseconds time to add to the level start time
	 */
	public void addToStartTime(long milliseconds) {
		this.startTime += milliseconds;
	}
}
