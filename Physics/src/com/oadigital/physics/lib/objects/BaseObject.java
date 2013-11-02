package com.oadigital.physics.lib.objects;

import com.oadigital.physics.lib.Vector2D;

/**
 * Base physical object class exposing common physical variables.
 * All objects extend from this.
 */
public class BaseObject{

	private int lastTime = -1;
	
	//Dimensions
	public Vector2D dimen = new Vector2D();

	//Properties
	public Vector2D position = new Vector2D();
	public Vector2D velocity = new Vector2D();
	public Vector2D acceleration = new Vector2D();

	//Mass in Kilograms
	public float mass = 10.0f;
	
	public Vector2D netForce = new Vector2D();
	public Vector2D appliedForce = new Vector2D();

	//Coefficient of Restitution
	public float cR = 0.0f;

	//Coefficient of Friction
	public float mu = 0.0f;
	
	public BaseObject(){
	}
}
