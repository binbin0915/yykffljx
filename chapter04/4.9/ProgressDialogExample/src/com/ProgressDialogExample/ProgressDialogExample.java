package com.ProgressDialogExample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProgressDialogExample extends Activity {
	Button circleBut;
	Button longBut;
	ProgressDialog myDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); // ����main.xml

		circleBut = (Button) this.findViewById(R.id.circleButt);// ��ȡcircleButt��Բ�ν���������ť
		longBut = (Button) this.findViewById(R.id.longButt);// ��ȡlongButt�����ν���������ť

		circleBut.setOnClickListener(new OnClickListener() {// Ϊ��Բ�ν���������ӵ����¼�

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						myDialog = new ProgressDialog(
								ProgressDialogExample.this);// ����ProgressDialog����
						myDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// ���ý���������ʽΪԲ����ʽ
						myDialog.setTitle("��ʾ");// ���ý������ı�����Ϣ
						myDialog.setMessage("���ݼ�����,���Ժ�...");// ���ý���������ʾ��Ϣ
						myDialog.setIcon(R.drawable.android);// ��ָ��������ͼ��
						myDialog.setIndeterminate(false);// ���ý������Ƿ�Ϊ����ȷ
						myDialog.setCancelable(true);// ���ý������Ƿ񰴷��ؼ�ȡ��
						// Ϊ��������ӡ�ȷ������ť����Ϊ�ð�ť��ӵ����¼�
						myDialog.setButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										myDialog.cancel();// ����������
									}

								});
						myDialog.show();// ��ʾ������
					}

				});

		longBut.setOnClickListener(new OnClickListener() {// Ϊ�����ν���������ӵ����¼�
					int count = 0;// �洢��������ǰ����ֵ����ʼֵΪ0

					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
						myDialog = new ProgressDialog(
								ProgressDialogExample.this);
						myDialog
								.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						myDialog.setTitle("��ʾ");
						myDialog.setMessage("���ݼ�����,���Ժ�...");
						myDialog.setIcon(R.drawable.android);
						myDialog.setIndeterminate(false);// ���ý������Ƿ�Ϊ����ȷ
						myDialog.setCancelable(true);
						myDialog.setMax(200);// ���ý������Ľ������ֵ
						myDialog.setProgress(0);// ���õ�ǰĬ�Ͻ���Ϊ0
						myDialog.setSecondaryProgress(100);// ���õڶ���������ֵΪ100
						// Ϊ��������ӡ�ȡ������ť����Ϊ�ð�ť��ӵ����¼�
						myDialog.setButton("ȡ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										myDialog.cancel();
									}

								});
						myDialog.show();// ��ʾ������

						new Thread() {// �����̣߳���̬�ı䵱ǰ��������ֵ
							public void run() {

								while (count <= 200) {
									myDialog.setProgress(count++);// ���õ�ǰ�������Ľ���ֵ
									try {
										Thread.sleep(100);// ��ͣ0.1��
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
								}
								

							}
						}.start();// �����߳�
					}

				});
	}
}