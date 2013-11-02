package com.oadigital.physics.lib.objects;

/**
 * Provides circle-specific variables
 */
public class Circle extends BaseObject{

	public float radius = 10;
	
	public Circle (float radius){
		this.dimen.X = radius;
		this.dimen.Y = radius;
		this.radius = radius;
	}	
}
