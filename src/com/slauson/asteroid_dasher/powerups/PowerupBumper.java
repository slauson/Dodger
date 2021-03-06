package com.slauson.asteroid_dasher.powerups;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.slauson.asteroid_dasher.game.Game;
import com.slauson.asteroid_dasher.objects.Asteroid;
import com.slauson.asteroid_dasher.objects.Item;
import com.slauson.asteroid_dasher.objects.Player;
import com.slauson.asteroid_dasher.status.Achievements;
import com.slauson.asteroid_dasher.status.LocalStatistics;
import com.slauson.asteroid_dasher.status.Upgrades;

/**
 * Bumper powerup which causes items to bounce in opposite direction
 * @author Josh Slauson
 *
 */
public class PowerupBumper extends ActivePowerup {

	// constants
	private static final int DURATION_0 = 10000;
	private static final int DURATION_1 = 15000;
	private static final int DURATION_2 = 20000;
	private static final int DURATION_3 = 30000;
	
	private int counter;
	
	// cooldown so that player ship doesn't activate on pickup
	private int cooldown;
	
	private Bitmap bitmapAlt;
	private RectF rectDest;
	
	// constants
	private static final int COUNTER_MAX = 20;
	private static final int COOLDOWN_MAX = 10;

	public PowerupBumper(Bitmap bitmap, Bitmap bitmapAlt, float x, float y, int level) {
		super(bitmap, x, y);
		
		this.bitmapAlt = bitmapAlt;
		
		counter = 0;
		cooldown = COOLDOWN_MAX;
		rectDest = new RectF(x - width/2, y - height/2, x + width/2, y + height/2);
		
		switch(level) {
		case Upgrades.BUMPER_UPGRADE_INCREASED_DURATION_1:
			activate(DURATION_1);
			break;
		case Upgrades.BUMPER_UPGRADE_INCREASED_DURATION_2:
			activate(DURATION_2);
			break;
		case Upgrades.BUMPER_UPGRADE_INCREASED_DURATION_3:
		case Upgrades.BUMPER_UPGRADE_INCREASED_SIZE:
			activate(DURATION_3);
			break;
		default:
			activate(DURATION_0);
			break;
		}
	}

	@Override
	public void alterAsteroid(Asteroid asteroid) {
		if (asteroid.getStatus() == Asteroid.STATUS_NORMAL && checkBoxCollision(asteroid) && checkItemMovingTowardsBumper(asteroid)) {
			
			asteroid.setDirY(-1*asteroid.getDirY());
			activateBumper();
			
			LocalStatistics.getInstance().asteroidsDestroyedByBumper++;
			numAffectedAsteroids++;
		}
	}

	public void alterItem(Item item) {
		if (checkBoxCollision(item) && checkItemMovingTowardsBumper(item)) {
			
			item.setDirY(-1*item.getDirY());
			activateBumper();
		}
	}
	
	public boolean alterPlayer(Player player) {
		if (!player.inPosition() && cooldown == 0 && 
				Math.abs(x - player.getX()) <= width/2 + player.getWidth()/2 &&
				Math.abs(y - player.getY()) <= height/2 + player.getHeight()/2 &&
				checkItemMovingTowardsBumper(player))
		{
			
			player.dash();
			activateBumper();
			
			// bumper between achievement
			Achievements.unlockLocalAchievement(Achievements.localBumperBetween);
			
			return true;
		}
		
		return false;
	}
	
	public void alterDrill(PowerupDrill drill) {
		if (Math.abs(x - drill.getX()) <= width/2 + drill.getWidth()/2 &&
				Math.abs(y - drill.getY()) <= height/2 + drill.getHeight()/2 &&
				checkItemMovingTowardsBumper(drill))
		{
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
		
		// update cooldown
		if (cooldown > 0) {
			cooldown--;
		}
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		
		// fade out
		if (remainingDuration() < FADE_OUT_DURATION && Game.gameStatus == Game.STATUS_RUNNING) {
			int alpha = (int)(255*(1.f*remainingDuration()/FADE_OUT_DURATION));
			
			if (alpha < 0) {
				alpha = 0;
			}
			paint.setAlpha(alpha);
		}
		
		if (counter > 0) {
			canvas.drawBitmap(bitmapAlt, null, rectDest, paint);
		} else {
			canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
		}
		
		// restore alpha
		if (remainingDuration() < FADE_OUT_DURATION && Game.gameStatus == Game.STATUS_RUNNING) {
			paint.setAlpha(255);
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
	
	public void checkAchievements() {
		if (numAffectedAsteroids >= Achievements.LOCAL_DESTROY_ASTEROIDS_BUMPER_NUM_1) {
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithBumper1);
		}
		
		if (numAffectedAsteroids >= Achievements.LOCAL_DESTROY_ASTEROIDS_BUMPER_NUM_2) {
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithBumper2);
		}
		
		if (numAffectedAsteroids >= Achievements.LOCAL_DESTROY_ASTEROIDS_BUMPER_NUM_3) {
			Achievements.unlockLocalAchievement(Achievements.localDestroyAsteroidsWithBumper3);
		}
	}
	
	/**
	 * Returns true if item is moving towards bumper
	 * @param item
	 * @return true if item is moving towards bumper
	 */
	private boolean checkItemMovingTowardsBumper(Item item) {
		
		// positive == bumper below
		float diffY = y - item.getY();
		
		// check if item is moving towards bumper
		if (Game.direction == Game.DIRECTION_NORMAL) {
			if (item.getDirY() < 0) {
				return diffY < 0;
			} else {
				return diffY >= 0;
			}
		} else {
			if (item.getDirY() < 0) {
				return diffY >= 0;
			} else {
				return diffY < 0;					
			}
		}

	}
}