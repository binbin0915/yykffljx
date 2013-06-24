package com.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends Activity {
	private EditText nameEditText = null;// �ļ����Ƶ�EditText�������
	private EditText contentEditText = null;// �ļ����ݵ�EditText�������
	private Button createFileButton = null;// �����ļ���ť��Button�������
	private String dirPath = "";// �ļ���/дָ��Ŀ¼

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_file);// Ϊ��ǰ���Activity����һ����ͼ

		nameEditText = (EditText) findViewById(R.id.createName);// ʵ����EditText�������
		contentEditText = (EditText) findViewById(R.id.createContent);// ʵ����EditText�������
		createFileButton = (Button) findViewById(R.id.createFileButton);// ʵ����Button�������

		Intent intent = getIntent();// ��ȡIntent
		dirPath = intent.getCharSequenceExtra("dirPath").toString();// ��ȡ�ļ����Ŀ¼
		createFileButton.setOnClickListener(new Button.OnClickListener() {// ΪButton��ӵ������

					@Override
					public void onClick(View arg0) {
						String fileName = nameEditText.getText().toString();// ��ȡ�����ļ�����
						String fileContent = contentEditText.getText()
								.toString();// ��ȡ�����ļ�����
						boolean isTrue = writeFile(fileName, fileContent);// ����д�ļ�����
						if (isTrue) {// �ж��ļ��Ƿ񴴽��ɹ�
							showMessage("�����ļ��ɹ�");// ������Ϣ��������������д�ļ��ɹ���Ϣ��ʾ
						} else {
							showMessage("�����ļ�ʧ��");// ������Ϣ��������������д�ļ�ʧ����Ϣ��ʾ
						}
					}
				});
	}

	/**
	 * д�ļ�
	 * 
	 * @param fileName
	 *            �ļ�����
	 * @param fileContent
	 *            �ļ�����
	 * @return
	 */
	public boolean writeFile(String fileName, String fileContent) {
		try {
			File file = new File(dirPath + File.separator + fileName + ".txt");// �����ļ�·��������.txt�ı��ļ�
			OutputStream outputStream = new FileOutputStream(file);// ʵ����OutputStream����
			byte[] contents = fileContent.getBytes();// ���ַ�������ת��Ϊbyte����
			outputStream.write(contents);// �� contents.length ���ֽڴ�ָ���� byte ����д��������
			outputStream.flush();// ˢ�´��������ǿ��д�����л��������ֽ�
			outputStream.close();// �رմ���������ͷ�������йص�����ϵͳ��Դ
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ��Ϣ��ʾ����ת����ҳ
	 * 
	 * @param message
	 *            ��Ϣ����
	 */
	public void showMessage(String message) {
		// ������Ϣ��ʾ��AlertDialog����
		// setTitle��������Ϣ����
		// setMessage��������Ϣ����
		// setNegativeButton��������Ϣȷ����ť�������尴ť����¼�
		// show����ʾ��
		new AlertDialog.Builder(CreateActivity.this).setTitle("��ʾ��Ϣ")
				.setMessage(message).setNegativeButton("ȷ��",
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								Intent intent = new Intent();// ʵ����Intent
								intent.setClass(CreateActivity.this,
										MainActivity.class);// ָ��intent������������
								startActivity(intent);// �����µ�Activity
							}
						}).show();
	}
}
