package com.oadigital.physics.demo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.oadigital.physics.lib.objects.BaseObject;
import com.oadigital.physics.lib.objects.Circle;
import com.orionadler.physics.R;

//Implementation of the IDrawable Interface
public class SoccerBall extends Circle implements com.oadigital.physics.lib.IDrawable {
	
	Matrix rotator = new Matrix();
	Bitmap bitmap = null;
	
	SoccerBall(Context context, int radius){
		super(radius);

		Resources res = context.getResources();
		bitmap = BitmapFactory.decodeResource(res, R.drawable.soccerball);
		bitmap = Bitmap.createScaledBitmap(bitmap, radius * 2, radius * 2, false);
	}

	public void draw(Canvas canvas, BaseObject obj) {
		rotator.reset();
		rotator.postTranslate(-radius, -radius);
		rotator.postRotate(obj.angularDisplacement * (float)(180/Math.PI));
		rotator.postTranslate(obj.position.X, obj.position.Y);		
		canvas.drawBitmap(bitmap, rotator, null);
	} 
}
