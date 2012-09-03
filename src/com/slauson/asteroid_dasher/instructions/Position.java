package com.slauson.asteroid_dasher.instructions;

import com.slauson.asteroid_dasher.game.Game;

public class Position {

	public static enum PositionType {
		COORDINATE, RESET, RESET_PLAYER_X, DELAY_ONCE, SKIP, DELAY_RANDOM
	}
	
	private PositionType type;
	private float x;
	private float y;
	
	public Position(PositionType type, float x, float y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public void skip() {
		type = PositionType.SKIP;
	}
	
	public PositionType getType() {
		return type;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

	/**
	 * Returns inverse y for when gravity is reversed
	 * @return inverse y for when gravity is reversed
	 */
	public float getInverseY() {
		return Game.canvasHeight - y;
	}
}
