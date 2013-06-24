package com.tyhj;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
/**
 * �ڶ���Logo���� 
 * */
public class Welcome1 extends Activity{
	/**
	 * ��дActivity�е�onCreate�ķ�����
	 * �÷�������Activity����ʱ��ϵͳ���ã���һ��Activity�������ڵĿ�ʼ��
	 * @param savedInstanceState������Activity��״̬�ġ�
	 *        Bundle���͵�������Map���͵��������ƣ�������key-value����ʽ�洢���ݵ�
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final Window win = getWindow();//���ص�ǰActivity��Window����,Window���и�����Android���ڵĻ������Ժͻ�������
		//����״̬��
		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//���ر�����
		this.setContentView(R.layout.welcome1);//���ò�����Դ
		ImageView iv=(ImageView)this.findViewById(R.id.wpic);//��ȡwelcome.xml��idΪwpic��ImageView���
		iv.setImageResource(R.drawable.welcome1);//����ImageView����ʾ����Դ
		tripListView();//��ʾ��·�б���
	}
		
	/**
	 * ��ӭ����,2���Ӻ��л�
	 * @param 
	 * @return
	 */
	public void tripListView() {
		new Thread(new Runnable() {//�����߳�
			public void run() {//ʵ��Runnable��run���������߳���
				try {
					Thread.sleep(2000);//��ӭ������ͣ2����
					Message m = new Message();//����Message����
					logHandler.sendMessage(m);//����Ϣ�ŵ���Ϣ������
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();//�����߳�
	}
	
	//ִ�н��յ�����Ϣ��ִ�е�˳���ǰ��ն��н��У����Ƚ��ȳ�
	Handler logHandler = new Handler() {
		public void handleMessage(Message msg) {
			tripList();//��ʾ��·�б����
		}
	};
	/**
	 * ��·�б����
	 * @param 
	 * @return
	 */
	public void tripList() {		
		Intent it=new Intent();//ʵ����Intent
		it.setClass(Welcome1.this, TripList.class);//����Class
    	startActivity(it);//����Activity
    	Welcome1.this.finish();//����Welcome1 Activity
	}
	/** 
	 * ���̰��������Ǵ����÷���
	 * @param keyCode�������µļ�ֵ�������� 
	 *        event�������¼��Ķ���
	 * @return
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==4 ){//���¡����ء�����
			android.os.Process.killProcess(android.os.Process.myPid());//�ó�����ȫ�˳�Ӧ��
		}
		return super.onKeyDown(keyCode, event);
	}
}
