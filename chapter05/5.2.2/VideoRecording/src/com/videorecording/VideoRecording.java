package com.videorecording;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VideoRecording extends Activity implements SurfaceHolder.Callback {
	private SurfaceView surfaceView = null;// 创建一个空SurfaceView对象
	private SurfaceHolder surfaceHolder = null;// 创建一个空SurfaceHolder对象
	private Button startButton = null;// 创建开始录制按钮的Button组件对象
	private Button stopButton = null;// 创建停止录制按钮的Button组件对象
	private MediaRecorder mediaRecorder = null;// 创建一个空MediaRecorder对象
	private Camera camera = null;// 创建一个空Camera对象
	private boolean previewRunning = false;// 预览状态
	private File videoFile = null;// 录制视频文件的File对象

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);// 窗口设置为半透明
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 窗口去掉标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 窗口设置为全屏
		// 调用setRequestedOrientation来翻转Preview
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.video);

		surfaceView = (SurfaceView) findViewById(R.id.surface_view);// 实例化SurfaceView
		surfaceHolder = surfaceView.getHolder();// 获取SurfaceHolder
		surfaceHolder.addCallback(this);// 注册实现好的Callback
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 设置缓存类型
		startButton = (Button) findViewById(R.id.start);// 实例化开始录制按钮的Button组件对象
		stopButton = (Button) findViewById(R.id.stop);// 实例化停止录制按钮的Button组件对象
		startButton.setEnabled(true);// 摄像按钮生效
		stopButton.setEnabled(false);// 停止按钮失效
		// 添加摄像按钮点击事件监听
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startRecording();// 调用开始摄像方法
			}
		});
		// 添加停止按钮点击事件监听
		stopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopRecording();// 调用停止摄像方法
			}
		});
	}

	/**
	 * 开始摄像方法
	 */
	public void startRecording() {
		try {
			stopCamera();// 调用停止Camera方法
			if (!getStorageState()) {// 判断是否有存储卡，如果没有就关闭页面
				VideoRecording.this.finish();// 结束应用程序
			}
			// 获取存储卡(SDCard)的根目录
			String sdCard = Environment.getExternalStorageDirectory().getPath();
			// 获取相片存放位置的目录
			String dirFilePath = sdCard + File.separator + "MyVideo";// 在SDCard上建立一个MyVideo文件夹
			File dirFile = new File(dirFilePath);// 获取录制文件夹的路径的File对象
			if (!dirFile.exists()) {// 判断文件夹是否存在
				dirFile.mkdir();// 创建文件夹
			}
			videoFile = File.createTempFile("video", ".3gp", dirFile);// 创建录制视频临时文件
			mediaRecorder = new MediaRecorder();// 初始化MediaRecorder对象
			mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());// 预览
			mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// 视频源
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 录音源为麦克风
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);// 输出格式为3gp
			mediaRecorder.setVideoSize(480, 320);// 视频尺寸
			mediaRecorder.setVideoFrameRate(15);// 视频帧频率
			mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);// 视频编码
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 音频编码
			mediaRecorder.setMaxDuration(10000);// 最大期限
			mediaRecorder.setOutputFile(videoFile.getAbsolutePath());// 保存路径
			mediaRecorder.prepare();// 准备录制
			mediaRecorder.start();// 开始录制
			// 文件录制错误监听
			mediaRecorder
					.setOnErrorListener(new MediaRecorder.OnErrorListener() {

						@Override
						public void onError(MediaRecorder arg0, int arg1,
								int arg2) {
							stopRecording();// 调用停止摄像方法
						}
					});
			startButton.setText("录制中");
			startButton.setEnabled(false);// 摄像按钮失效
			stopButton.setEnabled(true);// 停止按钮生效
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 停止摄像方法
	 */
	public void stopRecording() {
		if (mediaRecorder != null) {// 判断MediaRecorder对象是否为空
			mediaRecorder.stop();// 停止摄像
			mediaRecorder.release();// 释放资源
			mediaRecorder = null;// 置空MediaRecorder对象
			startButton.setEnabled(true);// 摄像按钮生效
			stopButton.setEnabled(false);// 停止按钮失效
			startButton.setText("录制");
			isSave();// 调用是否保存方法保存
		}
		stopCamera();// 调用停止Camera方法
		prepareCamera();// 调用初始化Camera方法
		startCamera();// 调用开始Camera方法

	}

	/**
	 * 初始化Camera
	 */
	public void prepareCamera() {
		camera = Camera.open();// 初始化Camera
		try {
			camera.setPreviewDisplay(surfaceHolder);// 设置预览
		} catch (IOException e) {
			camera.release();// 释放相机资源
			camera = null;// 置空Camera对象
		}
	}

	/**
	 * 开始Camera
	 */
	public void startCamera() {
		if (previewRunning) {// 判断预览开启
			camera.stopPreview();// 停止预览
		}
		try {
			// 设置用SurfaceView作为承载镜头取景画面的显示
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();// 开始预览
			previewRunning = true;// 设置预览状态为true
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 停止Camera
	 */
	public void stopCamera() {
		if (camera != null) {// 判断Camera对象不为空
			camera.stopPreview();// 停止预览
			camera.release();// 释放摄像头资源
			camera = null;// 置空Camera对象
			previewRunning = false;// 设置预览状态为false
		}
	}

	/**
	 * 手机按键监听事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 判断手机键盘按下的是否是返回键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			stopRecording();// 调用停止摄像方法
			Intent intent = new Intent();// 初始化Intent
			intent.setClass(VideoRecording.this, Welcome.class);// 指定intent对象启动的类
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 清除该进程空间的所有Activity
			startActivity(intent);// 启动新的Activity
			VideoRecording.this.finish();// 销毁这个Activity
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 是否保存录制的视频文件
	 */
	public void isSave() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();// 创建AlertDialog对象
		alertDialog.setTitle("提示信息");// 设置信息标题
		alertDialog.setMessage("是否保存 " + videoFile.getName() + " 视频文件？");// 设置信息内容
		// 设置确定按钮，并添加按钮监听事件
		alertDialog.setButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
		// 设置取消按钮，并添加按钮监听事件
		alertDialog.setButton2("取消",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if (videoFile.exists()) {// 判断文件是否存在
							videoFile.delete();// 删除该文件
						}
					}
				});
		alertDialog.show();// 设置弹出提示框
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
			alertDialog.setButton("确定",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							VideoRecording.this.finish();// 结束应用程序
						}
					});
			alertDialog.show();// 设置弹出提示框
			return false;
		}
	}

	/**
	 * 当预览界面的格式和大小发生改变时，该方法被调用
	 */
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		startCamera();// 调用开始Camera方法
	}

	/**
	 * 初次实例化，预览界面被创建时，该方法被调用
	 */
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		prepareCamera();// 调用初始化Camera方法
	}

	/**
	 * 当预览界面被关闭时，该方法被调用
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		stopCamera();// 调用停止Camera方法
	}
}