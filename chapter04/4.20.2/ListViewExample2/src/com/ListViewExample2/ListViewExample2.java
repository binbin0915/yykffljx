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
	private LinearLayout myListLayout;// ����LinearLayout���ͱ���
	private ListView tripListView;// ����ListView���ͱ���
	//����list������������б���ÿһ�е�Map��Ϣ
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);// ���ز�����Դ
		myListLayout = (LinearLayout) this.findViewById(R.id.myListView);//��ȡLinearLayout����

		tripListView = new ListView(this);//����ListView����
		//�������ֲ���
		LinearLayout.LayoutParams tripListViewParam = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		tripListView.setCacheColorHint(Color.WHITE);//����ק�б�ʱ����ʾ����ɫ��Ĭ��Ϊ��ɫ����������Ϊ��ɫ
		myListLayout.addView(tripListView, tripListViewParam);//���б�tripListView��ӵ���ʽ����myListLayout��
		//����SimpleAdapter���󣬹��캯������5������������ֱ�Ϊ��
		//��һ��������������Context
		//�ڶ���������ÿһ�еĲ�����Դ�ļ��������Զ�����б�����ļ�
		//������������HashMap�е�key��Ϣimg��name��money��zhe
		//���ĸ�������listviewrow.xml�е����id
		SimpleAdapter adapter = new SimpleAdapter(this, getTripListData(),
				R.layout.listviewrow, new String[] { "img", "name", "money",
						"zhe" }, new int[] { R.id.tripImg, R.id.phoneName,
						R.id.phoneMoney, R.id.phoneDiscount });
		tripListView.setAdapter(adapter);//Ϊ�б�tripListView���������
		//�б���ĵ����¼�
		tripListView.setOnItemClickListener(new OnItemClickListener() {
			/* ����б���ʱ����onItemClick�������ĸ���������ֱ�Ϊ
			 * arg0�����������¼���AdapterView
			 * arg1��AdapterView�б������View
			 * position����ǰ���������adapter���±�
			 * id����ǰ������е�id
			 */
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(ListViewExample2.this,
						"��ѡ�����" + list.get(position).get("name").toString(),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	
	/**
	 * ���ܣ���ȡ�б�����ʾ������
	 * @return List
	 */
	public List<Map<String, Object>> getTripListData() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("img", R.drawable.moto);
		map.put("name", "Ħ��������motorola��XT711 3G�ֻ�");
		map.put("money", "2699Ԫ");
		map.put("zhe", "9��");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.iphone);
		map.put("name", "iPhone4 16G��");
		map.put("money", "5880Ԫ");
		map.put("zhe", "8.5��");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.samsung);
		map.put("name", "���ǣ�SAMSUNG��i9003 3G�ֻ�");
		map.put("money", "3099Ԫ");
		map.put("zhe", "9��");
		list.add(map);

		return list;
	}
}