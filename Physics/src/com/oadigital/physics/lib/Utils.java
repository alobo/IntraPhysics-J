package com.oadigital.physics.lib;
//Class to hold miscellaneous, useful functions
public class Utils {
	
	//Returns a number's sign.
	public static float getSign(float number){
		if(number < 0){
			return -1;
		}else if(number > 0){
			return 1;
		}else{
			return 0;
		}		
	}
}
