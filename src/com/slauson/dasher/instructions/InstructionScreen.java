package com.slauson.dasher.instructions;

import java.util.ArrayList;
import java.util.List;

/**
 * Individual instruction screen.
 * @author Josh Slauson
 *
 */
public class InstructionScreen {

	public static enum REQUIRED_EVENT_TYPE {
		NONE, AVOID_ASTEROIDS, DASH_ASTEROIDS, ACTIVATE_POWERUPS, AVOID_AND_DASH_POWERUPS
	}
	
	/** True if user interaction is required **/
	private boolean userInteractionRequired;
	/** True if previous button is enabled **/
	private boolean previousButtonEnabled;
	/** True if finish button is shown instead of next button **/
	private boolean finishButtonEnabled;
	
	/** List of automators **/
	private List<Automator> automators;
	/** Instruction description **/
	private List<Integer> descriptionIds;
	
	/** Required event type, if any **/
	private REQUIRED_EVENT_TYPE eventType;
	
	/** Player's ability to move **/
	private boolean playerCanMove;
	/** Player's ability to dash **/
	private boolean playerCanDash;
	/** Drops enabled **/
	private boolean dropsEnabled;
	/** Player starting x coordinate **/
	private float playerStartX;

	public InstructionScreen(boolean userInteractionRequired, boolean previousButtonEnabled, boolean finishButtonEnabled, REQUIRED_EVENT_TYPE eventType) {
		this.userInteractionRequired = userInteractionRequired;
		this.previousButtonEnabled = previousButtonEnabled;
		this.finishButtonEnabled = finishButtonEnabled;
		this.eventType = eventType;
		
		playerCanMove = true;
		playerCanDash = true;
		dropsEnabled = true;
		automators = new ArrayList<Automator>();
		descriptionIds = new ArrayList<Integer>();
	}
	
	/**
	 * Adds automator to list of items to automate.
	 * @param automator automator to add
	 */
	public void addAutomator(Automator automator) {
		automators.add(automator);
	}
	
	/**
	 * Adds description id to list of description ids
	 * @param descriptionId description id to add
	 */
	public void addDescriptionId(int descriptionId) {
		descriptionIds.add(descriptionId);
	}
	
	/**
	 * Returns descriptionId at given index
	 * @param index index
	 * @return descriptionId at given index
	 */
	public int getDescriptionId(int index) {
		if (index < 0 || index >= descriptionIds.size()) {
			return -1;
		}
		
		return descriptionIds.get(index);
	}
	
	/**
	 * Returns true if user interaction is required.
	 * @return true if user interaction is required
	 */
	public boolean getUserInteractionRequired() {
		return userInteractionRequired;
	}
	
	/**
	 * Returns true if previous button is enabled.
	 * @return true if previous button is enabled
	 */
	public boolean getPreviousButtonEnabled() {
		return previousButtonEnabled;
	}
	
	/**
	 * Returns true if finish button is enabled.
	 * @return true if finish button is enabled
	 */
	public boolean getFinishButtonEnabled() {
		return finishButtonEnabled;
	}
	
	/**
	 * Returns list of automators.
	 * @return list of automators
	 */
	public List<Automator> getAutomators() {
		return automators;
	}
	
	/**
	 * Returns required event type 
	 * @return required event type
	 */
	public REQUIRED_EVENT_TYPE getEventType() {
		return eventType;
	}

	/**
	 * Sets player status.
	 * @param playerCanMove true if player can move
	 * @param playerCanDash true if player can dash
	 */
	public void setPlayerStatus(boolean playerCanMove, boolean playerCanDash) {
		this.playerCanMove = playerCanMove;
		this.playerCanDash = playerCanDash;
	}
	
	/**
	 * Returns true if player can move
	 * @return true if player can move
	 */
	public boolean getPlayerCanMove() {
		return playerCanMove;
	}
	
	/**
	 * Returns true if player can dash
	 * @return true if player can dash
	 */
	public boolean getPlayerCanDash() {
		return playerCanDash;
	}

	/**
	 * Sets drop status
	 * @param dropsEnabled true if drops are enabled
	 */
	public void setDropStatus(boolean dropsEnabled) {
		this.dropsEnabled = dropsEnabled;
	}
	
	/**
	 * Returns true if drops are enabled
	 * @return true if drops are enabled
	 */
	public boolean getDropsEnabled() {
		return dropsEnabled;
	}

	/**
	 * Sets player starting x coordinate
	 * @param playerStartX starting x coordinate for player
	 */
	public void setPlayerStartX(float playerStartX) {
		this.playerStartX = playerStartX;
		
	}
	
	/**
	 * Returns starting x coordinate for player
	 * @return starting x coordinate for player
	 */
	public float getPlayerStartX() {
		return playerStartX;
	}
}
