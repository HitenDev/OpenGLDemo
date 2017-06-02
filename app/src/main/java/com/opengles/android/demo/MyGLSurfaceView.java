package com.opengles.android.demo;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by admin on 2017/6/1.
 */

public class MyGLSurfaceView extends GLSurfaceView{
    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(new MyGLRender(getContext()));
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
