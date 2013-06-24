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
	private SurfaceView surfaceView = null;// ����һ��SurfaceView�������
	private SurfaceHolder surfaceHolder = null;// ����һ����SurfaceHolder����
	private Camera camera = null;// ����һ����Camera����
	private boolean previewRunning = false;// Ԥ��״̬

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);// ��������Ϊ��͸��
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ����ȥ������
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ��������Ϊȫ��
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.camera);// ����setRequestedOrientation����תPreview
		surfaceView = (SurfaceView) findViewById(R.id.surface_camera);// ʵ����SurfaceView����
		surfaceHolder = surfaceView.getHolder();// ��ȡSurfaceHolder
		surfaceHolder.addCallback(this);// ע��ʵ�ֺõ�Callback
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// ���û�������
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// �ж��ֻ����̰��µ��Ƿ������ռ����켣���
		if (keyCode == KeyEvent.KEYCODE_CAMERA
				|| keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			if (camera != null) {// �ж�Camera�����Ƿ�Ϊ��
				// �����������ťʱ��ִ����������takePicture()����,�÷����������ص���������Σ�����Ҫ��ʱ�������null
				camera.takePicture(null, null, jpegCallback);
				changeByTime(3000);// �����ӳٷ�����3�������Ԥ������
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �ӳٷ���
	 * 
	 * @param time����
	 */
	public void changeByTime(long time) {
		final Timer timer = new Timer();// ʵ����Timer����
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					stopCamera();// ����ֹͣCamera����
					prepareCamera();// ���ó�ʼ��Camera����
					startCamera();// ���ÿ�ʼCamera����
					timer.cancel();// ������ʱ��
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
		timer.schedule(task, time);// �趨���������ʱ��
	}

	/**
	 * ��Ԥ������ĸ�ʽ�ʹ�С�����ı�ʱ���÷���������
	 */
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		startCamera();// ���ÿ�ʼCamera����
	}

	/**
	 * ����ʵ������Ԥ�����汻����ʱ���÷���������
	 */
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		prepareCamera();// ���ó�ʼ��Camera����
	}

	/**
	 * ��Ԥ�����汻�ر�ʱ���÷���������
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		stopCamera();// ����ֹͣCamera����
	}

	/**
	 * ��ʼ��Camera
	 */
	public void prepareCamera() {
		camera = Camera.open();// ��ʼ��Camera
		try {
			camera.setPreviewDisplay(surfaceHolder);// ����Ԥ��
		} catch (IOException e) {
			camera.release();// �ͷ������Դ
			camera = null;// �ÿ�Camera����
		}
	}

	/**
	 * ��ʼCamera
	 */
	public void startCamera() {
		if (previewRunning) {// �ж�Ԥ������
			camera.stopPreview();// ֹͣԤ��
		}
		try {
			Camera.Parameters parameters = camera.getParameters();// ��������������
			parameters.setPictureFormat(PixelFormat.JPEG);// ���ø�ʽ
			// ����Ԥ����С
			// parameters.setPreviewSize(480, 320);
			// �����Զ��Խ�
			// parameters.setFocusMode("auto");
			// ����ͼƬ����ʱ�ķֱ��ʴ�С
			// parameters.setPictureSize(2048, 1536);
			camera.setParameters(parameters);// ������������øղ��趨�Ĳ���
			camera.setPreviewDisplay(surfaceHolder);// ������ SurfaceView��Ϊ���ؾ�ͷȡ���������ʾ
			camera.startPreview();// ��ʼԤ��
			previewRunning = true;// ����Ԥ��״̬Ϊtrue
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ֹͣCamera
	 */
	public void stopCamera() {
		if (camera != null) {// �ж�Camera����Ϊ��
			camera.stopPreview();// ֹͣԤ��
			camera.release();// �ͷ�����ͷ��Դ
			camera = null;// �ÿ�Camera����
			previewRunning = false;// ����Ԥ��״̬Ϊfalse
		}
	}

	// ��Ƭ����֮����¼�
	private PictureCallback jpegCallback = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// ��ȡ�洢��(SDCard)�ĸ�Ŀ¼
			String sdCard = Environment.getExternalStorageDirectory().getPath();
			// ��ȡ��Ƭ���λ�õ�Ŀ¼
			String dirFilePath = sdCard + File.separator + "MyCamera";
			// ��ȡ��ǰʱ����Զ����ַ���
			String date = (String) DateFormat.format(" yyyy-MM-dd hh-mm-ss",
					new Date());
			// onPictureTaken����ĵ�һ��������Ϊ��Ƭ��byte��ʵ����Bitmap����
			Bitmap bitmap = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
			try {
				File dirFile = new File(dirFilePath);// ������Ƭ���λ�õ�File����
				if (!dirFile.exists()) {// �ж�·���Ƿ񲻴���
					dirFile.mkdir();// �������ļ���
				}
				// ����һ��ǰ׺Ϊphoto����׺Ϊ.jpg��ͼƬ�ļ���createTempFile������������Ϊ�˱����ļ��ظ�
				File file = File.createTempFile("photo-", date + ".jpg",
						dirFile);
				BufferedOutputStream bOutputStream = new BufferedOutputStream(
						new FileOutputStream(file));
				// ����ѹ���ļ��ķ���
				bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bOutputStream);
				// ������棬����BufferedOutputStream
				bOutputStream.flush();
				// �ر�BufferedOutputStream
				bOutputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}