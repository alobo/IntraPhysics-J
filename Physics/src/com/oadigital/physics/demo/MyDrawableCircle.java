package com.oadigital.physics.demo;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.oadigital.physics.lib.objects.BaseObject;
import com.oadigital.physics.lib.objects.Circle;

/**
 * Sample implementation of the IDrawable Interface
 */
public class MyDrawableCircle extends Circle implements com.oadigital.physics.lib.IDrawable {
	int Stroke = 0xffFFCC00;
	int Fill = 0xffFFCC00;
	
	Paint paint = new Paint();
	
	MyDrawableCircle(int color, int radius){
		super(radius);
		paint.setColor(color);
	}
	
	public void draw(Canvas canvas, BaseObject obj) {
		canvas.drawCircle(obj.position.X, obj.position.Y, ((Circle) obj).radius, paint);
	} 
}
