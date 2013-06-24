package com.shared;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SharedData extends Activity {
	private String info = "user_info";// 共享文件名
	private String user = "";// 用户名
	private String password = "";// 密码
	private EditText userText = null;// 用户名EditText组件对象
	private EditText passwordText = null;// 密码EditText组件对象

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		userText = (EditText) findViewById(R.id.user);// 实例化用户名EditText组件对象
		passwordText = (EditText) findViewById(R.id.password);// 实例化密码EditText组件对象
		getData();// 调用获取文件中的数据

		Button button = (Button) findViewById(R.id.submit);// 实例化登录Button组件对象
		// 为登录Button组件对象添加点击事件监听
		button.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				user = userText.getText().toString().trim();// 获取用户输入框的值
				password = passwordText.getText().toString().trim();// 获取密码输入框的值
				saveData();// 调用保存数据到文件
			}
		});
	}

	/**
	 * 把user(用户名),password(密码)保存到文件
	 */
	public void saveData() {
		SharedPreferences sPreferences = getSharedPreferences(info, 0);//获取SharedPreferences
		Editor editor = sPreferences.edit();//打开SharedPreferences的编辑状态
		editor.putString("User", user);//存储用户名
		editor.putString("Password", password);//存储密码
		editor.commit();//保存数据
		//提示用户登录成功，并获取先保存的文件中
		new AlertDialog.Builder(SharedData.this).setTitle("登录信息").setMessage(
				"用户 " + sPreferences.getString("User", "") + " 登录成功")
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				}).show();
	}

	/**
	 * 获取文件中的数据，如果文件中存在相应的数据，把该数据赋值到相应的EditText组件对象
	 */
	public void getData() {
		SharedPreferences sPreferences = getSharedPreferences(info, 0);//获取SharedPreferences
		user = sPreferences.getString("User", "");//获取info文件中User对应的数据
		password = sPreferences.getString("Password", "");//获取info文件中Password对应的数据
		userText.setText(user);//把user赋值给用户EditText组件对象
		passwordText.setText(password);//把password赋值给密码EditText组件对象
	}
}