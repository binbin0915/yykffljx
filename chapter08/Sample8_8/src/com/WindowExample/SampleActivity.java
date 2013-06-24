package com.WindowExample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SampleActivity extends Activity
{
	static TextView tv;
	static Button button;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.main);        
        button=(Button)findViewById(R.id.Button01);
        tv=(TextView)findViewById(R.id.textview01);
        
        button.setOnClickListener
        (
        	new OnClickListener(){
				@Override
				public void onClick(View v){
					Intent intent=new Intent(SampleActivity.this,MyService.class);
			        startService(intent);
				}
        	}
        );
    }
    @Override
    public boolean onKeyDown(int KeyCode,KeyEvent event)
    {
    	if(KeyCode==4)
    	{
    		System.exit(0);
    	}
    	return true;
    }
}