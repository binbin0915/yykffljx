package com.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Example1 extends Activity {

	private String info = "user_info";
	private String password = "";
	private String user = "";
	private EditText userText = null;
	private EditText passwordText = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example1);
		userText = (EditText) findViewById(R.id.user);
		passwordText = (EditText) findViewById(R.id.password);
		passwordText.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
//		new AlertDialog.Builder(Example1.this).setMessage(
//				InputType.TYPE_CLASS_TEXT + "\n"
//						+ InputType.TYPE_TEXT_VARIATION_PASSWORD + "\n"
//						+ InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
//				.show();
		getData();

		Button button = (Button) findViewById(R.id.submit);
		button.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				user = userText.getText().toString().trim();
				password = passwordText.getText().toString().trim();
				saveData();
			}
		});
	}

	public void saveData() {
		SharedPreferences sPreferences = getSharedPreferences(info, 0);
		Editor editor = sPreferences.edit();
		editor.putString("User", user);
		editor.putString("Password", password);
		editor.commit();
		new AlertDialog.Builder(Example1.this).setTitle("登录信息").setMessage(
				"用户 " + sPreferences.getString("User", "") + " 登录成功")
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				}).show();
	}

	public void getData() {
		SharedPreferences sPreferences = getSharedPreferences(info, 0);
		user = sPreferences.getString("User", "");
		password = sPreferences.getString("Password", "");
		userText.setText(user);
		passwordText.setText(password);
	}
}
