package com.slauson.dodger.objects;

import android.graphics.Canvas;
import android.graphics.Paint;

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
	
	public void update(float speedModifier) {
		x1 = x1 + (dirX*move*speedModifier);
		x2 = x2 + (dirX*move*speedModifier);
		
		y1 = y1 + (dirY*move*speedModifier);
		y2 = y2 + (dirY*move*speedModifier);
	}

}
