package com.Slauson.Dodger;

public class PowerupWhiteHole extends Powerup {

	int x, y, size;
	
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
