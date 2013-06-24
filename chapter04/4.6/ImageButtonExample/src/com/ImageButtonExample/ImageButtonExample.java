package com.ImageButtonExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.ImageButton;

public class ImageButtonExample extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageButton imgBut2=(ImageButton)this.findViewById(R.id.imgbut2); //��ȡXML�е�ImageButton����
        //������ϰ��°���ʱ�������¼������������·����ѡ��ť
        imgBut2.setOnKeyListener(new OnKeyListener(){
        	
        	/**
        	 * ��������ϰ��¼��̰���ʱ�����÷���
        	 * @param v:������ǰ�¼������
        	 * @param keyCode:�����µ��������ı���
        	 * @param event:KeyEvent����
        	 * @return
        	 */
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				//����ImageButton�ı���ͼƬ������ԭ��ΪsetImageDrawable(Drawable draw)
				//getResources():���ص�ǰӦ�ó�����µ�������Դ
				((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.pinkrose));
				setTitle(" "+keyCode);  //����ǰ������keyCode��ʾ���������У�������Թ۲�
				
				// TODO Auto-generated method stub
				return false;
			}
        	
        });
        //�����¼������������ʱ�������¼�
        imgBut2.setOnClickListener(new OnClickListener(){

        	/**
        	 * �������ʱ�����÷���
        	 * @param v:������ǰ�¼������
        	 * @return
        	 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.bluerose));
			}
        	
        });       
        //����ı��¼�����������㷢���仯ʱ�������¼�
        imgBut2.setOnFocusChangeListener(new OnFocusChangeListener(){
        	
        	 /**
        	 * ����Ľ��㷢���仯ʱ�����÷���
        	 * @param v:������ǰ�¼������
        	 * @param hasFocus:��ǰ����Ľ���״̬��true����ȡ���㣬false��ʧȥ����
        	 * @return
        	 */
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub				
				if(hasFocus){
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.redrose));
					setTitle("Focus  redrose");
				}else{
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.whiterose));
					setTitle("Focus  whilterose");
				}
				
			}
        	
        });
    }
}