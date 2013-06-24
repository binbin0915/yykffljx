package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Welcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent();
		intent.setClass(Welcome.this, Example1.class);
		startActivity(intent);
		Welcome.this.finish();
	}

}
