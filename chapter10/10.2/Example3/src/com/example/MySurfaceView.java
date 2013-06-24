package com.example;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements
		SurfaceHolder.Callback, Runnable {

	private SurfaceHolder mSurfaceHolder = null;
	private int count = 0;
	private Context cot;

	public MySurfaceView(Context context) {
		super(context);
		cot = context;
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		new Thread(this).start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {

			}
			synchronized (mSurfaceHolder) {
				Draw();
			}
		}
	}

	public void Draw() {
		Canvas canvas = mSurfaceHolder.lockCanvas();
		if (mSurfaceHolder == null || canvas == null) {
			return;
		}
		InputStream iso;
		Bitmap bitmap = null;
		try {
			iso = cot.getAssets().open("pics/" + count + ".png");
			bitmap = BitmapFactory.decodeStream(iso);
		} catch (IOException e) {
			e.printStackTrace();
		}
		canvas.drawRGB(255, 255, 255);
		canvas.drawBitmap(bitmap, 300 / 2 - bitmap.getWidth() / 2,
				300 / 2 - bitmap.getHeight() / 2, null);
		mSurfaceHolder.unlockCanvasAndPost(canvas);
		count++;
		if (count > 11) {
			count = 0;
		}
	}
}