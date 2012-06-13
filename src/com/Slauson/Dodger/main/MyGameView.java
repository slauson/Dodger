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
import com.slauson.dodger.objects.StationaryPowerup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;


public class MyGameView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder surfaceHolder;
	
	// main thread
	private MyGameThread myGameThread = null;
	
	// Canvas stuff
	private int canvasWidth, canvasHeight;
	//private Bitmap myCanvasBitmap = null;
	//private Canvas myCanvas = null;
	private Matrix identityMatrix;
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private Random random;
	
	// Stuff on screen
	private Player player;
	private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	private LinkedList<SpritePowerup> fallingPowerups = new LinkedList<SpritePowerup>();
	
	private LinkedList<StationaryPowerup> activePowerups = new LinkedList<StationaryPowerup>();	
	
	// Initialization flags
	private boolean surfaceCreated = false;
	private boolean initialized = false;
	
	//private Controller controller = null;

	// current mode
	private int mode = 0;
	
	/**
	 * Debugging stuff
	 */
	
	private int debugConstantPowerup = POWERUP_NONE;
	private String debugText = "";	
	
	/**
	 * Constants
	 */
	// item
	private static final int NUM_ITEMS = 25;
	private static final int ITEM_SIZE = 32;
	
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
	private static final int R_POWERUP_DISCO = R.drawable.powerup_disco;
	private static final int R_POWERUP_DRILL = R.drawable.powerup_drill;
	private static final int R_POWERUP_MAGNET = R.drawable.powerup_magnet;
	private static final int R_POWERUP_SLOW = R.drawable.powerup_slow;
	private static final int R_POWERUP_SMALL = R.drawable.powerup_small;

	private static final int NUM_POWERUPS = 4;
	private static final float POWERUP_DROP_CHANCE = 0.5f;
	private static final int POWERUP_SPEED = 5;
	private static final float POWERUP_SECRET_CHANCE = 0.05f;
	private static final int POWERUP_SIZE = 16;	
	
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
		
		identityMatrix = new Matrix();
		
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
		
		// draw items
		Iterator<Asteroid> itemIterator = asteroids.iterator();
		
		paint.setStrokeWidth(2);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);

		while(itemIterator.hasNext()) {
			itemIterator.next().draw(canvas, paint);
		}
		
		// draw player
		player.draw(canvas, paint);
		
		// draw powerups
		synchronized (this) {
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
			
			for (int i = 0; i < NUM_ITEMS; i++) {
				asteroids.add(new Asteroid(canvasWidth, canvasHeight));
			}
			
			initialized = true;
		}
	}
	
	public void updateItems() {
		
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
			
			// reset item off screen
			if (temp.getY() - temp.getHeight()/2 > canvasHeight) {
				temp.init();
			}
			
			// check if asteroid is visible
			if (temp.isVisible() && temp.isIntact()) {
				
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
	
	public void updatePowerups() {
		
		SpritePowerup temp;
		
		// update falling powerups
		for (int i = 0; i < fallingPowerups.size(); i++) {
			
			temp = fallingPowerups.get(i);
			
			temp.update();
			
			// reset item off screen
			if (temp.getY() - temp.getHeight()/2 > canvasHeight) {
				fallingPowerups.remove(temp);
				i--;
			}
			
			// check collision with player
			if (temp.checkBoxCollision(player)) {
				
				// activate powerup
				player.activatePowerup(temp.getType());
				
				fallingPowerups.remove(temp);
				i--;
				
				temp.setSpeed(0);
			}
		}
		
		// update any active global powerups
		for (int i = 0; i < activePowerups.size(); i++) {
			
		}
	}
	
	public void updatePlayer() {
		player.update();
		
		debugText = "" + player.getSpeed();
		
		// check player bounds
		if (player.getX() - player.getWidth()/2 < 0) {
			player.setX((int)player.getWidth()/2);
		}
		
		if (player.getX() + player.getWidth()/2 > canvasWidth) {
			player.setX((int)(canvasWidth - player.getWidth()/2));
		}
	}
	
	public void updateStates() {
		
		if (!initialized) {
			init();
			return;
		}

		updateItems();
		
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
	
	void updateAccelerometer(float tx, float ty) {
		
		if (!initialized) {
			init();
			return;
		}
		
		//int move = (int)(50*tx);
		float normalizedX = 10*Math.abs(tx);
		
		int move = (int)Math.pow(Math.E, normalizedX);

		
		if (move < 2) {
			move = 0;
		}
		
		if (tx > 0) {
			move *= -1;
		}
		
		player.setSpeed(move);
    	setDebugText("" + move);
	}


}
