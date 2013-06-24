package com.WindowsSample;

import android.os.Looper;

public class Lopper extends Thread {
	SampleActivity spa;
	boolean flag=true;
	public Lopper(SampleActivity spa)
	{
		this.spa=spa;
	}
	@Override
    public void run() {
        Looper.prepare();
        while(flag){
        	spa.hd.sendEmptyMessage(0);
        }
        Looper.loop();
        spa.button.setText("更改Button的名称");
    }
}
