package com.folder;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TraverseFolder extends Activity {
	private TextView textView = null;//用于显示目录结构的TextView组件对象
	private File[] files = null;//File数组
	private ListView listView = null;//用于显示文件的ListView组件对象

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//实例化ListView组件对象
		listView = (ListView) findViewById(R.id.listView);
		//实例化TextView组件对象
		textView = (TextView) findViewById(R.id.text_view);
		//调用获取手机SDCard的存储状态
		boolean sdStatus = getStorageState();
		if (!sdStatus) {//判断SDCard的存储状态，如果是false,提示并结束本程序
			AlertDialog alertDialog = new AlertDialog.Builder(
					TraverseFolder.this).create();//创建AlertDialog对象
			alertDialog.setTitle("提示信息");//设置信息标题
			alertDialog.setMessage("未安装SD卡，请检查你的设备");//设置信息内容
			//设置确定按钮，并添加按钮监听事件
			alertDialog.setButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					TraverseFolder.this.finish();//结束应用程序
				}
			});
			alertDialog.show();//设置弹出提示框
		}

		Intent intent = getIntent();//获取Intent
		CharSequence charSequence = intent.getCharSequenceExtra("filePath");//获取CharSequence对象
		if (charSequence != null) {//判断CharSequence对象是否为空，为空就获取SDCard根目录，否则就获取传过来的文件目录
			File file = new File(charSequence.toString());//实例化File
			textView.setText(file.getPath());//更新TextView组件显示的目录结构
			files = file.listFiles();//获取该目录的所有文件及目录
		} else {
			File sdCardFile = Environment.getExternalStorageDirectory();//获取SDCard根目录File对象
			textView.setText(sdCardFile.getPath());//设置TextView组件显示的目录结构
			files = sdCardFile.listFiles();//获取SDCard根目录的所有文件及目录
		}

		List<HashMap<String, Object>> list = getList(files);//调用获取相应的集合
		setAdapter(list, files);//调用构造适配器并为ListView添加适配器
		
		listView.setOnItemClickListener(new OnItemClickListener() {//为ListView添加点击监听

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (files[arg2].isDirectory()) {//判断所点击的文件是否是文件夹
					File[] childFiles = files[arg2].listFiles();//获取该点击文件夹下的所有文件及文件夹
					if (childFiles != null && childFiles.length >= 0) {//判断该点击文件夹数组不为空
						Intent intent = new Intent();//初始化Intent
						intent.setClass(TraverseFolder.this,
								TraverseFolder.class);//指定intent对象启动的类
						intent.putExtra("filePath", files[arg2].getPath());//函数传递
						startActivity(intent);//启动新的Activity
					}
				}
			}
		});
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
		SimpleAdapter simpleAdapter = new SimpleAdapter(TraverseFolder.this,
				list, R.layout.folder_list, new String[] { "image_view",
						"folder_name" }, new int[] { R.id.image_view,
						R.id.folder_name });//实例化SimpleAdapter

		listView.setAdapter(simpleAdapter);//为ListView添加适配器
		this.files = files;//把当前File数组赋值
	}

	/**
	 * 获取手机SDCard的存储状态
	 * 
	 * @return 手机SDCard的存储状态(true/false)
	 */
	public boolean getStorageState() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {//判断手机SDCard的存储状态
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据File[]获取相应的集合
	 * @param files File数组
	 * @return List<HashMap<String, Object>>集合
	 */
	public List<HashMap<String, Object>> getList(File[] files) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();//创建List集合
		for (int i = 0; i < files.length; i++) {//循环File数组
			HashMap<String, Object> hashMap = new HashMap<String, Object>();//创建HashMap
			if (files[i].isDirectory()) {//判断该文件是否是文件夹
				hashMap.put("image_view", R.drawable.dir1);//往HashMap中添加文件夹图片
			} else {
				hashMap.put("image_view", R.drawable.file2);//往HashMap中添加文件图片
			}

			hashMap.put("folder_name", files[i].getName());//往HashMap中添加文件名
			list.add(hashMap);//将HashMap添加到List集合
		}
		return list;//返回List集合
	}
}