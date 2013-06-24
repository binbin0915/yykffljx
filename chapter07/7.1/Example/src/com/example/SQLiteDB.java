package com.example;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteDB extends SQLiteOpenHelper {

	public SQLiteDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS tableOne "
				+ "(uname VARCHAR(50), pwd VARCHAR(50));";
		db.execSQL(sql);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "CREATE TABLE IF NOT EXISTS tableTwo "
				+ "(uname VARCHAR(50), pwd VARCHAR(50));";
		db.execSQL(sql);
	}

	public String showTable() {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query("sqlite_master", new String[] { "name" },
				"type = ?", new String[] { "table" }, null, null, null, null);
		String tables = "";
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i += 1) {
				tables = tables + cursor.getString(0) + "\n";
				cursor.moveToNext();
			}
		}
		return tables;
	}
}
