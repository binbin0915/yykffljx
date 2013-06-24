package com.example;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FileUpload extends Activity {
	private Button button;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);

		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(new ConnectWeb().uploadFile());
			}
		});
	}

	private void showDialog(String mess) {
		new AlertDialog.Builder(FileUpload.this).setTitle("Message")
		.setMessage(mess).setNegativeButton("È·¶¨",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int which) {
				}
		}).show();
	}
}
