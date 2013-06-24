package com.example;

import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity{
	private MyView myView = null;
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.myView = new MyView(this);
        setContentView(myView);
    }
}
