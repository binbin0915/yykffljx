package com.call;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CallActivity extends Activity {
	private EditText editText = null;//�绰����EditText�������
	private Button callButton = null;//ֱ�Ӳ���ťButton�������
	private Button dialButton = null;//����������水ťButton�������

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		editText = (EditText) findViewById(R.id.phone_number);//ʵ�����绰����EditText�������
		callButton = (Button) findViewById(R.id.phone_call);//ʵ����ֱ�Ӳ���ťButton�������
		dialButton = (Button) findViewById(R.id.phone_dial);//ʵ��������������水ťButton�������
		callButton.setOnClickListener(new Button.OnClickListener() {//���Button��ť�������
			
			@Override
			public void onClick(View arg0) {
				call();// ����ֱ�Ӵ�绰�ķ���
			}
		});
		dialButton.setOnClickListener(new Button.OnClickListener() {//���Button��ť�������

			@Override
			public void onClick(View arg0) {
				dial();// ��������һ���������ķ���
			}
		});
	}

	/**
	 * ֱ�Ӵ�绰�ķ���
	 */
	public void call() {
		String data = "tel:" + editText.getText();// �绰��������ַ���
		Uri uri = Uri.parse(data);// ���ַ���ת��ΪUriʵ��
		Intent intent = new Intent();// ʵ����Intent
		intent.setAction(Intent.ACTION_CALL);// ����Intent��Action����
		intent.setData(uri);// ����Intent��Data����
		startActivity(intent);// ����Activity
	}

	/**
	 * ����һ���������ķ���
	 */
	public void dial() {
		String data = "tel:" + editText.getText();// �绰��������ַ���
		Uri uri = Uri.parse(data);// ���ַ���ת��ΪUriʵ��
		Intent intent = new Intent();// ʵ����Intent
		intent.setAction(Intent.ACTION_DIAL);// ����Intent��Action����
		intent.setData(uri);// ����Intent��Data����
		startActivity(intent);// ����Activity
	}
}