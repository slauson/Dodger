package com.slauson.asteroid_dasher.instructions;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

/**
 * Individual instruction screen.
 * @author Josh Slauson
 *
 */
public class InstructionScreen {

	/** Type of event required for this screen **/
	public static enum RequiredEventType {
		NONE, AVOID_ASTEROIDS, DASH_ASTEROIDS, ACTIVATE_POWERUPS, SURVIVE, PURCHASE_UPGRADE
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
	/** Default instruction descriptions common to all **/
	private int descriptionDefaultId;
	/** Requirement description **/
	private int descriptionRequirementId;
	
	/** Requirement number of events **/
	private int requirementNum;
	/** Number of completed requirements **/
	private int completionNum;
	/** Flag for when completion percentage has been updated **/
	private boolean completionUpdate;
	
	/** Required event type, if any **/
	private RequiredEventType eventType;
	
	/** Player's visibility **/
	private boolean playerVisible;
	/** Player's ability to move **/
	private boolean playerCanMove;
	/** Player's ability to dash **/
	private boolean playerCanDash;
	/** Drops enabled **/
	private boolean dropsEnabled;
	
	/** Last reset time for player **/
	private long lastResetTime;
	/** Flag **/
	private boolean flag;
	
	/** Current index of description id **/
	private int descriptionIdIndex;
	
	/** Upgrades button visibility **/
	private int upgradesButtonVisibility;
	
	public InstructionScreen(int descriptionDefaultId, int descriptionRequirementId, int requirementNum, boolean userInteractionRequired, boolean previousButtonEnabled, boolean finishButtonEnabled, RequiredEventType eventType) {
		this.descriptionDefaultId = descriptionDefaultId;
		this.descriptionRequirementId = descriptionRequirementId;
		this.requirementNum = requirementNum;
		this.userInteractionRequired = userInteractionRequired;
		this.previousButtonEnabled = previousButtonEnabled;
		this.finishButtonEnabled = finishButtonEnabled;
		this.eventType = eventType;
		
		descriptionIdIndex = 0;
		
		upgradesButtonVisibility = View.GONE;
		
		completionNum = 0;
		completionUpdate = false;
		
		lastResetTime = System.currentTimeMillis();
		flag = false;
		
		playerVisible = true;
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
	 * @return descriptionId at given index
	 */
	public int getDescriptionId() {
		return descriptionIds.get(descriptionIdIndex);
	}
	
	/**
	 * Sets description id index
	 * @param descriptionIdIndex index value
	 */
	public void setDescriptionIdIndex(int descriptionIdIndex) {
		if (descriptionIdIndex >= 0 && descriptionIdIndex < descriptionIds.size()) {
			this.descriptionIdIndex = descriptionIdIndex;
		}
	}
	
	/**
	 * Returns description default id
	 * @return description default id
	 */
	public int getDescriptionDefaultId() {
		return descriptionDefaultId;
	}
	
	/**
	 * Returns descriptions requirement id
	 * @return description requirement id
	 */
	public int getDescriptionRequirementId() {
		return descriptionRequirementId;
	}
	
	/**
	 * Returns number for required events to complete
	 * @return number for required events to complete
	 */
	public int getRequirementNum() {
		return requirementNum;
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
	public RequiredEventType getEventType() {
		return eventType;
	}

	/**
	 * Sets player status.
	 * @param playerCanMove true if player can move
	 * @param playerCanDash true if player can dash
	 */
	public void setPlayerStatus(boolean playerVisible, boolean playerCanMove, boolean playerCanDash) {
		this.playerVisible = playerVisible;
		this.playerCanMove = playerCanMove;
		this.playerCanDash = playerCanDash;
	}
	
	/**
	 * Returns true if player is visible
	 * @return true if player is visible
	 */
	public boolean getPlayerVisible() {
		return playerVisible;
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
	 * Returns last reset time
	 * @return last reset time
	 */
	public long getLastResetTime() {
		return lastResetTime;
	}
	
	/**
	 * Resets last reset time
	 */
	public void resetLastResetTime() {
		lastResetTime = System.currentTimeMillis();
	}

	/**
	 * Returns flag
	 * @return flag
	 */
	public boolean getFlag() {
		return flag;
	}
	/**
	 * Toggles flag
	 * @param flag flag value
	 */
	public void toggleFlag(boolean flag) {
		this.flag = flag;
	}
	
	/**
	 * Returns requirement completion status
	 * @return requirement completion status
	 */
	public String getCompletionStatusString() {
		return " (" + completionNum + "/" + requirementNum + ").";
	}
	
	/**
	 * Returns completion num
	 * @return completion num
	 */
	public int getCompletionNum() {
		return completionNum;
	}
	
	/**
	 * Resets progress towards requirement
	 */
	public void resetCompletionNum() {
		
		if (completionNum != requirementNum) {
			completionNum = 0;
			completionUpdate = true;
		}
	}
	
	/**
	 * Increments progress towards requirement
	 */
	public void incrementCompletionNum() {
		completionNum++;
		
		if (completionNum > requirementNum) {
			completionNum = requirementNum;
		} else {
			completionUpdate = true;
		}
	}
	
	/**
	 * Returns true if requirement completion has been updated.
	 * Also resets the update flag.
	 * @return true if completion updated
	 */
	public boolean getCompletionUpdate() {
		
		if (completionUpdate) {
			completionUpdate = false;
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if requirement is completed
	 * @return true if requirement is completed
	 */
	public boolean getRequirementCompleted() {
		return completionNum == requirementNum;
	}

	/**
	 * Sets upgrades button visibility
	 * @param visibility visibility of upgrades button
	 */
	public void setUpgradesButtonVisibility(int upgradesButtonVisibility) {
		this.upgradesButtonVisibility = upgradesButtonVisibility;
	}
	
	/**
	 * Returns upgrades button visibility
	 * @return upgrades button visibility
	 */
	public int getUpgradesButtonVisibility() {
		return upgradesButtonVisibility;
	}

}
