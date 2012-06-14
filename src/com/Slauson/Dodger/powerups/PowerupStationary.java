package com.slauson.dodger.powerups;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Powerup that is stationary on the screen
 * @author josh
 *
 */
public abstract class PowerupStationary extends Powerup {
	
	protected Bitmap bitmap;
	
	protected int x, y, width, height;
	
	public PowerupStationary(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
	}
}
