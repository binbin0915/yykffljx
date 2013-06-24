package com.LayoutParams1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LayoutParams1 extends Activity {
	/** Called when the activity is first created. */
	LinearLayout liearLayout;											 //����LinearLayout����
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);									 //����main.xml�����ļ�
		
		liearLayout = (LinearLayout) this.findViewById(R.id.layout);	 //��ȡmain.xml��idΪlayout��liearLayout����
		TextView txtFont = new TextView(this);//����һ��TextView����
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(//����ViewGroup.LayoutParams����
				ViewGroup.LayoutParams.FILL_PARENT,						 //���ò��ֲ�������Ŀ��
				ViewGroup.LayoutParams.WRAP_CONTENT);					 //���ò��ֲ�������ĸ߶�
		txtFont.setLayoutParams(layoutParams);							 //��txtFont��layoutParams����ָ����Ⱥ͸߶Ȳ���
		txtFont.setText("ͨ������ʵ�ֲ���ʾ��1");							 //����txtFont������ʾ������
		txtFont.setTextColor(Color.BLACK);								 //����������ɫΪ��ɫ
		liearLayout.addView(txtFont);									 //��txtFont��ӵ�liearLayout������		
	}
}