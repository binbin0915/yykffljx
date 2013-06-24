package com.ButtonEventExample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ButtonEventExample extends Activity {
	private LinearLayout layout;//����LinearLayout���ͱ���
	private Button redBut;//����Button���ͱ���
	private Button blueBut;//����Button���ͱ���
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//����main.xml�����ļ�
        layout=(LinearLayout)this.findViewById(R.id.layout);//ͨ��id��ȡ�����ļ��е�LinearLayout����
        redBut=(Button)this.findViewById(R.id.redBut);//ͨ��id��ȡ�����ļ��е�Button����
        blueBut=(Button)this.findViewById(R.id.blueBut);//ͨ��id��ȡ�����ļ��е�Button����
        
        redBut.setOnClickListener(new OnClickListener(){ //��ť�ĵ����¼�
			@Override
			public void onClick(View v) {//��ť�ĵ����¼�
				// TODO Auto-generated method stub
				layout.setBackgroundColor(Color.RED);//�޸�layout�ı�����ɫ
				((Button)v).setText("��������");		//�޸�Button��ť�ϵ�����
			}        	
        });
        blueBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layout.setBackgroundColor(Color.BLUE);
				((Button)v).setText("��������");
			}        	
        });
        
        
    }
}