package com.slauson.dasher.main;

import com.slauson.dasher.R;
import com.slauson.dasher.game.MyGameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Main menu
 * 
 * adapted from here: http://www.droidnova.com/creating-game-menus-in-android,518.html
 * 
 * @author Josh Slauson
 *
 */
public class MainMenu extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		// start button
		Button startButton = (Button)findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent StartGameIntent = new Intent(MainMenu.this,MyGameActivity.class);
				startActivity(StartGameIntent);
			}
		});
		
		// how to play button
		Button instructionsButton = (Button)findViewById(R.id.instructionsButton);
		instructionsButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent StartGameIntent = new Intent(MainMenu.this,OptionsMenu.class);
				startActivity(StartGameIntent);
			}
		});  

		// options button
		Button optionsButton = (Button)findViewById(R.id.optionsButton);
		optionsButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent StartGameIntent = new Intent(MainMenu.this,Instructions.class);
				startActivity(StartGameIntent);
			}
		});  
	}
}