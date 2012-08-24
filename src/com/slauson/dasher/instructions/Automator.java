package com.slauson.dasher.instructions;

import java.util.ArrayList;

import com.slauson.dasher.game.Game;
import com.slauson.dasher.instructions.Position.POSITION_TYPE;
import com.slauson.dasher.menu.InstructionsMenu;
import com.slauson.dasher.objects.Asteroid;
import com.slauson.dasher.objects.Item;

/**
 * Class for automating movement of an object
 * @author Josh Slauson
 *
 */
public class Automator {
	
	public static enum AUTOMATOR_TYPE {
		ASTEROID, DROP
	}

	/** List of positions to move the item to **/
	private ArrayList<Position> positions;
	/** List of durations of how long to take to move to each position **/
	private ArrayList<Integer> durations;
	
	/** Item to automate movement for **/
	private Item item;
	
	/** Type of item to automate movement for **/
	private AUTOMATOR_TYPE type;
	
	/** Current index into list of positions/duration **/
	private int index;
	
	/** Remaining time to get to next position **/
	private int remainingTime;
	/** Time of last update **/
	private long lastUpdateTime;
	
	/** True if automator is enabled **/
	private boolean enabled;
	
	/** Type of drop **/
	private int dropType;
	
	public Automator(Item item, AUTOMATOR_TYPE type) {
		this.item = item;
		this.type = type;
		
		positions = new ArrayList<Position>();
		durations = new ArrayList<Integer>();
		index = -1;
		remainingTime = -1;
		lastUpdateTime = System.currentTimeMillis();
		enabled = true;
		
		if (type == AUTOMATOR_TYPE.DROP) {
			dropType = 0;
		} else {
			dropType = -1;
		}
	}
	
	/**
	 * Updates automator
	 * @return true if update required on activity side
	 */
	public boolean update() {
		
		if (!enabled) {
			return false;
		}
		
		boolean value = false;
		
		long timeElapsed = System.currentTimeMillis() - lastUpdateTime;
		lastUpdateTime = System.currentTimeMillis();
		
		// we don't want to drop more than 1 frame
		if (timeElapsed > 2*Game.maxSleepTime) {
			timeElapsed = 2*Game.maxSleepTime;
		}
		
		remainingTime -= timeElapsed;
		
		if (index >= 0 && remainingTime < 0) {
			
			Position position = positions.get(index);
			
			// handle special cases
			if (position.getType() == POSITION_TYPE.RESET) {
				
				if (type == AUTOMATOR_TYPE.ASTEROID) {
					((Asteroid)item).reset(InstructionsMenu.ASTEROID_RADIUS_FACTOR, 0, 0);
						
				
					// move to next position
					item.setX(position.getX());
					if (Game.direction == Game.DIRECTION_NORMAL) {
						item.setY(position.getY());
					} else {
						item.setY(position.getInverseY());
					}
				} else if (type == AUTOMATOR_TYPE.DROP) {
					dropType++;
					
					if (dropType >= Game.numAvailableDrops) {
						dropType = 0;
					}
					
					value = true;
				}
			} else if (position.getType() == POSITION_TYPE.RESET_PLAYER_X) {
				// only applicable to asteroids
				if (type == AUTOMATOR_TYPE.ASTEROID) {
					((Asteroid)item).reset();
				
					// we set the y coordinate here, and make the activity set the x position 
					if (Game.direction == Game.DIRECTION_NORMAL) {
						item.setY(position.getY());
					} else {
						item.setY(position.getInverseY());
					}
					
					value = true;
				}
			} else if (position.getType() == POSITION_TYPE.DELAY_ONCE) {
				position.skip();
			}
			
			nextPosition(-remainingTime);
			
			if (positions.get(index).getType() == POSITION_TYPE.SKIP) {
				// move to next position right away
				remainingTime = -1;
			}
		}
		
		return value;
	}
	
	/**
	 * Disables automator.
	 */
	public void disable() {
		enabled = false;
	}
	
	/**
	 * Adds position to automate list
	 * @param position position to add
	 * @param duration duration to add
	 * @return reference to same Automator for chaining calls
	 */
	public Automator addPosition(Position position, int duration) {
		positions.add(position);
		durations.add(duration);

		// setup index and remaining time
		if (index == -1) {
			index = 0;
			remainingTime = durations.get(index);
		}

		return this;
	}
	
	/**
	 * Returns item to automate
	 * @return item to automate
	 */
	public Item getItem() {
		return item;
	}
	
	/**
	 * Returns type of automator
	 * @return type of automator
	 */
	public AUTOMATOR_TYPE getType() {
		return type;
	}
	
	/**
	 * Returns drop type
	 * @return
	 */
	public int getDropType() {
		return dropType;
	}
	
	/**
	 * Moves item to next position, setting its direction and speed
	 * @param time duration to decrement from next duration
	 * @return next position
	 */
	private Position nextPosition(int time) {

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
		
		if (item == null) {
			return positionNext;
		}
		
		if (positionNext.getType() == Position.POSITION_TYPE.COORDINATE) {
			
			float diffX = positionNext.getX() - positionCurrent.getX();
			float diffY = positionNext.getY() - positionCurrent.getY();

			// coordinate of -1 means use last coordinate
			if (positionNext.getX() == -1) {
				diffX = 0;
			}
			if (positionNext.getY() == -1) {
				diffY = 0;
			}
			
			float diffAbsTotal = Math.abs(diffX) + Math.abs(diffY);
			
			// set direction
			item.setDirX(diffX/diffAbsTotal);
			item.setDirY(diffY/diffAbsTotal);
			
			// set speed
			item.setSpeed(diffAbsTotal/(remainingTime/1000.f));
			
		} else {
			item.setDirX(0);
			item.setDirY(0);
			item.setSpeed(0);
		}
		
		return positionNext;
	}

}
