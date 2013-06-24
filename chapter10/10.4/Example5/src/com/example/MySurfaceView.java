package com.example;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements
		SurfaceHolder.Callback, Runnable {
	private SurfaceHolder mSurfaceHolder = null;
	private int count = -1;
	private boolean pan = true;
	private Context cot;
	private Bitmap bitmap = null;
	private float zoom=1.0f;
	private boolean zoompan=true;
	private float rotate=0;

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		cot = context;
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		count=0;
		new Thread(this).start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		count = -1;
		pan = false;
	}

	public void run() {
		while (pan) {
			synchronized (mSurfaceHolder) {
				Canvas canvas = mSurfaceHolder.lockCanvas();
				canvas.drawColor(Color.WHITE);
				Matrix matrix = new Matrix();
				switch (this.getCount()) {
				case 0:
					drawPic(matrix);
					break;
				case 1:
					zoomPic(matrix);
					break;
				case 2:
					rotatePic(matrix);
					break;
				case 3:
					skewPic(matrix);
					break;
				}
				canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));  
				canvas.drawBitmap(bitmap, matrix, null);
				mSurfaceHolder.unlockCanvasAndPost(canvas);
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {

			}
		}
	}

	public void drawPic(Matrix matrix){
		try {
			InputStream iso = cot.getAssets().open("pic/p1.jpg");
			bitmap = BitmapFactory.decodeStream(iso);
		} catch (Exception e) {
			e.printStackTrace();
		}
		zoom=0.8f;
		rotate=0;
	}
	
	public void zoomPic(Matrix matrix){		
		if(zoom<0.3 && zoompan){
			zoompan=false;
		}
		if(zoom>0.9 && !zoompan){
			zoompan=true;
		}
		if(zoompan){
			zoom=zoom-0.1f;
		}
		else{
			zoom=zoom+0.1f;
		}

		matrix.setScale(zoom, zoom); 	
		matrix.postTranslate(300 / 2 - bitmap.getWidth()*zoom / 2,
				300 / 2 - bitmap.getHeight()*zoom / 2 );	
	}
	
	public void rotatePic(Matrix matrix){
		rotate=rotate+60;		
		matrix.setScale(zoom, zoom); 
		matrix.postTranslate(300 / 2 - bitmap.getWidth()*zoom / 2,
				300 / 2 - bitmap.getHeight()*zoom / 2 );
		matrix.postRotate(rotate,300 / 2, 300 / 2);	
	}
	
	public void skewPic(Matrix matrix){
		matrix.setScale(zoom, zoom); 
		matrix.postTranslate(300 / 2 - bitmap.getWidth()*zoom / 2,
				300 / 2 - bitmap.getHeight()*zoom / 2 );
		matrix.postSkew(0.1f,0.1f,300 / 2, 300 / 2);	
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}