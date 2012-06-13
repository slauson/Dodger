package com.slauson.dodger.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Asteroid extends Sprite {

	private final int MIN_SPEED = 5;
	private final int MAX_SPEED = 10;
	
	private int maxWidth, maxHeight;
	
	private Random random;
	
	private boolean visible;
	private boolean intact;
	private int intactCounter;
	
	private float[] points;
	private double[] angles;
	
	/**
	 * Constants
	 */
	private static final int NUM_POINTS_MIN = 6;
	private static final int NUM_POINTS_MAX = 12;
	private static final int RADIUS_MIN = 8;
	private static final int RADIUS_AVG = 12;
	private static final int RADIUS_MAX = 16;
	
	private static final int LINE_SEGMENT_DURATION = 50;
	private static final float LINE_SEGMENT_MOVE = 1f;
	private static final float LINE_SEGMENT_MOVE_RANDOM_FACTOR = 0.5f;
	
	private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();
		
	public Asteroid(int maxWidth, int maxHeight) {
		super(0, 0, RADIUS_AVG*2, RADIUS_AVG*2);
		
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.random = new Random();

		init();
	}
	
	public void init() {
		x = random.nextInt(maxWidth);
		y = -random.nextInt(maxHeight);
		
		createRandomPoints();
		
		dirX = -.25f + 0.5f*random.nextFloat();
		
		dirY = 1 - dirX;
		speed = MIN_SPEED + random.nextInt(MAX_SPEED-MIN_SPEED);
		visible = true;
		intact = true;
		intactCounter = -1;
		
		lineSegments = new ArrayList<LineSegment>();
	}
	
	private void createRandomPoints() {
		
		int numPoints = NUM_POINTS_MIN + random.nextInt(NUM_POINTS_MAX - NUM_POINTS_MIN - 1);
		
		double pointAngle = 2*Math.PI/numPoints;
		
		// TODO: use Path instead
		points = new float[numPoints*4];
		angles = new double[numPoints];

		// for each point
		for(int i = 0; i < numPoints; i++) {
			
			// calculate angle based on point number and random offset
			double angle = 2*Math.PI*i/numPoints - pointAngle/2 + random.nextFloat()*pointAngle;
			
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
		for (int i = 0; i < points.length; i+=4) {
		
			LineSegment temp = new LineSegment(x + points[i], y + points[i+1], x + points[i+2], y + points[i+3]);
			
			// need to get perpendicular
			float yDiff = Math.abs(points[i] - points[i+2]);
			float xDiff = Math.abs(points[i+1] - points[i+3]);
			
			yDiff += Math.abs(dirY)*speed;
			xDiff += Math.abs(dirX)*speed;
			
			lineSegments.add(temp);
			
			temp.move = LINE_SEGMENT_MOVE;
			
			if (points[i] < 0) {
				temp.dirX = (-xDiff/(xDiff + yDiff));
			} else {
				temp.dirX = (xDiff/(xDiff + yDiff));
			}
			
			temp.dirY = (yDiff/(xDiff + yDiff)) + 1;
		}
		
		intact = false;
		intactCounter = LINE_SEGMENT_DURATION;
	}
	
	/*
	 * Item collision
	 * 
	 * combine items
	 * - calculate point that overlaps
	 * - remove point from item
	 * - add remaining points to other item (splice in carefully)
	 * 
	 * absorb
	 * - increase radius of larger item
	 * 
	 * destroy
	 * - crush smaller item until too small
	 * 
	 * transfer
	 */
	
	/*
	 * Make game like Osmos
	 * - play as asteroid 
	 * - try to get really big
	 * 
	 * - obstacles
	 */
	
	/*
	 * Player can run into small asteroids
	 * - make dents 
	 */
	
	public boolean checkCollision(Asteroid other) {
		
		if (visible && intact && other.visible && other.intact && checkBoxCollision(other)) {
		
			// TODO: finer collision detection
			
			return true;

			
			/*
			 * TODO: setup object combining, compare with object explosions
			 * choose one to be main theme of gameplay
			 */

			//visible = false;
			
			//return true;
			
			// go through each point
			/*
			for (int i = 0; i < points.length; i+=2) {
				
				if (checkBoxContains(x + points[i], y + points[i+1])) {
					
				
					// make other item invisible
					visible = false;
					
					// determine where to splice in new points
					
					// TODO: test this elsewhere...
					
					float[] tempPoints = new float[points.length/2 + points.length/2];
					double[] tempAngles = new double[angles.length + angles.length];
					
					System.out.println("tempPoints: " + tempPoints.length);
					System.out.println("tempAngles: " + tempAngles.length);
					
					int pointsIndex = 0;
					int anglesIndex = 0;
					
					// get all current points
					for (int j = 0; j < points.length; j+=4) {
						tempPoints[pointsIndex] = points[j];
						pointsIndex++;
						tempPoints[pointsIndex] = points[j+1];
						pointsIndex++;
					}
					
					// get all current angles
					for (int j = 0; j < angles.length; j++) {
						tempAngles[anglesIndex] = angles[j];
						anglesIndex++;
					}
					
					// get other points (except overlapping point)
					// get other angles (except overlapping point)
					for (int j = 0; j < points.length; j+=4) {
						if (j != i) {
							System.out.println("pointsIndex: " + pointsIndex);
							tempPoints[pointsIndex] = (x - x ) + points[j];
							pointsIndex++;
							tempPoints[pointsIndex] = (y - y) + points[j+1];
							pointsIndex++;
							
							float diffX = points[j] - x;
							float diffY = points[j+1] - y;
							double angle = Math.atan(diffY/diffX);
							
							if (x < 0) {
								angle += Math.PI;
							}
							
							if (angle < 0) {
								angle += 2*Math.PI;
							}
							
							tempAngles[anglesIndex] = angle;
							anglesIndex++;
						}
					}
					
					// sort angles/points
					// use bubble sort
					for (int j = 0; j < tempAngles.length - 1; j++) {
						for (int k = 0; k < tempAngles.length - 1; k++) {
							if (tempAngles[k] > tempAngles[k+1]) {
								double tempAngle = tempAngles[k];
								tempAngles[k] = tempAngles[k+1];
								tempAngles[k+1] = tempAngle;
								
								float tempPoint = tempPoints[k];
								tempPoints[k] = tempPoints[k+1];
								tempPoints[k+1] = tempPoint; 
							}
						}
					}
					
					// populate angles/points
					angles = tempAngles;
					
					points = new float[tempPoints.length*2];
					
					points[0] = tempPoints[0];
					
					for (int j = 1; j < tempPoints.length-1; j++) {
						points[2*j-1] = tempPoints[j];
						points[2*j] = tempPoints[j];
					}
					
					points[points.length-2] = tempPoints[tempPoints.length-1];
					points[points.length-1] = tempPoints[0];
					
					
					return true;
				}
					
					/*
					float diffX = points[i] - x;
					float diffY = points[i+1] - y;
					
					double angle = Math.atan(diffY/diffX);
					
					if (x < 0) {
						angle += Math.PI;
					}
					
					if (angle < 0) {
						angle += 2*Math.PI;
					}
					
					int index;
					for (index = 0; index < angles.length-1; index++) {
						if (angle > angles[index]) {
							break;
						}
					}
					*/
					
					// TODO: this is ugly!
					
					/*
					 * idea
					 * - add new points/angles for other item
					 * - sort all angles
					 * - sort corresponding points
					 */ 
					//
					
					/*
					// copy points/angles to temp arrays
					double[] tempAngles = angles;
					float[] tempPoints = points;
					
					angles = new double[tempAngles.length + angles.length - 1];
					points = new float[tempPoints.length + points.length - 2];
					
					// copy new points/angles
					System.arraycopy(tempAngles, 0, angles, 0, index+1);
					System.arraycopy(angles, i/2, angles, index+1, angles.length-i/2);
					
					if (i != 0) {
						System.arraycopy(angles, 0, angles, index+1+(angles.length-i/2), i/2-1);
					}
					System.arraycopy(tempAngles, index+1, angles, index+1+angles.length-1, tempAngles.length-index-1);
					
					System.arraycopy(tempPoints, 0, tempPoints, 0, 2*(index+1));
					System.arraycopy(points, i, points, 2*(index+1), points.length-i);
					
					if (i != 0) {
						System.arraycopy(points, 0, points, 2*(index+1)+(points.length-i), i-1);
					}
					System.arraycopy(tempPoints, 2*(index+1), points, 2*(index+1)+points.length-2, tempPoints.length-(2*(index+1)));
					
					System.out.println("\tpoint collision");
					
					
					return true;
				}
			}
			
			if (destroyed) {
				visible = true;
			}
			}*/
		}
		
		return false;
	}
	
	public void draw(Canvas canvas, Paint paint) {
		if (visible) {
			
			// intact item
			if (intact) {
				//canvas.drawBitmap(bitmap, x-width/2, y-height/2, null);
				canvas.save();
				canvas.translate(x, y);
				
				// TODO: draw to object, save result
				canvas.drawLines(points, paint);
				canvas.restore();
			}
			// broken up item
			else {
				int savedAlpha = paint.getAlpha();
				paint.setAlpha((int)(255 * (1.0*intactCounter/LINE_SEGMENT_DURATION)));
				Iterator<LineSegment> lineSegmentIterator = lineSegments.iterator();
				
				while(lineSegmentIterator.hasNext()) {
					lineSegmentIterator.next().draw(canvas, paint);
				}
				
				paint.setAlpha(savedAlpha);
			}
		}
	}
	
	@Override
	public void update() {
		update(1.0f);
	}
	
	public void update(float speedModifier) {
		
		x = x + (int)(dirX*speed*speedModifier);
		y = y + (int)(dirY*speed*speedModifier);
		
		// broken up item
		if (!intact) {
			Iterator<LineSegment> lineSegmentIterator = lineSegments.iterator();
			
			while(lineSegmentIterator.hasNext()) {
				lineSegmentIterator.next().update(speedModifier);
			}

			intactCounter--;
			
			if (intactCounter < 0) {
				visible = false;
			}
		}
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean isIntact() {
		return intact;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setIntact(boolean intact) {
		this.intact = intact;
	}
}
