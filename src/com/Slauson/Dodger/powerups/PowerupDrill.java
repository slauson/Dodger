package com.slauson.dodger.powerups;

public class PowerupDrill extends Powerup {
	
	private int range, width;
	
	public PowerupDrill() {
		super();
	}

	public PowerupDrill(int range, int width) {
		super();
		
		this.range = range;
		this.width = width;
	}

	public int getRange() {
		return range;
	}

	public int getWidth() {
		return width;
	}
}
