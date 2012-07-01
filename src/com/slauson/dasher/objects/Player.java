package com.slauson.dasher.objects;

import java.util.ArrayList;

import com.slauson.dasher.game.MyGameView;
import com.slauson.dasher.main.Configuration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

/**
 * Player ship
 * @author Josh Slauson
 *
 */
public class Player extends DrawObject {
	
	// player state
	private long startTime;
	
	private float goX;
	
	private float speedX, speedY;
	
	private int move;
	private boolean inPosition;
	private int direction;
	
	private RectF dashPercentRect;
	private RectF dashPercentRectSmall;
	private int dashTimeout;
	
	private int invulnerabilityCounter;

	/**
	 * Public constants
	 */
	
	public static final int MAX_SPEED = 250;
	
	
	/**
	 * Private constants
	 */
	
	private static final int MOVE_NONE = 0;
	private static final int MOVE_LEFT = 1;
	private static final int MOVE_RIGHT = 2;
	
	private static final float BUTTON_MOVE_FACTOR = 4f;
	private static final float BUTTON_MIN_SPEED = 2f;
	
	private static final float Y_BOTTOM = MyGameView.canvasHeight - 100;
	private static final float Y_TOP = 32;
	
	private static final float ROTATION_DEGREES = 900f;
	
	private static final int PLAYER_WIDTH = 32;
	private static final int PLAYER_HEIGHT = 32;
	private static final int REAR_OFFSET = -6;
	
	private static final int INVULNERABLE_DURATION = 5000;
	
	private static final int DASH_TIMEOUT_DURATION = 100;
	
	public Player() {
		super(MyGameView.canvasWidth/2, Y_BOTTOM, PLAYER_WIDTH, PLAYER_HEIGHT);

		goX = x;
		
		speedX = 0;
		speedY = 0;
		
		move = MOVE_NONE;
		inPosition = true;
		direction = MyGameView.DIRECTION_NORMAL;
		
		points = new float[] {
				-PLAYER_WIDTH/2, PLAYER_HEIGHT/2,
				0, -PLAYER_HEIGHT/2,
				0, -PLAYER_HEIGHT/2,
				PLAYER_WIDTH/2, PLAYER_HEIGHT/2,
				PLAYER_WIDTH/2, PLAYER_HEIGHT/2,
				0, PLAYER_HEIGHT/2+REAR_OFFSET,
				0, PLAYER_HEIGHT/2+REAR_OFFSET,
				-PLAYER_WIDTH/2, PLAYER_HEIGHT/2
		};
		
		altPoints = new float[] {
				-PLAYER_WIDTH/4, PLAYER_HEIGHT/4,
				0, -PLAYER_HEIGHT/4,
				0, -PLAYER_HEIGHT/4,
				PLAYER_WIDTH/4, PLAYER_HEIGHT/4,
				PLAYER_WIDTH/4, PLAYER_HEIGHT/4,
				0, PLAYER_HEIGHT/4+REAR_OFFSET/2,
				0, PLAYER_HEIGHT/4+REAR_OFFSET/2,
				-PLAYER_WIDTH/4, PLAYER_HEIGHT/4
		};
		
		dashTimeout = 0;
		dashPercentRect = new RectF(-4, -2, 4, 6);
		dashPercentRectSmall = new RectF(-2, 0, 2, 4);
		startTime = System.currentTimeMillis();
		
		invulnerabilityCounter = 0;
	}
	
	/**
	 * Resets timer
	 */
	public void reset() {
		this.startTime = System.currentTimeMillis();
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {

		if (status != STATUS_INVISIBLE) {
			
			canvas.save();
			
			canvas.translate(x, y);
			
			// check if we need to rotate
			if (speedY > 1 || direction == MyGameView.DIRECTION_REVERSE) {
				
				float degrees = ROTATION_DEGREES * (Y_BOTTOM - y) / (Y_BOTTOM - Y_TOP);			
				canvas.rotate(degrees);
			}

			// normal or invulnerable blink
			if ((status == STATUS_NORMAL && !MyGameView.powerupInvulnerable.isActive()) || (status == STATUS_INVULNERABLE && invulnerabilityCounter % 3 == 0) || (MyGameView.powerupInvulnerable.isActive() && MyGameView.powerupInvulnerable.getCounter() % 3 == 0)) {
				
				// if small powerup is active, draw resized bitmap
				if (MyGameView.powerupSmall.isActive()) {
					canvas.drawLines(altPoints, paint);
				}
				// draw normal bitmap
				else {
					canvas.drawLines(points, paint);
				}
				
				// draw dash timeout percentage
				
				paint.setStyle(Style.FILL_AND_STROKE);
				if (MyGameView.powerupSmall.isActive()) {
					canvas.drawArc(dashPercentRectSmall, -90, 360 - 360*(1f*dashTimeout/DASH_TIMEOUT_DURATION), true, paint);
				} else {
					canvas.drawArc(dashPercentRect, -90, 360 - 360*(1f*dashTimeout/DASH_TIMEOUT_DURATION), true, paint);
				}
			}
			// breaking up
			else if (status == STATUS_BREAKING_UP) {
				int savedAlpha = paint.getAlpha();
				paint.setAlpha((int)(255 * (1.0*timeCounter/BREAKING_UP_DURATION)));
				
				for (LineSegment lineSegment : lineSegments) {
					lineSegment.draw(canvas, paint);
				}
				
				paint.setAlpha(savedAlpha);	
			}
			
			canvas.restore();
		}
	}

	@Override
	public void update() {
		
		long timeElapsed = System.currentTimeMillis() - lastUpdateTime;
		lastUpdateTime = System.currentTimeMillis();
		
		float timeModifier = 1.f*timeElapsed/1000;

		if (timeCounter > 0) {
			timeCounter -= timeElapsed;
		}
		
		if (status == STATUS_NORMAL || status == STATUS_INVULNERABLE) {
		
			// touch based controls
			if (Configuration.controlType == Configuration.CONTROL_TOUCH) {
				// damn floating point arithmetic
				if (Math.abs(goX - x) > 1) {
					speedX = MAX_SPEED;
	
					// need to move right
					if (goX > x) {
						dirX = 1;
						
//						if (goX - x < MAX_SPEED) {
//							speedX = goX - x;
//						}
					}
					// need to move left
					else {
						dirX = -1;
						
//						if (x - goX < MAX_SPEED) {
//							speedX = x - goX;
//						}
					}
				} else {
					speedX = 0;
				}
			}
			// key based controls
			else if (Configuration.controlType == Configuration.CONTROL_KEYBOARD) {
				if (move != MOVE_NONE) {
					if (speedX < MAX_SPEED) {
						speedX += BUTTON_MOVE_FACTOR;
						
						if (speedX > MAX_SPEED) {
							speedX = MAX_SPEED;
						}
					}
				}
			}
			
			x = x + (dirX*speedX*timeModifier);
			
			// autocorrect position if we overshoot
			if ((dirX > 0 && x > goX) || (dirX < 0 && x < goX)) {
				x = goX;
			}
			
			speedY = 0;
			dirY = 0;
			
			// move from bottom to top
			if (!inPosition) {
				if (direction == MyGameView.DIRECTION_REVERSE && Math.abs(y - Y_TOP) > 1) {
					dirY = -1;
					
					speedY = MAX_SPEED;
					
//					if (y - Y_TOP <= MAX_SPEED) {
//						speedY = y - Y_TOP;
//						inPosition = true;
//					}
				}
				// move from top to bottom
				else if (direction == MyGameView.DIRECTION_NORMAL && Math.abs(y - Y_BOTTOM) > 1) {
					dirY = 1;
					
					speedY = MAX_SPEED;
					
//					if (Y_BOTTOM - y <= MAX_SPEED) {
//						speedY = Y_BOTTOM - y;
//						inPosition = true;
//					}
				}
				
				y = y + (dirY*speedY*timeModifier);
				
				// autocorrect position if we overshoot
				if (dirY > 0 && y > Y_BOTTOM) {
					y = Y_BOTTOM;
					inPosition = true;
				} else if (dirY < 0 && y < Y_TOP) {
					y = Y_TOP;
					inPosition = true;
				}
			}
			
			// update dash timeout
			if (dashTimeout > 0) {
				dashTimeout--;
			}

			// update invulnerable timer
			if (status == STATUS_INVULNERABLE) {
				invulnerabilityCounter++;

				// make ship invulnerable for short period
				if (timeCounter <= 0) {
					status = STATUS_NORMAL;
				}
			}

		}
		else if (status == STATUS_BREAKING_UP) {
			
			for (LineSegment lineSegment : lineSegments) {
				lineSegment.update(timeModifier);
			}
			
			// make ship invulnerable for short period
			if (timeCounter <= 0) {
				//System.out.println("INVULNERABLE");
				status = STATUS_INVULNERABLE;
				timeCounter = INVULNERABLE_DURATION;
			}
		} 
	}
	
	/**
	 * Breaks up ship into line segments
	 */
	public void breakup() {
		lineSegments = new ArrayList<LineSegment>();
		
		float modifier = 1f;
		
		if (MyGameView.powerupSmall.isActive()) {
			modifier = 0.5f;
		}
		
		for (int i = 0; i < points.length; i+=4) {
			
			LineSegment temp = new LineSegment(modifier*points[i], modifier*points[i+1], modifier*points[i+2], modifier*points[i+3]);
			
			// need to get perpendicular (these are opposite on purpose)
			float yDiff = Math.abs(points[i] - points[i+2]);
			float xDiff = Math.abs(points[i+1] - points[i+3]);
			
			yDiff += Math.abs(dirY)*speed;
			xDiff += Math.abs(dirX)*speed;
			
			lineSegments.add(temp);
			
			temp.move = BREAKING_UP_MOVE;
			
			// x direction is sometimes positive
			if (points[i] < 0 || points[i+2] < 0) {
				temp.dirX = (-xDiff/(xDiff + yDiff));
			} else {
				temp.dirX = (xDiff/(xDiff + yDiff));
			}
			
			// y direction is always positive
			temp.dirY = (yDiff/(xDiff + yDiff)) + 1;
			
			// reverse direction
			if (direction == MyGameView.DIRECTION_REVERSE) {
				temp.dirY *= -1;
			}
		}
		
		status = STATUS_BREAKING_UP;
		timeCounter = BREAKING_UP_DURATION;
		
		reset();
	}

	public long getStartTime() {
		return startTime;
	}

	public void setGoX(float goX) {
		this.goX = goX;
	}
	
	@Override
	public boolean checkBoxCollision(Item other) {
		if (MyGameView.powerupSmall.isActive()) {
			return Math.abs(x - other.x) <= width/4 + other.width/2 && Math.abs(y - other.y) <= width/4 + other.height/2;
		} else {
			return Math.abs(x - other.x) <= width/2 + other.height/2 && Math.abs(y - other.y) <= height/2 + other.height/2;
		}
	}
	
	/**
	 * Checks if given point is contained in a bounding box for the player
	 * defined by the given width and height factors and y offset
	 * 
	 * @param widthFactor factor to base the bounding box's width compared to the ship's width/2 (0-1)
	 * @param heightFactor factor to base the bounding box's height compared to the ship's height/2 (0-1)
	 * @param yOffset offset for lower y of bounding box
	 * @param checkX x coordinate of point to check
	 * @param checkY y coordinate of point to check
	 * @return true if point intersects with bounding box
	 */
	public boolean checkAsteroidCollisionHelper(float widthFactor, float heightFactor, float yOffset, float checkX, float checkY) {
		
		// TODO: handle reverse direction
		
		// small ship
		if (MyGameView.powerupSmall.isActive()) {
			yOffset /= 2;
			return checkX >= x - width*widthFactor/4 && checkX < x + width*widthFactor/4 &&
					checkY >= y + yOffset && checkY <= y + yOffset + height*heightFactor/4;
		}
		// normal ship
		else {
			return checkX >= x - width*widthFactor/2 && checkX < x + width*widthFactor/2 &&
					checkY >= y + yOffset && checkY <= y + yOffset + height*heightFactor/2;
		}
	}
	
	/**
	 * 
	 * @param asteroid
	 * @return
	 */
	public boolean checkAsteroidCollision(Asteroid asteroid) {
		
		// first single box-based collision
		if (checkBoxCollision(asteroid)) {
			
			// if player is dashing, just return true (don't want to mess around with rotation stuff)
			if (!inPosition) {
				return true;
			}
			
			// determine angle between player and asteroid
			float xDiff = x - asteroid.x;
			float yDiff = y - asteroid.y;
			
			//System.out.println("Player: " + x + ", " + y);
			//System.out.println("Asteroid: " + asteroid.x + ", " + asteroid.y);
			
			//System.out.println("Diff: " + xDiff + ", " + yDiff);
			

			// angle with respect to the asteroid
			double angle = Math.atan(yDiff/xDiff);
			
			// on left side
			if (xDiff < 0) {
				angle += Math.PI;
			}
			
			if (angle < 0) {
				angle += 2*Math.PI;
			}
			
			//System.out.println("Asteroid Angle: " + angle);

			// get two closest asteroid points to angle
			int asteroidPointsIndex = asteroid.getClosestPointsIndex(angle);
			
			float x1 = asteroid.x + asteroid.points[asteroidPointsIndex];
			float y1 = asteroid.y + asteroid.points[asteroidPointsIndex+1];
			
			float x2 = asteroid.x;
			float y2 = asteroid.y;
			
			if (asteroidPointsIndex + 2 >= asteroid.points.length) {
				x2 += asteroid.points[0];
				y2 += asteroid.points[1];
			} else {
				x2 += asteroid.points[asteroidPointsIndex+2];
				y2 += asteroid.points[asteroidPointsIndex+3];
			}
			
			// variables for helper method
			float widthFactor = 0.25f;
			float heightFactor = 0.25f;
			// checkAsteroidCollisionHelper() handles small ship
			float yOffset = -16;
			
			// check top box
			if (checkAsteroidCollisionHelper(widthFactor, heightFactor, yOffset, x1, y1) ||
					checkAsteroidCollisionHelper(widthFactor, heightFactor, yOffset, x2, y2))
			{
				return true;
			}
			
			widthFactor += 0.25f;
			yOffset += 8;
			
			// check middle top box
			if (checkAsteroidCollisionHelper(widthFactor, heightFactor, yOffset, x1, y1) ||
					checkAsteroidCollisionHelper(widthFactor, heightFactor, yOffset, x2, y2))
			{
				return true;
			}
			
			widthFactor += 0.25f;
			yOffset += 8;
			
			// check middle bottom box
			if (checkAsteroidCollisionHelper(widthFactor, heightFactor, yOffset, x1, y1) ||
					checkAsteroidCollisionHelper(widthFactor, heightFactor, yOffset, x2, y2))
			{
				return true;
			}
			
			widthFactor += 0.25f;
			yOffset += 8;
			
			// check bottom box
			if (checkAsteroidCollisionHelper(widthFactor, heightFactor, yOffset, x1, y1) ||
					checkAsteroidCollisionHelper(widthFactor, heightFactor, yOffset, x2, y2))
			{
				return true;
			}
			
			return false;
			
		}
		
		return false;
			
		// this is too complicated and not worth my time (rotation, etc...)
/*			// if dashing, just return true
			
			// determine angle between player and asteroid
			float xDiff = x - asteroid.x;
			float yDiff = y - asteroid.y;
			
			System.out.println("Player: " + x + ", " + y);
			System.out.println("Asteroid: " + asteroid.x + ", " + asteroid.y);
			
			System.out.println("Diff: " + xDiff + ", " + yDiff);
			

			// angle is originally with respect to the asteroid
			double angle = Math.atan(yDiff/xDiff);
			
			// on left side
			if (xDiff < 0) {
				angle += Math.PI;
			}
			
			if (angle < 0) {
				angle += 2*Math.PI;
			}
			
			System.out.println("Asteroid Angle: " + angle);

			// get two closest asteroid points to angle
			float[] asteroidClosestPoints = asteroid.getClosestPoints(angle);
			
			// get angle with respect to the player
			angle += Math.PI;
			angle %= 2*Math.PI;

			System.out.println("Player Angle: " + angle);
			System.out.println("Asteroid Closest Points: " + asteroidClosestPoints[0] + ", " + asteroidClosestPoints[1] + " - " + asteroidClosestPoints[2] + ", " + asteroidClosestPoints[3]);
			
			// determine if asteroid intersects with player ship
			float x1;
			float y1;
			float x2;
			float y2;
			
			// TODO: handle reverse direction
			// check ship's right side
			if (angle < Math.PI/2 || angle > 3*Math.PI/2) {
				
				x1 = x + points[4];
				y1 = y + points[5];
				x2 = x + points[6];
				y2 = y + points[7];
				
				if (MyGameView.powerupSmall.isActive()) {
					x1 = x + altPoints[4];
					y1 = y + altPoints[5];
					x2 = x + altPoints[6];
					y2 = y + altPoints[7];	
				}
			}
			// check ship's left side
			else {
				x1 = x + points[0];
				y1 = y + points[1];
				x2 = x + points[2];
				y2 = y + points[3];
				
				if (MyGameView.powerupSmall.isActive()) {
					x1 = x + altPoints[0];
					y1 = y + altPoints[1];
					x2 = x + altPoints[2];
					y2 = y + altPoints[3];	
				}				
			}
			
			System.out.println("Player Closest Points: " + x1 + ", " + y1 + " - " + x2 + ", " + y2);
			
			// first do a simple check to make sure lines will actually intersect
			if (asteroidClosestPoints[0] < x1 && asteroidClosestPoints[2] < x1 ||
					asteroidClosestPoints[0] > x2 && asteroidClosestPoints[2] > x2 ||
					y1 < y2 && (asteroidClosestPoints[1] < y1 && asteroidClosestPoints[3] < y1 || asteroidClosestPoints[1] > y2 && asteroidClosestPoints[3] > y2) ||
					y1 > y2 && (asteroidClosestPoints[1] < y2 && asteroidClosestPoints[3] < y2 || asteroidClosestPoints[1] > y1 && asteroidClosestPoints[3] > y1))
			{
				return false;
			}
			
			// get slope
			float slope = (y2 - y1) / (x2 - x1);
			
			// get y offset (use actual x,y values here)
			float yOffset = (y + y1) - slope*(x + x1);
			
			// determine y values for asteroid's x values
			float asteroidY1 = slope*asteroidClosestPoints[0] + yOffset;
			float asteroidY2 = slope*asteroidClosestPoints[2] + yOffset;
			
			System.out.println("slope: " + slope);
			System.out.println("yOffset: " + yOffset);
			System.out.println("asteroidY1: " + asteroidY1);
			System.out.println("asteroidY2: " + asteroidY2);
			
			// finally check if line intersects
			// TODO: make sure asteroid actually intersects with ship
			// and doesn't just intersect with line
			
			// check if both asteroid y points are less/greater than both ship y points
			// or both asteroid x points are less/greater than both ship x points
			if (asteroidClosestPoints[1] <= asteroidY1 && asteroidClosestPoints[3] >= asteroidY2 ||
					asteroidClosestPoints[1] >= asteroidY1 && asteroidClosestPoints[3] <= asteroidY2)
			{
				return true;
			}
			
			return false;
		}
		
		return false;*/
	}
	
	/**
	 * Set player's x value
	 * @param x x value to set to
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	public void moveLeft() {
		
		if (speedX == 0 || move != MOVE_LEFT) {
			speedX = BUTTON_MIN_SPEED;
		}
		
		dirX = -1;
		
		move = MOVE_LEFT;
	}
	
	public void moveRight() {
		if (speedX == 0 || move != MOVE_RIGHT) {
			speedX = BUTTON_MIN_SPEED;
		}
		
		dirX = 1;
		
		move = MOVE_RIGHT;
	}
	
	public void moveStop() {
		dirX = 0;
		move = MOVE_NONE;
	}
	
	public void dash() {
		
		if (dashTimeout <= 0) {
			inPosition = false;
		
			if (direction == MyGameView.DIRECTION_NORMAL) {
				direction = MyGameView.DIRECTION_REVERSE;
			} else {
				direction = MyGameView.DIRECTION_NORMAL;
			}
			
			dashTimeout = DASH_TIMEOUT_DURATION;
		}
	}
	
	public boolean inPosition() {
		return inPosition;
	}
	
	public int getDirection() {
		return direction;
	}
	
	/**
	 * Returns float between [-1, 1] depending on ship's position
	 * @return gravity
	 */
	public float getGravity() {
		return 1 - 2 * (Y_BOTTOM - y) / (Y_BOTTOM - Y_TOP);
	}
}