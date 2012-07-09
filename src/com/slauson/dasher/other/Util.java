package com.slauson.dasher.other;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility methods
 * @author Josh Slauson
 *
 */
public class Util {

	/**
	 * Returns time string for given number of seconds
	 * @param seconds number of seconds
	 * @return time string for given number of seconds
	 */
	public static String getTimeString(int seconds) {
		return "" + (seconds/60) + ":" + (seconds%60);
	}
	
	/**
	 * Returns date string for given time
	 * @param time time
	 * @return date string for given time
	 */
	public static String getDateString(long time) {
		
		// no time
		if (time == 0) {
			return "";
		}
		
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy hh:mm aa");
		
		return format.format(date);
		
	}

	
}
