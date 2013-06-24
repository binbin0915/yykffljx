package com.WindowExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class WindowExample extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        this.setTitle("progressing...."); //���ñ������ϵ�����
        Window window=this.getWindow();	//��ȡ��ǰActivity��Window
        this.requestWindowFeature(Window.FEATURE_PROGRESS); //��������ʾ������        
        setContentView(R.layout.main);//���õ�ǰ����Ĳ��ֹ����ļ�
        this.setProgressBarVisibility(true);//���ý������ɼ�
        this.setProgress(1800);//���õ�һ�������ĳ���
        this.setSecondaryProgress(8000);//���õڶ��������ĳ���
        
    }
}