package com.slauson.dasher.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.slauson.dasher.main.MyGameView;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Asteroid that player ship has to avoid
 * @author Josh Slauson
 *
 */
public class Asteroid extends DrawObject {

	/**
	 * Private fields
	 */

	private Random random;

	private float factor;
	
	private double[] angles;
	
	private int leftPoints, rightPoints;
	
	/**
	 * Private constants
	 */
	private static final int NUM_POINTS_MIN = 6;
	private static final int NUM_POINTS_MAX = 12;
	
	private static final int RADIUS_MIN = 8;
	private static final int RADIUS_AVG = 12;
	private static final int RADIUS_MAX = 16;

	private static final int MIN_SPEED = 5;
	private static final int MAX_SPEED = 10;

	public Asteroid() {
		super(0, 0, RADIUS_AVG*2, RADIUS_AVG*2);
		
		this.random = new Random();

		reset();
	}
	
	public void reset() {
		x = random.nextInt(MyGameView.canvasWidth);
		
		if (MyGameView.direction == MyGameView.DIRECTION_NORMAL) {
			y = -random.nextInt(MyGameView.canvasHeight);
		} else {
			y = MyGameView.canvasHeight + random.nextInt(MyGameView.canvasHeight);
		}
		
		leftPoints = 0;
		rightPoints = 0;
		
		createRandomPoints();
		
		dirX = -.25f + 0.5f*random.nextFloat();
		
		dirY = 1 - dirX;
		speed = MIN_SPEED + random.nextInt(MAX_SPEED-MIN_SPEED);
		counter = 0;
		factor = 1;
		
		status = STATUS_NORMAL;
	}
	
	private void createRandomPoints() {
		
		int numPoints = NUM_POINTS_MIN + random.nextInt(NUM_POINTS_MAX - NUM_POINTS_MIN - 1);
		
		double pointAngle = 2*Math.PI/numPoints;
		
		points = new float[numPoints*4];
		angles = new double[numPoints];

		// for each point
		for(int i = 0; i < numPoints; i++) {
			
			// calculate angle based on point number and random offset
			double angle = 2*Math.PI*i/numPoints - pointAngle/2 + random.nextFloat()*pointAngle;
			
			if (angle < Math.PI/2 || angle > 3*Math.PI/2) {
				rightPoints++;
			} else {
				leftPoints++;
			}
			
			// calculate radius
			float radius = RADIUS_MIN + (RADIUS_MAX - RADIUS_MIN)*random.nextFloat(); 
			
			// get cos/sin values
			double cos = Math.cos(angle);
			double sin = Math.sin(angle);
			
			// calculate x/y coordinates
			float x = (float)(radius*cos);
			float y = (float)(radius*sin);
			
			angles[i] = angle;
			
			if (i != 0) {
				points[4*i-2] = x;
				points[4*i-1] = y;
			}
			points[4*i] = x;
			points[4*i+1] = y;
		}
		
		points[4*numPoints-2] = points[0];
		points[4*numPoints-1] = points[1];
	}
	
	// item breaks into line segments that move outward
	public void breakup() {
		if (status == STATUS_NORMAL) {
			
			lineSegments = new ArrayList<LineSegment>();

			for (int i = 0; i < points.length; i+=4) {
			
				LineSegment temp = new LineSegment(x + points[i], y + points[i+1], x + points[i+2], y + points[i+3]);
				
				// need to get perpendicular (these are opposite on purpose)
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
	}
	
	public void disappear() {
		if (status == STATUS_NORMAL) {
			status = STATUS_DISAPPEARING;
			
			// create temporary array of points so we can shrink the asteroid
			altPoints = new float[points.length];
			
			System.arraycopy(points, 0, altPoints, 0, points.length);
		}
	}
	
	public void fadeOut() {
		if (status == STATUS_NORMAL) {
			status = STATUS_FADING_OUT;
			counter = FADING_OUT_DURATION;
		}
	}
	
	public void splitUp() {
		if (status == STATUS_NORMAL) {
			status = STATUS_SPLITTING_UP;
			counter = SPLITTING_UP_DURATION;
			
			// left half points (4 extra for perfect split)
			altPoints = new float[leftPoints*4 + 4];
			
			int indexRight = 2, indexLeft = 0;
			boolean leftSwitch = false, rightSwitch = false;
			
			// start at 2 so we get each pair of points (same values) for each corresponding angle
			for (int i = 2; i < points.length-2; i+=4) {
				System.out.println("i: " + i + " (" + points[i] + ", " + points[i+1] + ", " + angles[(i+2)/4] + ")");
				
				// right points
				if (angles[(i+2)/4] < Math.PI/2.0 || angles[(i+2)/4] > 3.0*Math.PI/2.0) {
					System.out.println("right: " + indexRight);

					// add one right point to left side
					if (leftSwitch && !rightSwitch) {
						altPoints[indexLeft] = points[i];
						altPoints[indexLeft+1] = points[i+1];
						altPoints[indexLeft+2] = points[i+2];
						altPoints[indexLeft+3] = points[i+3];

						indexLeft += 4;
						rightSwitch = true;
					}
					
					points[indexRight] = points[i];
					points[indexRight+1] = points[i+1];
					points[indexRight+2] = points[i+2];
					points[indexRight+3] = points[i+3];
					
					indexRight += 4;
				}
				// left points
				else {
					System.out.println("left: " + indexLeft);
					
					// add one left point to right side
					if (!leftSwitch) {
						points[indexRight] = points[i];
						points[indexRight+1] = points[i+1];
						points[indexRight+2] = points[i+2];
						points[indexRight+3] = points[i+3];

						indexRight += 4;
						leftSwitch = true;
					}
					
					altPoints[indexLeft] = points[i];
					altPoints[indexLeft+1] = points[i+1];
					
					// only add first point once on left side
					if (indexLeft == 0) {
						indexLeft += 2;
					} else { 
						altPoints[indexLeft+2] = points[i+2];
						altPoints[indexLeft+3] = points[i+3];
						indexLeft += 4;
					}
				}
			}
			
			// close right points
			points[indexRight] = points[points.length-2];
			points[indexRight+1] = points[points.length-1];
			
			// close left points
			altPoints[indexLeft] = altPoints[0];
			altPoints[indexLeft+1] = altPoints[1];
		}
	}
	
	
	public boolean checkCollision(Asteroid other) {
		
		if (status == STATUS_NORMAL && other.status == STATUS_NORMAL && checkBoxCollision(other)) {
			return true;
		}
		
		return false;
	}
	
	public void draw(Canvas canvas, Paint paint) {
		if (status != STATUS_INVISIBLE && onScreen()) {
			
			// intact asteroid
			if (status == STATUS_NORMAL) {
				canvas.save();
				canvas.translate(x, y);
				
				canvas.drawLines(points, paint);
				canvas.restore();
			}
			// broken up asteroid
			else if (status == STATUS_BREAKING_UP){
				int savedAlpha = paint.getAlpha();
				paint.setAlpha((int)(255 * (1.0*counter/BREAKING_UP_DURATION)));
				Iterator<LineSegment> lineSegmentIterator = lineSegments.iterator();
				
				while(lineSegmentIterator.hasNext()) {
					lineSegmentIterator.next().draw(canvas, paint);
				}
				
				paint.setAlpha(savedAlpha);	
			}
			// disappearing asteroid
			else if (status == STATUS_DISAPPEARING) {
				canvas.save();
				canvas.translate(x, y);
				
				canvas.drawLines(altPoints, paint);
				canvas.restore();
			}
			// fading out asteroid
			else if (status == STATUS_FADING_OUT) {
				int savedAlpha = paint.getAlpha();
				paint.setAlpha((int)(255 * (1.0*counter/FADING_OUT_DURATION)));
				canvas.save();
				canvas.translate(x,  y);

				canvas.drawLines(points, paint);
				
				canvas.restore();
				paint.setAlpha(savedAlpha);	
			}
			// splitting up
			else if (status == STATUS_SPLITTING_UP) {
				int savedAlpha = paint.getAlpha();
				paint.setAlpha((int)(255 * (1.0*counter/SPLITTING_UP_DURATION)));
				
				canvas.save();
				canvas.translate(x + (SPLITTING_UP_DURATION-counter)*SPLITTING_UP_FACTOR,  y);

				canvas.drawLines(points, 0, rightPoints*4+4, paint);
				
				canvas.restore();
				
				canvas.save();
				canvas.translate(x - (SPLITTING_UP_DURATION-counter)*SPLITTING_UP_FACTOR,  y);

				canvas.drawLines(altPoints, paint);
				
				canvas.restore();
				paint.setAlpha(savedAlpha);
			}
		}
	}
	
	@Override
	public void update() {
		update(1.0f);
	}
	
	public void update(float speedModifier) {
		
		x = x + (dirX*speed*speedModifier);
		y = y + (MyGameView.gravity*dirY*speed*speedModifier);
		
		// broken up item
		if (status == STATUS_BREAKING_UP) {
			Iterator<LineSegment> lineSegmentIterator = lineSegments.iterator();
			
			while(lineSegmentIterator.hasNext()) {
				lineSegmentIterator.next().update(speedModifier);
			}
			
			counter--;

			if (counter < 0) {
				status = STATUS_INVISIBLE;
			}
		}
		// disappearing asteroid
		else if (status == STATUS_DISAPPEARING) {
			
			// update all points
			for (int i =  0; i < points.length; i++) {
				altPoints[i] = factor*points[i];
			}
			
			if (factor < DISAPPEARING_FACTOR) {
				status = STATUS_INVISIBLE;
			}
		}
		// fading out asteroid
		else if (status == STATUS_FADING_OUT) {
			counter--;

			if (counter < 0) {
				status = STATUS_INVISIBLE;
			}
		}
		// splitting up asteroid
		else if (status == STATUS_SPLITTING_UP) {
			counter--;
			
			if (counter < 0) {
				status = STATUS_INVISIBLE;
			}
		}
	}
	
	public void setInvisible() {
		status = STATUS_INVISIBLE;
	}
	
	public void setFactor(float factor) {
		this.factor = factor;
	}
	
	/**
	 * Returns index into points closest to the given angle
	 * @param angle angle to check
	 * @return index into points closest to the given angle
	 */
	public int getClosestPointsIndex(double angle) {
		int i;
		
		for(i = 0; i < angles.length; i++) {
			if (angles[i] >= angle) {
				break;
			}
		}
		System.out.println("getClosestPoints(): " + i + " out of " + angles.length);
		
		// handle special cases
		if (i == 0 || i == angles.length) {
			return points.length-2;
		} else {
			return 4*i-4;
		}
	}
}
