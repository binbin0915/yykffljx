package com.example;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MyActivity extends Activity{
	private Button button;
	private ImageView view;
	private AnimationDrawable draw;
	
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  
        
        view=(ImageView)this.findViewById(R.id.view);
        draw=(AnimationDrawable)view.getDrawable();
        
        button=(Button)this.findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(draw.isRunning()){
					draw.stop();
				}
				draw.start();
			}
		});
        
    }
}