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

	//�Զ���Handler��Ϣ���룬������Ϊʶ���¼����� 
	protected static final int Start_NOTIFIER = 0x101;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//����main.xml�����ļ�
		myTextView=(TextView) this.findViewById(R.id.mess);//��ȡTextView����
		Timer timer = new Timer();//����Timerʱ�Ӷ���
		timer.scheduleAtFixedRate(new MyShedule(), 1, 6000); //ÿ��6����ִ��MyShedule
	}

	private class MyShedule extends TimerTask {
		@Override
		public void run() {
			Message message=new Message(); //����Message����
			message.what=HandlerExample.Start_NOTIFIER;//�û��Զ�����Ϣ���룬�Ա��ռ����ҵ�ѶϢ��ʲô
			handler.sendMessage(message);//��Handler������Ϣ
		}
	}
	//����Handler����ͨ��ʵ��handleMessage������������Ϣ
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {//�������ʵ�ָ÷����ſ��Խ��յ���Ϣ
			switch(msg.what){//�ж���Ϣ����ֵ
			case HandlerExample.Start_NOTIFIER:
				myTextView.setText(new Integer(count).toString());//�޸�TextView��ʾ������
				count++;
				break;
			}
		}
	};
}