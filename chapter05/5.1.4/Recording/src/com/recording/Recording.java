package com.recording;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class Recording extends Activity {
	private Button startButton = null;// 播放Button组件对象
	private Button stopButton = null;// 停止Button组件对象
	private ListView listView = null;// 用于显示文件列表的ListView组件对象
	private File[] files = null;// File数组
	private String dirPath = "";// 文件读/写指定目录
	private MediaRecorder mediaRecorder = null;// 创建一个空MediaRecorder对象

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recording);

		startButton = (Button) findViewById(R.id.button_start);// 实例化播放Button组件对象
		stopButton = (Button) findViewById(R.id.button_stop);// 实例化停止Button组件对象
		listView = (ListView) findViewById(R.id.listView);// 实例化ListView组件对象
		stopButton.setEnabled(false);// 停止按钮失效
		setListViewData();// 为ListView填充数据
		startButton.setOnClickListener(new Button.OnClickListener() {// 添加录音按钮点击事件监听

					@Override
					public void onClick(View arg0) {
						startRecord();// 调用录音方法
					}
				});
		stopButton.setOnClickListener(new Button.OnClickListener() {// 添加停止按钮点击事件监听

					@Override
					public void onClick(View arg0) {
						stopRecord();// 调用停止录音方法
					}
				});
		listView.setOnItemClickListener(new OnItemClickListener() {// 为ListView添加点击监听

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent intent = new Intent();// 初始化Intent
						intent.setClass(Recording.this, Player.class);// 指定intent对象启动的类
						intent.putExtra("filePath", files[arg2].getPath());// 函数传递
						startActivity(intent);// 启动新的Activity
					}
				});
	}

	/**
	 * 为ListView填充数据
	 */
	public void setListViewData() {
		boolean sdStatus = getStorageState();// 调用获取手机SDCard的存储状态
		if (sdStatus) {// 判断SDCard的存储状态，如果是false,提示并结束本程序
			File sdCardFile = Environment.getExternalStorageDirectory();// 获取SDCard根目录File对象
			dirPath = sdCardFile.getPath() + File.separator + "recording";// 指定文件存放目录
			File dirFile = new File(dirPath);
			if (!dirFile.exists()) {// 判断文件存放目录是否存在
				dirFile.mkdir();// 创建文件存放目录
			}
			files = dirFile.listFiles();// 获取文件存放目录中的文件File对象
			List<HashMap<String, Object>> list = getList(files);// 调用获取相应的集合
			setAdapter(list, files);// 调用构造适配器并为ListView添加适配器
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
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,
				R.layout.file_list, new String[] { "file_name" },
				new int[] { R.id.fileName });// 实例化SimpleAdapter

		listView.setAdapter(simpleAdapter);// 为ListView添加适配器
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
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();// 创建AlertDialog对象
			alertDialog.setTitle("提示信息");// 设置信息标题
			alertDialog.setMessage("未安装SD卡，请检查你的设备");// 设置信息内容
			// 设置确定按钮，并添加按钮监听事件
			alertDialog.setButton("确定", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// MainActivity.this.finish();// 结束应用程序
				}
			});
			alertDialog.show();// 设置弹出提示框
			return false;
		}
	}

	/**
	 * 开始录音方法
	 */
	public void startRecord() {
		String path = getRecordFilePath();// 获取录音文件路径
		if (!"".equals(path)) {
			mediaRecorder = new MediaRecorder();// 实例化MediaRecorder
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);// 设置音频源
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);// 设置输出格式
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);// 设置音频编码器
			mediaRecorder.setOutputFile(path);// 设置输出路径

			// 文件录制错误监听
			mediaRecorder
					.setOnErrorListener(new MediaRecorder.OnErrorListener() {

						@Override
						public void onError(MediaRecorder arg0, int arg1,
								int arg2) {
							if (mediaRecorder != null) {
								// 解除资源与MediaRecorder的赋值关系,让资源可以为其它程序利用
								mediaRecorder.release();
							}
						}
					});
		}
		try {
			mediaRecorder.prepare();// 准备
			mediaRecorder.start();// 开始录音
			startButton.setEnabled(false);// 录音按钮失效
			stopButton.setEnabled(true);// 停止按钮生效
			startButton.setText("录音中...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 停止录音方法
	 */
	public void stopRecord() {
		if (mediaRecorder != null) {
			mediaRecorder.stop();// 停止录音
			mediaRecorder.release();// 释放资源
			startButton.setEnabled(true);// 录音按钮生效
			stopButton.setEnabled(false);// 停止按钮失效
			startButton.setText("录音");
			setListViewData();// 录音完成，重新为ListView填充数据
		}
	}

	/**
	 * 获取录音文件的路径
	 * 
	 * @return
	 */
	public String getRecordFilePath() {
		String filePath = "";// 声明文件路径
		boolean sdCardState = getStorageState();// 获取SDCard状态
		if (!sdCardState) {// 判断SDCard状态是否为非正常状态
			return filePath;// 返回空字符串路径
		}
		String sdCardPath = Environment.getExternalStorageDirectory().getPath();// 获取SDCard根目录路径
		File dirFile = new File(sdCardPath + File.separator + "recording");// 自定义录音文件夹的File对象
		if (!dirFile.exists()) {// 判断录音文件夹是否存在
			dirFile.mkdir();// 创建文件夹
		}
		try {
			// 创建一个前缀为test后缀为.amr的录音文件，createTempFile方法来创建是为了避免文件重复
			filePath = File.createTempFile("test", ".amr", dirFile)
					.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;// 返回录音文件路径
	}
}