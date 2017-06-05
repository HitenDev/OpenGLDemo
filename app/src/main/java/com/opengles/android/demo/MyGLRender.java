package com.opengles.android.demo;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.opengles.android.demo.shape.Ball;
import com.opengles.android.demo.shape.Circle;
import com.opengles.android.demo.shape.Cube;
import com.opengles.android.demo.shape.IShape;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * GLSurfaceView renderer
 * Created by admin on 2017/6/1.
 */

public class MyGLRender implements GLSurfaceView.Renderer {

    private Context context;
    private IShape shape;

    public MyGLRender(Context context) {
        this.context = context;
        shape = new Ball();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        shape.init();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        shape.onSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        shape.draw();
    }
}
