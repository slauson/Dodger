package com.slauson.dodger.powerups;

public class PowerupStop extends Powerup {
	
	private int range;
	
	public PowerupStop() {
		super();
		
		this.range = 0;
	}
	
	public PowerupStop(int range) {
		super();
		
		this.range = range;
	}

	public int getRange() {
		return range;
	}
}