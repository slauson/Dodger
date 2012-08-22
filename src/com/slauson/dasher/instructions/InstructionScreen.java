package com.slauson.dasher.instructions;

import java.util.ArrayList;
import java.util.List;

import com.slauson.dasher.objects.Player;

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
	private int descriptionId;
	
	/** Required event type, if any **/
	private REQUIRED_EVENT_TYPE eventType;
	
	/** Player's ability to move **/
	private boolean playerCanMove;
	
	/** Player's ability to dash **/
	private boolean playerCanDash;

	/*
	 * To add:
	 * player starting position 
	 */
	
	public InstructionScreen(int descriptionId) {
		this(descriptionId, false, true, false, REQUIRED_EVENT_TYPE.NONE);
	}
	
	public InstructionScreen(int descriptionId, boolean userInteractionRequired, boolean previousButtonEnabled, boolean finishButtonEnabled, REQUIRED_EVENT_TYPE eventType) {
		this.descriptionId = descriptionId;
		this.userInteractionRequired = userInteractionRequired;
		this.previousButtonEnabled = previousButtonEnabled;
		this.finishButtonEnabled = finishButtonEnabled;
		this.eventType = eventType;
		
		playerCanMove = true;
		playerCanDash = true;
		automators = new ArrayList<Automator>();
	}
	
	/**
	 * Updates all automators.
	 */
	public void update() {
		for (Automator automator : automators) {
			automator.update();
		}
	}
	
	/**
	 * Adds automator to list of items to automate.
	 * @param automator automator to add
	 */
	public void addAutomator(Automator automator) {
		automators.add(automator);
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
	 * Returns instruction description id.
	 * @return instruction description id
	 */
	public int getDescriptionId() {
		return descriptionId;
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
}
