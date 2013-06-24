package com.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends Activity {
	private TextView nameTextView = null;// 文件名称的TextView组件对象
	private TextView contentTextView = null;// 文件内容的TextView组件对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_file);// 为当前活动的Activity设置一个视图
		nameTextView = (TextView) findViewById(R.id.showName);// 实例化文件名称的TextView组件对象
		contentTextView = (TextView) findViewById(R.id.showContent);// 实例化文件内容的TextView组件对象
		Intent intent = getIntent();// 获取Intent
		String filePath = intent.getCharSequenceExtra("filePath").toString();// 获取文件路径
		String name = new File(filePath).getName();//获取文件名
		String content = readFile(filePath);//调用读取文件方法，获取文件内容

		nameTextView.setText("文件名称：" + name);//设置文件名称
		contentTextView.setText("文件内容：" + content);//设置文件内容
	}

	/**
	 * 读文件
	 * @param filePath 文件路径
	 * @return
	 */
	public String readFile(String filePath) {
		try {
			File file = new File(filePath);//实例化File对象
			InputStream inputStream = new FileInputStream(file);//实例化InputStream对象
			byte[] b = new byte[inputStream.available()];//inputStream.available()获取此输入流下一个方法调用可以不受阻塞地从此输入流读取（或跳过）的估计字节数
			inputStream.read(b);//从输入流中读取字节，并将其存储在缓冲区数组 b 中
			inputStream.close();//关闭此输入流并释放与该流关联的所有系统资源
			return new String(b);//返回字符串内容
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
