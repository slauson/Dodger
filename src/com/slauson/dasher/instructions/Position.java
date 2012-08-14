package com.slauson.dasher.instructions;

public class Position {

	public static enum POSITION_TYPE {
		COORDINATE, DASH
	}
	
	private POSITION_TYPE type;
	private float x;
	private float y;
	
	public Position(POSITION_TYPE type, float x, float y) {
		this.type = type;
		this.x = x;
		this.y = y;
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
}
