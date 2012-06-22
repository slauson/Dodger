package com.slauson.dodger.objects;

import java.util.ArrayList;
import java.util.Iterator;

import com.slauson.dodger.main.MyGameView;

import android.graphics.Canvas;
import android.graphics.Paint;

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

	
	/**
	 * Constants
	 */
	public static final int MAX_SPEED = 25;
	
	private static final int MOVE_NONE = 0;
	private static final int MOVE_LEFT = 1;
	private static final int MOVE_RIGHT = 2;
	
	private static final float BUTTON_MOVE_FACTOR = 4f;
	private static final float BUTTON_MIN_SPEED = 2f;
	
	//private static final float SLOW_BLUR_FACTOR = 3f;
	
	private static final float Y_BOTTOM = MyGameView.canvasHeight - 100;
	private static final float Y_TOP = 32;
	
	private static final float ROTATION_DEGREES = 900f;
	
	private static final int PLAYER_WIDTH = 32;
	private static final int PLAYER_HEIGHT = 32;
	private static final int REAR_OFFSET = -6;
	
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
		
		startTime = System.currentTimeMillis();
	}
	
	public void reset() {
		//x = startX;
		//y = startY;
		
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

			// normal
			if (status == STATUS_NORMAL) {
				
				// if small powerup is active, draw resized bitmap
				if (MyGameView.powerupSmall.isActive()) {
					canvas.drawLines(altPoints, paint);
				}
				// draw normal bitmap
				else {
					canvas.drawLines(points, paint);
				}
			}
			// breaking up
			else if (status == STATUS_BREAKING_UP) {
				int savedAlpha = paint.getAlpha();
				paint.setAlpha((int)(255 * (1.0*counter/BREAKING_UP_DURATION)));
				Iterator<LineSegment> lineSegmentIterator = lineSegments.iterator();
				
				while(lineSegmentIterator.hasNext()) {
					lineSegmentIterator.next().draw(canvas, paint);
				}
				
				paint.setAlpha(savedAlpha);	
			}
			
			canvas.restore();
		}
	}

	@Override
	public void update() {
		
		if (status == STATUS_NORMAL) {
		
			// touch based controls
			if (MyGameView.controlMode == MyGameView.CONTROL_TOUCH) {
				// damn floating point arithmetic
				if (Math.abs(goX - x) > 1) {
					speedX = MAX_SPEED;
	
					// need to move right
					if (goX > x) {
						dirX = 1;
						
						if (goX - x < MAX_SPEED) {
							speedX = goX - x;
						}
					}
					// need to move left
					else {
						dirX = -1;
						
						if (x - goX < MAX_SPEED) {
							speedX = x - goX;
						}
					}
				} else {
					speedX = 0;
				}
			}
			// key based controls
			else if (MyGameView.controlMode == MyGameView.CONTROL_BUTTONS) {
				if (move != MOVE_NONE) {
					if (speedX < MAX_SPEED) {
						speedX += BUTTON_MOVE_FACTOR;
						
						if (speedX > MAX_SPEED) {
							speedX = MAX_SPEED;
						}
					}
				}
			}
			
			x = x + (dirX*speedX);
			
			speedY = 0;
			dirY = 0;
			
			// move from bottom to top
			if (!inPosition) {
				if (direction == MyGameView.DIRECTION_REVERSE && Math.abs(y - Y_TOP) > 1) {
					dirY = -1;
					
					speedY = MAX_SPEED;
					
					if (y - Y_TOP <= MAX_SPEED) {
						speedY = y - Y_TOP;
						inPosition = true;
					}
				}
				// move from top to bottom
				else if (direction == MyGameView.DIRECTION_NORMAL && Math.abs(y - Y_BOTTOM) > 1) {
					dirY = 1;
					
					speedY = MAX_SPEED;
					
					if (Y_BOTTOM - y <= MAX_SPEED) {
						speedY = Y_BOTTOM - y;
						inPosition = true;
					}
				}
			}
			
			y = y + (dirY*speedY);
		}
		else if (status == STATUS_BREAKING_UP) {
			Iterator<LineSegment> lineSegmentIterator = lineSegments.iterator();
			
			while(lineSegmentIterator.hasNext()) {
				lineSegmentIterator.next().update();
			}
			
			counter--;

			if (counter < 0) {
				status = STATUS_NORMAL;
			}

		}
	}
	
	public void breakup() {
		lineSegments = new ArrayList<LineSegment>();
		
		float modifier = 1f;
		
		if (MyGameView.powerupSmall.isActive()) {
			modifier = 0.5f;
		}
		
		for (int i = 0; i < points.length; i+=4) {
			
			LineSegment temp = new LineSegment(modifier*points[i], modifier*points[i+1], modifier*points[i+2], modifier*points[i+3]);
			
			// need to get perpendicular
			float yDiff = Math.abs(points[i] - points[i+2]);
			float xDiff = Math.abs(points[i+1] - points[i+3]);
			
			yDiff += Math.abs(dirY)*speed;
			xDiff += Math.abs(dirX)*speed;
			
			lineSegments.add(temp);
			
			temp.move = BREAKING_UP_MOVE;
			
			// x direction is sometimes positive
			if (points[i] < 0) {
				temp.dirX = (-xDiff/(xDiff + yDiff));
			} else {
				temp.dirX = (xDiff/(xDiff + yDiff));
			}
			
			// y direction is always positive
			temp.dirY = (yDiff/(xDiff + yDiff)) + 1;
		}
		
		status = STATUS_BREAKING_UP;
		counter = BREAKING_UP_DURATION;
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
	
	public void switchDirection() {
		inPosition = false;
		
		if (direction == MyGameView.DIRECTION_NORMAL) {
			direction = MyGameView.DIRECTION_REVERSE;
		} else {
			direction = MyGameView.DIRECTION_NORMAL;
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