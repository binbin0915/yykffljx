package com.player;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Player extends Activity {
	private MediaPlayer mediaPlayer = null;// 创建一个空MediaPlayer对象
	private Button startButton = null;// 播放Button组件对象
	private Button pauseButton = null;// 暂停Button组件对象
	private Button stopButton = null;// 停止Button组件对象
	private TextView nameTextView = null;// 文件名称TextView组件对象
	private boolean isPause = false;// 是否暂停

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		nameTextView = (TextView) findViewById(R.id.mp3_name);// 实例化文件名称TextView组件对象
		nameTextView.setText("http://5.gaosu.com/download/ring/000/086/8e2c23ec414592efdfad5353c21ce888.mp3");// 设置文件名称
		startButton = (Button) findViewById(R.id.button_start);// 实例化播放Button组件对象
		startButton.setOnClickListener(new Button.OnClickListener() {// 添加播放按钮点击事件监听

					@Override
					public void onClick(View arg0) {
						start();// 调用MP3播放方法
					}
				});
		pauseButton = (Button) findViewById(R.id.button_pause);// 实例化暂停Button组件对象
		pauseButton.setOnClickListener(new Button.OnClickListener() {// 添加暂停按钮点击事件监听

					@Override
					public void onClick(View arg0) {
						pause();// 调用MP3暂停播放方法
					}
				});
		stopButton = (Button) findViewById(R.id.button_stop);// 实例化停止Button组件对象
		stopButton.setOnClickListener(new Button.OnClickListener() {// 添加停止按钮点击事件监听

					@Override
					public void onClick(View arg0) {
						stop();// 调用MP3停止播放方法
					}
				});
	}

	/**
	 * MP3开始播放方法
	 */
	public void start() {
		try {
			if (mediaPlayer != null) {// 判断MediaPlayer对象不为空
				if (mediaPlayer.isPlaying()) {// 判断MediaPlayer对象正在播放中，并不执行以下程序
					return;
				}
			}
			if (isPause) {// 判断MediaPlayer对象是否暂停，如果暂停就不重新播放
				return;
			}
			mediaPlayer = new MediaPlayer();
			// 文件播放完毕监听事件
			mediaPlayer
					.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer arg0) {// 覆盖文件播出完毕事件
							// 解除资源与MediaPlayer的赋值关系,让资源可以为其它程序利用
							mediaPlayer.release();
							startButton.setText("播放");
							isPause = false;// 取消暂停状态
							mediaPlayer = null;
						}
					});
			// 文件播放错误监听
			mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
					// 解除资源与MediaPlayer的赋值关系,让资源可以为其它程序利用
					mediaPlayer.release();
					isPause = false;// 取消暂停状态
					mediaPlayer = null;
					return false;
				}
			});
			//MP3网络资源路径
			String path = "http://5.gaosu.com/download/ring/000/086/8e2c23ec414592efdfad5353c21ce888.mp3";
			mediaPlayer.setDataSource(path);// 为MediaPlayer设置数据源
			mediaPlayer.prepare();// 准备播放
			mediaPlayer.start();// 开始播放
			startButton.setText("正在播放");
			pauseButton.setText("暂停");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * MP3播放暂停方法
	 */
	public void pause() {
		try {
			if (mediaPlayer != null) {// 判断MediaPlayer对象不为空
				if (mediaPlayer.isPlaying()) {// 判断MediaPlayer对象正在播放中
					mediaPlayer.pause();// 暂停播放
					pauseButton.setText("取消暂停");
					isPause = true;// 暂停状态
				} else {
					mediaPlayer.start();// 开始播放
					pauseButton.setText("暂停");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * MP3停止播放方法
	 */
	public void stop() {
		try {
			if (mediaPlayer != null) {// 判断MediaPlayer对象不为空
				mediaPlayer.stop();// 停止播放
				startButton.setText("播放");
				pauseButton.setText("暂停");
				isPause = false;// 取消暂停状态
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}