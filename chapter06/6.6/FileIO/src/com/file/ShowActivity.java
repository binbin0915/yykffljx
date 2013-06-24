package com.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends Activity {
	private TextView nameTextView = null;// �ļ����Ƶ�TextView�������
	private TextView contentTextView = null;// �ļ����ݵ�TextView�������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_file);// Ϊ��ǰ���Activity����һ����ͼ
		nameTextView = (TextView) findViewById(R.id.showName);// ʵ�����ļ����Ƶ�TextView�������
		contentTextView = (TextView) findViewById(R.id.showContent);// ʵ�����ļ����ݵ�TextView�������
		Intent intent = getIntent();// ��ȡIntent
		String filePath = intent.getCharSequenceExtra("filePath").toString();// ��ȡ�ļ�·��
		String name = new File(filePath).getName();//��ȡ�ļ���
		String content = readFile(filePath);//���ö�ȡ�ļ���������ȡ�ļ�����

		nameTextView.setText("�ļ����ƣ�" + name);//�����ļ�����
		contentTextView.setText("�ļ����ݣ�" + content);//�����ļ�����
	}

	/**
	 * ���ļ�
	 * @param filePath �ļ�·��
	 * @return
	 */
	public String readFile(String filePath) {
		try {
			File file = new File(filePath);//ʵ����File����
			InputStream inputStream = new FileInputStream(file);//ʵ����InputStream����
			byte[] b = new byte[inputStream.available()];//inputStream.available()��ȡ����������һ���������ÿ��Բ��������شӴ���������ȡ�����������Ĺ����ֽ���
			inputStream.read(b);//���������ж�ȡ�ֽڣ�������洢�ڻ��������� b ��
			inputStream.close();//�رմ����������ͷ����������������ϵͳ��Դ
			return new String(b);//�����ַ�������
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
