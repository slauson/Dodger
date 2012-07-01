package com.slauson.dasher.game;

public class Level {
	
	private int level;
	private long levelStartTime;
	
	// constants
	private static final int NUM_ASTEROIDS_BASE = 10;
	private static final int NUM_ASTEROIDS_INCREMENT = 4;
	private static final int NUM_ASTEROIDS_MAX = 50;
	
	private static final int ASTEROID_RADIUS_MIN = 10;
	private static final int ASTEROID_RADIUS_INCREMENT = 2;
	private static final int ASTEROID_RADIUS_MAX = 30;
	
	private static final float ASTEROID_SPEED_MIN = 100;
	private static final float ASTEROID_SPEED_MIN_INCREMENT = 5;
	private static final float ASTEROID_SPEED_MAX = 100;
	private static final float ASTEROID_SPEED_MAX_INCREMENT = 10;
	private static final float ASTEROID_SPEED_LIMIT = 250;
	
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
	
	public int getAsteroidRadiusMin() {
		return ASTEROID_RADIUS_MIN;
	}
	
	public int getAsteroidRadiusOffset() {
		int num = level*ASTEROID_RADIUS_INCREMENT;
		
		if (ASTEROID_RADIUS_MIN + num > ASTEROID_RADIUS_MAX) {
			return ASTEROID_RADIUS_MAX - ASTEROID_RADIUS_MIN;
		}
		
		return num;
	}
	
	public float getAsteroidSpeedMin() {
		float num = ASTEROID_SPEED_MIN + level*ASTEROID_SPEED_MIN_INCREMENT;
		
		if (num > ASTEROID_SPEED_LIMIT) {
			return ASTEROID_SPEED_LIMIT;
		}
		
		return num;
	}
	
	public float getAsteroidSpeedOffset() {
		float num = ASTEROID_SPEED_MAX + level*ASTEROID_SPEED_MAX_INCREMENT;
		
		if (num > ASTEROID_SPEED_LIMIT) {
			return ASTEROID_SPEED_LIMIT;
		}
		
		return num;
	}
	
	public boolean hasAsteroidHorizontalMovement() {
		return level >= ASTEROID_HORIZONTAL_MOVEMENT_LEVEL;
	}
}
