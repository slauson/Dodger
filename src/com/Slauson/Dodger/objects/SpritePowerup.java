package com.slauson.dodger.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class SpritePowerup extends Sprite {

	private Bitmap bitmap;
	private int type;
	
	public SpritePowerup(Bitmap bitmap, float x, float y, int type) {
		super(x, y, bitmap.getWidth(), bitmap.getHeight());
		
		this.bitmap = bitmap;
		
		this.type = type;
		this.dirY = 1;
		this.dirX = 0;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
	}

	public int getType() {
		return type;
	}
}
