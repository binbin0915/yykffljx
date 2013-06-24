package com.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Example extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example);
		String fileName="test.txt";//文件名称
		String content="天天向上abcd123";//指定数据内容
		String result="";//读取文件返回的String对象
		boolean istrue = writeFile(fileName, content);//调用写入数据到文件的方法
		if (istrue) {//判断写入数据是否成功
			result+=fileName+"创建成功\n\r";
		}else {
			result+=fileName+"创建失败\n\r";
		}
		result+=readFile(fileName);//调用读取文件方法，获取返回String对象
		TextView textView = (TextView) findViewById(R.id.textView);//初始化TextView
		textView.setText(result);//把读取文件的返回结果显示到TextView中
	}

	/**
	 * 为指定的文件中写入指定的数据
	 * @param fileName 文件名称
	 * @param content 指定数据内容
	 * @return boolean类型(true表示数据写入成功，false表示数据写入失败)
	 */
	public boolean writeFile(String fileName, String content) {
		try {
			FileOutputStream fOutputStream = openFileOutput(fileName,
					MODE_PRIVATE);//创建FileOutputStream对象，MODE_PRIVATE：默认模式
			byte[] buffer = content.getBytes();//将写入的字符串转化成byte数组
			fOutputStream.write(buffer);//将byte数组写入文件
			fOutputStream.flush();//清空缓存
			fOutputStream.close();//关闭FileOutputStream对象
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 读取指定文件的数据，并返回String对象
	 * @param fileName 文件名称
	 * @return String对象
	 */
	public String readFile(String fileName) {
		String result = "";//返回字符串结果
		try {
			FileInputStream fInputStream = openFileInput(fileName);//创建FileInputStream对象
			int len = fInputStream.available();//获取文件的长度
			byte[] buffer = new byte[len];//创建文件长度大小的byte数组
			fInputStream.read(buffer);//将文件流写入byte数组
			result = new String(buffer);//将byte数组转换为String对象
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;//返回字符串结果
	}
}