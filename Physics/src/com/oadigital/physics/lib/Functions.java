package com.oadigital.physics.lib;

public class Functions {
	public static float sign(final float x) {
	      if (Float.isNaN(x)) {
	          return Float.NaN;
	      }
	      return (x == 0.0F) ? 0.0F : (x > 0.0F) ? 1.0F : -1.0F;
	}
	
	public static int millis(){
		return (int)System.currentTimeMillis();
	}
}
