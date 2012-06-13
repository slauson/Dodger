package com.slauson.dodger.main;
//package com.Slauson.Dodger;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//
//import com.Slauson.Dodger.R;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.Config;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.PorterDuff.Mode;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//
//public class MyForeground extends MyGameSurfaceView {
//	
//	private Player player;
//	
//	private ArrayList<Item> items;
//	
//	private boolean initialized = false;
//	
//	private final int NUM_ITEMS = 10;
//	private final int ITEM_SIZE = 32;
//
//	public MyForeground(Context context) {
//		super(context);
//		init();
//	}
//
//	public MyForeground(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		init();
//	}
//
//	public MyForeground(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		init();
//	}
//	
//	private void init() {
//		
//		if (surfaceCreated && !initialized) {
//			//player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.icon_me), canvasWidth/2, canvasHeight - 100);
//			player = new Player(Bitmap.createBitmap(32, 32, Config.ARGB_8888), canvasWidth/2, canvasHeight - 100);
//			
//			items = new ArrayList<Item>();
//			
//			for (int i = 0; i < NUM_ITEMS; i++) {
//				//items.add(new Item(BitmapFactory.decodeResource(getResources(), R.drawable.icon_me), canvasWidth, ITEM_SIZE));
//				items.add(new Item(Bitmap.createBitmap(ITEM_SIZE, ITEM_SIZE, Config.ARGB_8888), canvasWidth, ITEM_SIZE));
//			}
//			
//			initialized = true;
//		}
//	}
//	
//	@Override
//	protected void onDraw(Canvas canvas) {
//		
//		// TODO: come up with a better way of doing this
//		if (!initialized) {
//			init();
//			return;
//		}
//		
//		// clear canvas with transparent background
//		canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
//		
//		// draw items
//		Iterator<Item> itemIterator = items.iterator();
//		
//		while(itemIterator.hasNext()) {
//			itemIterator.next().draw(canvas);
//		}
//		
//		// draw player
//		player.draw(canvas);
//	}
//	
//	@Override
//	public void updateStates() {
//		
//		if (!initialized) {
//			init();
//			return;
//		}
//		
//		// update items
//		Iterator<Item> itemIterator = items.iterator();
//		Item temp;
//		
//		while(itemIterator.hasNext()) {
//			temp = itemIterator.next();
//			temp.update();
//			
//			// reset item off screen
//			if (temp.y - temp.height/2 > canvasHeight) {
//				temp.init();
//			}
//			
//			// check collision with player
//			if (temp.checkBoxCollision(player)) {
//				temp.bitmap.eraseColor(Color.RED);
//				System.out.println("Collision");
//			}
//			
//		}
//		
//		// update player
//		player.update();
//	}
//	
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		
//		if (!initialized) {
//			init();
//			return false;
//		}
//		
//		// update player position
//		int x = (int) event.getX();
//		
//		int action = event.getAction();
//		
//		switch(action) {
//		case MotionEvent.ACTION_DOWN:
//		case MotionEvent.ACTION_MOVE:
//			//System.out.println("onTouchEvent(): " + x + ", " + y);
//			player.x = x;
//			break;
//		default:
//			break;
//		}
//		
//		return true;
//	}
//	
//	void updateAccelerometer(float x, float y) {
//		
//		if (!initialized) {
//			init();
//			return;
//		}
//		
//		player.x = (int)x;
//	}
//}
