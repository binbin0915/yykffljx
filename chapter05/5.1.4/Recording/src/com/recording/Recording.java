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
	private Button startButton = null;// ����Button�������
	private Button stopButton = null;// ֹͣButton�������
	private ListView listView = null;// ������ʾ�ļ��б��ListView�������
	private File[] files = null;// File����
	private String dirPath = "";// �ļ���/дָ��Ŀ¼
	private MediaRecorder mediaRecorder = null;// ����һ����MediaRecorder����

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recording);

		startButton = (Button) findViewById(R.id.button_start);// ʵ��������Button�������
		stopButton = (Button) findViewById(R.id.button_stop);// ʵ����ֹͣButton�������
		listView = (ListView) findViewById(R.id.listView);// ʵ����ListView�������
		stopButton.setEnabled(false);// ֹͣ��ťʧЧ
		setListViewData();// ΪListView�������
		startButton.setOnClickListener(new Button.OnClickListener() {// ���¼����ť����¼�����

					@Override
					public void onClick(View arg0) {
						startRecord();// ����¼������
					}
				});
		stopButton.setOnClickListener(new Button.OnClickListener() {// ���ֹͣ��ť����¼�����

					@Override
					public void onClick(View arg0) {
						stopRecord();// ����ֹͣ¼������
					}
				});
		listView.setOnItemClickListener(new OnItemClickListener() {// ΪListView��ӵ������

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent intent = new Intent();// ��ʼ��Intent
						intent.setClass(Recording.this, Player.class);// ָ��intent������������
						intent.putExtra("filePath", files[arg2].getPath());// ��������
						startActivity(intent);// �����µ�Activity
					}
				});
	}

	/**
	 * ΪListView�������
	 */
	public void setListViewData() {
		boolean sdStatus = getStorageState();// ���û�ȡ�ֻ�SDCard�Ĵ洢״̬
		if (sdStatus) {// �ж�SDCard�Ĵ洢״̬�������false,��ʾ������������
			File sdCardFile = Environment.getExternalStorageDirectory();// ��ȡSDCard��Ŀ¼File����
			dirPath = sdCardFile.getPath() + File.separator + "recording";// ָ���ļ����Ŀ¼
			File dirFile = new File(dirPath);
			if (!dirFile.exists()) {// �ж��ļ����Ŀ¼�Ƿ����
				dirFile.mkdir();// �����ļ����Ŀ¼
			}
			files = dirFile.listFiles();// ��ȡ�ļ����Ŀ¼�е��ļ�File����
			List<HashMap<String, Object>> list = getList(files);// ���û�ȡ��Ӧ�ļ���
			setAdapter(list, files);// ���ù�����������ΪListView���������
		}
	}

	/**
	 * ����File[]��ȡ��Ӧ�ļ���
	 * 
	 * @param files
	 *            File����
	 * @return List<HashMap<String, Object>>����
	 */
	public List<HashMap<String, Object>> getList(File[] files) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();// ����List����
		for (int i = 0; i < files.length; i++) {// ѭ��File����
			HashMap<String, Object> hashMap = new HashMap<String, Object>();// ����HashMap
			hashMap.put("file_name", files[i].getName());// ��HashMap������ļ���
			list.add(hashMap);// ��HashMap��ӵ�List����
		}
		return list;// ����List����
	}

	/**
	 * ������������ΪListView���������
	 * 
	 * @param list
	 *            HashMap�ļ���
	 * @param files
	 *            File����
	 */
	public void setAdapter(List<HashMap<String, Object>> list, File[] files) {
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,
				R.layout.file_list, new String[] { "file_name" },
				new int[] { R.id.fileName });// ʵ����SimpleAdapter

		listView.setAdapter(simpleAdapter);// ΪListView���������
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
			alertDialog.setButton("ȷ��", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// MainActivity.this.finish();// ����Ӧ�ó���
				}
			});
			alertDialog.show();// ���õ�����ʾ��
			return false;
		}
	}

	/**
	 * ��ʼ¼������
	 */
	public void startRecord() {
		String path = getRecordFilePath();// ��ȡ¼���ļ�·��
		if (!"".equals(path)) {
			mediaRecorder = new MediaRecorder();// ʵ����MediaRecorder
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);// ������ƵԴ
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);// ���������ʽ
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);// ������Ƶ������
			mediaRecorder.setOutputFile(path);// �������·��

			// �ļ�¼�ƴ������
			mediaRecorder
					.setOnErrorListener(new MediaRecorder.OnErrorListener() {

						@Override
						public void onError(MediaRecorder arg0, int arg1,
								int arg2) {
							if (mediaRecorder != null) {
								// �����Դ��MediaRecorder�ĸ�ֵ��ϵ,����Դ����Ϊ������������
								mediaRecorder.release();
							}
						}
					});
		}
		try {
			mediaRecorder.prepare();// ׼��
			mediaRecorder.start();// ��ʼ¼��
			startButton.setEnabled(false);// ¼����ťʧЧ
			stopButton.setEnabled(true);// ֹͣ��ť��Ч
			startButton.setText("¼����...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ֹͣ¼������
	 */
	public void stopRecord() {
		if (mediaRecorder != null) {
			mediaRecorder.stop();// ֹͣ¼��
			mediaRecorder.release();// �ͷ���Դ
			startButton.setEnabled(true);// ¼����ť��Ч
			stopButton.setEnabled(false);// ֹͣ��ťʧЧ
			startButton.setText("¼��");
			setListViewData();// ¼����ɣ�����ΪListView�������
		}
	}

	/**
	 * ��ȡ¼���ļ���·��
	 * 
	 * @return
	 */
	public String getRecordFilePath() {
		String filePath = "";// �����ļ�·��
		boolean sdCardState = getStorageState();// ��ȡSDCard״̬
		if (!sdCardState) {// �ж�SDCard״̬�Ƿ�Ϊ������״̬
			return filePath;// ���ؿ��ַ���·��
		}
		String sdCardPath = Environment.getExternalStorageDirectory().getPath();// ��ȡSDCard��Ŀ¼·��
		File dirFile = new File(sdCardPath + File.separator + "recording");// �Զ���¼���ļ��е�File����
		if (!dirFile.exists()) {// �ж�¼���ļ����Ƿ����
			dirFile.mkdir();// �����ļ���
		}
		try {
			// ����һ��ǰ׺Ϊtest��׺Ϊ.amr��¼���ļ���createTempFile������������Ϊ�˱����ļ��ظ�
			filePath = File.createTempFile("test", ".amr", dirFile)
					.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;// ����¼���ļ�·��
	}
}