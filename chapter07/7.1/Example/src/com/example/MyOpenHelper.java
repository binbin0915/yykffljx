package com.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyOpenHelper extends Activity{
	private Button firstButton;
	private Button secondButton;
	private SQLiteDB dbHelper;
	private final String dbName = "helperdb"; 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout);
		
		firstButton = (Button) findViewById(R.id.first);
		firstButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dbVersion(1);
			}
		});
		
		secondButton = (Button) findViewById(R.id.second);
		secondButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dbVersion(2);
			}
		});	
	}
	
	public void dbVersion(int version){
		dbHelper=new SQLiteDB(this,dbName,null,version);
		showMessage(dbHelper.showTable());
	}
	
	public void showMessage(String msg){
		new AlertDialog.Builder(MyOpenHelper.this).setTitle("Message")
		.setMessage(msg).setNegativeButton("È·¶¨",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int which) {
				}
		}).show();
	}
}
