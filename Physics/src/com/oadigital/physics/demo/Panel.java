package com.oadigital.physics.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.oadigital.physics.lib.Environment;
import com.oadigital.physics.lib.IDrawable;

/**
 * Draws the simulated environment to the screen
 */
class Panel extends View {
	
	Environment environment = new Environment();
	
	Bitmap mBitmap;
	Paint a = new Paint();
	
	public Panel(Context context) {
	    super(context);
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
}