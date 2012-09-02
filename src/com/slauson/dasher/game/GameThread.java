package com.slauson.dasher.game;

import com.slauson.dasher.other.GameBaseActivity;

/**
 * Game thread
 * @author Josh Slauson
 *
 */
public class GameThread extends Thread {

	/** True when the game thread is running **/
	private volatile boolean running = false;
	
	/** Game view **/
	private GameBaseActivity gameActivity;
	
	public GameThread(GameBaseActivity gameActivity) {
		super();
		this.gameActivity = gameActivity;
	}
	
	/**
	 * Sets thread running state
	 * @param running true if thread should be running 
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@Override
	public void run() {
		
		long lastUpdateTime, sleepTime;
		
		do {
			try {
				lastUpdateTime = System.currentTimeMillis();
				gameActivity.update();
				sleepTime = Game.maxSleepTime - (System.currentTimeMillis() - lastUpdateTime);
				
				if (sleepTime < 0) {
					System.out.println("Slowdown: " + sleepTime);
					sleepTime = 0;
				}
				
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (running);
	}
}