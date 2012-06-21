package com.slauson.dodger.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.slauson.dodger.main.MyGameView;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Asteroid extends Sprite {

	/**
	 * Public constants
	 */
	public static final int STATUS_NORMAL = 0;
	public static final int STATUS_INVISIBLE = 1;
	public static final int STATUS_BREAKING_UP = 2;
	public static final int STATUS_DISAPPEARING = 3;
	public static final int STATUS_FADING_OUT = 4;
	public static final int STATUS_SPLITTING_UP = 5;

	/**
	 * Private fields
	 */

	private Random random;

	private int status = STATUS_NORMAL;
	private int counter;
	private float factor;
	
	private float[] points;
	private float[] tempPoints;
	private double[] angles;
	
	private int leftPoints, rightPoints;
	
	private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();
	
	/**
	 * Private constants
	 */
	private static final int NUM_POINTS_MIN = 6;
	private static final int NUM_POINTS_MAX = 12;
	private static final int RADIUS_MIN = 8;
	private static final int RADIUS_AVG = 12;
	private static final int RADIUS_MAX = 16;
	
	private static final int BREAKING_UP_DURATION = 50;
	private static final float BREAKING_UP_MOVE = 1f;
	private static final float DISAPPEARING_FACTOR = 0.25f;
	private static final int FADING_OUT_DURATION = 10;
	private static final int SPLITTING_UP_DURATION = 50;
	private static final float SPLITTING_UP_FACTOR = 0.5f;

	private static final int MIN_SPEED = 5;
	private static final int MAX_SPEED = 10;

	public Asteroid() {
		super(0, 0, RADIUS_AVG*2, RADIUS_AVG*2);
		
		this.random = new Random();

		init();
	}
	
	public void init() {
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
			for (int i = 0; i < points.length; i+=4) {
			
				LineSegment temp = new LineSegment(x + points[i], y + points[i+1], x + points[i+2], y + points[i+3]);
				
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
	}
	
	public void disappear() {
		if (status == STATUS_NORMAL) {
			status = STATUS_DISAPPEARING;
			
			// create temporary array of points so we can shrink the asteroid
			tempPoints = new float[points.length];
			
			System.arraycopy(points, 0, tempPoints, 0, points.length);
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
			
			System.out.println("points:");
			for (int i = 0; i < points.length; i+=2) {
				System.out.println("\t" + points[i] + ", " + points[i+1]);
			}
			
			System.out.println("leftPoints: " + leftPoints);
			System.out.println("rightPoints: " + rightPoints);
			
			// left half points (4 extra for perfect split)
			tempPoints = new float[leftPoints*4 + 4];
			
			int indexRight = 2, indexLeft = 0;
			boolean leftSwitch = false, rightSwitch = false;
			
			// iterate over existing points, moving points around
			// TODO: fix indexLeft
			
			// start at 2 so we get each pair of points (same values) for each corresponding angle
			for (int i = 2; i < points.length-2; i+=4) {
				System.out.println("i: " + i + " (" + points[i] + ", " + points[i+1] + ", " + angles[(i+2)/4] + ")");
				
				// right points
				if (angles[(i+2)/4] < Math.PI/2.0 || angles[(i+2)/4] > 3.0*Math.PI/2.0) {
					System.out.println("right: " + indexRight);

					// add one right point to left side
					if (leftSwitch && !rightSwitch) {
						tempPoints[indexLeft] = points[i];
						tempPoints[indexLeft+1] = points[i+1];
						tempPoints[indexLeft+2] = points[i+2];
						tempPoints[indexLeft+3] = points[i+3];

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
					
					tempPoints[indexLeft] = points[i];
					tempPoints[indexLeft+1] = points[i+1];
					
					// only add first point once on left side
					if (indexLeft == 0) {
						indexLeft += 2;
					} else { 
						tempPoints[indexLeft+2] = points[i+2];
						tempPoints[indexLeft+3] = points[i+3];
						indexLeft += 4;
					}
				}
			}
			
//			for (int i = 4; i < points.length-2; i+=4) {
//				System.out.println("i: " + i + " (" + points[i-2] + ", " + points[i-1] + ", " + angles[i/4] + ")");
//				
//				// right points
//				if (angles[i/4] < Math.PI/2 || angles[i/4] > 3*Math.PI/2) {
//					System.out.println("right: " + indexRight);
//					indexRight += 4;
//					
//					// move points if index is different from i
//					if (indexRight + 2 != i) {
//						
//						// add one right point to left side
//						if (!rightSwitch) {
//							points[indexLeft] = points[i-2];
//							points[indexLeft+1] = points[i-1];
//							points[indexLeft+2] = points[i];
//							points[indexLeft+3] = points[i+1];
//
//							indexLeft += 4;
//							rightSwitch = true;
//						}
//						
//						points[indexRight-4] = points[i-2];
//						points[indexRight-3] = points[i-1];
//						points[indexRight-2] = points[i];
//						points[indexRight-1] = points[i+1];
//					}
//				}
//				// left points
//				else {
//					System.out.println("left: " + indexLeft);
//					
//					// add one left point to right side
//					if (!leftSwitch) {
//						points[indexRight] = points[i-2];
//						points[indexRight+1] = points[i-1];
//						points[indexRight+2] = points[i];
//						points[indexRight+3] = points[i+1];
//
//						indexRight += 4;
//						rightSwitch = true;
//					}
//					
//					tempPoints[indexLeft] = points[i-2];
//					tempPoints[indexLeft+1] = points[i-1];
//					
//					if (indexLeft == 0) {
//						indexLeft += 2;
//					} else { 
//						tempPoints[indexLeft+2] = points[i];
//						tempPoints[indexLeft+3] = points[i+1];
//						indexLeft += 4;
//					}
//				}
//			}
			
			// TODO: assuming first/last points are on right
			
			// close right points
			points[indexRight] = points[points.length-2];
			points[indexRight+1] = points[points.length-1];
			
			// close left points
			tempPoints[indexLeft] = tempPoints[0];
			tempPoints[indexLeft+1] = tempPoints[1];
			
			System.out.println("new points: ");
			for (int i = 0; i < points.length; i+=2) {
				System.out.println("\t" + points[i] + ", " + points[i+1]);
			}

			System.out.println("new tempPoints:");
			for (int i = 0; i < tempPoints.length; i+=2) {
				System.out.println("\t" + tempPoints[i] + ", " + tempPoints[i+1]);
			}

		}
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
		
		if (status == STATUS_NORMAL && other.status == STATUS_NORMAL && checkBoxCollision(other)) {
		
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
		if (status != STATUS_INVISIBLE && onScreen()) {
			
			// intact asteroid
			if (status == STATUS_NORMAL) {
				//canvas.drawBitmap(bitmap, x-width/2, y-height/2, null);
				canvas.save();
				canvas.translate(x, y);
				
				// TODO: draw to object, save result
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
				
				canvas.drawLines(tempPoints, paint);
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

				canvas.drawLines(tempPoints, paint);
				
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
				tempPoints[i] = factor*points[i];
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
	
	public int getStatus() {
		return status;
	}
	
	public void setInvisible() {
		status = STATUS_INVISIBLE;
	}
	
	public void setFactor(float factor) {
		this.factor = factor;
	}
	
	public boolean onScreen() {
		return y > 0 && y < MyGameView.canvasHeight;
	}

}
