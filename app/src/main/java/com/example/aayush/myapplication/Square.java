package com.example.aayush.myapplication;

import android.opengl.GLES20;
import java.io.BufferedReader;
import java.io.File;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;


/**
 * Created by Aayush on 12-06-2015.
 */

import android.opengl.GLES20;
import android.os.Environment;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.glEnableClientState;
import static android.opengl.GLES10.glVertexPointer;

public class Square {

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    // Use to access and set the view transformation
    private int mMVPMatrixHandle;

    //public static float colors[];
    //public FloatBuffer colorBuffer;
    private FloatBuffer vertexBuffer;
    private IntBuffer drawListBuffer;
    File sdcard = Environment.getExternalStorageDirectory();

    //Get the text file
    float color[] ={(float) 0.232629, (float) 0.970107, (float) -0.0691169,1 };
    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;


/*
    static float squareCoords[] = {
            -0.4f,  0.4f, 0.4f,   // top left
            -0.4f, -0.4f, 0.4f,   // bottom left

            0.4f,  0.4f, 0.4f,
            0.4f, -0.4f, 0.4f,
            -0.4f,  0.4f, -0.4f,   // top left
            -0.4f, -0.4f, -0.4f,   // bottom left
             // bottom right
            0.4f,  0.4f, -0.4f,
            0.4f, -0.4f, -0.4f };

    private int drawOrder[] = {  0, 1, 2, 1, 2, 3,4,5,6,6,5,7,4,5,1,1,4,0,7,3,2,7,6,2,0,4,6,0,2,6,5,7,1,1,3,7}; // order to draw vertices

*/
    static float[] squareCoords ;
    private int drawOrder[];


    //try
    {



        File file = Environment.getExternalStorageDirectory();
        File textFile = new File(file.getAbsolutePath()+File.separator+"hand.ply");

       BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(textFile));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        String line;

        int j=0,z=0;
        long x=0;

        int u=0,l=0;
        //squareCoords= new float[981969];
        //drawOrder= new int[1963998];
        //color=new float[981969*4/3];

        String[] strArray;

        int m=13,n;
        try {
            while((line=br.readLine())!=null){
                x++;
                strArray = line.split(" ");
                u++;
                if((u<m+13)) {
                    if(x==3) {
                        m = Integer.parseInt(strArray[2]);
                        squareCoords= new float[m*3];
                        //drawOrder= new int[m*3];
                    }
                    else if(x==10)
                    {
                        n = Integer.parseInt(strArray[2]);
                        drawOrder= new int[n*3];
                        //drawOrder= new int[3];

                    }
                    else if (x > 12)
                        for (int i = 0; (i < strArray.length); i++) {
                            if (i < 3) {
                                squareCoords[j++] = (Float.parseFloat(strArray[i]))/10;
                                //drawOrder[j-1]=u-13;

                                }
                            //else if(i<strArray.length)
                                //color[l++]=(Float.parseFloat(strArray[i]));
                            //else
                              //  color[l++]=1;
                        }
                }
                else {
                    for (int i = 0; (i < strArray.length ); i++) {
                        if (i>0) {
          //                  drawOrder[z++] = Integer.parseInt(strArray[i]);
                            //drawOrder[0] = Integer.parseInt(strArray[i]);
                        }
                    }
                //drawOrder[0]=1;
                    }

            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }/*
        //      myAwesomeTextViewer.setText(squareCoords[--j]+"");
        try {
            br.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }*/
    }



    private final int mProgram;
    public Square() {


        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
       // ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
         //       drawOrder.length * 4);
        //dlb.order(ByteOrder.nativeOrder());
        //drawListBuffer = dlb.asIntBuffer();
        //drawListBuffer.put(drawOrder);
        //drawListBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
    }

    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex


    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // Draw the triangle


        // Disable vertex array
        //GLES20.glDisableVertexAttribArray(mPositionHandle);
        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        ;
        // Draw the triangle
       /* GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_INT, drawListBuffer);*/

        GLES20.glDrawArrays(GL10.GL_POINTS, 0, squareCoords.length);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}

