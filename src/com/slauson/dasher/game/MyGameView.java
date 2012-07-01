package com.slauson.dasher.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.slauson.dasher.main.Configuration;
import com.slauson.dasher.objects.Asteroid;
import com.slauson.dasher.objects.Drop;
import com.slauson.dasher.objects.Player;
import com.slauson.dasher.powerups.ActivePowerup;
import com.slauson.dasher.powerups.PowerupBumper;
import com.slauson.dasher.powerups.PowerupDrill;
import com.slauson.dasher.powerups.PowerupMagnet;
import com.slauson.dasher.powerups.PowerupSlow;
import com.slauson.dasher.powerups.PowerupSmall;
import com.slauson.dasher.powerups.PowerupInvulnerable;
import com.slauson.dasher.powerups.PowerupWhiteHole;
import com.slauson.dasher.R;

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

/**
 * Main game view
 * 
 * Adapted from here: http://android-coding.blogspot.com/2012/01/create-surfaceview-game-step-by-step.html
 * 
 * @author Josh Slauson
 *
 */
public class MyGameView extends SurfaceView implements SurfaceHolder.Callback {

	/**
	 * Debugging stuff
	 */
	
	private int debugPowerupType = POWERUP_WHITE_HOLE;
	private String debugText = "";
	private int debugLevel = 0;

	
	/**
	 * Private stuff
	 */
	
	private SurfaceHolder surfaceHolder;
	
	// main thread
	private MyGameThread myGameThread;
	
	// Canvas stuff
	private Paint paint;
	
	private Random random;
	
	// Stuff on screen
	private Player player;
	private ArrayList<Asteroid> asteroids;
	private LinkedList<Drop> drops;

	// powerups
	private LinkedList<ActivePowerup> activePowerups;	
	private int bombCounter;
	
	// Initialization flags
	private boolean surfaceCreated = false;
	private boolean initialized = false;
	
	// for swipe-based dodging
	private float touchDownY;
	
	private Level level;

	
	/**
	 * Constants - private
	 */
	
	// powerup sprites
	private static final int R_POWERUP_DRILL = R.drawable.powerup_drill;
	private static final int R_POWERUP_MAGNET = R.drawable.powerup_magnet;
	private static final int R_POWERUP_SLOW = R.drawable.powerup_slow;
	private static final int R_POWERUP_WHITE_HOLE = R.drawable.powerup_white_hole;
	private static final int R_POWERUP_BUMPER = R.drawable.powerup_bumper;
	private static final int R_POWERUP_BOMB = R.drawable.powerup_bomb;
	private static final int R_POWERUP_SMALL = R.drawable.powerup_ship;
	private static final int R_POWERUP_INVULNERABLE = R.drawable.powerup_invulnerable;
	
	// asteroid
	//private static final int ASTEROID_COUNT = 25;
	//private static final int ASTEROID_SIZE = 32;
	
	// mode
	private static final int MODE_PAUSED = 0;
	private static final int MODE_RUNNING = 1;
	
	// drops
	private static final float DROP_CHANCE = 1f;
	
	// powerup durations
	private static final int SLOW_DURATION = 10000;
	private static final int INVULNERABLE_DURATION = 10000;
	private static final int SMALL_DURATION = 10000;
	private static final int MAGNET_DURATION = 10000;
	private static final int WHITE_HOLE_DURATION = 10000;
	private static final int BUMPER_DURATION = 10000;
	private static final int DRILL_DURATION = 10000;
	private static final int BOMB_COUNTER_MAX = 10;

	private static final int PLAYER_PAINT_STROKE_WIDTH = 2;
	private static final int PLAYER_PAINT_COLOR = Color.WHITE;
	
	private static final int ASTEROID_PAINT_STROKE_WIDTH = 1;
	private static final int ASTEROID_PAINT_COLOR = Color.WHITE;
	
	// touch
	private static final float DASH_TOUCH_FACTOR = 1.5f;
	private static final float DASH_SWIPE_MIN_DISTANCE = 50;

	// accelerometer
	private static final float ACCELEROMETER_DEADZONE = 0.05f;
	private static final float ACCELEROMETER_MAX = 0.3f;

	/**
	 * Constants - public
	 */
	
	// powerups
	public static final int NUM_POWERUPS = 8;
	public static final int POWERUP_NONE = -100;
	public static final int POWERUP_DRILL = 0;
	public static final int POWERUP_SLOW = 1;
	public static final int POWERUP_MAGNET = 2;
	public static final int POWERUP_WHITE_HOLE = 3;
	public static final int POWERUP_BUMPER = 4;
	public static final int POWERUP_BOMB = 5;
	public static final int POWERUP_SMALL = 6;
	public static final int POWERUP_INVULNERABLE = 7;
	
	// powerup stuff
	
	// direction
	public static final int DIRECTION_NORMAL = 0;
	public static final int DIRECTION_REVERSE = 1;
	
	// stationary powerups to draw
	public static final int R_MAGNET = R.drawable.magnet;
	public static final int R_WHITE_HOLE = R.drawable.white_hole;
	public static final int R_DRILL = R.drawable.drill_external_1;
	public static final int R_BUMPER = R.drawable.bumper4;
	public static final int R_BUMPER_ALT = R.drawable.bumper4_1;

	/**
	 * Shared stuff
	 */
	
	public static int canvasWidth, canvasHeight;
	
	public static PowerupSlow powerupSlow = new PowerupSlow();
	public static PowerupInvulnerable powerupInvulnerable = new PowerupInvulnerable();
	public static PowerupSmall powerupSmall = new PowerupSmall();
	
	// current state
	public static int gameMode = MODE_RUNNING;
	public static int direction = DIRECTION_NORMAL;
	public static float gravity = 1f;

	
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

	public void surfaceCreated(SurfaceHolder holder) {
		canvasWidth = getWidth();
		canvasHeight = getHeight();
		
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
		myGameThread = new MyGameThread(this);
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
		
		// draw active powerups
		synchronized (activePowerups) {
			for (ActivePowerup activePowerup : activePowerups) {
				activePowerup.draw(canvas, paint);
			}
		}
		
		// draw asteroids
		paint.setStrokeWidth(ASTEROID_PAINT_STROKE_WIDTH);
		paint.setColor(ASTEROID_PAINT_COLOR);
		synchronized (asteroids) {
		
			paint.setStrokeWidth(2);
			paint.setColor(Color.WHITE);
			paint.setStyle(Style.STROKE);

			for (Asteroid asteroid : asteroids) {
				asteroid.draw(canvas, paint);
			}
		}
		
		// draw player
		paint.setStrokeWidth(PLAYER_PAINT_STROKE_WIDTH);
		paint.setColor(PLAYER_PAINT_COLOR);
		player.draw(canvas, paint);
		
		// draw drops
		synchronized (drops) {
			for (Drop drop : drops) {
				drop.draw(canvas, paint);
			}
		}

		// overlay bomb animation 
		if (bombCounter > 0) {
			float factor = Math.abs(1f*BOMB_COUNTER_MAX/2 - bombCounter)/BOMB_COUNTER_MAX*2;
			
			paint.setAlpha((int)(255 - 255*factor));
			paint.setStyle(Style.FILL);
			canvas.drawRect(0, 0, canvasWidth, canvasHeight, paint);

			paint.setAlpha(255);
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
			
			level = new Level(debugLevel);
			
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			random = new Random();		
			
			asteroids = new ArrayList<Asteroid>();
			drops = new LinkedList<Drop>();

			// powerups
			activePowerups = new LinkedList<ActivePowerup>();	
			bombCounter = 0;
						
			player = new Player();
			
			int radius;
			float speed;
			
			for (int i = 0; i < level.getNumAsteroids(); i++) {
				
				radius = level.getAsteroidRadiusMin() + random.nextInt(level.getAsteroidRadiusOffset() + 1);
				speed = level.getAsteroidSpeedMin() + (level.getAsteroidSpeedOffset()*random.nextFloat());
				
				asteroids.add(new Asteroid(radius, speed, level.hasAsteroidHorizontalMovement()));
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
				if (powerupSlow.isActive()) {
					temp.update(0.5f);
				} else {
					temp.update();
				}
				
				// reset asteroid off screen (include non-visible, non-intact asteroids here)
				if (direction == DIRECTION_NORMAL && temp.getY() - temp.getHeight()/2 > canvasHeight) {
					resetAsteroid(temp);
				} else if (direction == DIRECTION_REVERSE && temp.getY() + temp.getHeight()/2 < 0) {
					resetAsteroid(temp);
				}
				
				// don't do anything for asteroids not on screen
				if (!temp.onScreen()) {
					continue;
				}
				
				// alter asteroid for each active powerup
				synchronized (activePowerups) {
					for (ActivePowerup activePowerup : activePowerups) {
						activePowerup.alterAsteroid(temp);
					}
				}
				
				// only check collision with player is asteroid is in normal status
				if (temp.getStatus() == Asteroid.STATUS_NORMAL) {
					
					// check collision with player
					if (player.getStatus() == Player.STATUS_NORMAL && !powerupInvulnerable.isActive() && player.checkAsteroidCollision(temp)) {
						
						if (player.inPosition()) {
							player.breakup();
							temp.breakup();
							level.reset();
							resetAsteroids();
						} else {
							temp.breakup();
							dropPowerup(temp.getX(), temp.getY());
						}
					}
				}
				
				// check collisions with other asteroids
				/*for(int j = 0; j < asteroids.size(); j++) {
					
					// don't check collision with self
					if (i == j) {
						continue;
					}
					
					// check if there's a collision 
					if (temp.checkCollision(asteroids.get(j))) {
						
						// breakup asteroids
						temp.breakup();
						asteroids.get(j).breakup();
						
						dropPowerup(temp.getX(), temp.getY());
					}
				}*/
			}
		}
	}
	
	public void updatePowerups() {
		
		synchronized (drops) {
		
			Drop temp;
			
			// update falling powerups
			for (int i = 0; i < drops.size(); i++) {
				
				temp = drops.get(i);
				
				if (powerupSlow.isActive()) {
					temp.update(0.5f);
				} else {
					temp.update();
				}
				
				// reset powerup off screen
				if (direction == DIRECTION_NORMAL && temp.getY() - temp.getHeight()/2 > canvasHeight) {
					drops.remove(temp);
					i--;
				} else if (direction == DIRECTION_REVERSE && temp.getY() + temp.getHeight()/2 < 0) {
					drops.remove(temp);
					i--;
				}
				
				// check collision with player
				if (player.getStatus() == Player.STATUS_NORMAL && temp.isVisible() && temp.checkBoxCollision(player)) {
					
					switch(temp.getType()) {
					case POWERUP_MAGNET:
						activePowerups.add(new PowerupMagnet(BitmapFactory.decodeResource(getResources(), R_MAGNET), temp.getX(), temp.getY(), MAGNET_DURATION, player.getDirection()));
						break;
					case POWERUP_WHITE_HOLE:
						activePowerups.add(new PowerupWhiteHole(BitmapFactory.decodeResource(getResources(), R_WHITE_HOLE), temp.getX(), temp.getY(), WHITE_HOLE_DURATION));
						break;
					case POWERUP_DRILL:
						activePowerups.add(new PowerupDrill(BitmapFactory.decodeResource(getResources(), R_DRILL), temp.getX(), temp.getY(), DRILL_DURATION, player.getDirection()));
						break;
					case POWERUP_BUMPER:
						activePowerups.add(new PowerupBumper(BitmapFactory.decodeResource(getResources(), R_BUMPER), BitmapFactory.decodeResource(getResources(), R_BUMPER_ALT), temp.getX(), temp.getY(), BUMPER_DURATION));
						break;
					// activate one and done powerup
					case POWERUP_INVULNERABLE:
						powerupInvulnerable.activate(INVULNERABLE_DURATION);
						break;
					case POWERUP_BOMB:
						bombCounter = BOMB_COUNTER_MAX;
						break;
					// activate player powerup
					case POWERUP_SMALL:
						powerupSmall.activate(SMALL_DURATION);
						break;
					case POWERUP_SLOW:
						powerupSlow.activate(SLOW_DURATION);
						break;
					}
					
					drops.remove(temp);
					i--;
				} else {
				
					// alter falling powerup for each active powerup
					synchronized (activePowerups) {
						for (ActivePowerup activePowerup : activePowerups) {
							// TODO: use something better here
							if (activePowerup instanceof PowerupBumper) {
								((PowerupBumper)activePowerup).alterSprite(temp);
							}
						}
					}
				}
			}
		}
		
		// update inactive powerups
		powerupInvulnerable.update();
		powerupSlow.update();
		powerupSmall.update();
		
		synchronized (activePowerups) {
		
			// update any active global powerups
			for (int i = 0; i < activePowerups.size(); i++) {
				
				if (powerupSlow.isActive()) {
					activePowerups.get(i).update(0.5f);
				} else {
					activePowerups.get(i).update(1f);
				}
				
				// check if any active powerups should be removed
				if (!activePowerups.get(i).isActive()) {
					activePowerups.remove(i);
					i--;
				} else {
				
					// alter active powerup for each active powerup
					synchronized (activePowerups) {
						
						for (ActivePowerup activePowerup : activePowerups) {
							// TODO: use something better here
							if (activePowerup instanceof PowerupBumper && activePowerups.get(i) instanceof PowerupDrill) {
								((PowerupBumper)activePowerup).alterDrill((PowerupDrill)activePowerups.get(i));
							}
						}
					}
				}
			}
		}
	}
	
	public void updatePlayer() {
		player.update();
		
		//System.out.println("updatePlayer(): " + direction + " - " + player.getDirection() + ", " + gravity + " - " + player.getGravity());
		
		// check player bounds
		if (player.getX() - player.getWidth()/2 < 0) {
			player.setX(player.getWidth()/2);
		}
		
		if (player.getX() + player.getWidth()/2 > canvasWidth) {
			player.setX((canvasWidth - player.getWidth()/2));
		}
		
		// check if player is in transition
		if (direction != player.getDirection() || Math.abs(gravity - player.getGravity()) > 0.01) {
			
			// update gravity
			gravity = player.getGravity();
			
			if (player.inPosition()) {
				direction = player.getDirection();
			}
		}
		
		// alter player for each active powerup
		if (player.getStatus() == Player.STATUS_NORMAL && !powerupInvulnerable.isActive()) {
			synchronized (activePowerups) {
				for (ActivePowerup activePowerup : activePowerups) {
					// TODO: use something better here
					if (activePowerup instanceof PowerupBumper) {
						((PowerupBumper)activePowerup).alterPlayer(player);
					}
				}
			}
		}
	}
	
	public void updateStates() {
		
		if (!initialized) {
			init();
			return;
		}
		
		if (bombCounter > 0) {
			bombCounter--;
			
			if (bombCounter == BOMB_COUNTER_MAX/2) {
				activateBomb();
			}
		}
		
		debugText = "" + player.getSpeed() + " - level " + level.getLevel();
		
		if (level.update()) {
			// add more asteroids if necessary
			int numAsteroidsToAdd = level.getNumAsteroids() - asteroids.size();
			int radius;
			float speed;
			
			for (int i = 0; i < numAsteroidsToAdd; i++) {
				
				radius = level.getAsteroidRadiusMin() + random.nextInt(level.getAsteroidRadiusOffset() + 1);
				speed = level.getAsteroidSpeedMin() + (level.getAsteroidSpeedOffset()*random.nextFloat());
				
				asteroids.add(new Asteroid(radius, speed, level.hasAsteroidHorizontalMovement()));
			}
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
		
		// only move player ship when its in normal or invulnerable status
		if (player.getStatus() != Player.STATUS_NORMAL && player.getStatus() != Player.STATUS_INVULNERABLE) {
			return false;
		}
		
		// get event position
		float x = event.getX();
		float y = event.getY();
		
		int action = event.getAction();
		
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			
			// check if pressed on player's position, then dash
			if (x < player.getX() + player.getWidth()*DASH_TOUCH_FACTOR/2 && x > player.getX() - player.getWidth()*DASH_TOUCH_FACTOR/2 &&
					y < player.getY() + player.getHeight()*DASH_TOUCH_FACTOR/2 && y > player.getY() - player.getHeight()*DASH_TOUCH_FACTOR/2)
			{
				player.dash();
				break;
			}
			touchDownY = y;
		case MotionEvent.ACTION_MOVE:
			
			// only move horizontally when player is in position
			if (player.inPosition()) {
				player.setGoX(x);
			}
			break;
		case MotionEvent.ACTION_UP:
			
			// TODO: use touch history here (need to make sure y changed quick enough)
			// dash based on a swipe motion event
			if (player.getStatus() == Player.STATUS_NORMAL) {
				if (direction == DIRECTION_NORMAL && touchDownY - y > DASH_SWIPE_MIN_DISTANCE) {
					player.dash();
				} else if (direction == DIRECTION_REVERSE && y - touchDownY > DASH_SWIPE_MIN_DISTANCE) {
					player.dash();
				}
			}
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

		// only move player ship when its in normal or invulnerable status
		if (player.getStatus() != Player.STATUS_NORMAL && player.getStatus() != Player.STATUS_INVULNERABLE) {
			return;
		}

		switch(keyCode) {
		// left
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case KeyEvent.KEYCODE_S:
			if (Configuration.controlType == Configuration.CONTROL_KEYBOARD) {
				player.moveLeft();
				System.out.println("PLAYER MOVE LEFT");
			}
			break;			
		// right
		case KeyEvent.KEYCODE_DPAD_RIGHT:
		case KeyEvent.KEYCODE_L:
			if (Configuration.controlType == Configuration.CONTROL_KEYBOARD) {
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

		// only move player ship when its in normal or invulnerable status
		if (player.getStatus() != Player.STATUS_NORMAL && player.getStatus() != Player.STATUS_INVULNERABLE) {
			return;
		}

		switch(keyCode) {
		// left/right
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case KeyEvent.KEYCODE_DPAD_RIGHT:
		case KeyEvent.KEYCODE_S:
		case KeyEvent.KEYCODE_L:
			if (Configuration.controlType == Configuration.CONTROL_KEYBOARD) {
				player.moveStop();
				System.out.println("PLAYER NO MOVE");
			}
			break;
		// dpad center
		case KeyEvent.KEYCODE_DPAD_CENTER:
		case KeyEvent.KEYCODE_SPACE:
			player.dash();
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
		
		// only move player ship when its in normal or invulnerable status
		if (player.getStatus() != Player.STATUS_NORMAL && player.getStatus() != Player.STATUS_INVULNERABLE) {
			return;
		}
		
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
    	//debugText = debugText + tx;
	}
	
	/**
	 * May drop a random powerup at the given position
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	private void dropPowerup(float x, float y) {
		// drop powerup					
		if (random.nextFloat() < DROP_CHANCE) {
		
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
			case POWERUP_MAGNET:
				r_powerup = R_POWERUP_MAGNET;
				break;
			case POWERUP_WHITE_HOLE:
				r_powerup = R_POWERUP_WHITE_HOLE;
				break;
			case POWERUP_BUMPER:
				r_powerup = R_POWERUP_BUMPER;
				break;
			case POWERUP_BOMB:
				r_powerup = R_POWERUP_BOMB;
				break;
			case POWERUP_SMALL:
				r_powerup = R_POWERUP_SMALL;
				break;
			case POWERUP_INVULNERABLE:
				r_powerup = R_POWERUP_INVULNERABLE;
				break;
			}
			
			Drop drop= new Drop(BitmapFactory.decodeResource(getResources(), r_powerup), x, y, powerup);
			
			drops.add(drop);		
		}
	}
	
	/**
	 * Activates bomb powerup, destroying everything on screen
	 */
	private void activateBomb() {
		// destroy all on-screen asteroids
		for (Asteroid asteroid : asteroids) {
			asteroid.fadeOut();
		}
		
		// destroy all falling powerups
		drops.clear();
		
		// destroy all active powerups
		activePowerups.clear();
	}
	
	private void resetAsteroid(Asteroid asteroid) {
		int radius = level.getAsteroidRadiusMin() + random.nextInt(level.getAsteroidRadiusOffset() + 1);
		float speed = level.getAsteroidSpeedMin() + (level.getAsteroidSpeedOffset()*random.nextFloat());
		
		asteroid.reset(radius, speed, level.hasAsteroidHorizontalMovement());
	}
	
	private void resetAsteroids() {
		int numAsteroids = level.getNumAsteroids();
		
		while (asteroids.size() > numAsteroids) {
			asteroids.remove(asteroids.size()-1);
		}
		
		for (Asteroid asteroid : asteroids) {
			resetAsteroid(asteroid);
		}
	}
}
