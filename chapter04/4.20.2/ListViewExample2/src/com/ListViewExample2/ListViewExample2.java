package com.ListViewExample2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewExample2 extends Activity {
	private LinearLayout myListLayout;// 声明LinearLayout类型变量
	private ListView tripListView;// 声明ListView类型变量
	//创建list对象，用来存放列表项每一行的Map信息
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);// 加载布局资源
		myListLayout = (LinearLayout) this.findViewById(R.id.myListView);//获取LinearLayout对象

		tripListView = new ListView(this);//创建ListView对象
		//创建布局参数
		LinearLayout.LayoutParams tripListViewParam = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		tripListView.setCacheColorHint(Color.WHITE);//当拖拽列表时，显示的颜色，默认为黑色，这里设置为白色
		myListLayout.addView(tripListView, tripListViewParam);//将列表tripListView添加到流式布局myListLayout中
		//构建SimpleAdapter对象，构造函数共有5个参数，含义分别为：
		//第一个参数：上下文Context
		//第二个参数：每一行的布局资源文件，这里自定义的列表项布局文件
		//第三个参数：HashMap中的key信息img，name，money，zhe
		//第四个参数：listviewrow.xml中的组件id
		SimpleAdapter adapter = new SimpleAdapter(this, getTripListData(),
				R.layout.listviewrow, new String[] { "img", "name", "money",
						"zhe" }, new int[] { R.id.tripImg, R.id.phoneName,
						R.id.phoneMoney, R.id.phoneDiscount });
		tripListView.setAdapter(adapter);//为列表tripListView添加适配器
		//列表项的单击事件
		tripListView.setOnItemClickListener(new OnItemClickListener() {
			/* 点击列表项时触发onItemClick方法，四个参数含义分别为
			 * arg0：发生单击事件的AdapterView
			 * arg1：AdapterView中被点击的View
			 * position：当前点击的行在adapter的下标
			 * id：当前点击的行的id
			 */
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(ListViewExample2.this,
						"您选择的是" + list.get(position).get("name").toString(),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	
	/**
	 * 功能：获取列表项显示的数据
	 * @return List
	 */
	public List<Map<String, Object>> getTripListData() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("img", R.drawable.moto);
		map.put("name", "摩托罗拉（motorola）XT711 3G手机");
		map.put("money", "2699元");
		map.put("zhe", "9折");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.iphone);
		map.put("name", "iPhone4 16G版");
		map.put("money", "5880元");
		map.put("zhe", "8.5折");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.samsung);
		map.put("name", "三星（SAMSUNG）i9003 3G手机");
		map.put("money", "3099元");
		map.put("zhe", "9折");
		list.add(map);

		return list;
	}
}