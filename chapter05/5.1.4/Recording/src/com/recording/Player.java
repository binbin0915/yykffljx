package com.recording;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Player extends Activity {
	private MediaPlayer mediaPlayer = null;// ����һ����MediaPlayer����
	private Button startButton = null;// ����Button�������
	private Button pauseButton = null;// ��ͣButton�������
	private Button stopButton = null;// ֹͣButton�������
	private TextView nameTextView = null;// �ļ�����TextView�������
	private String path = "";// ý���ļ�����·��

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		path = getIntent().getExtras().getString("filePath");// ��ȡ�ļ�·��
		nameTextView = (TextView) findViewById(R.id.mp3_name);// ʵ�����ļ�����TextView�������
		startButton = (Button) findViewById(R.id.button_start);// ʵ��������Button�������
		pauseButton = (Button) findViewById(R.id.button_pause);// ʵ������ͣButton�������
		stopButton = (Button) findViewById(R.id.button_stop);// ʵ����ֹͣButton�������
		nameTextView.setText(new File(path).getName());// �����ļ�����
		startButton.setEnabled(true);//���ÿ�ʼ���Ű�ť��Ч
		pauseButton.setEnabled(false);//������ͣ���Ű�ťʧЧ
		stopButton.setEnabled(false);//����ֹͣ���Ű�ťʧЧ
		startButton.setOnClickListener(new Button.OnClickListener() {// ��Ӳ��Ű�ť����¼�����

					@Override
					public void onClick(View arg0) {
						start();// ����MP3���ŷ���
					}
				});
		pauseButton.setOnClickListener(new Button.OnClickListener() {// �����ͣ��ť����¼�����

					@Override
					public void onClick(View arg0) {
						pause();// ����MP3��ͣ���ŷ���
					}
				});
		stopButton.setOnClickListener(new Button.OnClickListener() {// ���ֹͣ��ť����¼�����

					@Override
					public void onClick(View arg0) {
						stop();// ����MP3ֹͣ���ŷ���
					}
				});
	}

	/**
	 * MP3��ʼ���ŷ���
	 */
	public void start() {
		try {
			mediaPlayer = new MediaPlayer();//ʵ����MediaPlayer
			mediaPlayer.setDataSource(path);// ΪMediaPlayer��������Դ
			mediaPlayer.prepare();// ׼������
			mediaPlayer.start();// ��ʼ����
			startButton.setEnabled(false);//���ÿ�ʼ���Ű�ťʧЧ
			pauseButton.setEnabled(true);//������ͣ���Ű�ť��Ч
			stopButton.setEnabled(true);//����ֹͣ���Ű�ť��Ч
			startButton.setText("���ڲ���");
			pauseButton.setText("��ͣ");
			// �ļ�������ϼ����¼�
			mediaPlayer
					.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer arg0) {// �����ļ���������¼�
							// �����Դ��MediaPlayer�ĸ�ֵ��ϵ,����Դ����Ϊ������������
							mediaPlayer.release();
							startButton.setText("����");
							startButton.setEnabled(true);//���ÿ�ʼ���Ű�ť��Ч
							pauseButton.setEnabled(false);//������ͣ���Ű�ťʧЧ
							stopButton.setEnabled(false);//����ֹͣ���Ű�ťʧЧ
						}
					});
			// �ļ����Ŵ������
			mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
					// �����Դ��MediaPlayer�ĸ�ֵ��ϵ,����Դ����Ϊ������������
					mediaPlayer.release();
					startButton.setEnabled(true);//���ÿ�ʼ���Ű�ť��Ч
					pauseButton.setEnabled(false);//������ͣ���Ű�ťʧЧ
					stopButton.setEnabled(false);//����ֹͣ���Ű�ťʧЧ
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * MP3������ͣ����
	 */
	public void pause() {
		try {
			if (mediaPlayer != null) {// �ж�MediaPlayer����Ϊ��
				if (mediaPlayer.isPlaying()) {// �ж�MediaPlayer�������ڲ�����
					mediaPlayer.pause();// ��ͣ����
					pauseButton.setText("ȡ����ͣ");
				} else {
					mediaPlayer.start();// ��ʼ����
					pauseButton.setText("��ͣ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * MP3ֹͣ���ŷ���
	 */
	public void stop() {
		try {
			if (mediaPlayer != null) {// �ж�MediaPlayer����Ϊ��
				mediaPlayer.stop();// ֹͣ����
				startButton.setText("����");
				pauseButton.setText("��ͣ");
				startButton.setEnabled(true);//���ÿ�ʼ���Ű�ť��Ч
				pauseButton.setEnabled(false);//������ͣ���Ű�ťʧЧ
				stopButton.setEnabled(false);//����ֹͣ���Ű�ťʧЧ
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}