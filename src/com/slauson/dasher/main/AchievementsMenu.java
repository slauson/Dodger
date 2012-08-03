package com.slauson.dasher.main;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.slauson.dasher.R;
import com.slauson.dasher.status.Achievement;
import com.slauson.dasher.status.Achievements;

public class AchievementsMenu extends Activity {

	/** Table layout to add achievements to **/
	private TableLayout table;
	
	/** Table row **/
	private int row = 0;

	/** True when achievements have been loaded from application preferences **/
	protected boolean achievementsLoaded = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
		setContentView(R.layout.achievements_menu);
    	
    	table = (TableLayout)findViewById(R.id.achievementsMenuTable);

    	Achievements.loadResources(getResources(), getPackageName());
    	
    	// for local achievements menu
    	if (!achievementsLoaded) {
    		loadAchievements();
    	}
    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	/**
	 * Loads achievements
	 */
	private void loadAchievements() {
    	List<Achievement> achievements = Achievements.getAchievements();

    	// add achievements
    	for (Achievement achievement : achievements) {
    		addAchievement(achievement);
    	}
	}
	
	/**
	 * Adds achievement to table
	 * @param achievement achievement to add
	 */
	protected void addAchievement(Achievement achievement) {
		
		System.out.println("adding achievement " + achievement + " to row " + row);
		
		// get first row
		TableRow tableRow1 = (TableRow)table.getChildAt(row);
		tableRow1.setVisibility(View.VISIBLE);
		
		// get imageview
		ImageView imageView = (ImageView)tableRow1.getChildAt(0);
		imageView.setImageResource(achievement.getIcon());
		
		// get textview
		TextView textView1 = (TextView)tableRow1.getChildAt(1);
		textView1.setText(getResources().getString(achievement.getTitle()));
		
		row++;

		// get second row
		TableRow tableRow2 = (TableRow)table.getChildAt(row);
		tableRow2.setVisibility(View.VISIBLE);
		
		// set progress bar
		ProgressBar progressBar = (ProgressBar)tableRow2.getChildAt(0);
		
		if (achievement.getProgress() > 0) {
			progressBar.setProgress((int)(achievement.getProgress()*100));
		}

		row++;
		
		// get third row
		TableRow tableRow3 = (TableRow)table.getChildAt(row);
		tableRow3.setVisibility(View.VISIBLE);
		
		// get textview
		TextView textView2 = (TextView)tableRow3.getChildAt(0);
		textView2.setText(achievement.getDescription());

		row++;
		
		// check if achievement is unlocked
		if (achievement.getValue()) {
			tableRow1.setBackgroundColor(Color.WHITE);
			textView1.setTextColor(Color.BLACK);
			tableRow2.setBackgroundColor(Color.WHITE);
			textView2.setTextColor(Color.BLACK);
			tableRow3.setBackgroundColor(Color.WHITE);
			
			progressBar.setProgress(0);
			
			// add time unlocked
			if (!achievement.getTimeString().isEmpty()) {
				textView2.setText(textView2.getText() + "\n" + achievement.getTimeString());
			}
		}		
	}
}