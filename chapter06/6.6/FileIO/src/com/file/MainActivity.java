package com.file;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	private ListView listView = null;// 用于显示文件列表的ListView组件对象
	private File[] files = null;// File数组
	private Button createButton = null;// 创建按钮的Button组件对象
	private String dirPath = "";// 文件读/写指定目录

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//为当前活动的Activity设置一个视图
		listView = (ListView) findViewById(R.id.listView);// 实例化ListView组件对象
		createButton = (Button) findViewById(R.id.createButton);// 实例化Button组件对象
		setData();// 加载数据
		listView.setOnItemClickListener(new OnItemClickListener() {// 为ListView添加点击监听

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent intent = new Intent();// 初始化Intent
						intent.setClass(MainActivity.this, ShowActivity.class);// 指定intent对象启动的类
						intent.putExtra("filePath", files[arg2].getPath());// 函数传递
						startActivity(intent);// 启动新的Activity
					}
				});

		createButton.setOnClickListener(new Button.OnClickListener() {//为Button添加点击监听

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();// 初始化Intent
				intent.setClass(MainActivity.this, CreateActivity.class);// 指定intent对象启动的类
				intent.putExtra("dirPath", dirPath);// 函数传递
				startActivity(intent);// 启动新的Activity
			}
		});
	}

	/**
	 * 填充数据
	 */
	public void setData() {
		boolean sdStatus = getStorageState();// 调用获取手机SDCard的存储状态
		if (!sdStatus) {// 判断SDCard的存储状态，如果是false,提示并结束本程序
			AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
					.create();// 创建AlertDialog对象
			alertDialog.setTitle("提示信息");// 设置信息标题
			alertDialog.setMessage("未安装SD卡，请检查你的设备");// 设置信息内容
			// 设置确定按钮，并添加按钮监听事件
			alertDialog.setButton("确定", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					MainActivity.this.finish();// 结束应用程序
				}
			});
			alertDialog.show();// 设置弹出提示框
		}

		File sdCardFile = Environment.getExternalStorageDirectory();// 获取SDCard根目录File对象
		dirPath = sdCardFile.getPath() + File.separator + "FileIO";//指定文件存放目录
		File dirFile = new File(dirPath);
		if (!dirFile.exists()) {//判断文件存放目录是否存在
			dirFile.mkdir();//创建文件存放目录
		}
		files = dirFile.listFiles();//获取文件存放目录中的文件File对象
		List<HashMap<String, Object>> list = getList(files);// 调用获取相应的集合
		setAdapter(list, files);// 调用构造适配器并为ListView添加适配器
	}

	/**
	 * 获取手机SDCard的存储状态
	 * 
	 * @return 手机SDCard的存储状态(true/false)
	 */
	public boolean getStorageState() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {// 判断手机SDCard的存储状态
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据File[]获取相应的集合
	 * 
	 * @param files
	 *            File数组
	 * @return List<HashMap<String, Object>>集合
	 */
	public List<HashMap<String, Object>> getList(File[] files) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();// 创建List集合
		for (int i = 0; i < files.length; i++) {// 循环File数组
			HashMap<String, Object> hashMap = new HashMap<String, Object>();// 创建HashMap
			hashMap.put("file_name", files[i].getName());// 往HashMap中添加文件名
			list.add(hashMap);// 将HashMap添加到List集合
		}
		return list;// 返回List集合
	}

	/**
	 * 构造适配器并为ListView添加适配器
	 * 
	 * @param list
	 *            HashMap的集合
	 * @param files
	 *            File数组
	 */
	public void setAdapter(List<HashMap<String, Object>> list, File[] files) {
		SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this,
				list, R.layout.file_list, new String[] { "file_name" },
				new int[] { R.id.fileName });// 实例化SimpleAdapter

		listView.setAdapter(simpleAdapter);// 为ListView添加适配器
	}
}