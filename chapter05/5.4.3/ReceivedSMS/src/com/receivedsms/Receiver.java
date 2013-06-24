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
		if (intent.getAction().equals(SMS_RECEIVED)) {// 判断是否是SMS_RECEIVED事件被触发
			StringBuilder sb = new StringBuilder();// 初始化StringBuilder
			Bundle bundle = intent.getExtras();// 获取Bundle对象
			if (bundle != null) {// 判断Bundle对象是否为空
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] msg = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				for (SmsMessage currMsg : msg) {
					sb.append("发件人:");
					sb.append(currMsg.getDisplayOriginatingAddress());
					sb.append("\n内容：");
					sb.append(currMsg.getDisplayMessageBody());
				}
				Toast toast = Toast.makeText(context, "收到了短消息: \n"
						+ sb.toString(), Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}
}
