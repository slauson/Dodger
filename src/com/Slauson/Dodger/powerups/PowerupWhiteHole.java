package com.slauson.dodger.powerups;

public class PowerupWhiteHole extends Powerup {

	private int x, y, size;
	
	public PowerupWhiteHole(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getSize() {
		return size;
	}
	
}
