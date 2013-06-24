package com.shared;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SharedData extends Activity {
	private String info = "user_info";// �����ļ���
	private String user = "";// �û���
	private String password = "";// ����
	private EditText userText = null;// �û���EditText�������
	private EditText passwordText = null;// ����EditText�������

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		userText = (EditText) findViewById(R.id.user);// ʵ�����û���EditText�������
		passwordText = (EditText) findViewById(R.id.password);// ʵ��������EditText�������
		getData();// ���û�ȡ�ļ��е�����

		Button button = (Button) findViewById(R.id.submit);// ʵ������¼Button�������
		// Ϊ��¼Button���������ӵ���¼�����
		button.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				user = userText.getText().toString().trim();// ��ȡ�û�������ֵ
				password = passwordText.getText().toString().trim();// ��ȡ����������ֵ
				saveData();// ���ñ������ݵ��ļ�
			}
		});
	}

	/**
	 * ��user(�û���),password(����)���浽�ļ�
	 */
	public void saveData() {
		SharedPreferences sPreferences = getSharedPreferences(info, 0);//��ȡSharedPreferences
		Editor editor = sPreferences.edit();//��SharedPreferences�ı༭״̬
		editor.putString("User", user);//�洢�û���
		editor.putString("Password", password);//�洢����
		editor.commit();//��������
		//��ʾ�û���¼�ɹ�������ȡ�ȱ�����ļ���
		new AlertDialog.Builder(SharedData.this).setTitle("��¼��Ϣ").setMessage(
				"�û� " + sPreferences.getString("User", "") + " ��¼�ɹ�")
				.setPositiveButton("ȷ��", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				}).show();
	}

	/**
	 * ��ȡ�ļ��е����ݣ�����ļ��д�����Ӧ�����ݣ��Ѹ����ݸ�ֵ����Ӧ��EditText�������
	 */
	public void getData() {
		SharedPreferences sPreferences = getSharedPreferences(info, 0);//��ȡSharedPreferences
		user = sPreferences.getString("User", "");//��ȡinfo�ļ���User��Ӧ������
		password = sPreferences.getString("Password", "");//��ȡinfo�ļ���Password��Ӧ������
		userText.setText(user);//��user��ֵ���û�EditText�������
		passwordText.setText(password);//��password��ֵ������EditText�������
	}
}