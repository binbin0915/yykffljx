package com.GridViewExample;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewExample extends Activity {

	private GridView myGridView;// ����GridView���ͱ���
	private List<ResolveInfo> myAppIcon;// �����������������Ӧ�ó���ͼ��

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);// ���ز�����Դ

		myGridView = (GridView) findViewById(R.id.myGrid);// ��ȡ��Դ�ļ��е�GridView���
		loadAppIcon();// ��������ͼ��
		BaseAdapter adapter = new BaseAdapter() {// ����BaseAdapter����ʵ�ֳ��󷽷�
			@Override
			public int getCount() {// ��Ŀ����
				// TODO Auto-generated method stub
				return myAppIcon.size();
			}

			@Override
			public Object getItem(int position) {// ��ȡָ��λ�õ���Ŀ
				// TODO Auto-generated method stub
				return myAppIcon.get(position);
			}

			@Override
			public long getItemId(int position) {// ��ȡָ��λ����Ŀid
				// TODO Auto-generated method stub
				return position;
			}

			// ����ÿһ����ʾ������
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				ImageView imageView;

				if (convertView == null) {
					imageView = new ImageView(GridViewExample.this);// ����ImageView����
					// ����ͼƬ����䷽ʽ,����Ϊ����������ͼƬ
					imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
					// ����imageView�Ĵ�СΪ50*50
					imageView
							.setLayoutParams(new GridView.LayoutParams(50, 50));
				} else {
					imageView = (ImageView) convertView;
				}
				// ��ȡmyAppIcon���±�Ϊposition��ResolveInfo
				ResolveInfo info = myAppIcon.get(position);
				// ����imageView��ʾ��ͼƬ
				imageView.setImageDrawable(info.activityInfo
						.loadIcon(getPackageManager()));

				return imageView;
			}

		};
		myGridView.setAdapter(adapter);// ΪmyGridView���������
	}

	/**
	 * ��������ͼ��
	 */
	private void loadAppIcon() {
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);// ����Intent
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);// �������Ӧ�ó����б�Intent��

		myAppIcon = getPackageManager().queryIntentActivities(mainIntent, 0);
	}
}