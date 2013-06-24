package com.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View {

	private int count = 0;

	public MyView(Context context) {
		super(context);
	}

	public void onDraw(Canvas canvas) {
		int color=0;
		switch (count) {
		case 0:
			color=Color.BLACK;
			break;
		case 1:
			color=Color.BLUE;
			break;
		case 2:
			color=Color.GREEN;
			break;
		case 3:
			color=Color.RED;
			break;
		case 4:
			color=Color.YELLOW;
			break;
		}
		count=count+1;
		if(count>4){
			count=0;
		}
		Paint mPaint = new Paint();
		mPaint.setColor(color);
		mPaint.setTextSize(28);
		mPaint.setAntiAlias(true);
		canvas.drawRGB(255, 255, 255);
		canvas.drawText("Hello World!", 20, 120, mPaint);
	}
}
