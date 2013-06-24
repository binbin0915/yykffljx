package com.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class MyView extends View {

	private Bitmap bitmap = null;
	private Animation animation = null;

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.p1))
				.getBitmap();
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(bitmap, 0, 0, null);
	}

	public void type(int num) {
		switch (num) {
		case 0:
			animation = new AlphaAnimation(1.0f, 0.1f);
			animation.setDuration(3000);
			animation.setRepeatMode(2);
			animation.setRepeatCount(3);
			this.startAnimation(animation);
			break;
		case 1:
			animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			animation.setDuration(1000);
			animation.setRepeatMode(2);
			animation.setRepeatCount(3);
			this.startAnimation(animation);
			break;
		case 2:
			animation = new TranslateAnimation(10, 100, 10, 100);
			animation.setDuration(1000);
			animation.setRepeatMode(2);
			animation.setRepeatCount(3);
			this.startAnimation(animation);
			break;
		case 3:
			animation = new RotateAnimation(0.0f, +360,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			animation.setDuration(1000);
			animation.setStartOffset(1000);
			animation.setRepeatMode(1);
			animation.setRepeatCount(4);
			this.startAnimation(animation);
			break;
		}
	}
}
