package com.opengles.android.demo.shape;


import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.opengles.android.demo.utils.ShaderUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

/**
 * Created by admin on 2017/6/2.
 */

public class Circle extends IShape {

    //圆形就是正N边形
    private static int N =360;

    private static float radius = 0.3f;

    private float[] vertexs;


    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;

    private Context context;
    private int program;
    private static float[] color = {1.0f, 0.0f, 0.0f, 1.0f};

    public Circle(Context context) {
        this.context = context;
        initVertexs();
    }

    private void initVertexs() {
        ArrayList<Float> floats = new ArrayList<>();
        float perAngle = 360 / N;
        floats.add(0.0f);
        floats.add(0.0f);
        floats.add(0.0f);
        for (float i = 0; i <= 360; i += perAngle) {
            floats.add((float) (radius * Math.sin(i * (Math.PI / 180f))));
            floats.add((float) (radius * Math.cos(i * (Math.PI / 180f))));
            floats.add(0.0f);
        }
        vertexs = new float[floats.size()];
        for (int i = 0; i < floats.size(); i++) {
            vertexs[i] = floats.get(i);
        }
    }

    @Override
    public void init() {
        vertexBuffer = ByteBuffer.allocateDirect(vertexs.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertexs);
        vertexBuffer.position(0);

        int vShader = ShaderUtils.loadShaderFormAssets(context, GLES20.GL_VERTEX_SHADER, "shape/base_vertex.glsl");
        int fShader = ShaderUtils.loadShaderFormAssets(context, GLES20.GL_FRAGMENT_SHADER, "shape/base_fragment.glsl");
        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vShader);
        GLES20.glAttachShader(program, fShader);
        GLES20.glLinkProgram(program);
    }

    @Override
    public void draw() {
        GLES20.glUseProgram(program);
        int positionLocation = GLES20.glGetAttribLocation(program, "a_position");
        int colorLocation = GLES20.glGetAttribLocation(program, "a_color");

        GLES20.glEnableVertexAttribArray(positionLocation);
        GLES20.glVertexAttrib4fv(colorLocation, color, 0);
        GLES20.glVertexAttribPointer(positionLocation, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexs.length / 3);
        GLES20.glDisableVertexAttribArray(positionLocation);
    }
}
