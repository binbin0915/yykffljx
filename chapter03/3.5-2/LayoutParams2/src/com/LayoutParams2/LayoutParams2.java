package com.LayoutParams2;

import com.LayoutParams2.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class LayoutParams2 extends Activity {
	/** Called when the activity is first created. */
	LinearLayout liearLayoutMain; // 声明LinearLayout变量

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); // 加载main.xml布局文件
		
		liearLayoutMain = (LinearLayout) this.findViewById(R.id.layout); // 获取main.xml中id为layout的liearLayout对象

		TextView bookName = new TextView(this);// 声明一个TextView对象
		ViewGroup.LayoutParams bookNameParams = new ViewGroup.LayoutParams(// 声明ViewGroup.LayoutParams对象
				ViewGroup.LayoutParams.FILL_PARENT, // 设置布局参数对象的宽度
				ViewGroup.LayoutParams.WRAP_CONTENT); // 设置布局参数对象的高
		bookName.setLayoutParams(bookNameParams);// 设置bookName的布局方式
		bookName.setText("桂林上水甲天下"); // 设置bookName上面显示的文字
		bookName.setTextColor(Color.BLACK);// 设置bookName的文字颜色
		bookName.setGravity(Gravity.CENTER);// 设置bookName文字居中显示
		TextPaint bttp = bookName.getPaint();// 获取TextPaint对象
		bttp.setFakeBoldText(true);// 设置bookName加粗显示

		TextView bookDesc = new TextView(this);// 声明一个TextView对象
		LinearLayout.LayoutParams bookDescParams = new LinearLayout.LayoutParams(// 声明ViewGroup.LayoutParams对象
				ViewGroup.LayoutParams.WRAP_CONTENT, // 设置布局参数对象的宽度
				ViewGroup.LayoutParams.WRAP_CONTENT); // 设置布局参数对象的高度
		bookDesc.setLayoutParams(bookDescParams);// 设置bookDesc的布局方式
		bookDesc.setText(R.string.desc); // 设置txtFont上面显示的文字
		bookDesc.setTextColor(Color.BLACK);

		ImageView bookPic = new ImageView(this);// 声明一个ImageView对象
		LinearLayout.LayoutParams bookPicParams = new LinearLayout.LayoutParams(
				251, 183);
		bookPicParams.setMargins(25, 0, 0, 5);// 设置边距，方法原型void setMargins (int
												// left, int top, int right, int
												// bottom)
		bookPic.setLayoutParams(bookPicParams);
		bookPic.setBackgroundResource(R.drawable.guilin);// 设置ImageView的背景图

		liearLayoutMain.addView(bookName); // 将bookName添加到liearLayoutMain布局上
		liearLayoutMain.addView(bookPic);// 将bookPic添加到liearLayoutMain布局上
		liearLayoutMain.addView(bookDesc);// 将bookDesc添加到liearLayoutMain布局上
	}
}