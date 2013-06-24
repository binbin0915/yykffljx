package com.ProgressDialogExample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProgressDialogExample extends Activity {
	Button circleBut;
	Button longBut;
	ProgressDialog myDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); // 加载main.xml

		circleBut = (Button) this.findViewById(R.id.circleButt);// 获取circleButt”圆形进度条“按钮
		longBut = (Button) this.findViewById(R.id.longButt);// 获取longButt“长形进度条”按钮

		circleBut.setOnClickListener(new OnClickListener() {// 为“圆形进度条”添加单击事件

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						myDialog = new ProgressDialog(
								ProgressDialogExample.this);// 创建ProgressDialog对象
						myDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的样式为圆形样式
						myDialog.setTitle("提示");// 设置进度条的标题信息
						myDialog.setMessage("数据加载中,请稍后...");// 设置进度条的提示信息
						myDialog.setIcon(R.drawable.android);// 是指进度条的图标
						myDialog.setIndeterminate(false);// 设置进度条是否为不明确
						myDialog.setCancelable(true);// 设置进度条是否按返回键取消
						// 为进度条添加“确定”按钮，并为该按钮添加单击事件
						myDialog.setButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										myDialog.cancel();// 撤销进度条
									}

								});
						myDialog.show();// 显示进度条
					}

				});

		longBut.setOnClickListener(new OnClickListener() {// 为“长形进度条”添加单击事件
					int count = 0;// 存储进度条当前进度值，初始值为0

					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
						myDialog = new ProgressDialog(
								ProgressDialogExample.this);
						myDialog
								.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						myDialog.setTitle("提示");
						myDialog.setMessage("数据加载中,请稍后...");
						myDialog.setIcon(R.drawable.android);
						myDialog.setIndeterminate(false);// 设置进度条是否为不明确
						myDialog.setCancelable(true);
						myDialog.setMax(200);// 设置进度条的进度最大值
						myDialog.setProgress(0);// 设置当前默认进度为0
						myDialog.setSecondaryProgress(100);// 设置第二进度条的值为100
						// 为进度条添加“取消”按钮，并为该按钮添加单击事件
						myDialog.setButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										myDialog.cancel();
									}

								});
						myDialog.show();// 显示进度条

						new Thread() {// 定义线程，动态改变当前进度条的值
							public void run() {

								while (count <= 200) {
									myDialog.setProgress(count++);// 设置当前进度条的进度值
									try {
										Thread.sleep(100);// 暂停0.1秒
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
								}
								

							}
						}.start();// 启动线程
					}

				});
	}
}