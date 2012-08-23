package com.slauson.dasher.instructions;

import com.slauson.dasher.game.Game;

public class Position {

	public static enum POSITION_TYPE {
		COORDINATE, RESET, DELAY_ONCE, SKIP
	}
	
	private POSITION_TYPE type;
	private float x;
	private float y;
	
	public Position(POSITION_TYPE type, float x, float y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public void skip() {
		type = POSITION_TYPE.SKIP;
	}
	
	public POSITION_TYPE getType() {
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
