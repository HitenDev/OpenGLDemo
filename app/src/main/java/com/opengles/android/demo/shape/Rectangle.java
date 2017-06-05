package com.opengles.android.demo.shape;

import android.content.Context;
import android.opengl.GLES20;

import com.opengles.android.demo.utils.ShaderUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by admin on 2017/6/1.
 */

public class Rectangle extends IShape {

    private Context context;
    private int program;

    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;

    // x y z r g b a
    private float[] vertexData = {
            -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
            -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f
    };
    static short index[]={
            0,1,3,2
    };

    public Rectangle(Context context) {
        this.context = context;
    }

    @Override
    public void init() {
        ByteBuffer cc= ByteBuffer.allocateDirect(index.length*2);
        cc.order(ByteOrder.nativeOrder());
        indexBuffer=cc.asShortBuffer();
        indexBuffer.put(index);
        indexBuffer.position(0);

        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertexData);
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
        GLES20.glEnableVertexAttribArray(colorLocation);

        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(positionLocation, 3, GLES20.GL_FLOAT, false, 7 * 4, vertexBuffer);
        vertexBuffer.position(3);
        GLES20.glVertexAttribPointer(colorLocation, 4, GLES20.GL_FLOAT, false, 7 * 4, vertexBuffer);

        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP,index.length, GLES20.GL_UNSIGNED_SHORT,indexBuffer);

        GLES20.glDisableVertexAttribArray(positionLocation);
        GLES20.glDisableVertexAttribArray(colorLocation);

    }

    @Override
    public void onSurfaceChanged(int width, int height) {

    }
}
