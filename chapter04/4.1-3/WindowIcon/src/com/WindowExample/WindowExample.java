package com.WindowExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class WindowExample extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        this.setTitle("Window Icon"); //���ñ������ϵ�����
        Window window=this.getWindow();	//��ȡ��ǰActivity��Window
        this.requestWindowFeature(Window.FEATURE_LEFT_ICON); //�����������ʾͼ��
        setContentView(R.layout.main);//���õ�ǰ����Ĳ��ֹ����ļ�
        this.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.webicon);//��������ͼ��
        
        
    }
}