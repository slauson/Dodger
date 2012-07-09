package com.slauson.dasher.status;

import java.util.LinkedList;

import android.content.SharedPreferences;

public class HighScores {
	
	private static LinkedList<HighScore> highScores = new LinkedList<HighScore>();
	
	static {
		highScores.add(new HighScore());
		highScores.add(new HighScore());
		highScores.add(new HighScore());
		highScores.add(new HighScore());
		highScores.add(new HighScore());
	}
	
	/**
	 * Loads high scores from application preferences
	 * @param preferences preferences to load from
	 */
	public static void load(SharedPreferences preferences) {
		
		int num = 1;
		for (HighScore highScore : highScores) {
			highScore.load(preferences, num);
			num++;
		}
	}
	
	/**
	 * Saves upgrades to application preferences
	 * @param preferenceEditor preferences to save to
	 */
	public static void save(SharedPreferences.Editor preferencesEditor) {
		
		int num = 1;
		for (HighScore highScore : highScores) {
			highScore.save(preferencesEditor, num);
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
				HighScore temp = highScores.get(highScores.size()-1);
				temp.setScore(timePlayed);
				highScores.add(i, temp);
				return;
			}
		}
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
}
