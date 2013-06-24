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
	private SurfaceView surfaceView = null;// ����һ����SurfaceView����
	private SurfaceHolder surfaceHolder = null;// ����һ����SurfaceHolder����
	private Button startButton = null;// ������ʼ¼�ư�ť��Button�������
	private Button stopButton = null;// ����ֹͣ¼�ư�ť��Button�������
	private MediaRecorder mediaRecorder = null;// ����һ����MediaRecorder����
	private Camera camera = null;// ����һ����Camera����
	private boolean previewRunning = false;// Ԥ��״̬
	private File videoFile = null;// ¼����Ƶ�ļ���File����

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);// ��������Ϊ��͸��
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ����ȥ������
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ��������Ϊȫ��
		// ����setRequestedOrientation����תPreview
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.video);

		surfaceView = (SurfaceView) findViewById(R.id.surface_view);// ʵ����SurfaceView
		surfaceHolder = surfaceView.getHolder();// ��ȡSurfaceHolder
		surfaceHolder.addCallback(this);// ע��ʵ�ֺõ�Callback
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// ���û�������
		startButton = (Button) findViewById(R.id.start);// ʵ������ʼ¼�ư�ť��Button�������
		stopButton = (Button) findViewById(R.id.stop);// ʵ����ֹͣ¼�ư�ť��Button�������
		startButton.setEnabled(true);// ����ť��Ч
		stopButton.setEnabled(false);// ֹͣ��ťʧЧ
		// �������ť����¼�����
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startRecording();// ���ÿ�ʼ���񷽷�
			}
		});
		// ���ֹͣ��ť����¼�����
		stopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopRecording();// ����ֹͣ���񷽷�
			}
		});
	}

	/**
	 * ��ʼ���񷽷�
	 */
	public void startRecording() {
		try {
			stopCamera();// ����ֹͣCamera����
			if (!getStorageState()) {// �ж��Ƿ��д洢�������û�о͹ر�ҳ��
				VideoRecording.this.finish();// ����Ӧ�ó���
			}
			// ��ȡ�洢��(SDCard)�ĸ�Ŀ¼
			String sdCard = Environment.getExternalStorageDirectory().getPath();
			// ��ȡ��Ƭ���λ�õ�Ŀ¼
			String dirFilePath = sdCard + File.separator + "MyVideo";// ��SDCard�Ͻ���һ��MyVideo�ļ���
			File dirFile = new File(dirFilePath);// ��ȡ¼���ļ��е�·����File����
			if (!dirFile.exists()) {// �ж��ļ����Ƿ����
				dirFile.mkdir();// �����ļ���
			}
			videoFile = File.createTempFile("video", ".3gp", dirFile);// ����¼����Ƶ��ʱ�ļ�
			mediaRecorder = new MediaRecorder();// ��ʼ��MediaRecorder����
			mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());// Ԥ��
			mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// ��ƵԴ
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // ¼��ԴΪ��˷�
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);// �����ʽΪ3gp
			mediaRecorder.setVideoSize(480, 320);// ��Ƶ�ߴ�
			mediaRecorder.setVideoFrameRate(15);// ��Ƶ֡Ƶ��
			mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);// ��Ƶ����
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// ��Ƶ����
			mediaRecorder.setMaxDuration(10000);// �������
			mediaRecorder.setOutputFile(videoFile.getAbsolutePath());// ����·��
			mediaRecorder.prepare();// ׼��¼��
			mediaRecorder.start();// ��ʼ¼��
			// �ļ�¼�ƴ������
			mediaRecorder
					.setOnErrorListener(new MediaRecorder.OnErrorListener() {

						@Override
						public void onError(MediaRecorder arg0, int arg1,
								int arg2) {
							stopRecording();// ����ֹͣ���񷽷�
						}
					});
			startButton.setText("¼����");
			startButton.setEnabled(false);// ����ťʧЧ
			stopButton.setEnabled(true);// ֹͣ��ť��Ч
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ֹͣ���񷽷�
	 */
	public void stopRecording() {
		if (mediaRecorder != null) {// �ж�MediaRecorder�����Ƿ�Ϊ��
			mediaRecorder.stop();// ֹͣ����
			mediaRecorder.release();// �ͷ���Դ
			mediaRecorder = null;// �ÿ�MediaRecorder����
			startButton.setEnabled(true);// ����ť��Ч
			stopButton.setEnabled(false);// ֹͣ��ťʧЧ
			startButton.setText("¼��");
			isSave();// �����Ƿ񱣴淽������
		}
		stopCamera();// ����ֹͣCamera����
		prepareCamera();// ���ó�ʼ��Camera����
		startCamera();// ���ÿ�ʼCamera����

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
			// ������SurfaceView��Ϊ���ؾ�ͷȡ���������ʾ
			camera.setPreviewDisplay(surfaceHolder);
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

	/**
	 * �ֻ����������¼�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// �ж��ֻ����̰��µ��Ƿ��Ƿ��ؼ�
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			stopRecording();// ����ֹͣ���񷽷�
			Intent intent = new Intent();// ��ʼ��Intent
			intent.setClass(VideoRecording.this, Welcome.class);// ָ��intent������������
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // ����ý��̿ռ������Activity
			startActivity(intent);// �����µ�Activity
			VideoRecording.this.finish();// �������Activity
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �Ƿ񱣴�¼�Ƶ���Ƶ�ļ�
	 */
	public void isSave() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();// ����AlertDialog����
		alertDialog.setTitle("��ʾ��Ϣ");// ������Ϣ����
		alertDialog.setMessage("�Ƿ񱣴� " + videoFile.getName() + " ��Ƶ�ļ���");// ������Ϣ����
		// ����ȷ����ť������Ӱ�ť�����¼�
		alertDialog.setButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
		// ����ȡ����ť������Ӱ�ť�����¼�
		alertDialog.setButton2("ȡ��",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if (videoFile.exists()) {// �ж��ļ��Ƿ����
							videoFile.delete();// ɾ�����ļ�
						}
					}
				});
		alertDialog.show();// ���õ�����ʾ��
	}

	/**
	 * ��ȡ�ֻ�SDCard�Ĵ洢״̬
	 * 
	 * @return �ֻ�SDCard�Ĵ洢״̬(true/false)
	 */
	public boolean getStorageState() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {// �ж��ֻ�SDCard�Ĵ洢״̬
			return true;
		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();// ����AlertDialog����
			alertDialog.setTitle("��ʾ��Ϣ");// ������Ϣ����
			alertDialog.setMessage("δ��װSD������������豸");// ������Ϣ����
			// ����ȷ����ť������Ӱ�ť�����¼�
			alertDialog.setButton("ȷ��",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							VideoRecording.this.finish();// ����Ӧ�ó���
						}
					});
			alertDialog.show();// ���õ�����ʾ��
			return false;
		}
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
}