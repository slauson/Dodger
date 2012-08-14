package com.slauson.dasher.instructions;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.slauson.dasher.game.MyGame;
import com.slauson.dasher.objects.Item;

/**
 * Class for automating movement of an object
 * @author Josh Slauson
 *
 */
public class Automator {

	/** List of positions to move the item to **/
	private List<Position> positions;
	/** List of durations of how long to take to move to each position **/
	private List<Integer> durations;

	/** Item to automate movement for **/
	private Item item;
	
	/** Current index into list of positions/duration **/
	private int index;
	
	/** Remaining time to get to next position **/
	private int remainingTime;
	/** Time of last update **/
	private long lastUpdateTime;
	
	public Automator(List<Position> positions, List<Integer> durations, Item item) {
		this.positions = positions;
		this.durations = durations;
		this.item = item;
		
		index = 0;
		remainingTime = durations.get(index);
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public void update() {
		long timeElapsed = System.currentTimeMillis() - lastUpdateTime;
		lastUpdateTime = System.currentTimeMillis();
		
		// we don't want to drop more than 1 frame
		if (timeElapsed > 2*MyGame.maxSleepTime) {
			timeElapsed = 2*MyGame.maxSleepTime;
		}
		
		remainingTime -= timeElapsed;
		
		if (remainingTime < 0) {
			if (nextPosition(-remainingTime)) {
				// TODO: dash, other special cases
			}
		}
	}
	
	/**
	 * Moves item to next position, setting its direction and speed
	 * @param time duration to decrement from next duration
	 * @return true if next position is a special, non-coordinate position
	 */
	private boolean nextPosition(int time) {

		Position positionCurrent = positions.get(index);
		
		// increment index
		index++;
		if (index >= positions.size()) {
			index = 0;
		}
		
		// recalculate remaining time
		remainingTime = durations.get(index) - time;
		
		// set item move
		Position positionNext = positions.get(index);
		
		if (positionNext.getType() == Position.POSITION_TYPE.COORDINATE) {
			float diffX = positionNext.getX() - positionCurrent.getX();
			float diffY = positionNext.getY() - positionCurrent.getY();
			
			float diffAbsTotal = Math.abs(diffX) + Math.abs(diffY);
			
			// set direction
			item.setDirX(diffX/diffAbsTotal);
			item.setDirY(diffY/diffAbsTotal);
			
			// set speed
			item.setSpeed(diffAbsTotal/remainingTime);
			
			return false;
		} else {
			return true;
		}
	}
}
