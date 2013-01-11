package com.oadigital.physics.lib.objects;

import com.oadigital.physics.lib.Functions;
import com.oadigital.physics.lib.Vector2D;

/**
 * Base physical object class exposing common physical variables.
 * All objects extend from this.
 */
public class BaseObject{

	private int lastTime = -1;
	
	//Dimensions
	public Vector2D dimen = new Vector2D();

	public float x = 0;
	public float y = 0;

	//Mass in Kilograms
	public float mass = 10.0f;

	//Velocity
	public Vector2D v = new Vector2D();
	
	//Acceleration
	public Vector2D a = new Vector2D();

	//Force in Newtons
	public Vector2D f = new Vector2D();
	public Vector2D ff = new Vector2D();

	//Coefficient of Restitution
	public float cR = 0.0f;
	
	//Coefficient of Friction
	public float mu = 0.0f;//0.05f;

	public float displacement = 0;
	
	public BaseObject(){
	}
	
	public void update(float environmentScale){
		
		if(lastTime == -1){
			lastTime = Functions.millis();
			return;
		}
		
		int deltaTime = Functions.millis() - lastTime;
		lastTime = Functions.millis();

		//Forces
		float fFriction = f.Y * mu * Functions.sign(a.X) * -1;	//Friction opposes acceleration

		//Acceleration
		a.X = (f.X - fFriction) / mass;
		a.Y = f.Y / mass;

		//Velocity
		v.X += (a.X * environmentScale * deltaTime)/1000;
		v.Y += (a.Y * environmentScale * deltaTime)/1000 * -1; //Compensate for Android's coordinate system
				
		//v.X *= mu;

		//Position
		x += ((v.X * environmentScale * deltaTime)/1000);    
		y += ((v.Y * environmentScale * deltaTime)/1000); 

		//Log.i("Physics", "Position: X:" + x + "   Y:" + y);
		//Log.i("Physics", "delta T: " + Float.toString((deltaTime / 1000f)));
	}	
}
