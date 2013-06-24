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
		Button button = (Button) findViewById(R.id.camera_button);// ʵ����Button�������
		button.setOnClickListener(new Button.OnClickListener() {// ΪButton��ӵ������

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent();// ��ʼ��Intent
						intent.setClass(Welcome.this, CameraActivity.class);// ָ��intent������������
						startActivity(intent);// �����µ�Activity
					}
				});
	}

}
