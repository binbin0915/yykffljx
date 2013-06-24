package com.WindowExample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
 
public class MyService extends Service
{
	static final String action1="Broadcast_action1";
	Intent it;
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	@Override
	public void onCreate()   
	{
		super.onCreate();
		new Thread(){
			@Override
			public void run(){
				while(true){
					it = new Intent(action1);
					sendBroadcast(it); 
					try{
						Thread.sleep(200);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		this.stopService(it);
	}
}