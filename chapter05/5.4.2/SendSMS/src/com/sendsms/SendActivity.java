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
	private Button sendButton = null;// 创建发送按钮Button组件对象
	private EditText addressee = null;// 创建收件人编辑框EditText组件对象
	private EditText message = null;// 创建信息内容编辑框EditText组件对象

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		sendButton = (Button) findViewById(R.id.send);// 实例化发送按钮Button组件对象
		addressee = (EditText) findViewById(R.id.addressee);// 实例化收件人编辑框EditText组件对象
		message = (EditText) findViewById(R.id.message);// 实例化收件人编辑框EditText组件对象
		addressee.setText("请输入接收人的电话号码");//设置默认收件人提示信息
		message.setText("请输入短信内容");//设置默认信息内容提示信息
		//添加收件人编辑框点击事件监听
		addressee.setOnClickListener(new EditText.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				addressee.setText("");
			}
		});
		//添加信息内容编辑框点击事件监听
		message.setOnClickListener(new EditText.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				message.setText("");
			}
		});
		//添加发送按钮点击事件监听
		sendButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String strAddressee = addressee.getText().toString();// 获取收件人信息
				String strMessage = message.getText().toString();// 获取发送内容消息
				if ("".equals(strAddressee)) {//判断收件人信息是否为空
					showMessage("收件人信息不能为空");//调用信息提示方法
					return;
				}
				if ("".equals(strMessage)) {//判断发送内容是否为空
					showMessage("信息内容不能为空");//调用信息提示方法
					return;
				}
				// 构建一个Default的SmsManager对象
				SmsManager smsManager = SmsManager.getDefault();
				// 构建PendingIntent对象，并使用getBroadcast()广播
				PendingIntent pendingIntent = PendingIntent.getBroadcast(
						SendActivity.this, 0, new Intent(), 0);
				smsManager.sendTextMessage(strAddressee, null, strMessage,
						pendingIntent, null);// 发送短信消息
				Toast.makeText(SendActivity.this, "短信发送成功", 1000).show();//信息提示方法
			}
		});
	}

	/**
	 * 提示消息弹出方法
	 */
	public void showMessage(String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();// 创建AlertDialog对象
		alertDialog.setTitle("提示信息");// 设置信息标题
		alertDialog.setMessage(message);// 设置信息内容
		// 设置确定按钮，并添加按钮监听事件
		alertDialog.setButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
		alertDialog.show();// 设置弹出提示框
	}
}