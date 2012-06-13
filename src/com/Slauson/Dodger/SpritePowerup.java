package com.Slauson.Dodger;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class SpritePowerup extends Sprite {

	private Bitmap bitmap;
	private int type;
	
	public SpritePowerup(Bitmap bitmap, int x, int y, int type) {
		super(x, y, bitmap.getWidth(), bitmap.getHeight());
		
		this.bitmap = bitmap;
		this.type = type;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
		
	}

	public int getType() {
		return type;
	}
}
