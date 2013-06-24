package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MyActivity extends Activity{
	private MyView myView = null;
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.myView = new MyView(this);
        setContentView(myView);
        
        new Thread(new Runnable() {
			public void run() {
				try {
					while(true){						
						Message m = new Message();
						viewHandler.sendMessage(m);
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
    }
	
	Handler viewHandler = new Handler() {
		public void handleMessage(Message msg) {
			myView.invalidate();
			super.handleMessage(msg);
		}
	};
}
