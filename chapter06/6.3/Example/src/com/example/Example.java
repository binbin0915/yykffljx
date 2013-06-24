package com.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Example extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example);
		String fileName="test.txt";//�ļ�����
		String content="��������abcd123";//ָ����������
		String result="";//��ȡ�ļ����ص�String����
		boolean istrue = writeFile(fileName, content);//����д�����ݵ��ļ��ķ���
		if (istrue) {//�ж�д�������Ƿ�ɹ�
			result+=fileName+"�����ɹ�\n\r";
		}else {
			result+=fileName+"����ʧ��\n\r";
		}
		result+=readFile(fileName);//���ö�ȡ�ļ���������ȡ����String����
		TextView textView = (TextView) findViewById(R.id.textView);//��ʼ��TextView
		textView.setText(result);//�Ѷ�ȡ�ļ��ķ��ؽ����ʾ��TextView��
	}

	/**
	 * Ϊָ�����ļ���д��ָ��������
	 * @param fileName �ļ�����
	 * @param content ָ����������
	 * @return boolean����(true��ʾ����д��ɹ���false��ʾ����д��ʧ��)
	 */
	public boolean writeFile(String fileName, String content) {
		try {
			FileOutputStream fOutputStream = openFileOutput(fileName,
					MODE_PRIVATE);//����FileOutputStream����MODE_PRIVATE��Ĭ��ģʽ
			byte[] buffer = content.getBytes();//��д����ַ���ת����byte����
			fOutputStream.write(buffer);//��byte����д���ļ�
			fOutputStream.flush();//��ջ���
			fOutputStream.close();//�ر�FileOutputStream����
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ��ȡָ���ļ������ݣ�������String����
	 * @param fileName �ļ�����
	 * @return String����
	 */
	public String readFile(String fileName) {
		String result = "";//�����ַ������
		try {
			FileInputStream fInputStream = openFileInput(fileName);//����FileInputStream����
			int len = fInputStream.available();//��ȡ�ļ��ĳ���
			byte[] buffer = new byte[len];//�����ļ����ȴ�С��byte����
			fInputStream.read(buffer);//���ļ���д��byte����
			result = new String(buffer);//��byte����ת��ΪString����
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;//�����ַ������
	}
}