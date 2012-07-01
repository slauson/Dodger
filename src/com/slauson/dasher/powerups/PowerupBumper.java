package com.slauson.dasher.powerups;

import com.slauson.dasher.objects.Asteroid;
import com.slauson.dasher.objects.Item;
import com.slauson.dasher.objects.Player;
import com.slauson.dasher.powerups.PowerupDrill;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Bumper powerup which causes items to bounce in opposite direction
 * @author Josh Slauson
 *
 */
public class PowerupBumper extends ActivePowerup {

	private int counter;
	private int cooldown;
	
	private Bitmap bitmapAlt;
	private Rect rectDest;
	
	// constants
	private static final int COUNTER_MAX = 20;
	private static final int COOLDOWN_MAX = 10;

	public PowerupBumper(Bitmap bitmap, Bitmap bitmapAlt, float x, float y, int duration) {
		super(bitmap, x, y);
		
		this.bitmapAlt = bitmapAlt;
		
		counter = 0;
		cooldown = COOLDOWN_MAX;
		rectDest = new Rect((int)(x - width/2), (int)(y - height/2), (int)(x + width/2), (int)(y + height/2));
		
		activate(duration);
	}

	@Override
	public void alterAsteroid(Asteroid asteroid) {
		if (asteroid.getStatus() == Asteroid.STATUS_NORMAL) {
			alterSprite(asteroid);
		}
	}

	public void alterSprite(Item item) {
		if (checkBoxCollision(item)) {
			item.setDirY(-1*item.getDirY());
			activateBumper();
		}
	}
	
	public void alterPlayer(Player player) {
		if (!player.inPosition() && cooldown == 0 && Math.abs(x - player.getX()) <= width/2 + player.getWidth()/2 && Math.abs(y - player.getY()) <= height/2 + player.getHeight()/2) {
			player.dash();
			activateBumper();
		}
	}
	
	public void alterDrill(PowerupDrill drill) {
		if (Math.abs(x - drill.getX()) <= width/2 + drill.getWidth()/2 && Math.abs(y - drill.getY()) <= height/2 + drill.getHeight()/2) {
			drill.switchDirection();
			activateBumper();
		}
	}
	
	@Override
	public void update(float speedModifier) {
		
		// update rectDest
		if (counter > 0) {
			
			counter--;
			
			float factor = 2 - Math.abs(1f*COUNTER_MAX/2 - counter)/COUNTER_MAX*2;
			
			rectDest.set((int)(x - width/2), (int)(y - factor*height/2), (int)(x + width/2), (int)(y + factor*height/2));
		}
		
		if (cooldown > 0) {
			cooldown--;
		}
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		
		if (counter > 0) {
			canvas.drawBitmap(bitmapAlt, null, rectDest, paint);
		} else {
			canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
		}
	}
	
	public void activateBumper() {
		if (counter != 0) {
			if (counter > COUNTER_MAX/2) {
				counter = COUNTER_MAX - counter;
			}
		} else {
			counter = COUNTER_MAX;
		}
	}
}
