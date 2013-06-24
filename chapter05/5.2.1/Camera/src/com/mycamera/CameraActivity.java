package com.mycamera;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {
	private SurfaceView surfaceView = null;// 创建一个SurfaceView组件对象
	private SurfaceHolder surfaceHolder = null;// 创建一个空SurfaceHolder对象
	private Camera camera = null;// 创建一个空Camera对象
	private boolean previewRunning = false;// 预览状态

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);// 窗口设置为半透明
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 窗口去掉标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 窗口设置为全屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.camera);// 调用setRequestedOrientation来翻转Preview
		surfaceView = (SurfaceView) findViewById(R.id.surface_camera);// 实例化SurfaceView对象
		surfaceHolder = surfaceView.getHolder();// 获取SurfaceHolder
		surfaceHolder.addCallback(this);// 注册实现好的Callback
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 设置缓存类型
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 判断手机键盘按下的是否是拍照键、轨迹球键
		if (keyCode == KeyEvent.KEYCODE_CAMERA
				|| keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			if (camera != null) {// 判断Camera对象是否不为空
				// 当按下相机按钮时，执行相机对象的takePicture()方法,该方法有三个回调对象做入参，不需要的时候可以设null
				camera.takePicture(null, null, jpegCallback);
				changeByTime(3000);// 调用延迟方法，3秒后重新预览拍照
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 延迟方法
	 * 
	 * @param time毫秒
	 */
	public void changeByTime(long time) {
		final Timer timer = new Timer();// 实例化Timer对象
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					stopCamera();// 调用停止Camera方法
					prepareCamera();// 调用初始化Camera方法
					startCamera();// 调用开始Camera方法
					timer.cancel();// 撤消计时器
					break;
				}
				super.handleMessage(msg);
			}

		};
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		};
		timer.schedule(task, time);// 设定运行任务的时间
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
			Camera.Parameters parameters = camera.getParameters();// 获得相机参数对象
			parameters.setPictureFormat(PixelFormat.JPEG);// 设置格式
			// 设置预览大小
			// parameters.setPreviewSize(480, 320);
			// 设置自动对焦
			// parameters.setFocusMode("auto");
			// 设置图片保存时的分辨率大小
			// parameters.setPictureSize(2048, 1536);
			camera.setParameters(parameters);// 给相机对象设置刚才设定的参数
			camera.setPreviewDisplay(surfaceHolder);// 设置用 SurfaceView作为承载镜头取景画面的显示
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

	// 照片拍摄之后的事件
	private PictureCallback jpegCallback = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// 获取存储卡(SDCard)的根目录
			String sdCard = Environment.getExternalStorageDirectory().getPath();
			// 获取相片存放位置的目录
			String dirFilePath = sdCard + File.separator + "MyCamera";
			// 获取当前时间的自定义字符串
			String date = (String) DateFormat.format(" yyyy-MM-dd hh-mm-ss",
					new Date());
			// onPictureTaken传入的第一个参数及为相片的byte，实例化Bitmap对象
			Bitmap bitmap = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
			try {
				File dirFile = new File(dirFilePath);// 创建相片存放位置的File对象
				if (!dirFile.exists()) {// 判断路径是否不存在
					dirFile.mkdir();// 创建该文件夹
				}
				// 创建一个前缀为photo，后缀为.jpg的图片文件，createTempFile方法来创建是为了避免文件重复
				File file = File.createTempFile("photo-", date + ".jpg",
						dirFile);
				BufferedOutputStream bOutputStream = new BufferedOutputStream(
						new FileOutputStream(file));
				// 采用压缩文件的方法
				bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bOutputStream);
				// 清除缓存，更新BufferedOutputStream
				bOutputStream.flush();
				// 关闭BufferedOutputStream
				bOutputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}