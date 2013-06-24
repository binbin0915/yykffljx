package com.AlertDialogExample;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class AlertDialogExample extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//������Դ�ļ�
		final Button btnQuit = (Button) findViewById(R.id.okCancelBut);//��ȡ��Դ�ļ��е�Button
		btnQuit.setOnClickListener(new Button.OnClickListener() {//btnQuit�����¼�
			public void onClick(View v) {
				new AlertDialog.Builder(AlertDialogExample.this)//����AlertDialog.Builder����
						.setTitle("����")//���ñ���
						.setMessage("ȷ��Ҫ�˳���")//������ʾ��Ϣ
						.setIcon(R.drawable.icon)//���ñ�������ʾ��ͼ��
						.setPositiveButton("ȷ��",new DialogInterface.OnClickListener(){//���ȷ����ť���¼�
									public void onClick(DialogInterface dialog,int whichButton) {
										setResult(RESULT_OK);
										finish();
									}
								})
						.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {//���ȡ����ť���¼�
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).show();//��ʾ�Ի���
			}
		});
		final Button btnTravels = (Button) findViewById(R.id.listBut);//��ȡ��Դ�ļ��е� Button
		btnTravels.setOnClickListener(new Button.OnClickListener() {//btnTravels�����¼�
			public void onClick(View v) {
				new AlertDialog.Builder(AlertDialogExample.this)//����AlertDialog.Builder����
						.setTitle("���뵼����ϵ��")//���ñ���
						.setItems(R.array.arrcontent,new DialogInterface.OnClickListener() {//���öԻ�������ʾ��list��������ʾ���ݴ�ŵ�arrcontent������
									public void onClick(DialogInterface dialog,int whichcountry) {//whichcountry:��ǰ�����Button��id�����б���±�
										String[] travelcountries = getResources().getStringArray(R.array.arrcontent);//����Դ�ļ��е�arrcontentת��Ϊ����
										//����б�Ի����е�ÿһ����ĶԻ���
										new AlertDialog.Builder(AlertDialogExample.this)
												.setMessage("�����ˣ�"	+ travelcountries[whichcountry])//������ʾ��Ϣ
												.setNeutralButton("ȡ��",new DialogInterface.OnClickListener() {//���õ��ȡ����ť�¼�
															public void onClick(DialogInterface dialog,int whichButton) {
															}
														}).show();//��ʾ�Ի���
									}})
						.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										
									}
								}).show();
			}
		});
		
		Button button=(Button)findViewById(R.id.defineBut);//��ȡ��Դ�ļ��е�Button
		button.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//����Dialog���� ���캯���еĲ���һ��Context,���������Ի������ʽ��������style.xml��
					final Dialog dialog = new Dialog(AlertDialogExample.this,R.style.dialog);
					dialog.setContentView(R.layout.myalertdialog);//���öԻ���Ĳ�����Դ�ļ�
					final ImageButton cancelBut=(ImageButton) dialog.findViewById(R.id.cancel);//��ȡ�Ի����ϵİ�ť
					cancelBut.setOnClickListener(new OnClickListener(){//"ȡ��"��ť�ĵ����¼�
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub							
							dialog.hide();//���ضԻ���
						}
						 
					 });
					 dialog.show();
					 
				}
				
			});
		
	}
}