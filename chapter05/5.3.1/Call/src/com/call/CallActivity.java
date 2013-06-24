package com.call;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CallActivity extends Activity {
	private EditText editText = null;//电话号码EditText组件对象
	private Button callButton = null;//直接拨打按钮Button组件对象
	private Button dialButton = null;//启动拨打界面按钮Button组件对象

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		editText = (EditText) findViewById(R.id.phone_number);//实例化电话号码EditText组件对象
		callButton = (Button) findViewById(R.id.phone_call);//实例化直接拨打按钮Button组件对象
		dialButton = (Button) findViewById(R.id.phone_dial);//实例化启动拨打界面按钮Button组件对象
		callButton.setOnClickListener(new Button.OnClickListener() {//添加Button按钮点击监听
			
			@Override
			public void onClick(View arg0) {
				call();// 调用直接打电话的方法
			}
		});
		dialButton.setOnClickListener(new Button.OnClickListener() {//添加Button按钮点击监听

			@Override
			public void onClick(View arg0) {
				dial();// 调用启动一个拨号器的方法
			}
		});
	}

	/**
	 * 直接打电话的方法
	 */
	public void call() {
		String data = "tel:" + editText.getText();// 电话号码参数字符串
		Uri uri = Uri.parse(data);// 将字符串转化为Uri实例
		Intent intent = new Intent();// 实例化Intent
		intent.setAction(Intent.ACTION_CALL);// 设置Intent的Action属性
		intent.setData(uri);// 设置Intent的Data属性
		startActivity(intent);// 启动Activity
	}

	/**
	 * 启动一个拨号器的方法
	 */
	public void dial() {
		String data = "tel:" + editText.getText();// 电话号码参数字符串
		Uri uri = Uri.parse(data);// 将字符串转化为Uri实例
		Intent intent = new Intent();// 实例化Intent
		intent.setAction(Intent.ACTION_DIAL);// 设置Intent的Action属性
		intent.setData(uri);// 设置Intent的Data属性
		startActivity(intent);// 启动Activity
	}
}