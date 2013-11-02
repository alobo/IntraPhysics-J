package com.oadigital.physics.lib;

public class Vector2D{
	public float X = 0.0f;
	public float Y = 0.0f;
	
	//Constructor - Initializes the components to 0
	public Vector2D(){
	}
	
	//Constructor - initializes the components to the specified values
	public Vector2D(float intX, float intY){
		this.X = intX;
		this.Y = intY;
	}
		
	//Formats and returns a vector as a human readable string
	public String toString(String strUnits){
		float dblRad = (float) Math.atan(X/Y);
		float dblDegrees = (float) Math.toDegrees(dblRad);
		float dblHyp = (float) Math.sqrt((Math.pow(X, 2) + Math.pow(Y, 2)));
		
		String strOut = (int)dblHyp + strUnits + " ";
		
		if(dblDegrees == 90){
			strOut+= " [E]";
		}else if(dblDegrees == -90){
			strOut+= " [W]";
		}else{
			//strOut += " [N" + dblDegrees + "]";
		}
		
		return strOut;				
	}
	
	//Adds the given vector to this vector
	public Vector2D add(Vector2D vector){
		return new Vector2D( X + vector.X, Y + vector.Y);
	}
	
	//Subtracts the given vector from this vector
	public Vector2D subtract(Vector2D vector){
		return new Vector2D( X - vector.X, Y - vector.Y);
	}
	
	public Vector2D scalarMultiple(float multiple){
		return new Vector2D(X * multiple, Y * multiple);
	}
	
	//Returns the magnitude of the vector
	public float getMagnitude(){
		return (float) Math.sqrt(X * X + Y * Y);
	}
	
	//Returns the vector perpendicular to this vector
	public Vector2D getNormal(){
		float length = getMagnitude();
		if(length == 0){
			length = Float.MIN_VALUE;
		}
		return new Vector2D(X/length, Y/length);
	}
}
