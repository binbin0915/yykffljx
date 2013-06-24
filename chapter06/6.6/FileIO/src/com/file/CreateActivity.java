package com.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends Activity {
	private EditText nameEditText = null;// 文件名称的EditText组件对象
	private EditText contentEditText = null;// 文件内容的EditText组件对象
	private Button createFileButton = null;// 创建文件按钮的Button组件对象
	private String dirPath = "";// 文件读/写指定目录

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_file);// 为当前活动的Activity设置一个视图

		nameEditText = (EditText) findViewById(R.id.createName);// 实例化EditText组件对象
		contentEditText = (EditText) findViewById(R.id.createContent);// 实例化EditText组件对象
		createFileButton = (Button) findViewById(R.id.createFileButton);// 实例化Button组件对象

		Intent intent = getIntent();// 获取Intent
		dirPath = intent.getCharSequenceExtra("dirPath").toString();// 获取文件存放目录
		createFileButton.setOnClickListener(new Button.OnClickListener() {// 为Button添加点击监听

					@Override
					public void onClick(View arg0) {
						String fileName = nameEditText.getText().toString();// 获取输入文件名称
						String fileContent = contentEditText.getText()
								.toString();// 获取输入文件内容
						boolean isTrue = writeFile(fileName, fileContent);// 调用写文件方法
						if (isTrue) {// 判断文件是否创建成功
							showMessage("创建文件成功");// 调用消息弹出方法，弹出写文件成功消息提示
						} else {
							showMessage("创建文件失败");// 调用消息弹出方法，弹出写文件失败消息提示
						}
					}
				});
	}

	/**
	 * 写文件
	 * 
	 * @param fileName
	 *            文件名称
	 * @param fileContent
	 *            文件内容
	 * @return
	 */
	public boolean writeFile(String fileName, String fileContent) {
		try {
			File file = new File(dirPath + File.separator + fileName + ".txt");// 根据文件路径，创建.txt文本文件
			OutputStream outputStream = new FileOutputStream(file);// 实例化OutputStream对象
			byte[] contents = fileContent.getBytes();// 把字符串内容转化为byte数组
			outputStream.write(contents);// 将 contents.length 个字节从指定的 byte 数组写入此输出流
			outputStream.flush();// 刷新此输出流并强制写出所有缓冲的输出字节
			outputStream.close();// 关闭此输出流并释放与此流有关的所有系统资源
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 消息提示并跳转到首页
	 * 
	 * @param message
	 *            消息内容
	 */
	public void showMessage(String message) {
		// 创建消息提示框AlertDialog对象
		// setTitle：设置消息标题
		// setMessage：设置消息内容
		// setNegativeButton：设置消息确定按钮，并定义按钮点击事件
		// show：显示消
		new AlertDialog.Builder(CreateActivity.this).setTitle("提示信息")
				.setMessage(message).setNegativeButton("确定",
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								Intent intent = new Intent();// 实例化Intent
								intent.setClass(CreateActivity.this,
										MainActivity.class);// 指定intent对象启动的类
								startActivity(intent);// 启动新的Activity
							}
						}).show();
	}
}
