package com.slauson.dodger.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.slauson.dodger.main.R;
import com.slauson.dodger.objects.Asteroid;
import com.slauson.dodger.objects.Player;
import com.slauson.dodger.objects.SpritePowerup;
import com.slauson.dodger.powerups.PowerupMagnet;
import com.slauson.dodger.powerups.PowerupStationary;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyGameView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder surfaceHolder;
	
	// main thread
	private MyGameThread myGameThread = null;
	
	// Canvas stuff
	private int canvasWidth, canvasHeight;
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private Random random;
	
	// Stuff on screen
	private Player player;
	private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	private LinkedList<SpritePowerup> fallingPowerups = new LinkedList<SpritePowerup>();
	
	private LinkedList<PowerupStationary> activePowerups = new LinkedList<PowerupStationary>();	
	
	// Initialization flags
	private boolean surfaceCreated = false;
	private boolean initialized = false;
	
	//private Controller controller = null;

	/**
	 * Debugging stuff
	 */
	
	private int debugConstantPowerup = POWERUP_SLOW;
	private int debugPowerupType = POWERUP_NONE;
	private String debugText = "";
	
	/**
	 * Constants
	 */
	
	// asteroid
	private static final int ASTEROID_COUNT = 25;
	private static final int ASTEROID_SIZE = 32;
	
	// mode
	private static final int MODE_PAUSED = 0;
	private static final int MODE_RUNNING = 1;
	
	// powerups (player)
	public static final int POWERUP_NONE = -100;
	public static final int POWERUP_DISCO = -1;
	public static final int POWERUP_DRILL = 0;
	public static final int POWERUP_SLOW = 1;
	public static final int POWERUP_SMALL = 2;
	
	// powerups (global)
	public static final int POWERUP_MAGNET = 3;
	public static final int POWERUP_WHITE_HOLE = 4;
	
	// powerup sprites
	public static final int R_POWERUP_DISCO = R.drawable.powerup_disco;
	public static final int R_POWERUP_DRILL = R.drawable.powerup_drill;
	public static final int R_POWERUP_MAGNET = R.drawable.powerup_magnet;
	public static final int R_POWERUP_SLOW = R.drawable.powerup_slow;
	public static final int R_POWERUP_SMALL = R.drawable.powerup_small;

	public static final int NUM_POWERUPS = 4;
	public static final float POWERUP_DROP_CHANCE = 0.5f;
	public static final int POWERUP_SPEED = 5;
	public static final float POWERUP_SECRET_CHANCE = 0.05f;
	public static final int POWERUP_SIZE = 32;
	
	public static final int CONTROL_TOUCH = 0;
	public static final int CONTROL_ACCELEROMETER = 1;
	public static final int CONTROL_BUTTONS = 2;
	
	// stationary powerups to draw
	public static final int R_MAGNET = R.drawable.magnet;
	//public static final int R_WHITE_HOLE = R.drawable.magnet;
	
	// current mode
	public static int gameMode = MODE_RUNNING;
	public static int controlMode = CONTROL_TOUCH;
		
	public MyGameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public MyGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	
	public void setDebugText(String str) {
		debugText = str;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		System.out.println("surfaceCreated()");
		// TODO Auto-generated method stub
		
		canvasWidth = getWidth();
		canvasHeight = getHeight();
		//myCanvasBitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
		//myCanvas = new Canvas();
		//myCanvas.setBitmap(myCanvasBitmap);
		
		//controller = new Controller(PLAYER_MOVEMENT_MAX_SPEED, PLAYER_MOVEMENT_SCALE_INCREASE, PLAYER_MOVEMENT_SCALE_DECREASE);
		
		surfaceCreated = true;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	public void MyGameSurfaceView_OnResume() {
		
		random = new Random();
		surfaceHolder = getHolder();
		getHolder().addCallback(this);
		
		// Create and start background Thread
		myGameThread = new MyGameThread(this, 50);
		myGameThread.setRunning(true);
		myGameThread.start();
	}
	
	public void MyGameSurfaceView_OnPause() {
		// Kill the background thread
		boolean retry = true;
		myGameThread.setRunning(false);
		
		while (retry) {
			try {
				myGameThread.join();
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		// TODO: come up with a better way of doing this
		if (!initialized) {
			init();
			return;
		}
		
		// clear screen
		canvas.drawColor(Color.BLACK);
		
		// draw background
		//canvas.drawBitmap(myCanvasBitmap, identityMatrix, null);
		
		// clear canvas with transparent background
		//canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		
		// draw active powerups
		synchronized (activePowerups) {
			Iterator<PowerupStationary> powerupIterator = activePowerups.iterator();
		
			while (powerupIterator.hasNext()) {
				powerupIterator.next().draw(canvas, paint);
			}
		}
		
		// draw asteroids
		synchronized (asteroids) {
		
			Iterator<Asteroid> asteroidIterator = asteroids.iterator();
			
			paint.setStrokeWidth(2);
			paint.setColor(Color.WHITE);
			paint.setStyle(Style.STROKE);
	
			while (asteroidIterator.hasNext()) {
				asteroidIterator.next().draw(canvas, paint);
			}
		}
		
		// draw player
		player.draw(canvas, paint);
		
		// draw powerups
		// TODO: not sure if this is needed
		synchronized (fallingPowerups) {
			Iterator<SpritePowerup> powerupSpriteIterator = fallingPowerups.iterator();
		
			while (powerupSpriteIterator.hasNext()) {
				powerupSpriteIterator.next().draw(canvas, paint);
			}
		}
		
		// draw debug text
		long duration = System.currentTimeMillis() - player.getStartTime();
		
		String durationText = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
		paint.setStrokeWidth(1);
		paint.setColor(Color.WHITE);
		canvas.drawText(durationText + "    " + debugText, 0, canvasHeight, paint);
	}
	
	public boolean updateSurfaceView() {
		// the function run in background thread, not ui thread
		
		if (!surfaceCreated) {
			return false;
		}
		
		Canvas canvas = null;
		
		try {
			canvas = surfaceHolder.lockCanvas();
			
			synchronized (surfaceHolder) {
				updateStates();
				onDraw(canvas);
			}
		} finally {
			if (canvas != null) {
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
		
		return true;
	}
	
	private void init() {
		
		// initialize stuff
		if (surfaceCreated && !initialized) {
			player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.ship), canvasWidth/2, canvasHeight - 100);
			player.setupBitmaps(BitmapFactory.decodeResource(getResources(), R.drawable.ship_drill));
			
			for (int i = 0; i < ASTEROID_COUNT; i++) {
				asteroids.add(new Asteroid(canvasWidth, canvasHeight));
			}
			
			initialized = true;
		}
	}
	
	public void updateAsteroids() {
		
		synchronized (asteroids) {
		
			Asteroid temp;
			
			// update asteroids
			for(int i = 0; i < asteroids.size(); i++) {
				temp = asteroids.get(i);
				
				// move asteroids
				if (player.isSlowActive()) {
					temp.update(0.5f);
				} else {
					temp.update();
				}
				
				// reset asteroid off screen
				if (temp.getY() - temp.getHeight()/2 > canvasHeight) {
					temp.init();
				}
				
				// check if asteroid is visible
				if (temp.isVisible() && temp.isIntact()) {
					
					// alter asteroid for each active magnet
					synchronized (activePowerups) {
						Iterator<PowerupStationary> powerupIterator = activePowerups.iterator();
						PowerupStationary activePowerup;
					
						while (powerupIterator.hasNext()) {
							activePowerup = powerupIterator.next();
						
							// check for magnet powerups
							if (activePowerup instanceof PowerupMagnet) {
								((PowerupMagnet)activePowerup).alterAsteroid(temp);
							}
						}
					}
					
					// check collision with player drill
					if (player.isDrillActive() && player.checkDrillCollision(temp)) {
						
						// TODO: break up asteroid into two halves
						temp.breakup();
					}
					
					// check collision with player
					if (player.checkBoxCollision(temp)) {
						player.reset();
						temp.setVisible(false);
					}
				}
				
				// check collisions with other asteroids
				for(int j = 0; j < asteroids.size(); j++) {
					
					// don't check collision with self
					if (i == j) {
						continue;
					}
					
					// check if there's a collision 
					if (temp.checkCollision(asteroids.get(j))) {
						
						// breakup asteroids
						temp.breakup();
						asteroids.get(j).breakup();
						
						// drop powerup					
						if (random.nextFloat() < POWERUP_DROP_CHANCE) {
						
							// create powerup
							int powerup = random.nextInt(NUM_POWERUPS);
							
							// debug mode
							if (debugPowerupType != POWERUP_NONE) {
								powerup = debugPowerupType;
							}
							
							int r_powerup = 0;
							
							switch(powerup) {
							case POWERUP_DRILL:
								r_powerup = R_POWERUP_DRILL;
								break;
							case POWERUP_SLOW:
								r_powerup = R_POWERUP_SLOW;
								break;
							case POWERUP_SMALL:
								r_powerup = R_POWERUP_SMALL;
								break;
							case POWERUP_MAGNET:
								r_powerup = R_POWERUP_MAGNET;
								break;
							}
							
							// secret powerup
							if (random.nextFloat() < POWERUP_SECRET_CHANCE) {
								r_powerup = R_POWERUP_DISCO;
							}
							
							SpritePowerup powerupSprite = new SpritePowerup(BitmapFactory.decodeResource(getResources(), r_powerup), temp.getX(), temp.getY(), powerup);
							powerupSprite.setSpeed(POWERUP_SPEED);
							
							fallingPowerups.add(powerupSprite);		
						}
					}
				}
			}
			
			// constant powerup
			if (debugConstantPowerup != POWERUP_NONE) {
				player.activatePowerup(debugConstantPowerup);
			}
		}
	}
	
	public void updatePowerups() {
		
		synchronized (fallingPowerups) {
		
			SpritePowerup temp;
			
			// update falling powerups
			for (int i = 0; i < fallingPowerups.size(); i++) {
				
				temp = fallingPowerups.get(i);
				
				temp.update();
				
				// reset powerup off screen
				if (temp.getY() - temp.getHeight()/2 > canvasHeight) {
					fallingPowerups.remove(temp);
					i--;
				}
				
				// check collision with player
				if (temp.checkBoxCollision(player)) {
					
					switch(temp.getType()) {
					case POWERUP_MAGNET:
						activePowerups.add(new PowerupMagnet(BitmapFactory.decodeResource(getResources(), R_MAGNET), temp.getX(), temp.getY()));
						break;
					//case POWERUP_WHITE_HOLE:
					//	activePowerups.add(new StationaryPowerup(BitmapFactory.decodeResource(getResources(), R_WHITE_HOLE), temp.getX(), temp.getY()));
					//	break;
					// otherwise activate powerup for player
					default:
						player.activatePowerup(temp.getType());
						break;
					}
					
					fallingPowerups.remove(temp);
					i--;
					
					temp.setSpeed(0);
				}
			}
		}
		
		synchronized (activePowerups) {
		
			// update any active global powerups
			for (int i = 0; i < activePowerups.size(); i++) {
				
				// check if any active powerups should be removed
				if (!activePowerups.get(i).isActive()) {
					activePowerups.remove(i);
					i--;
				}
			}
		}
	}
	
	public void updatePlayer() {
		player.update();
		
		debugText = "" + player.getSpeed();
		
		// check player bounds
		if (player.getX() - player.getWidth()/2 < 0) {
			player.setX(player.getWidth()/2);
		}
		
		if (player.getX() + player.getWidth()/2 > canvasWidth) {
			player.setX((canvasWidth - player.getWidth()/2));
		}
	}
	
	public void updateStates() {
		
		if (!initialized) {
			init();
			return;
		}

		updateAsteroids();
		
		updatePowerups();
		
		updatePlayer();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if (!initialized) {
			init();
			return false;
		}
		
		// get event position
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		int action = event.getAction();
		
		switch(action) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			player.setGoX(x);
			break;
		default:
			break;
		}
		
		return true;
	}

	/**
	 * Handle key down events, this is called from game activity
	 * @param keyCode
	 * @param event
	 */
	void keyDown(int keyCode, KeyEvent event) {
		
		if (!initialized) {
			init();
			return;
		}


		switch(keyCode) {
		// left
		case KeyEvent.KEYCODE_DPAD_LEFT:
			if (controlMode == CONTROL_BUTTONS) {
				player.moveLeft();
				System.out.println("PLAYER MOVE LEFT");
			}
			break;			
		// right
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			if (controlMode == CONTROL_BUTTONS) {
				player.moveRight();
				System.out.println("PLAYER MOVE RIGHT");
			}
			break;
		}
	}
	
	/**
	 * Handle key down events, this is called from game activity
	 * @param keyCode
	 * @param event
	 */
	void keyUp(int keyCode, KeyEvent event) {
		
		if (!initialized) {
			init();
			return;
		}


		switch(keyCode) {
		// left
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			if (controlMode == CONTROL_BUTTONS) {
				player.moveStop();
				System.out.println("PLAYER NO MOVE");
			}
			break;			
		// pause game when menu/back/search is pressed
		case KeyEvent.KEYCODE_MENU:
		case KeyEvent.KEYCODE_BACK:
		case KeyEvent.KEYCODE_SEARCH:
			if (gameMode == MODE_PAUSED) {
				gameMode = MODE_RUNNING;
			} else {
				gameMode = MODE_PAUSED;
			}
			break;
		}
	}
	
	/**
	 * Handle accelerometer, this is called from game activity
	 * @param tx
	 * @param ty
	 */
	void updateAccelerometer(float tx, float ty) {
		
		if (!initialized) {
			init();
			return;
		}

		float ACCELEROMETER_DEADZONE = 0.05f;
		float ACCELEROMETER_MAX = 0.3f;
		
		float moveX = 0f;
		
		if (tx > 0) {
			player.setDirX(-1);
		} else {
			tx = Math.abs(tx);
			player.setDirX(1);
		}
		
		if (tx < ACCELEROMETER_DEADZONE) {
			player.setDirX(0);
			return;
		}

		if (tx > ACCELEROMETER_MAX) {
			tx = ACCELEROMETER_MAX;
		}
		
		moveX = ((tx - ACCELEROMETER_DEADZONE)/ACCELEROMETER_MAX)*Player.MAX_SPEED;
		player.setSpeed(moveX);
		//player.setSpeed(move);
    	setDebugText("" + tx);
	}


}
