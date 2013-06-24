package com.HandlerExample;

import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class HandlerExample extends Activity {
	private TextView myTextView;
	private int count = 0;

	//自定义Handler信息代码，用以作为识别事件处理 
	protected static final int Start_NOTIFIER = 0x101;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//加载main.xml布局文件
		myTextView=(TextView) this.findViewById(R.id.mess);//获取TextView对象
		Timer timer = new Timer();//创建Timer时钟对象
		timer.scheduleAtFixedRate(new MyShedule(), 1, 6000); //每隔6秒钟执行MyShedule
	}

	private class MyShedule extends TimerTask {
		@Override
		public void run() {
			Message message=new Message(); //创建Message对象
			message.what=HandlerExample.Start_NOTIFIER;//用户自定义消息代码，以便收件人找到讯息是什么
			handler.sendMessage(message);//向Handler发送消息
		}
	}
	//创建Handler对象，通过实现handleMessage方法，接收信息
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {//子类必须实现该方法才可以接收到信息
			switch(msg.what){//判断消息代码值
			case HandlerExample.Start_NOTIFIER:
				myTextView.setText(new Integer(count).toString());//修改TextView显示的文字
				count++;
				break;
			}
		}
	};
}