package com.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements
		SurfaceHolder.Callback, Runnable {
	private SurfaceHolder mSurfaceHolder = null;
	private int count = -1;
	private boolean pan = true;

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
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
				switch (this.getCount()) {
				case 0:
					drawGraphic(canvas);
					break;
				case 1:
					fillGraphic(canvas);
					break;
				case 2:
					linearGradientGraphic(canvas);
					break;
				}
				mSurfaceHolder.unlockCanvasAndPost(canvas);
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {

			}
		}
	}

	public void drawGraphic(Canvas canvas) {
		Paint mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(3);
		mPaint.setAntiAlias(true);
		Draw(canvas, mPaint);
	}

	public void fillGraphic(Canvas canvas) {
		Paint mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(Color.BLUE);
		mPaint.setAntiAlias(true);
		Draw(canvas, mPaint);
	}

	public void linearGradientGraphic(Canvas canvas) {
		Shader linearShader = new LinearGradient(0, 0, 25, 25, Color.BLACK,
				Color.WHITE, Shader.TileMode.MIRROR);
		Shader sweepShader = new SweepGradient(60, 130, Color.BLACK,
				Color.WHITE);
		Shader radialShader = new RadialGradient(150, 130, 40, Color.BLACK,
				Color.WHITE, Shader.TileMode.MIRROR);
		Draw(canvas, linearShader, sweepShader,radialShader);
	}

	public void Draw(Canvas canvas, Paint mPaint) {
		canvas.drawCircle(40, 40, 30, mPaint);
		canvas.drawRect(100, 10, 160, 70, mPaint);
		canvas.drawRect(10, 90, 100, 150, mPaint);
		RectF rf = new RectF(120, 90, 200, 150);
		canvas.drawRoundRect(rf, 10, 10, mPaint);
		rf = new RectF(10, 180, 90, 220);
		canvas.drawOval(rf, mPaint);
		Path path = new Path();
		path.moveTo(110, 180);
		path.lineTo(110, 240);
		path.lineTo(170, 240);
		path.close();
		canvas.drawPath(path, mPaint);
	}

	public void Draw(Canvas canvas, Shader shader1, Shader shader2, Shader shader3) {
		Paint mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setShader(shader1);
		canvas.drawCircle(40, 40, 30, mPaint);
		canvas.drawRect(100, 10, 160, 70, mPaint);
		mPaint.setShader(shader2);
		canvas.drawRect(10, 90, 100, 150, mPaint);
		RectF rf = new RectF(120, 90, 200, 150);
		mPaint.setShader(shader3);
		canvas.drawRoundRect(rf, 10, 10, mPaint);
		rf = new RectF(10, 180, 90, 220);
		canvas.drawOval(rf, mPaint);
		Path path = new Path();
		path.moveTo(110, 180);
		path.lineTo(110, 240);
		path.lineTo(170, 240);
		path.close();
		canvas.drawPath(path, mPaint);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}