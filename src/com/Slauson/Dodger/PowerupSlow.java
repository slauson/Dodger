package com.Slauson.Dodger;

public class PowerupSlow extends Powerup {
	
	private int range;
	
	public PowerupSlow() {
		super();
		
		this.range = 0;
	}
	
	public PowerupSlow(int range) {
		super();
		
		this.range = range;
	}

	public int getRange() {
		return range;
	}
}