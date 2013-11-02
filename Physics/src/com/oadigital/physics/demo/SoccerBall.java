package com.oadigital.physics.demo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.oadigital.physics.lib.objects.BaseObject;
import com.oadigital.physics.lib.objects.Circle;
import com.orionadler.physics.R;

//Implementation of the IDrawable Interface
public class SoccerBall extends Circle implements com.oadigital.physics.lib.IDrawable {
	
	Bitmap bitmap = null;
	
	SoccerBall(Context context, int radius){
		super(radius);

		Resources res = context.getResources();
		bitmap = BitmapFactory.decodeResource(res, R.drawable.soccerball);
		bitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
	}

	public void draw(Canvas canvas, BaseObject obj) {
		canvas.drawBitmap(bitmap, obj.position.X, obj.position.Y, null);
	} 
}
