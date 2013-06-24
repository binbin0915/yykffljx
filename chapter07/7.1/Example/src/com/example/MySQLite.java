package com.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MySQLite extends Activity{
	private Button baseButton;
	private Button tableButton;
	private Button executeButton;
	private Button queryButton;
	private final String dbName = "mydb"; 
	private final String tableName = "users"; 
	private SQLiteDatabase db=null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);

		baseButton = (Button) findViewById(R.id.base);
		baseButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				db=openOrCreateDatabase(dbName,MODE_PRIVATE,null);
			}
		});
		
		tableButton = (Button) findViewById(R.id.table);
		tableButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(db!=null){
					creatTable();
				}
			}
		});
		
		executeButton = (Button) findViewById(R.id.execute);
		executeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(db!=null){
					executeData();
				}
			}
		});
		
		queryButton = (Button) findViewById(R.id.query);
		queryButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(db!=null){
					queryData();
				}
			}
		});
	}
	
	public void creatTable(){
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName     
		 + " (uname VARCHAR(50), pwd VARCHAR(50));";  
		db.execSQL(sql);
		Cursor cursor = db.query("sqlite_master", new String[]{"name"}, "type = ?",new String[]{"table"}, null, null, null,null);
		String tables="";
		if(cursor.getCount()!=0){
			cursor.moveToFirst();
			for(int i=0;i<cursor.getCount();i+=1){
				tables=tables+cursor.getString(0)+" ";
				cursor.moveToNext();
			}
		}
		
		new AlertDialog.Builder(MySQLite.this).setTitle("Message")
		.setMessage(tables).setNegativeButton("确定",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int which) {
				}
		}).show();
	}
	
	public void executeData(){
		String sql = "insert into "+tableName+" values ('张三','123456')";  
		db.execSQL(sql);
		
		ContentValues cv=new ContentValues();
		cv.put("uname", "李四");
		cv.put("pwd", "987654");
		db.insert(tableName, null, cv);
		
		sql = "update "+tableName+" set pwd='654321' where uname='张三'";  
		db.execSQL(sql);
		
		cv=new ContentValues();
		cv.put("pwd", "456789");
		db.update(tableName, cv, "uname=?",new String[]{"李四"});
		
		sql = "delete from "+tableName+" where uname='张三'";  
		db.execSQL(sql);
		
		db.delete(tableName, "uname=?", new String[]{"李四"});
	}
	
	public void queryData(){
		Cursor cursor = db.query(tableName, null, "pwd like ?",new String[]{"%3%"}, null, null, null,null);
		String str="";
		if(cursor.getCount()!=0){
			cursor.moveToFirst();
			for(int i=0;i<cursor.getCount();i+=1){
				str=str+cursor.getString(0)+" "+cursor.getString(1)+"\n";
				cursor.moveToNext();
			}
		}
		
		new AlertDialog.Builder(MySQLite.this).setTitle("Message")
		.setMessage(str).setNegativeButton("确定",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int which) {
				}
		}).show();
	}
}
