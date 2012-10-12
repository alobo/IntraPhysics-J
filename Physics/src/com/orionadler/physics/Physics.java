package com.orionadler.physics;

import processing.core.PApplet;
import android.util.Log;

public class Physics extends PApplet {

	Object[] objectsArray = new Object[3];
	int currentClickIndex = -1;

	int lastTime = 0;

	public void setup() {

		Object ball = new Object();
		ball.x = 200;
		ball.y = 40;
		ball.radius = 20;
		ball.Drawable.Stroke = 0xff000000;
		ball.Drawable.Fill = 0xffFFCC00;
		ball.cR = 0.85f;
		ball.v.X = 24.0f;

		Object ball2 = new Object();
		ball2.x = 20;
		ball2.y = 12;
		ball2.radius = 20;
		ball2.Drawable.Stroke = 0xff000000;
		ball2.Drawable.Fill = 0xffE41919;
		ball2.cR = 0.65f;
		ball2.v.X = -20.0f;

		Object ball3 = new Object();
		ball3.x = 80;
		ball3.y = 52;
		ball3.radius = 30;
		ball3.Drawable.Stroke = 0xff000000;
		ball3.Drawable.Fill = 0xff195CE4;
		ball3.cR = 0.95f;
		ball3.v.X = 40.0f;

		objectsArray[0] = ball;
		objectsArray[1] = ball2;
		objectsArray[2] = ball3;

		size((int)Environment.screenSizeX, (int)Environment.screenSizeY);
		Environment.screenSizeX = width;
		Environment.screenSizeY = height;

		strokeWeight(1);
		background(255);
		stroke(0);
		fill(0);
	}

	public void draw() {
		int currentTime = millis();
		nextFrame();
		if (mousePressed == true) {
			if(currentClickIndex > -1){
				objectsArray[currentClickIndex].v.Y = 0;
				objectsArray[currentClickIndex].x = mouseX;
				objectsArray[currentClickIndex].y = mouseY;

				objectsArray[currentClickIndex].v.X = mouseX - (pmouseX);
				objectsArray[currentClickIndex].v.Y = mouseY - (pmouseY);

			}else{     
				currentClickIndex = checkMouseClick(mouseX, mouseY); 
			}
			//line(pmouseX, pmouseY, mouseX, mouseY);
		}else{
			currentClickIndex = -1;
		}

	}

	public void nextFrame() 
	{
		background(255);
		for (int i = 0; i < objectsArray.length; i++){
			if(objectsArray[i] != null){  
				objectsArray[i].update();
				Environment.boundingArea(objectsArray[i]);  
				stroke(objectsArray[i].Drawable.Stroke);
				fill(objectsArray[i].Drawable.Fill);
				ellipse(objectsArray[i].x, objectsArray[i].y, objectsArray[i].radius * 2, objectsArray[i].radius * 2);
			}
		}
	}

	/**
	 * Returns the index of any object at the given point
	 */
	public int checkMouseClick(int mX, int mY){
		for (int i = 0; i < objectsArray.length; i++){
			if(objectsArray[i] != null){
				if(mX >= objectsArray[i].x - objectsArray[i].radius && mX <= objectsArray[i].x + objectsArray[i].radius){
					if(mY >= objectsArray[i].y - objectsArray[i].radius && mY <= objectsArray[i].y + objectsArray[i].radius){
						return i; 
					}
				}
			}
		}
		return -1;
	}

	/**
	 * Provides Environmental Constants
	 */
	static class Environment{
		static float scale = 10;  //10px = 1m
		static float screenSizeX = 640;
		static float screenSizeY = 640;
		static float gravity = -9.8f;

		public static void boundingArea(Object obj){
			// EDGES OF THE SCREEN
			if(obj.x > screenSizeX - obj.radius && obj.v.X > 0) {
				obj.v.X *= -obj.cR;
				obj.a.X *= -obj.cR;
				obj.x = screenSizeX - obj.radius;
			}
			if(obj.x < obj.radius && obj.v.X < 0) {
				obj.v.X *= -obj.cR;
				obj.a.X *= -obj.cR;
				obj.x = obj.radius;
			}
			if(obj.y > screenSizeY - obj.radius && obj.v.Y > 0) {
				obj.v.Y *= -obj.cR;
				obj.y = screenSizeY - obj.radius;
			}
			if(obj.y < obj.radius && obj.v.Y < 0) {
				obj.v.Y *= -obj.cR;
				obj.y = obj.radius;
			}
		}
		
		public static void objectCollisions(Object[] objArray){
			for (int i = 0; i < objArray.length; i++){
				if(objArray[i] != null){  

				}
			}
		}

	}

	class Vector2D{
		public float X = 0.0f;
		public float Y = 0.0f;
	}

	class Object{

		public Drawable Drawable = new Drawable();

		private int lastTime = -1;



		public float x = 0;
		public float y = 0;

		public float radius = 0;

		public float mass = 10.0f; //Kilograms  

		public Vector2D v = new Vector2D();
		public Vector2D a = new Vector2D();

		public Vector2D f = new Vector2D(); //Force in Newtons
		public Vector2D ff = new Vector2D();

		public float cR = 0.0f;			//Coefficient of Restitution
		public float mu = 0.0f;//0.05f;

		public float displacement = 0;

		Object(){
			//Set acceleration due to gravity
			//a.Y = Environment.gravity * -1;

			f.Y = Environment.gravity * mass;

		}

		public void update(){

			int deltaTime = millis() - lastTime;
			lastTime = millis();

			//Forces
			float fFriction = f.Y * mu * Functions.sign(a.X) * -1;	//Friction opposes acceleration

			Log.i("Physics", fFriction + "");

			//Acceleration
			a.X = (f.X - fFriction) / mass;
			a.Y = f.Y / mass;

			//Velocity
			v.X += (a.X * Environment.scale * deltaTime)/1000;
			v.Y += (a.Y * Environment.scale * deltaTime)/1000 * -1;	//Compensate for Processing's drawing system

			//v.X *= mu;

			//Position
			x += ((v.X * Environment.scale * deltaTime)/1000);    
			y += ((v.Y * Environment.scale * deltaTime)/1000); 


		}
	}


	class Drawable{
		int Stroke = 0xffFFCC00;
		int Fill = 0xffFFCC00; 
	}

}
