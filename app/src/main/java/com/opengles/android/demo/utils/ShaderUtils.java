package com.opengles.android.demo.utils;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by admin on 2017/6/1.
 */

public class ShaderUtils {

    private static final String TAG = "ShaderUtils";

    public static int loadShader(int shaderType, String shaderCode) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shaderCode != null && !"".equals(shaderCode)) {
            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader:" + shaderCode);
                Log.e(TAG, "GLES20 Error:" + GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
            }
        }
        return shader;
    }

    public static int loadShaderFormAssets(Context context, int shaderType, String file) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream open = context.getAssets().open(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadShader(shaderType, stringBuilder.toString());
    }
}
