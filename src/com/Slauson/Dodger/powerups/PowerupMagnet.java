package com.slauson.dodger.powerups;


import com.slauson.dodger.objects.Asteroid;

import android.graphics.Bitmap;

public class PowerupMagnet extends PowerupStationary {
	
	public static final int MAGNET_RANGE = 200;
	
	private static final int MAX_NUM_HITS = 5;
	
	private int numHits;
	
	public PowerupMagnet(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
		
		activate(Integer.MAX_VALUE);
		
		this.numHits = 0;
	}
	
	/**
	 * Alters asteroids direction based on distance from magnet
	 * Also breaks up asteroid if too close
	 * @param asteroid asteroid to modify
	 */
	public void alterAsteroid(Asteroid asteroid) {
	
		// don't pull asteroids past magnet
		if (asteroid.getY() > y) {
			return;
		}
		
		// get distance from asteroid
		float distanceX = x - asteroid.getX();
		float distanceY = y - asteroid.getY();
		
		float absDistanceX = Math.abs(distanceX);
		float absDistanceY = Math.abs(distanceY);
		
		int distance = (int)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
		
		if (distance < MAGNET_RANGE) {
			
			// direction directly to asteroid
			float magnetDirX = 1.0f*distanceX/(absDistanceX + absDistanceY);
			float magnetDirY = 1.0f*distanceY/(absDistanceX + absDistanceY);
			
			float magnetPullFactor = 1.0f*distance/MAGNET_RANGE;
			
			float dirX = (1 - magnetPullFactor)*asteroid.getDirX() + (magnetPullFactor)*magnetDirX;
			float dirY = (1 - magnetPullFactor)*asteroid.getDirY() + (magnetPullFactor)*magnetDirY;
			
			asteroid.setDirX(dirX);
			asteroid.setDirY(dirY);
			
			// check if asteroid hit magnet
			if (absDistanceX < width/2 + asteroid.getWidth()/2 && absDistanceY < height/2 + asteroid.getHeight()/2) {
				asteroid.breakup();
				
				numHits++;
			}
		}
	}
	
	@Override
	public boolean isActive() {
		return super.isActive() && numHits < MAX_NUM_HITS;
	}
}