package com.example.aayush.myapplication;

/**
 * Created by Aayush on 12-06-2015.
 */

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
//import android.os.SystemClock;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;

import java.nio.FloatBuffer;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Square mSquare;


    private static final String TAG = "MyGLRenderer";
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    public volatile float mAngle;


    float zoom=1;
    float ratio;



    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        // initialize a square

        mSquare = new Square();


    }
    private float[] mRotationMatrix = new float[16];

    //int s = ((MyApplication) this.getApplication()).getSomeVariable();
    public void onDrawFrame(GL10 unused) {

        float[] scratch = new float[16];
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
       Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        //int s = ((MyApplication) this.getApplication()).getSomeVariable();

        //if(s==0)
        Matrix.setRotateM(mRotationMatrix, 0, angle,0 , 0,-1f);

        Matrix.frustumM(mProjectionMatrix, 0, -ratio*zoom, ratio*zoom, -1*zoom, 1*zoom, 3, 7);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
        mSquare.draw(scratch);


        // Draw shape
        //mTriangle.draw(mMVPMatrix);

    }

    ;

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

         ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }



    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

    public void setYValue(float angle) {
        zoom = angle;
        zoom/=100;
    }


}
