package com.file;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	private ListView listView = null;// ������ʾ�ļ��б��ListView�������
	private File[] files = null;// File����
	private Button createButton = null;// ������ť��Button�������
	private String dirPath = "";// �ļ���/дָ��Ŀ¼

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//Ϊ��ǰ���Activity����һ����ͼ
		listView = (ListView) findViewById(R.id.listView);// ʵ����ListView�������
		createButton = (Button) findViewById(R.id.createButton);// ʵ����Button�������
		setData();// ��������
		listView.setOnItemClickListener(new OnItemClickListener() {// ΪListView��ӵ������

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent intent = new Intent();// ��ʼ��Intent
						intent.setClass(MainActivity.this, ShowActivity.class);// ָ��intent������������
						intent.putExtra("filePath", files[arg2].getPath());// ��������
						startActivity(intent);// �����µ�Activity
					}
				});

		createButton.setOnClickListener(new Button.OnClickListener() {//ΪButton��ӵ������

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();// ��ʼ��Intent
				intent.setClass(MainActivity.this, CreateActivity.class);// ָ��intent������������
				intent.putExtra("dirPath", dirPath);// ��������
				startActivity(intent);// �����µ�Activity
			}
		});
	}

	/**
	 * �������
	 */
	public void setData() {
		boolean sdStatus = getStorageState();// ���û�ȡ�ֻ�SDCard�Ĵ洢״̬
		if (!sdStatus) {// �ж�SDCard�Ĵ洢״̬�������false,��ʾ������������
			AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
					.create();// ����AlertDialog����
			alertDialog.setTitle("��ʾ��Ϣ");// ������Ϣ����
			alertDialog.setMessage("δ��װSD������������豸");// ������Ϣ����
			// ����ȷ����ť������Ӱ�ť�����¼�
			alertDialog.setButton("ȷ��", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					MainActivity.this.finish();// ����Ӧ�ó���
				}
			});
			alertDialog.show();// ���õ�����ʾ��
		}

		File sdCardFile = Environment.getExternalStorageDirectory();// ��ȡSDCard��Ŀ¼File����
		dirPath = sdCardFile.getPath() + File.separator + "FileIO";//ָ���ļ����Ŀ¼
		File dirFile = new File(dirPath);
		if (!dirFile.exists()) {//�ж��ļ����Ŀ¼�Ƿ����
			dirFile.mkdir();//�����ļ����Ŀ¼
		}
		files = dirFile.listFiles();//��ȡ�ļ����Ŀ¼�е��ļ�File����
		List<HashMap<String, Object>> list = getList(files);// ���û�ȡ��Ӧ�ļ���
		setAdapter(list, files);// ���ù�����������ΪListView���������
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
			return false;
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
		SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this,
				list, R.layout.file_list, new String[] { "file_name" },
				new int[] { R.id.fileName });// ʵ����SimpleAdapter

		listView.setAdapter(simpleAdapter);// ΪListView���������
	}
}