package com.tyhj;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

/**
 * 线路列表窗体
 * */
public class TripList extends Activity {
	private LinearLayout tripListLayout;// 声明LinearLayout对象
	private ListView myListView;// 声明ListView对象
	private TextView tripName;// 声明TextView对象
	private List<Route> tripLists;// 声明List对象

	/**
	 * 重写Activity中的onCreate的方法。 该方法是在Activity创建时被系统调用，是一个Activity生命周期的开始。	 * 
	 * @param savedInstanceState
	 *            ：保存Activity的状态的。 Bundle类型的数据与Map类型的数据相似，都是以key-value的形式存储数据的
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.title);// 设置窗体标题，为String.xml中title的值
		this.setContentView(R.layout.triplist);// 设置布局资源
		setTripPoiList();// 显示线路列表方法
	}

	/**
	 * 显示线路列表
	 * 
	 * @param
	 * @return
	 */
	public void setTripPoiList() {
		tripName = (TextView) this.findViewById(R.id.tripName);// 从triplist.xml中获取名字为tripName的TextView对象
		tripName.setText("精品旅游线路推荐");// 设置tripName显示文字
		tripListLayout = (LinearLayout) this.findViewById(R.id.tripListLayout);// 从triplist.xml中获取名字为tripListLayout的LinearLayout对象
		myListView = new ListView(this);// 动态创建ListView对象
		LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(
				// 设置布局参数
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		myListView.setCacheColorHint(Color.WHITE);// 列表拖拽过程中高亮颜色，默认是黑色
		tripListLayout.addView(myListView, param3);// 将myListView添加到tripListLayout上，按照param3方式布局
		// 创建适配器
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.trippoilistrow,
				new String[] { "title", "tel", "img", }, new int[] {
						R.id.poiTitle, R.id.poiTel, R.id.poiImg });
		myListView.setAdapter(adapter);// 为myListView添加适配器
		// setViewBinder()方法设置binder用于绑定数据到视图 参数：用于绑定数据到视图的binder
		adapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View arg0, Object arg1,
					String textRepresentation) {
				// TODO Auto-generated method stub
				if ((arg0 instanceof ImageView) & (arg1 instanceof Bitmap)) {
					ImageView imageView = (ImageView) arg0;
					Bitmap bitmap = (Bitmap) arg1;
					imageView.setImageBitmap(bitmap);
					return true;
				} else {
					return false;
				}

			}
		});

		// ListView列表项点击事件
		myListView.setOnItemClickListener(new OnItemClickListener() {
			// position 指在ListView里的位置 ，id是view的资源id。
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent it = new Intent();// 实例化Intent
				Bundle poiMsg = new Bundle(); // 实例化Bundle
				it.setClass(TripList.this, TripDetail.class);// 设置Class
				// 将当前ListView被点击的项目的Route对象放到Bundle中
				poiMsg.putSerializable("tripObj", (Serializable) tripLists
						.get(position));
				it.putExtras(poiMsg);// 将该对象作为参数传递给下一个窗体，即TripDetail
				startActivity(it);// 启动Activity
			}

		});
	}

	/**
	 * 获取线路信息
	 * 
	 * @param
	 * @return
	 */
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 创建List对象

		WAnalysisFile readFile = new WAnalysisFile();// 创建WAnalysisFile自定义类对象
		tripLists = readFile.sortBigRoute(TripList.this);// 排序，按大路书名称

		for (int i = 0; i < tripLists.size(); i += 1) {// 循环获取路书信息
			Map<String, Object> map = new HashMap<String, Object>();
			Route trip = tripLists.get(i);
			map.put("title", trip.getName() + "，" + trip.getMileage() + "公里");

			map.put("tel", trip.getCity());

			String tripImg = trip.getImage();
			InputStream iso = null;
			try {
				iso = this.getAssets().open(trip.getId() + "/" + tripImg);// 获取
																			// assets下图片
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Bitmap bitmap = null;
			bitmap = BitmapFactory.decodeStream(iso);
			map.put("img", bitmap);

			list.add(map);
		}
		return list;
	}

}
