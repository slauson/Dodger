package com.slauson.dasher.objects;

import com.slauson.dasher.main.MyGameView;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Single line segment for when player, asteroids breakup
 * @author Josh Slauson
 *
 */
public class LineSegment {
	public float width, height;
	
	public float x1, x2, y1, y2;
	
	public float dirX, dirY;
	public float move;
	
	public LineSegment(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		
		this.dirX = 0;
		this.dirY = 0;
		this.move = 0;
	}
	
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawLine(x1, y1, x2, y2, paint);
	}
	
	public void update() {
		update(1f);
	}
	
	public void update(float speedModifier) {
		x1 = x1 + (dirX*move*speedModifier);
		x2 = x2 + (dirX*move*speedModifier);
		
		y1 = y1 + (MyGameView.gravity*dirY*move*speedModifier);
		y2 = y2 + (MyGameView.gravity*dirY*move*speedModifier);
	}

}
