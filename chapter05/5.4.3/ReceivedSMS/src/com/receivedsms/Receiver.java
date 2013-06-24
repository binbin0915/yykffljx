package com.receivedsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(SMS_RECEIVED)) {// �ж��Ƿ���SMS_RECEIVED�¼�������
			StringBuilder sb = new StringBuilder();// ��ʼ��StringBuilder
			Bundle bundle = intent.getExtras();// ��ȡBundle����
			if (bundle != null) {// �ж�Bundle�����Ƿ�Ϊ��
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] msg = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				for (SmsMessage currMsg : msg) {
					sb.append("������:");
					sb.append(currMsg.getDisplayOriginatingAddress());
					sb.append("\n���ݣ�");
					sb.append(currMsg.getDisplayMessageBody());
				}
				Toast toast = Toast.makeText(context, "�յ��˶���Ϣ: \n"
						+ sb.toString(), Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}
}
