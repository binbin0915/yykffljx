package com.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DownMP3  extends Activity{
	
	private Button button;
	private ProgressDialog dDialog;
	private int fileSize = 0;
	private int dowloaded = 0;
	private byte[] bytes;
	private MediaPlayer player = new MediaPlayer();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mp3layout);
		
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				downMp3();
			}
		});
	}
	
	private void downMp3(){
		
		final HttpURLConnection conn=new ConnectWeb().getMP3Stream();
		if(conn==null){
			return;
		}
		
		dDialog = new ProgressDialog(DownMP3.this);
		dDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dDialog.setTitle("请稍等...");
		dDialog.setMessage("正在下载中...");
		dDialog.setIndeterminate(false);
		dDialog.setCancelable(false);
		dDialog.show();
		
		fileSize=conn.getContentLength();
		if (fileSize - dowloaded > 2048) {
			bytes = new byte[2048];
		} else {
			bytes = new byte[fileSize - dowloaded];
		}

		dDialog.setMax(fileSize);
		dDialog.setProgress(0);
		
		new Thread() {
			public void run() {
				try {
					int len = 0;
					InputStream iStream = conn.getInputStream();
					File file = new File(Environment.getExternalStorageDirectory().getPath()+File.separator+ "mp3"+ File.separator+"1.mp3");
					if(!file.getParentFile().exists()){
						file.getParentFile().mkdirs();
					}
					OutputStream oStream = new FileOutputStream(file);
					
					while ((len = iStream.read(bytes)) != -1) {
						dowloaded += len;
						if (dowloaded != fileSize) {
							dDialog.setProgress(dowloaded);
							Thread.sleep(100);
						} else {
							dDialog.cancel();
						}
						oStream.write(bytes, 0, len);
					}
					
					dDialog.cancel();
					oStream.flush();
					oStream.close();
					iStream.close();
					
					Message m = new Message();
					mp3Handler.sendMessage(m);
					
				}
				catch(Exception e){
					e.printStackTrace();
					dDialog.cancel();
				}
			}
		}.start();
	}
	
	Handler mp3Handler = new Handler() {
		public void handleMessage(Message msg) {			
			try {
				player.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/mp3/1.mp3");
				player.reset();	
				player.prepare();
				player.start();	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==4 && player.isPlaying()){
			player.reset();
		}	
		return super.onKeyDown(keyCode, event);
	}
}
