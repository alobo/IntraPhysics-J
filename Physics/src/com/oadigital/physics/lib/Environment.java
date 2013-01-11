package com.oadigital.physics.lib;

import android.util.Log;

import com.oadigital.physics.lib.objects.BaseObject;

/**
 * Provides Environmental Constants
 */
public class Environment{
	float scale = 10;  //10px = 1m
	public float screenSizeX = 640;
	public float screenSizeY = 640;
	float gravity = -9.8f;

	public BaseObject[] objectsArray = new BaseObject[1];
	
	
	public void addObject(BaseObject object){
		
		//Ensure the object has implemented IDrawable
		if (object instanceof IDrawable) {
			
			//Set object's Fg (Force caused by gravity)
			object.f.Y = gravity * object.mass;
			
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
		if(obj.x > screenSizeX - obj.dimen.X && obj.v.X > 0) {
			obj.v.X *= -obj.cR;
			obj.a.X *= -obj.cR;
			obj.x = screenSizeX - obj.dimen.X;
		}
		if(obj.x < obj.dimen.X && obj.v.X < 0) {
			obj.v.X *= -obj.cR;
			obj.a.X *= -obj.cR;
			obj.x = obj.dimen.X;
		}
		if(obj.y > screenSizeY - obj.dimen.Y && obj.v.Y > 0) {
			obj.v.Y *= -obj.cR;
			obj.y = screenSizeY - obj.dimen.Y;
			Log.i("PHYSICS", "Bounce Up");
		}
		if(obj.y < obj.dimen.Y && obj.v.Y < 0) {
			obj.v.Y *= -obj.cR;
			obj.y = obj.dimen.Y;
			Log.i("PHYSICS", "Bounce Down");
		}
	}
	
	public static void objectCollisions(Object[] objArray){
		for (int i = 0; i < objArray.length; i++){
			if(objArray[i] != null){  

			}
		}
	}
	
	public void processAll(){
		for (int i = 0; i < objectsArray.length; i++){
			if(objectsArray[i] != null){  
				objectsArray[i].update(scale);
				boundingArea(objectsArray[i]);
				//ellipse(objectsArray[i].x, objectsArray[i].y, objectsArray[i].radius * 2, objectsArray[i].radius * 2);
			}
		}
	}

//	/**
//	 * Returns the index of any object at the given point
//	 */
//	public int checkMouseClick(int mX, int mY){
//		for (int i = 0; i < objectsArray.length; i++){
//			if(objectsArray[i] != null){
//				if(mX >= objectsArray[i].x - objectsArray[i].radius && mX <= objectsArray[i].x + objectsArray[i].radius){
//					if(mY >= objectsArray[i].y - objectsArray[i].radius && mY <= objectsArray[i].y + objectsArray[i].radius){
//						return i; 
//					}
//				}
//			}
//		}
//		return -1;
//	}
}
