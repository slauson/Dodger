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
	private String description;
	
	/** Required event type, if any **/
	private REQUIRED_EVENT_TYPE eventType;
	
	public InstructionScreen(String description) {
		this(description, false, true, false, REQUIRED_EVENT_TYPE.NONE);
	}
	
	public InstructionScreen(String description, boolean userInteractionRequired, boolean previousButtonEnabled, boolean finishButtonEnabled, REQUIRED_EVENT_TYPE eventType) {
		this.description = description;
		this.userInteractionRequired = userInteractionRequired;
		this.previousButtonEnabled = previousButtonEnabled;
		this.finishButtonEnabled = finishButtonEnabled;
		this.eventType = eventType;
		
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
	 * Returns instruction description.
	 * @return instruction description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns required event type 
	 * @return required event type
	 */
	public REQUIRED_EVENT_TYPE getEventType() {
		return eventType;
	}
}
