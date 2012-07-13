package com.slauson.dasher.status;

import java.util.LinkedList;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class HighScores {
	
	private static LinkedList<HighScore> highScores = new LinkedList<HighScore>();

	private static boolean initialized = false;
	
	static {
		highScores.add(new HighScore());
		highScores.add(new HighScore());
		highScores.add(new HighScore());
		highScores.add(new HighScore());
		highScores.add(new HighScore());
	}
	
	/**
	 * Loads high scores from application preferences
	 * @param sharedPreferences preferences to load from
	 */
	public static void load(SharedPreferences sharedPreferences) {
		
		initialized = true;
		
		int num = 1;
		for (HighScore highScore : highScores) {
			highScore.load(sharedPreferences, num);
			num++;
		}
	}
	
	/**
	 * Saves upgrades to application preferences
	 * @param sharedPreferenceEditor preferences to save to
	 */
	public static void save(SharedPreferences.Editor sharedPreferencesEditor) {
		
		int num = 1;
		for (HighScore highScore : highScores) {
			highScore.save(sharedPreferencesEditor, num);
			num++;
		}
	}

	/**
	 * Updates high scores with given score
	 * @param timePlayed score to add
	 */
	public static void update(int timePlayed) {
		
		for (int i = 0; i < highScores.size(); i++) {
			if (timePlayed > highScores.get(i).getScore()) {
				
				// remove last high score, update score, add it at i 
				HighScore temp = highScores.remove(highScores.size()-1);
				temp.setScore(timePlayed);
				highScores.add(i, temp);
				return;
			}
		}
	}
	
	/**
	 * Returns true if the high scores were loaded from application preferences
	 * @return true if the high scores were loaded from application preferences
	 */
	public static boolean initialized() {
		return initialized;
	}
	
	/**
	 * Returns high score for given number
	 * @param num number of high score to return
	 * @return high score for given number
	 */
	public static HighScore getHighScore(int num) {
		if (num > highScores.size()) {
			return null;
		}
		
		return highScores.get(num-1);
	}

	public static void reset(Editor sharedPreferencesEditor) {
		
		for (HighScore highScore : highScores) {
			highScore.reset();
		}
		
		save(sharedPreferencesEditor);
	}
}
