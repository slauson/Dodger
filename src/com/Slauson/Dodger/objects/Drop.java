package com.slauson.dodger.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Drop which gives the player a powerup
 * @author Josh Slauson
 *
 */
public class Drop extends Sprite {

	// type of powerup granted
	private int type;
	
	public Drop(Bitmap bitmap, float x, float y, int type) {
		super(bitmap, x, y);
		
		this.type = type;
		dirY = 1;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
	}

	/**
	 * Returns type of powerup drop is for
	 * @return type of powerup drop is for
	 */
	public int getType() {
		return type;
	}
}
