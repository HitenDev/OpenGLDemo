package com.opengles.android.demo.shape;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.opengles.android.demo.utils.ShaderUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/5.
 */

public class Ball extends IShape {
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform mat4 vMatrix;" +
                    "varying  vec4 vColor;" +
                    "void main() {" +
                    "  gl_Position = vMatrix*vPosition;" +
                    "  float color;" +
                    "  if(vPosition.z>0.0){" +
                    "      color=vPosition.z;" +
                    "  }else{" +
                    "      color=-vPosition.z;" +
                    "  }" +
                    "  vColor=vec4(color,color,color,1.0);" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    private static final float step = 2.0f;
    private float[] vertexs;


    private FloatBuffer vertexBuffer;
    private int program;
    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    public Ball() {
        createPosition();
    }

    private void createPosition() {
        List<Float> data = new ArrayList<>();
        float r1, r2;
        float y1, y2;
        float sin, cos;
        for (float i = -90; i < 90 + step; i += step) {
            r1 = (float) Math.cos(i * Math.PI / 180.0);
            r2 = (float) Math.cos((i + step) * Math.PI / 180.0);
            y1 = (float) Math.sin(i * Math.PI / 180.0);
            y2 = (float) Math.sin((i + step) * Math.PI / 180.0);
            float step2 = 2 * step;
            for (float j = 0.0f; j < 360 + step; j += step2) {
                cos = (float) Math.cos(j * Math.PI / 180.0);
                sin = -(float) Math.sin(j * Math.PI / 180.0);
                data.add(r2 * cos);
                data.add(y2);
                data.add(r2 * sin);
                data.add(r1 * cos);
                data.add(y1);
                data.add(r1 * sin);
            }
        }

        vertexs = new float[data.size()];
        for (int i = 0; i < data.size(); i++) {
            vertexs[i] = data.get(i);
        }

    }

    @Override
    public void init() {
        vertexBuffer = ByteBuffer.allocateDirect(vertexs.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertexs);
        vertexBuffer.position(0);

        int vShader = ShaderUtils.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fShader = ShaderUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vShader);
        GLES20.glAttachShader(program, fShader);
        GLES20.glLinkProgram(program);
    }

    @Override
    public void draw() {
        GLES20.glUseProgram(program);
        int matrixLocation = GLES20.glGetUniformLocation(program, "vMatrix");

        GLES20.glUniformMatrix4fv(matrixLocation, 1, false, mMVPMatrix, 0);

        int positionLocation = GLES20.glGetAttribLocation(program, "vPosition");

        GLES20.glEnableVertexAttribArray(positionLocation);
        GLES20.glVertexAttribPointer(positionLocation, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexs.length / 3);
        GLES20.glDisableVertexAttribArray(positionLocation);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
//计算宽高比
        float ratio = (float) width / height;
        //设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 20);
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 1.0f, -10.0f, -4.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }
}
