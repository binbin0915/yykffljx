package com.example;

import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity{
	private MySurfaceView mySurfaceView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView);       
    }
}