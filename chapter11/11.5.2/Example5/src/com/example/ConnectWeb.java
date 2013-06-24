package com.example;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ConnectWeb {
	public Bitmap getPicBitmap(){
		Bitmap bitmap = null;
		try{
			String url="http://192.168.1.8:8080/AndroidWeb/pics/flower.jpg";
			URL picUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) picUrl.openConnection();
			conn.connect();
			if(conn.getResponseCode()==200){
				InputStream ins = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(ins);
				ins.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return bitmap;
	}
	
	public HttpURLConnection getMP3Stream(){
		HttpURLConnection conn = null;
		try{
			String url="http://192.168.1.8:8080/AndroidWeb/mp3/hasta_siempre_comandante.mp3";
			URL picUrl = new URL(url);
			conn = (HttpURLConnection) picUrl.openConnection();
			conn.connect();
			if(conn.getResponseCode()!=200){
				conn = null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			conn = null;
		}
		return conn;
	}
}
