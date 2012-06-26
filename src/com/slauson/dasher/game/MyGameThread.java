package com.slauson.dasher.game;

/**
 * Game thread
 * @author Josh Slauson
 *
 */
public class MyGameThread extends Thread {

	volatile boolean running = false;
	
	MyGameView parent;
	long sleepTime;
	
	MyGameThread(MyGameView sv, long st) {
		super();
		parent = sv;
		sleepTime = st;
	}
	
	public void setRunning(boolean r) {
		running = r;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (running) {
			try {
				sleep(sleepTime);
				parent.updateSurfaceView();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
