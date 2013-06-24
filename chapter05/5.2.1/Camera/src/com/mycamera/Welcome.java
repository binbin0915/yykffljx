package com.mycamera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		Button button = (Button) findViewById(R.id.camera_button);// 实例化Button组件对象
		button.setOnClickListener(new Button.OnClickListener() {// 为Button添加点击监听

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent();// 初始化Intent
						intent.setClass(Welcome.this, CameraActivity.class);// 指定intent对象启动的类
						startActivity(intent);// 启动新的Activity
					}
				});
	}

}
