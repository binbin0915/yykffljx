package com.WindowsSample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SampleActivity extends Activity
{
	Handler hd=new Handler()
	{
		@Override
		public void handleMessage(Message msg)//÷ÿ–¥∑Ω∑®
		{
			switch(msg.what)
			{
				case 0:
					SampleActivity.tv.setTextSize(20);
					SampleActivity.tv.setText(ZMDUtil.next());
				break;
			}
		}
	};
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
					new Thread()
				    {
				    	@Override
				    	public void run()
				    	{ 
				    		while(true)
				    		{
				    			SampleActivity.this.hd.sendEmptyMessage(0);
				    		}
				    	}
				    }.start();
				}
        	}
        );
    }
}