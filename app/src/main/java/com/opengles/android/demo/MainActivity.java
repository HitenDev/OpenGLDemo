package com.opengles.android.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by admin on 2017/6/1.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyGLSurfaceView myGLSurfaceView = new MyGLSurfaceView(this);
        setContentView(myGLSurfaceView);
    }
}
