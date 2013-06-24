package com.AndroidBookProject2;

import java.net.URL;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;

public class CartListView extends Activity {
	private LinearLayout myListLayout;//声明LinearLayout类型变量
	private ListView tripListView;//声明ListView类型变量

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.viewcartlist);//加载布局资源文件
		myListLayout = (LinearLayout) this.findViewById(R.id.cartList);//获取资源文件中LinearLayout
		tripListView = new ListView(this);//创建ListView
		//创建布局参数
		LinearLayout.LayoutParams tripListViewParam = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		tripListView.setCacheColorHint(Color.WHITE);
		myListLayout.addView(tripListView, tripListViewParam);//将ListView添加到LinearLayout上
		showGoodsList();//获取商品列表
		
		Button accountsBut = (Button) this.findViewById(R.id.accountsBut);//加载资源文件中的Button
		accountsBut.setOnClickListener(new OnClickListener() {//Button的单击事件

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 判断用户是否登录				
				if (DataShare.user.getUid().equals("")) {				
					Intent intent = new Intent();
					intent.setClass(CartListView.this, ViewLogin.class);
					startActivity(intent);
					Toast.makeText(CartListView.this, "请先登录", Toast.LENGTH_LONG).show();
				} else {
					Intent intent = new Intent();
					intent.setClass(CartListView.this, BillDetailView.class);
					startActivity(intent);
				}
			}

		});
	}

	// 填充商品列表适配器
	public void showGoodsList() {
		SimpleAdapter adapter = new SimpleAdapter(this, getTripList(),
				R.layout.cartlistrow, new String[] { "img", "num", "name",
						"price" }, new int[] { R.id.cartImg, R.id.cartNum,
						R.id.cartName, R.id.cartPrice });
		tripListView.setAdapter(adapter);
		adapter.setViewBinder(new ViewBinder() {
			public boolean setViewValue(View arg0, Object arg1,
					String textRepresentation) {
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
	}

	
	public List<Map<String, Object>> getTripList() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < DataShare.shopList.size(); i += 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			Goods goods = DataShare.shopList.get(i);
			try {
				URL picUrl = new URL(goods.getDir() + "/" + goods.getPic());
				Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
				map.put("img", pngBM);
			} catch (Exception e) {
				e.printStackTrace();
			}

			map.put("num", "商品编号：" + goods.getGid());
			map.put("name", "商品名称：" + goods.getBrand());
			map.put("price", "￥" + goods.getPrice()+"  数量："+goods.getBuyCount());

			list.add(map);
		}

		return list;
	}

}
