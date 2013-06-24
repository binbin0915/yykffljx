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
 * ��·�б���
 * */
public class TripList extends Activity {
	private LinearLayout tripListLayout;// ����LinearLayout����
	private ListView myListView;// ����ListView����
	private TextView tripName;// ����TextView����
	private List<Route> tripLists;// ����List����

	/**
	 * ��дActivity�е�onCreate�ķ����� �÷�������Activity����ʱ��ϵͳ���ã���һ��Activity�������ڵĿ�ʼ��	 * 
	 * @param savedInstanceState
	 *            ������Activity��״̬�ġ� Bundle���͵�������Map���͵��������ƣ�������key-value����ʽ�洢���ݵ�
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.title);// ���ô�����⣬ΪString.xml��title��ֵ
		this.setContentView(R.layout.triplist);// ���ò�����Դ
		setTripPoiList();// ��ʾ��·�б���
	}

	/**
	 * ��ʾ��·�б�
	 * 
	 * @param
	 * @return
	 */
	public void setTripPoiList() {
		tripName = (TextView) this.findViewById(R.id.tripName);// ��triplist.xml�л�ȡ����ΪtripName��TextView����
		tripName.setText("��Ʒ������·�Ƽ�");// ����tripName��ʾ����
		tripListLayout = (LinearLayout) this.findViewById(R.id.tripListLayout);// ��triplist.xml�л�ȡ����ΪtripListLayout��LinearLayout����
		myListView = new ListView(this);// ��̬����ListView����
		LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(
				// ���ò��ֲ���
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		myListView.setCacheColorHint(Color.WHITE);// �б���ק�����и�����ɫ��Ĭ���Ǻ�ɫ
		tripListLayout.addView(myListView, param3);// ��myListView��ӵ�tripListLayout�ϣ�����param3��ʽ����
		// ����������
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.trippoilistrow,
				new String[] { "title", "tel", "img", }, new int[] {
						R.id.poiTitle, R.id.poiTel, R.id.poiImg });
		myListView.setAdapter(adapter);// ΪmyListView���������
		// setViewBinder()��������binder���ڰ����ݵ���ͼ ���������ڰ����ݵ���ͼ��binder
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

		// ListView�б������¼�
		myListView.setOnItemClickListener(new OnItemClickListener() {
			// position ָ��ListView���λ�� ��id��view����Դid��
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent it = new Intent();// ʵ����Intent
				Bundle poiMsg = new Bundle(); // ʵ����Bundle
				it.setClass(TripList.this, TripDetail.class);// ����Class
				// ����ǰListView���������Ŀ��Route����ŵ�Bundle��
				poiMsg.putSerializable("tripObj", (Serializable) tripLists
						.get(position));
				it.putExtras(poiMsg);// ���ö�����Ϊ�������ݸ���һ�����壬��TripDetail
				startActivity(it);// ����Activity
			}

		});
	}

	/**
	 * ��ȡ��·��Ϣ
	 * 
	 * @param
	 * @return
	 */
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// ����List����

		WAnalysisFile readFile = new WAnalysisFile();// ����WAnalysisFile�Զ��������
		tripLists = readFile.sortBigRoute(TripList.this);// ���򣬰���·������

		for (int i = 0; i < tripLists.size(); i += 1) {// ѭ����ȡ·����Ϣ
			Map<String, Object> map = new HashMap<String, Object>();
			Route trip = tripLists.get(i);
			map.put("title", trip.getName() + "��" + trip.getMileage() + "����");

			map.put("tel", trip.getCity());

			String tripImg = trip.getImage();
			InputStream iso = null;
			try {
				iso = this.getAssets().open(trip.getId() + "/" + tripImg);// ��ȡ
																			// assets��ͼƬ
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
