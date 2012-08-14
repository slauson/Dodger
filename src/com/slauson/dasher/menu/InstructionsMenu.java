package com.slauson.dasher.menu;

import com.slauson.dasher.R;

import android.app.Activity;
import android.os.Bundle;

public class InstructionsMenu extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.instructions_menu);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}