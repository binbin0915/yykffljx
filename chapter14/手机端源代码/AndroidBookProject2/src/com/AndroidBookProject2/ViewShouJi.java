package com.AndroidBookProject2;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

public class ViewShouJi extends Activity {
	private LinearLayout myListLayout; //����LinearLayout���ͱ���
	private ListView tripListView;//����ListView���ͱ���
	private ProgressDialog myDialog;//����ProgressDialog���ͱ���
	private List<Goods> goodsList;//����List���ͱ���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.viewshouji);//����viewtuijian.xml��Դ�ļ�
		myListLayout = (LinearLayout) this.findViewById(R.id.tripList);// ��ȡ��Դ�ļ��е�LinearLayout

		tripListView = new ListView(this);//����ListView����
		//����LinearLayout.LayoutParams���Ͷ���
		LinearLayout.LayoutParams tripListViewParam = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		tripListView.setCacheColorHint(Color.WHITE);
		myListLayout.addView(tripListView, tripListViewParam);//��tripListView��ӵ�myListLayout������
		getGoodsList();//��ȡ��Ʒ�б�
		
		tripListView.setOnItemClickListener(new OnItemClickListener() {//tripListView�б�����¼�

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				// TODO Auto-generated method stub
				Goods theGood=goodsList.get(position);//��ȡ��ǰ�б���ѡ�е���Ʒ
				Intent it = new Intent();//����Intent����
				Bundle bundle = new Bundle();//����Bundle����
				it.setClass(ViewShouJi.this, ShangPinDetailView.class);
				bundle.putSerializable("GoodObj", (Serializable) theGood);
				it.putExtras(bundle);
				startActivity(it);
			}
		});
	}

	/**
	 * ��ȡ��Ʒ�б�����
	 */
	private void getGoodsList() {
		myDialog = ProgressDialog.show(ViewShouJi.this, "���Ե�...", "���ݼ�����...",
				true);
		new Thread() {
			public void run() {
				try {
					goodsList = new ConnectWeb().getTypeList(2);//��ȡ�ֻ����������Ʒ�б�2��ʾ�ֻ�����
					Message m = new Message();
					listHandler.sendMessage(m);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					myDialog.dismiss();
				}
			}
		}.start();
	}

	Handler listHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (goodsList.size() == 0) {
				return;
			}
			showGoodsList();
		}
	};

	/**
	 * �����Ʒ�б�������
	 */
	public void showGoodsList() {
		SimpleAdapter adapter = new SimpleAdapter(this, getTripList(),
				R.layout.tuijianrow, new String[] { "img", "name", "money",
						"zhe" }, new int[] { R.id.tripImg, R.id.tripTitle,
						R.id.tripSegName, R.id.tripProv });
		tripListView.setAdapter(adapter);//ΪtripListView���������adapter
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
		for (int i = 0; i < goodsList.size(); i += 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			Goods goods = goodsList.get(i);

			try {
				URL picUrl = new URL(goods.getDir() + "/" + goods.getPic());
				Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
				map.put("img", pngBM);
			} catch (Exception e) {
				e.printStackTrace();
			}

			map.put("name", "��Ʒ���ƣ�"+goods.getBrand());
			map.put("money", "��Ʒ�۸�"+"��" + goods.getPrice());
			map.put("zhe", "��Ʒ�ۿۣ�"+goods.getDiscount());

			list.add(map);
		}

		return list;
	}

}
		
