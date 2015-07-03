package com.example.aayush.myapplication;

import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class MainActivity extends Activity {

    private GLSurfaceView mGLView;
    int x=0;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
        Button b = new Button(this);


        //((MyApplication) this.getApplication()).setSomeVariable(1);
        b.setText("change axis");
        this.addContentView(b,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        b.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {


                test();
                //((MyApplication) this.getApplication()).setSomeVariable("foo");

            }
        });
    }
    public void test()
    {
        if(x<3)
        ((MyApplication) this.getApplication()).setSomeVariable(x++);
        else
            x=0;
    }
}