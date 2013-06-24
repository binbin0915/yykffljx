package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyActivity extends Activity {

	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private MyView myView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);  
		myView=(MyView)this.findViewById(R.id.sview);
   
        button1=(Button)this.findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				myView.type(1);
			}
		});
        
        button2=(Button)this.findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				myView.type(2);
			}
		});
        
        button3=(Button)this.findViewById(R.id.button3);
        button3.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				myView.type(3);
			}
		});
        
        button4=(Button)this.findViewById(R.id.button4);
        button4.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				myView.type(0);
			}
		});
	}
}