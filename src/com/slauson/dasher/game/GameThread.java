package com.slauson.dasher.game;

import com.slauson.dasher.other.GameThreadCallback;

/**
 * Game thread
 * @author Josh Slauson
 *
 */
public class GameThread extends Thread {

	/** True when the game thread is running **/
	private volatile boolean running = false;
	
	/** Game view **/
	private GameThreadCallback gameThreadCallback;
	
	public GameThread(GameThreadCallback gameThreadCallback) {
		super();
		this.gameThreadCallback = gameThreadCallback;
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
		
		long lastUpdateTime = System.currentTimeMillis();
		long sleepTime = Game.maxSleepTime - (System.currentTimeMillis() - lastUpdateTime);
		
		while (running) {
			try {
				sleep(sleepTime);
				lastUpdateTime = System.currentTimeMillis();
				gameThreadCallback.update();
				sleepTime = Game.maxSleepTime - (System.currentTimeMillis() - lastUpdateTime);
				
				if (sleepTime < 0) {
					System.out.println("Slowdown: " + sleepTime);
					sleepTime = 0;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}