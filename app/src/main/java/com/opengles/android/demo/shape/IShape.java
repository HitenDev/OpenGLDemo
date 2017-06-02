package com.opengles.android.demo.shape;

import android.opengl.GLES20;

import javax.microedition.khronos.opengles.GL10;

/**
 * 形状基类
 * Created by admin on 2017/6/1.
 */

public abstract class IShape {

    public abstract void init();

    public abstract void draw();

    public void onSurfaceChanged(int width, int height) {
    }

}
