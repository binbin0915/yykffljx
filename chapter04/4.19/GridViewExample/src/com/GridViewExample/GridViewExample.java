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

	private GridView myGridView;// 声明GridView类型变量
	private List<ResolveInfo> myAppIcon;// 声明变量，存放桌面应用程序图标

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);// 加载布局资源

		myGridView = (GridView) findViewById(R.id.myGrid);// 获取资源文件中的GridView组件
		loadAppIcon();// 加载桌面图标
		BaseAdapter adapter = new BaseAdapter() {// 声明BaseAdapter对象，实现抽象方法
			@Override
			public int getCount() {// 项目个数
				// TODO Auto-generated method stub
				return myAppIcon.size();
			}

			@Override
			public Object getItem(int position) {// 获取指定位置的项目
				// TODO Auto-generated method stub
				return myAppIcon.get(position);
			}

			@Override
			public long getItemId(int position) {// 获取指定位置项目id
				// TODO Auto-generated method stub
				return position;
			}

			// 定义每一项显示的内容
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				ImageView imageView;

				if (convertView == null) {
					imageView = new ImageView(GridViewExample.this);// 创建ImageView对象
					// 设置图片的填充方式,这里为按比例拉伸图片
					imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
					// 设置imageView的大小为50*50
					imageView
							.setLayoutParams(new GridView.LayoutParams(50, 50));
				} else {
					imageView = (ImageView) convertView;
				}
				// 获取myAppIcon中下标为position的ResolveInfo
				ResolveInfo info = myAppIcon.get(position);
				// 设置imageView显示的图片
				imageView.setImageDrawable(info.activityInfo
						.loadIcon(getPackageManager()));

				return imageView;
			}

		};
		myGridView.setAdapter(adapter);// 为myGridView添加适配器
	}

	/**
	 * 加载桌面图标
	 */
	private void loadAppIcon() {
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);// 创建Intent
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);// 添加桌面应用程序列表到Intent中

		myAppIcon = getPackageManager().queryIntentActivities(mainIntent, 0);
	}
}