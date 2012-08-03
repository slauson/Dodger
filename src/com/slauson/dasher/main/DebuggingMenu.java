package com.slauson.dasher.main;

import java.util.ArrayList;

import com.slauson.dasher.R;
import com.slauson.dasher.game.MyGameView;
import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.Debugging;
import com.slauson.dasher.status.GlobalStatistics;
import com.slauson.dasher.status.HighScores;
import com.slauson.dasher.status.Points;
import com.slauson.dasher.status.Upgrades;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DebuggingMenu extends Activity {

	/** Shared preferences **/
	private SharedPreferences sharedPreferences;
	/** Shared preferences editor **/
	private SharedPreferences.Editor sharedPreferencesEditor;
	
	/** Edit text for inputting points **/
	private EditText points;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.debugging_menu);
    	
    	sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		sharedPreferencesEditor = sharedPreferences.edit();

    	
    	// load debugging options
    	if (!Debugging.initialized()) {
    		Debugging.load(sharedPreferences);
    	}
    	
    	// drop selection
    	Spinner dropSelection = (Spinner)findViewById(R.id.debuggingMenuDropSelection);
    	ArrayList<String> dropList = new ArrayList<String>();
    	dropList.add("None");
    	dropList.add("Small");
    	dropList.add("Slow");
    	dropList.add("Invulnerable");
    	dropList.add("Drill");
    	dropList.add("Magnet");
    	dropList.add("Black Hole");
    	dropList.add("Bumper");
    	dropList.add("Bomb");
    	
    	ArrayAdapter<String> dropAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dropList);
    	dropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	dropSelection.setAdapter(dropAdapter);
    	
    	// set previous selection
    	dropSelection.setSelection(Debugging.dropType);
    	
    	
    	dropSelection.setOnItemSelectedListener(new OnItemSelectedListener() {
    		
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

				// this must match above list
				switch(pos) {
				case 0:
					Debugging.dropType = MyGameView.POWERUP_NONE;
					break;
				case 1:
					Debugging.dropType = MyGameView.POWERUP_SMALL;
					break;
				case 2:
					Debugging.dropType = MyGameView.POWERUP_SLOW;
					break;
				case 3:
					Debugging.dropType = MyGameView.POWERUP_INVULNERABLE;
					break;
				case 4:
					Debugging.dropType = MyGameView.POWERUP_DRILL;
					break;
				case 5:
					Debugging.dropType = MyGameView.POWERUP_MAGNET;
					break;
				case 6:
					Debugging.dropType = MyGameView.POWERUP_BLACK_HOLE;
					break;
				case 7:
					Debugging.dropType = MyGameView.POWERUP_BUMPER;
					break;
				case 8:
					Debugging.dropType = MyGameView.POWERUP_BOMB;
					break;
				default:
					Debugging.dropType = MyGameView.POWERUP_NONE;
					break;
				}
				
				if (Debugging.dropType != MyGameView.POWERUP_NONE) {
					Toast.makeText(parent.getContext(), "Drop type set to " + Debugging.dropType, Toast.LENGTH_SHORT).show();
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}	
    	});
    	
    	// level selection
    	Spinner levelSelection = (Spinner)findViewById(R.id.debuggingMenuLevelSelection);
    	ArrayList<Integer> levelList = new ArrayList<Integer>();
    	levelList.add(0);
    	levelList.add(2);
    	levelList.add(4);
    	levelList.add(6);
    	levelList.add(8);
    	levelList.add(10);
    	levelList.add(12);
    	levelList.add(14);
    	levelList.add(16);
    	levelList.add(18);
    	levelList.add(20);
    	
    	ArrayAdapter<Integer> levelAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, levelList);
    	levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	levelSelection.setAdapter(levelAdapter);
    	
    	// set previous selection
    	levelSelection.setSelection(Debugging.level/2);
    	
    	levelSelection.setOnItemSelectedListener(new OnItemSelectedListener() {
    		
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				// this must match above list
				Debugging.level = 2*pos;
				
				if (Debugging.level != 0) {
					Toast.makeText(parent.getContext(), "Level set to " + Debugging.level, Toast.LENGTH_SHORT).show();
				}
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}
    	});

    	
    	// level progression
    	CheckBox levelProgression = (CheckBox)findViewById(R.id.debuggingMenuLevelProgression);
    	levelProgression.setChecked(true);
    	levelProgression.setText("Level Progression");
    	
    	// set previous selection
    	levelProgression.setChecked(Debugging.levelProgression);
    	
    	levelProgression.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton button, boolean value) {
				Debugging.levelProgression = value;
				
				Toast.makeText(button.getContext(), "Level progression set to " + Debugging.levelProgression, Toast.LENGTH_SHORT).show();
			}
    	});
    	
    	// god mode
    	CheckBox godMode = (CheckBox)findViewById(R.id.debuggingMenuGodMode);
    	godMode.setText("God Mode");
    	
    	// set previous selection
    	godMode.setChecked(Debugging.godMode);
    	
    	godMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton button, boolean value) {
				Debugging.godMode = value;
				
				Toast.makeText(button.getContext(), "God mode set to " + Debugging.godMode, Toast.LENGTH_SHORT).show();
			}
    	});
    	
    	// points
    	points = (EditText)findViewById(R.id.debuggingMenuPoints);

    	// set current points
    	points.setText("" + Points.getNumPoints());

    	Button updatePoints = (Button)findViewById(R.id.debuggingMenuPointsUpdate);
    	
    	updatePoints.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				int numPoints = 0;
				
				// make sure its a valid integer
				try {
					numPoints = Integer.parseInt(points.getText().toString());
				}
				catch (NumberFormatException e) {
					Toast.makeText(view.getContext(), "Invalid points", Toast.LENGTH_SHORT).show();
				}
				
				// update points
				Points.update(numPoints - Points.getNumPoints());
				Points.save(sharedPreferencesEditor);
				sharedPreferencesEditor.commit();
				
				Toast.makeText(view.getContext(), "You have " + Points.getNumPoints() + " points", Toast.LENGTH_SHORT).show();
			}
    		
    	});
    	
    	// reset stats
    	Button resetStats = (Button)findViewById(R.id.debuggingMenuResetStats);
    	
    	resetStats.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
	    		GlobalStatistics.reset(sharedPreferencesEditor);
	    		sharedPreferencesEditor.commit();
				Toast.makeText(v.getContext(), "Stats reset", Toast.LENGTH_SHORT).show();
			}
    	});
    	
    	// reset points
    	Button resetPoints = (Button)findViewById(R.id.debuggingMenuResetPoints);
    	
    	resetPoints.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Points.reset(sharedPreferencesEditor);
				sharedPreferencesEditor.commit();
				Toast.makeText(v.getContext(), "Points reset", Toast.LENGTH_SHORT).show();
			}
    	});
    	
    	// reset upgrades
    	Button resetUpgrades = (Button)findViewById(R.id.debuggingMenuResetUpgrades);
    	
    	resetUpgrades.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Upgrades.reset(sharedPreferencesEditor);
				sharedPreferencesEditor.commit();
				Toast.makeText(v.getContext(), "Upgrades reset", Toast.LENGTH_SHORT).show();
			}
    	});
    	
    	// reset high scores
    	Button resetHighScores = (Button)findViewById(R.id.debuggingMenuResetHighScores);
    	
    	resetHighScores.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HighScores.reset(sharedPreferencesEditor);
				sharedPreferencesEditor.commit();
				Toast.makeText(v.getContext(), "High scores reset", Toast.LENGTH_SHORT).show();
			}
    	});
    	
    	// reset achievements
    	Button resetAchievements = (Button)findViewById(R.id.debuggingMenuResetAchievements);
    	
    	resetAchievements.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Achievements.reset(sharedPreferencesEditor);
				sharedPreferencesEditor.commit();
				Toast.makeText(v.getContext(), "Achievements reset", Toast.LENGTH_SHORT).show();
			}
    	});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
    	// save debugging options
    	Debugging.save(sharedPreferencesEditor);
    	sharedPreferencesEditor.commit();
	}
}
