package com.oadigital.physics.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.oadigital.physics.lib.Environment;
import com.oadigital.physics.lib.IDrawable;

/**
 * Draws the simulated environment to the screen
 */
class Panel extends View implements OnTouchListener {
	
	Environment environment = new Environment();
	
	Bitmap mBitmap;
	Paint a = new Paint();
	
	boolean isTouched = false;
	int touchObjectIndex = -1;
	int lastX = 0;
	int lastY = 0;
	long deltaT = 0;
	
	public Panel(Context context) {
	    super(context);
	    setOnTouchListener(this);
	    //mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		
		a.setColor(Color.MAGENTA);
		a.setStrokeWidth(10);
		a.setStyle(Paint.Style.STROKE);
		a.setDither(true);
		a.setAntiAlias(true);  
		
		//Set up the screen size listener
		ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
		if (viewTreeObserver.isAlive()) {
			viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					Panel.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					environment.screenSizeX = Panel.this.getWidth();
					environment.screenSizeY = Panel.this.getHeight();

					Log.i("IntraPhysics", environment.screenSizeX + " x " + environment.screenSizeY);
				}
			});
		}
		
		setup();
		
	}
	
	public void setup() {

		MyDrawableCircle ball1 = new MyDrawableCircle(Color.MAGENTA, 20);		
		ball1.position.X = 200;
		ball1.position.Y = 40;
		ball1.cR = 0.85f;
		ball1.velocity.X = 24.0f;

		MyDrawableCircle ball2 = new MyDrawableCircle(Color.YELLOW, 20);
		ball2.position.X = 20;
		ball2.position.Y = 12;
		ball2.cR = 0.65f;
		ball2.velocity.X = -20.0f;

		MyDrawableCircle ball3 = new MyDrawableCircle(Color.BLUE, 30);
		ball3.position.X = 80;
		ball3.position.Y = 52;
		ball3.cR = 0.95f;
		ball3.velocity.X = 40.0f;

		SoccerBall soccerBall = new SoccerBall(this.getContext(), 55);
		soccerBall.position.X = 1000;
		soccerBall.position.Y = 60;
		soccerBall.cR = 0.75f;
		soccerBall.velocity.X = 25.0f;
		
		environment.addObject(ball1);
		environment.addObject(ball2);
		environment.addObject(ball3);
		environment.addObject(soccerBall);
		
	}

 
	@Override
	public void onDraw(Canvas canvas) {

	    canvas.drawColor(Color.WHITE);
	    
		environment.processAll();
		
		Log.i("Physics", "Drawing Frame.");

		//Loop through all items and draw them
		for (int i = 0; i < environment.objectsArray.length; i++){
			if(environment.objectsArray[i] != null){  
				//Invoke the draw method on the interface
				((IDrawable)environment.objectsArray[i]).draw(canvas, environment.objectsArray[i]);
			}
		}
		
		this.invalidate();
	    
	}
	
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		
		switch (event.getAction()){
		
		case MotionEvent.ACTION_DOWN:
			
			//Get the index of the clicked object
			touchObjectIndex = environment.getObjectAtCoordinate((int)event.getX(), (int)event.getY());
			
			if(touchObjectIndex > -1){
				isTouched = true;
			}			
			
			break;

		case MotionEvent.ACTION_MOVE:
			
			//Check if an object is being moved
			if(touchObjectIndex > -1){
				environment.objectsArray[touchObjectIndex].acceleration.X = 0;
				environment.objectsArray[touchObjectIndex].acceleration.Y = 0;
				
				environment.objectsArray[touchObjectIndex].velocity.X = 0;
				environment.objectsArray[touchObjectIndex].velocity.Y = 0;
				
				environment.objectsArray[touchObjectIndex].position.X = event.getX();
				environment.objectsArray[touchObjectIndex].position.Y = event.getY();
				
				if(event.getHistorySize() > 0){
					lastX = (int) event.getHistoricalX(0);
					lastY = (int) event.getHistoricalX(0);
					deltaT =  SystemClock.uptimeMillis() - event.getHistoricalEventTime(0);
				}
			}
			
			break;

		case MotionEvent.ACTION_UP:
			
			//Check if an object is being moved
			if(touchObjectIndex > -1){
				environment.objectsArray[touchObjectIndex].velocity.X = ((event.getX() - (float)lastX)/deltaT)/0.25f;
				environment.objectsArray[touchObjectIndex].velocity.Y = ((event.getY() - (float)lastY)/deltaT)/0.25f;
			}
			
			touchObjectIndex = -1;
			
			break;
		}
		return true;
	}
}