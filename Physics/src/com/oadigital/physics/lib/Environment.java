package com.oadigital.physics.lib;

import android.util.Log;

import com.oadigital.physics.lib.objects.BaseObject;

/**
 * Provides Environmental Constants
 */
public class Environment{
	float scale = 0.25f;  //10px = 1m
	public float screenSizeX = 640;
	public float screenSizeY = 640;
	float accelerationGravity = 9.8f;

	public BaseObject[] objectsArray = new BaseObject[1];
		
	public void addObject(BaseObject object){
		
		//Ensure the object has implemented IDrawable
		if (object instanceof IDrawable) {
			
			//Add the new item to the array
			BaseObject[] newObjectArray = new BaseObject[objectsArray.length + 1];
			System.arraycopy(objectsArray, 0, newObjectArray, 0, objectsArray.length);
			newObjectArray[newObjectArray.length - 1] = object;
			
			objectsArray = newObjectArray;
			
		}else{
			throw new Error("Objects must implement IDrawable!");
		}

	}
	
	public void boundingArea(BaseObject obj){
		// EDGES OF THE SCREEN
		if(obj.position.X > screenSizeX - obj.dimen.X && obj.velocity.X > 0) {
			obj.velocity.X *= -obj.cR;
			obj.acceleration.X *= -obj.cR;
			obj.position.X = screenSizeX - obj.dimen.X;
		}
		if(obj.position.X < obj.dimen.X && obj.velocity.X < 0) {
			obj.velocity.X *= -obj.cR;
			obj.acceleration.X *= -obj.cR;
			obj.position.X = obj.dimen.X;
		}
		if(obj.position.Y > screenSizeY - obj.dimen.Y && obj.velocity.Y > 0) {
			obj.velocity.Y *= -obj.cR;
			obj.position.Y = screenSizeY - obj.dimen.Y;
		}
		if(obj.position.Y < obj.dimen.Y && obj.velocity.Y < 0) {
			obj.velocity.Y *= -obj.cR;
			obj.position.Y = obj.dimen.Y;
		}
	}
	
	public static void objectCollisions(BaseObject[] objArray){
		for (int i = 0; i < objArray.length; i++){

			for (int j = 0; j < objArray.length; j++){

				if(objArray[i] != null && objArray[j] != null){
					//Skip if it's the same object
					if(i != j){
					

						float distance = (float) Math.sqrt(
								Math.pow(objArray[i].position.X - objArray[j].position.X, 2) + 
								Math.pow(objArray[i].position.Y - objArray[j].position.Y, 2)
								);
	
						//if(distance < (objArray[i].dimen.X + objArray[j].dimen.X)){
						objArray[i].velocity.X *= -1;
						objArray[i].velocity.Y *= -1;
						objArray[j].velocity.X *= -1;
						objArray[j].velocity.Y *= -1;
						//}
					}
				}

			}				


		}
	}
	
	public void processAll(){
		float coefficientFriction = 0.01f;
		float forceGravity = 0.0f;
		float forceNormal = 0.0f;
		float forceFriction = 0.0f;
		
		for (int i = 0; i < objectsArray.length; i++){
			if(objectsArray[i] != null){  
				
				//Calculate forces
				forceGravity = accelerationGravity * objectsArray[i].mass;
				forceNormal = forceGravity;
				forceFriction = forceNormal * coefficientFriction;
				
				//Calculate net force
				objectsArray[i].netForce.X = objectsArray[i].appliedForce.X;
				
				//Hack Solution - Stops erratic friction application by rounding velocity values
				if(objectsArray[i].velocity.X < 0.5 && objectsArray[i].velocity.X > 0){
					objectsArray[i].velocity.X = 0;
				}else if(objectsArray[i].velocity.X > -0.5 && objectsArray[i].velocity.X < 0){
					objectsArray[i].velocity.X = 0;
				}
				
				if(objectsArray[i].velocity.X != 0){
					//Apply kinetic friction only if the object is moving
					objectsArray[i].netForce.X +=  -1 * forceFriction * Utils.getSign(objectsArray[i].velocity.X);
				}
			
				objectsArray[i].netForce.Y = forceGravity + objectsArray[i].appliedForce.Y;
				
				//Calculate Acceleration, Velocity, Position
				objectsArray[i].acceleration.X = objectsArray[i].netForce.X / objectsArray[i].mass;
				objectsArray[i].acceleration.Y = objectsArray[i].netForce.Y / objectsArray[i].mass;
				objectsArray[i].velocity.X += objectsArray[i].acceleration.X * scale;
				objectsArray[i].velocity.Y += objectsArray[i].acceleration.Y * scale;
				objectsArray[i].position.X += objectsArray[i].velocity.X * scale;
				objectsArray[i].position.Y += objectsArray[i].velocity.Y * scale;
				
				objectsArray[i].angularDisplacement += objectsArray[i].angularVelocity;
				
				//objectCollisions(objectsArray);
				boundingArea(objectsArray[i]);		
            }
		}
	}
	
	/**
	 * Returns the index of any object at the given point
	 */
	public int getObjectAtCoordinate(int mX, int mY){
		for (int i = 0; i < objectsArray.length; i++){
			if(objectsArray[i] != null){
				if(mX >= objectsArray[i].position.X - objectsArray[i].dimen.X && mX <= objectsArray[i].position.X + objectsArray[i].dimen.X){
					if(mY >= objectsArray[i].position.Y - objectsArray[i].dimen.Y && mY <= objectsArray[i].position.Y + objectsArray[i].dimen.Y){
						return i; 
					}
				}
			}
		}
		return -1;
	}
}
