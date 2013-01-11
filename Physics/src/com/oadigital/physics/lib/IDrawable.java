package com.oadigital.physics.lib;

import android.graphics.Canvas;

import com.oadigital.physics.lib.objects.BaseObject;

/**
 * Interface that allows implementing Objects to receive and handle draw() requests
 */
public interface IDrawable {
	public abstract void draw(Canvas canvas, BaseObject obj);
}
