package com.player;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Player extends Activity {
	private MediaPlayer mediaPlayer = null;// ����һ����MediaPlayer����
	private Button startButton = null;// ����Button�������
	private Button pauseButton = null;// ��ͣButton�������
	private Button stopButton = null;// ֹͣButton�������
	private TextView nameTextView = null;// �ļ�����TextView�������
	private boolean isPause = false;// �Ƿ���ͣ

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		nameTextView = (TextView) findViewById(R.id.mp3_name);// ʵ�����ļ�����TextView�������
		nameTextView.setText("dudong.mp3");// �����ļ�����
		startButton = (Button) findViewById(R.id.button_start);// ʵ��������Button�������
		startButton.setOnClickListener(new Button.OnClickListener() {// ��Ӳ��Ű�ť����¼�����

					@Override
					public void onClick(View arg0) {
						start();// ����MP3���ŷ���
					}
				});
		pauseButton = (Button) findViewById(R.id.button_pause);// ʵ������ͣButton�������
		pauseButton.setOnClickListener(new Button.OnClickListener() {// �����ͣ��ť����¼�����

					@Override
					public void onClick(View arg0) {
						pause();// ����MP3��ͣ���ŷ���
					}
				});
		stopButton = (Button) findViewById(R.id.button_stop);// ʵ����ֹͣButton�������
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
			if (mediaPlayer != null) {// �ж�MediaPlayer����Ϊ��
				if (mediaPlayer.isPlaying()) {// �ж�MediaPlayer�������ڲ����У�����ִ�����³���
					return;
				}
			}
			if (isPause) {// �ж�MediaPlayer�����Ƿ���ͣ�������ͣ�Ͳ����²���
				return;
			}
			mediaPlayer = new MediaPlayer();
			// �ļ�������ϼ����¼�
			mediaPlayer
					.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer arg0) {// �����ļ���������¼�
							// �����Դ��MediaPlayer�ĸ�ֵ��ϵ,����Դ����Ϊ������������
							mediaPlayer.release();
							startButton.setText("����");
							isPause = false;// ȡ����ͣ״̬
							mediaPlayer=null;
						}
					});
			// �ļ����Ŵ������
			mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
					// �����Դ��MediaPlayer�ĸ�ֵ��ϵ,����Դ����Ϊ������������
					mediaPlayer.release();
					isPause = false;// ȡ����ͣ״̬
					mediaPlayer=null;
					return false;
				}
			});
			String sdCard = Environment.getExternalStorageDirectory().getPath();
			mediaPlayer.setDataSource(sdCard + File.separator + "dudong.mp3");// ΪMediaPlayer��������Դ
			mediaPlayer.prepare();// ׼������
			mediaPlayer.start();// ��ʼ����
			startButton.setText("���ڲ���");
			pauseButton.setText("��ͣ");
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
					isPause = true;// ��ͣ״̬
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
				isPause = false;// ȡ����ͣ״̬
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}