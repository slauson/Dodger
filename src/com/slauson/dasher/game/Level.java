package com.slauson.dasher.game;

public class Level {
	
	private int level;
	private long levelStartTime;
	
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
	
	public Level(int level) {
		this.level = 0;
		levelStartTime = System.currentTimeMillis();
	}
	
	public Level() {
		level = 0;
		levelStartTime = System.currentTimeMillis();
	}
	
	public int getLevel() {
		return level;
	}
	
	public void reset() {
		level = 0;
		levelStartTime = System.currentTimeMillis();
	}
	
	public boolean update() {
		
		if (System.currentTimeMillis() - levelStartTime > LEVEL_TIME) {
			levelStartTime = System.currentTimeMillis();
			level++;
			return true;
		}
		
		return false;
	}
	
	public int getNumAsteroids() {
		
		int num = NUM_ASTEROIDS_BASE + level*NUM_ASTEROIDS_INCREMENT;
		
		if (num > NUM_ASTEROIDS_MAX) {
			return NUM_ASTEROIDS_MAX;
		}
		
		return num;
	}
	
	public float getAsteroidRadiusFactorMin() {
		return ASTEROID_RADIUS_FACTOR_MIN;
	}
	
	public float getAsteroidRadiusFactorOffset() {
		float num = level*ASTEROID_RADIUS_FACTOR_INCREMENT;
		
		if (ASTEROID_RADIUS_FACTOR_MIN + num > ASTEROID_RADIUS_FACTOR_MAX) {
			return ASTEROID_RADIUS_FACTOR_MAX - ASTEROID_RADIUS_FACTOR_MIN;
		}
		
		return num;
	}
	
	public float getAsteroidSpeedFactorMin() {
		float num = ASTEROID_SPEED_FACTOR_MIN + level*ASTEROID_SPEED_FACTOR_MIN_INCREMENT;
		
		if (num > ASTEROID_SPEED_FACTOR_LIMIT) {
			return ASTEROID_SPEED_FACTOR_LIMIT;
		}
		
		return num;
	}
	
	public float getAsteroidSpeedFactorOffset() {
		float num = ASTEROID_SPEED_FACTOR_MAX + level*ASTEROID_SPEED_FACTOR_MAX_INCREMENT;
		
		if (num > ASTEROID_SPEED_FACTOR_LIMIT) {
			return ASTEROID_SPEED_FACTOR_LIMIT;
		}
		
		return num;
	}
	
	public boolean hasAsteroidHorizontalMovement() {
		return level >= ASTEROID_HORIZONTAL_MOVEMENT_LEVEL;
	}
}
