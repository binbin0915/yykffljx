package com.tyhj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 服务区列表窗体
 * */
public class DZDealersList extends Activity {
	private LinearLayout dealerListLayout;// 声明LinearLayout变量
	private ListView myListView;// 声明ListView变量
	private List<Beetle> beetleList = new ArrayList();// 声明List变量
	private ImageButton searchButt;// 声明ImageButton变量
	private TextView searchPro;// 声明TextView变量
	private TextView searchCity;// 声明TextView变量
	private ProgressDialog myDialog;// 声明ProgressDialog变量
	private TextView dealerPro;// 声明变量
	private String pro = "";// 声明String变量
	private String city = "";// 声明String变量

	/**
	 * 重写Activity中的onCreate的方法。 该方法是在Activity创建时被系统调用，是一个Activity生命周期的开始。
	 * 
	 * @param savedInstanceState
	 *            ：保存Activity的状态的。 Bundle类型的数据与Map类型的数据相似，都是以key-value的形式存储数据的
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.title);// 设置标题
		this.setContentView(R.layout.dzdealerslist);// 加载布局资源
		setDealersList();
	}

	/**
	 * 获取服务区列表
	 */
	public void setDealersList() {
		// 动态生成服务器列表
		dealerListLayout = (LinearLayout) this
				.findViewById(R.id.dealerListLayout);// 获取服务区列表的LinearLayout
		myListView = new ListView(this);// 创建ListView对象
		// 创建布局参数
		LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		myListView.setCacheColorHint(Color.WHITE);// 设置myListView高亮显示颜色，默认黑色
		dealerListLayout.addView(myListView, param3);// 为dealerListLayout添加myListView组件，布局参数为param3
		setDealersAdapter();// 设置myListView的adapter方法
		// myListView的下拉项单击事件
		myListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent();// 实例化Intent
				Bundle beanMsg = new Bundle();// 实例化Bundle
				it.setClass(DZDealersList.this, DZDealersDetail.class);// 设置Class
				beanMsg.putSerializable("beanObj", (Serializable) beetleList
						.get(arg2));// 将服务器对象保存到beanMsg中
				it.putExtras(beanMsg);// 将该对象作为参数传递给下一个窗体
				startActivity(it);// 启动Activity
			}

		});
		dealerPro = (TextView) this.findViewById(R.id.dealerPro);// 获取服务器的省份TextView组件
		dealerPro.setOnClickListener(new OnClickListener() {// 省份点击事件
					public void onClick(View v) {
						prePro();
					}
				});

		searchPro = (TextView) this.findViewById(R.id.dealerPro);// 获取省市TextView组件

		searchButt = (ImageButton) this.findViewById(R.id.dealerSearch);// 搜索按钮ImageButton组件
		searchButt.setOnClickListener(new OnClickListener() {// 搜索按钮单击事件

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						get4S();
					}

				});
		get4S();
	}

	/**
	 * 请求数据，获取服务器信息
	 */
	public void get4S() {
		myDialog = ProgressDialog.show(DZDealersList.this, "请稍等...",
				"数据检索中...", true);
		new Thread() {
			public void run() {
				try {
					pro = searchPro.getText().toString().trim();
					beetleList = new WAnalysisFile().searchBeetlsByKeyword(
							DZDealersList.this, "", pro);
					Message m = new Message();
					poiHandler.sendMessage(m);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					myDialog.dismiss();
				}
			}
		}.start();
	}

	// 兴趣点详情Handler
	Handler poiHandler = new Handler() {
		public void handleMessage(Message msg) {
			setDealersAdapter();
		}
	};

	/**
	 * 设置adapter
	 */
	private void setDealersAdapter() {
		OverrideAdapter adapter = new OverrideAdapter(DZDealersList.this,
				getData(), R.layout.dzdealerslistrow, new String[] {
						"dealerName", "dealerAddr", "dealerTel", }, new int[] {
						R.id.dealersName, R.id.dealersAddr, R.id.dealersTel });
		myListView.setAdapter(adapter);

	}

	/**
	 * 获取服务器列表数据
	 * 
	 * @return
	 */
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < beetleList.size(); i += 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			Beetle s4 = beetleList.get(i);
			map.put("dealerName", s4.getName());
			map.put("dealerAddr", s4.getAddress());
			map.put("dealerTel", s4.getTel().replace("  ", ","));
			list.add(map);
		}
		return list;
	}

	/**
	 * 省市下拉菜单
	 */
	public void prePro() {
		final String temp[] = StaticString.pro;
		new AlertDialog.Builder(DZDealersList.this).setTitle("选择").setItems(
				temp, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichcountry) {
						dealerPro.setText("  " + temp[whichcountry]);
					}
				}).setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface d, int which) {
						d.dismiss();
					}
				}).show();
	}
}
