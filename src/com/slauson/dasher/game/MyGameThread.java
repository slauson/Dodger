package com.slauson.dasher.game;

import com.slauson.dasher.other.GameActivity;

/**
 * Game thread
 * @author Josh Slauson
 *
 */
public class MyGameThread extends Thread {

	/** True when the game thread is running **/
	private volatile boolean running = false;
	
	/** Game view **/
	private GameActivity gameActivity;
	
	public MyGameThread(GameActivity gameActivity) {
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
		
		long lastUpdateTime = System.currentTimeMillis();
		long sleepTime = MyGame.maxSleepTime - (System.currentTimeMillis() - lastUpdateTime);
		
		while (running) {
			try {
				sleep(sleepTime);
				lastUpdateTime = System.currentTimeMillis();
				gameActivity.update();
				sleepTime = MyGame.maxSleepTime - (System.currentTimeMillis() - lastUpdateTime);
				
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