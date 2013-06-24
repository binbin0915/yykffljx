package com.ToastExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ToastExample extends Activity {
	Button defaultToast;
	Button defineToast;
	Button iconToast;
	Button defineAllToast;
	Toast toast;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 默认Toast
		defaultToast = (Button) this.findViewById(R.id.defaultToast);
		defaultToast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// makeText方法三个参数，第一个参数：Context，第二个参数：提示信息，
				// 第三个参数：信息框消失方式 ，有两种取值Toast.LENGTH_SHORT（在段时间内消失）和
				// Toast.LENGTH_LONG(较长时间消失)
				Toast.makeText(ToastExample.this, R.string.ToastText,
						Toast.LENGTH_SHORT).show();
			}

		});
		// 自定义显示位置Toast
		defineToast = (Button) this.findViewById(R.id.defineToast);
		defineToast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toast = Toast.makeText(ToastExample.this, R.string.ToastText,
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);// 提示框出现的位置，参数1：位置通过Gravity类设置，参数2：x偏移量，参数3：y偏移量
				toast.show();// 显示Toast
			}
		});
		// 带图标的Toast
		iconToast = (Button) this.findViewById(R.id.IconToast);
		iconToast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toast = Toast.makeText(ToastExample.this, R.string.ToastText,
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout view = (LinearLayout) toast.getView();// getView():获取Toast的View对象
				ImageView imgView = new ImageView(ToastExample.this);// 创建ImageView对象
				imgView.setImageResource(R.drawable.icon);// 设置imgView的背景图片
				view.addView(imgView);// 将imgView添加到View上
				toast.setView(view);// 将view显示在Toast上
				toast.show();// 显示Toast
			}

		});
		// 完全自定义Toast
		defineAllToast = (Button) this.findViewById(R.id.defineAllToast);
		defineAllToast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toast = new Toast(ToastExample.this);
				LayoutInflater inflater = getLayoutInflater();// 获取LayoutInflater对象
				// inflate():将Layout文件转换为View，这里是将definetoast.xml中的myToastLayout组件转化为View
				View myToastLayout = inflater.inflate(R.layout.definetoast,
						(ViewGroup) findViewById(R.id.myToastLayout));
				// 设置提示信息出现的位置
				toast.setGravity(Gravity.RIGHT | Gravity.BOTTOM, 40, 40);
				toast.setDuration(Toast.LENGTH_LONG);// 设置如何显示提示信息

				toast.setView(myToastLayout);// 将myToastLayout显示在Toast上
				toast.show();// 显示Toast

			}

		});

	}
}