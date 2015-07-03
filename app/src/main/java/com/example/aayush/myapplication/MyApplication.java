package com.example.aayush.myapplication;

/**
 * Created by Aayush on 18-06-2015.
 */
import android.app.Application;

public class MyApplication extends Application {

    private int x;

    public int getSomeVariable() {
        return x;
    }

    public void setSomeVariable(int x) {
        this.x = x;
    }
}