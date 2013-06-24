package com.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MyView extends View {

	private Bitmap bitmap = null;
	private Animation animation = null;
	private Context cont=null;
	
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		cont=context;
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
			animation = AnimationUtils.loadAnimation(cont,R.anim.alpha);
			this.startAnimation(animation);
			break;
		case 1:
			animation =AnimationUtils.loadAnimation(cont,R.anim.scale);
			this.startAnimation(animation);
			break;
		case 2:
			animation =AnimationUtils.loadAnimation(cont,R.anim.translate);
			this.startAnimation(animation);
			break;
		case 3:
			animation =AnimationUtils.loadAnimation(cont,R.anim.rotate);
			this.startAnimation(animation);
			break;
		}
	}
}
