package com.slauson.dasher.game;

/**
 * Game thread
 * @author Josh Slauson
 *
 */
public class MyGameThread extends Thread {

	private volatile boolean running = false;
	private long maxSleepTime;
	
	private static final int FRAME_RATE = 30;
	
	MyGameView parent;
	
	MyGameThread(MyGameView sv) {
		super();
		parent = sv;
		
		maxSleepTime = 1000/FRAME_RATE;
	}
	
	public void setRunning(boolean r) {
		running = r;
	}
	
	@Override
	public void run() {
		
		long lastUpdateTime = System.currentTimeMillis();
		long sleepTime = maxSleepTime - (System.currentTimeMillis() - lastUpdateTime);
		
		// TODO Auto-generated method stub
		while (running) {
			try {
				sleep(sleepTime);
				lastUpdateTime = System.currentTimeMillis();
				parent.updateSurfaceView();
				sleepTime = maxSleepTime - (System.currentTimeMillis() - lastUpdateTime);
				
				if (sleepTime < 0) {
					//System.out.println("Slowdown: " + sleepTime);
					sleepTime = 0;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
