package com.AlertDialogExample;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class AlertDialogExample extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//加载资源文件
		final Button btnQuit = (Button) findViewById(R.id.okCancelBut);//获取资源文件中的Button
		btnQuit.setOnClickListener(new Button.OnClickListener() {//btnQuit单击事件
			public void onClick(View v) {
				new AlertDialog.Builder(AlertDialogExample.this)//创建AlertDialog.Builder对象
						.setTitle("标题")//设置标题
						.setMessage("确定要退出吗？")//设置显示信息
						.setIcon(R.drawable.icon)//设置标题栏显示的图标
						.setPositiveButton("确定",new DialogInterface.OnClickListener(){//添加确定按钮及事件
									public void onClick(DialogInterface dialog,int whichButton) {
										setResult(RESULT_OK);
										finish();
									}
								})
						.setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加取消按钮及事件
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).show();//显示对话框
			}
		});
		final Button btnTravels = (Button) findViewById(R.id.listBut);//获取资源文件中的 Button
		btnTravels.setOnClickListener(new Button.OnClickListener() {//btnTravels单击事件
			public void onClick(View v) {
				new AlertDialog.Builder(AlertDialogExample.this)//创建AlertDialog.Builder对象
						.setTitle("导入导出联系人")//设置标题
						.setItems(R.array.arrcontent,new DialogInterface.OnClickListener() {//设置对话框中显示的list，这里显示内容存放到arrcontent数组中
									public void onClick(DialogInterface dialog,int whichcountry) {//whichcountry:当前点击的Button的id或者列表的下标
										String[] travelcountries = getResources().getStringArray(R.array.arrcontent);//将资源文件中的arrcontent转换为数组
										//点击列表对话框中的每一项弹出的对话框
										new AlertDialog.Builder(AlertDialogExample.this)
												.setMessage("你点击了："	+ travelcountries[whichcountry])//设置显示信息
												.setNeutralButton("取消",new DialogInterface.OnClickListener() {//设置点击取消按钮事件
															public void onClick(DialogInterface dialog,int whichButton) {
															}
														}).show();//显示对话框
									}})
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										
									}
								}).show();
			}
		});
		
		Button button=(Button)findViewById(R.id.defineBut);//获取资源文件中的Button
		button.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//创建Dialog对象 构造函数中的参数一：Context,参数二：对话框的样式，定义在style.xml中
					final Dialog dialog = new Dialog(AlertDialogExample.this,R.style.dialog);
					dialog.setContentView(R.layout.myalertdialog);//设置对话框的布局资源文件
					final ImageButton cancelBut=(ImageButton) dialog.findViewById(R.id.cancel);//获取对话框上的按钮
					cancelBut.setOnClickListener(new OnClickListener(){//"取消"按钮的单击事件
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub							
							dialog.hide();//隐藏对话框
						}
						 
					 });
					 dialog.show();
					 
				}
				
			});
		
	}
}