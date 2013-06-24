package com.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View{

	public MyView(Context context) {
		super(context);
	}

	public void onDraw(Canvas canvas){
        Paint mPaint = new Paint(); 
        mPaint.setColor(Color.RED);  
        mPaint.setTextSize(28);
        mPaint.setAntiAlias(true);
        canvas.drawRGB(255, 255, 255);
        canvas.drawText("Hello World!", 20, 120, mPaint);
    }
}
