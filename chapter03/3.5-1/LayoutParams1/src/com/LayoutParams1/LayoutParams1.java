package com.LayoutParams1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LayoutParams1 extends Activity {
	/** Called when the activity is first created. */
	LinearLayout liearLayout;											 //声明LinearLayout变量
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);									 //加载main.xml布局文件
		
		liearLayout = (LinearLayout) this.findViewById(R.id.layout);	 //获取main.xml中id为layout的liearLayout对象
		TextView txtFont = new TextView(this);//声明一个TextView对象
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(//声明ViewGroup.LayoutParams对象
				ViewGroup.LayoutParams.FILL_PARENT,						 //设置布局参数对象的宽度
				ViewGroup.LayoutParams.WRAP_CONTENT);					 //设置布局参数对象的高度
		txtFont.setLayoutParams(layoutParams);							 //将txtFont以layoutParams对象指定宽度和高度布局
		txtFont.setText("通过代码实现布局示例1");							 //设置txtFont上面显示的文字
		txtFont.setTextColor(Color.BLACK);								 //设置字体颜色为黑色
		liearLayout.addView(txtFont);									 //将txtFont添加到liearLayout布局上		
	}
}