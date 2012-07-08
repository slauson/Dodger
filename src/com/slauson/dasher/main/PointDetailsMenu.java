package com.slauson.dasher.main;

import com.slauson.dasher.R;

import android.app.Activity;
import android.os.Bundle;

public class PointDetailsMenu extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.point_details_menu);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
