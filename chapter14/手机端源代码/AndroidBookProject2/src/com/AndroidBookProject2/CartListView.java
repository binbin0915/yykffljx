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
	private LinearLayout myListLayout;//����LinearLayout���ͱ���
	private ListView tripListView;//����ListView���ͱ���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.viewcartlist);//���ز�����Դ�ļ�
		myListLayout = (LinearLayout) this.findViewById(R.id.cartList);//��ȡ��Դ�ļ���LinearLayout
		tripListView = new ListView(this);//����ListView
		//�������ֲ���
		LinearLayout.LayoutParams tripListViewParam = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		tripListView.setCacheColorHint(Color.WHITE);
		myListLayout.addView(tripListView, tripListViewParam);//��ListView��ӵ�LinearLayout��
		showGoodsList();//��ȡ��Ʒ�б�
		
		Button accountsBut = (Button) this.findViewById(R.id.accountsBut);//������Դ�ļ��е�Button
		accountsBut.setOnClickListener(new OnClickListener() {//Button�ĵ����¼�

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// �ж��û��Ƿ��¼				
				if (DataShare.user.getUid().equals("")) {				
					Intent intent = new Intent();
					intent.setClass(CartListView.this, ViewLogin.class);
					startActivity(intent);
					Toast.makeText(CartListView.this, "���ȵ�¼", Toast.LENGTH_LONG).show();
				} else {
					Intent intent = new Intent();
					intent.setClass(CartListView.this, BillDetailView.class);
					startActivity(intent);
				}
			}

		});
	}

	// �����Ʒ�б�������
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

			map.put("num", "��Ʒ��ţ�" + goods.getGid());
			map.put("name", "��Ʒ���ƣ�" + goods.getBrand());
			map.put("price", "��" + goods.getPrice()+"  ������"+goods.getBuyCount());

			list.add(map);
		}

		return list;
	}

}
