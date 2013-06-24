package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowPic extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		imageView.setImageBitmap(new ConnectWeb().getPicBitmap());
	}
}
