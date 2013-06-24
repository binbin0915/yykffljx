package com.sendsms;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendActivity extends Activity {
	private Button sendButton = null;// �������Ͱ�ťButton�������
	private EditText addressee = null;// �����ռ��˱༭��EditText�������
	private EditText message = null;// ������Ϣ���ݱ༭��EditText�������

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		sendButton = (Button) findViewById(R.id.send);// ʵ�������Ͱ�ťButton�������
		addressee = (EditText) findViewById(R.id.addressee);// ʵ�����ռ��˱༭��EditText�������
		message = (EditText) findViewById(R.id.message);// ʵ�����ռ��˱༭��EditText�������
		addressee.setText("����������˵ĵ绰����");//����Ĭ���ռ�����ʾ��Ϣ
		message.setText("�������������");//����Ĭ����Ϣ������ʾ��Ϣ
		//����ռ��˱༭�����¼�����
		addressee.setOnClickListener(new EditText.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				addressee.setText("");
			}
		});
		//�����Ϣ���ݱ༭�����¼�����
		message.setOnClickListener(new EditText.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				message.setText("");
			}
		});
		//��ӷ��Ͱ�ť����¼�����
		sendButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String strAddressee = addressee.getText().toString();// ��ȡ�ռ�����Ϣ
				String strMessage = message.getText().toString();// ��ȡ����������Ϣ
				if ("".equals(strAddressee)) {//�ж��ռ�����Ϣ�Ƿ�Ϊ��
					showMessage("�ռ�����Ϣ����Ϊ��");//������Ϣ��ʾ����
					return;
				}
				if ("".equals(strMessage)) {//�жϷ��������Ƿ�Ϊ��
					showMessage("��Ϣ���ݲ���Ϊ��");//������Ϣ��ʾ����
					return;
				}
				// ����һ��Default��SmsManager����
				SmsManager smsManager = SmsManager.getDefault();
				// ����PendingIntent���󣬲�ʹ��getBroadcast()�㲥
				PendingIntent pendingIntent = PendingIntent.getBroadcast(
						SendActivity.this, 0, new Intent(), 0);
				smsManager.sendTextMessage(strAddressee, null, strMessage,
						pendingIntent, null);// ���Ͷ�����Ϣ
				Toast.makeText(SendActivity.this, "���ŷ��ͳɹ�", 1000).show();//��Ϣ��ʾ����
			}
		});
	}

	/**
	 * ��ʾ��Ϣ��������
	 */
	public void showMessage(String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();// ����AlertDialog����
		alertDialog.setTitle("��ʾ��Ϣ");// ������Ϣ����
		alertDialog.setMessage(message);// ������Ϣ����
		// ����ȷ����ť������Ӱ�ť�����¼�
		alertDialog.setButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
		alertDialog.show();// ���õ�����ʾ��
	}
}