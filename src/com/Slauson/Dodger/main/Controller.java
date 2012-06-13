package com.slauson.dodger.main;

public class Controller {

	public float dirX, dirY;
	
	private float maxX, maxY;
	private float scaleIncrease, scaleDecrease;
	
	public final int BUTTON_NONE = 0;
	public final int BUTTON_LEFT = 1;
	public final int BUTTON_RIGHT = 2;
	
	public final int MODE_RELEASED = 0;
	public final int MODE_PRESSED = 1;
	
	public int button = BUTTON_NONE;
	public int mode = MODE_RELEASED;
	
	public Controller(float max, float scaleIncrease, float scaleDecrease) {
		this.maxX = max;
		this.maxY = max;
		this.scaleIncrease = scaleIncrease;
		this.scaleDecrease = scaleDecrease;
		
		init();
	}

	public Controller(float maxX, float maxY, float scaleIncrease, float scaleDecrease) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.scaleIncrease = scaleIncrease;
		this.scaleDecrease = scaleDecrease;
		
		init();
	}
	
	private void init() {
		dirX = 0;
		dirY = 0;
	}
	
	public void update() {
		
		if (mode == MODE_PRESSED) {
			if (button == BUTTON_LEFT) {
				dirX *= scaleIncrease;
				
				if (dirX >= 0) {
					dirX = -1;
				} else if (dirX < -maxX) {
					dirX = -maxX;
				}
			} else if (button == BUTTON_RIGHT) {
				dirX *= scaleIncrease;
				
				if (dirX <= 0) {
					dirX = 1;
				} else if (dirX > maxX) {
					dirX = maxX;
				}
			}
		} else if (mode == MODE_RELEASED) {
			
			dirX /= scaleDecrease;
			dirY /= scaleDecrease;
		
			if (Math.abs(dirX) <= 1) {
				dirX = 0;
			}
			if (Math.abs(dirY) <= 1) {
				dirY = 0;
			}
		}
	}

}
