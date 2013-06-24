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
 * �������б���
 * */
public class DZDealersList extends Activity {
	private LinearLayout dealerListLayout;// ����LinearLayout����
	private ListView myListView;// ����ListView����
	private List<Beetle> beetleList = new ArrayList();// ����List����
	private ImageButton searchButt;// ����ImageButton����
	private TextView searchPro;// ����TextView����
	private TextView searchCity;// ����TextView����
	private ProgressDialog myDialog;// ����ProgressDialog����
	private TextView dealerPro;// ��������
	private String pro = "";// ����String����
	private String city = "";// ����String����

	/**
	 * ��дActivity�е�onCreate�ķ����� �÷�������Activity����ʱ��ϵͳ���ã���һ��Activity�������ڵĿ�ʼ��
	 * 
	 * @param savedInstanceState
	 *            ������Activity��״̬�ġ� Bundle���͵�������Map���͵��������ƣ�������key-value����ʽ�洢���ݵ�
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.title);// ���ñ���
		this.setContentView(R.layout.dzdealerslist);// ���ز�����Դ
		setDealersList();
	}

	/**
	 * ��ȡ�������б�
	 */
	public void setDealersList() {
		// ��̬���ɷ������б�
		dealerListLayout = (LinearLayout) this
				.findViewById(R.id.dealerListLayout);// ��ȡ�������б��LinearLayout
		myListView = new ListView(this);// ����ListView����
		// �������ֲ���
		LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		myListView.setCacheColorHint(Color.WHITE);// ����myListView������ʾ��ɫ��Ĭ�Ϻ�ɫ
		dealerListLayout.addView(myListView, param3);// ΪdealerListLayout���myListView��������ֲ���Ϊparam3
		setDealersAdapter();// ����myListView��adapter����
		// myListView����������¼�
		myListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent();// ʵ����Intent
				Bundle beanMsg = new Bundle();// ʵ����Bundle
				it.setClass(DZDealersList.this, DZDealersDetail.class);// ����Class
				beanMsg.putSerializable("beanObj", (Serializable) beetleList
						.get(arg2));// �����������󱣴浽beanMsg��
				it.putExtras(beanMsg);// ���ö�����Ϊ�������ݸ���һ������
				startActivity(it);// ����Activity
			}

		});
		dealerPro = (TextView) this.findViewById(R.id.dealerPro);// ��ȡ��������ʡ��TextView���
		dealerPro.setOnClickListener(new OnClickListener() {// ʡ�ݵ���¼�
					public void onClick(View v) {
						prePro();
					}
				});

		searchPro = (TextView) this.findViewById(R.id.dealerPro);// ��ȡʡ��TextView���

		searchButt = (ImageButton) this.findViewById(R.id.dealerSearch);// ������ťImageButton���
		searchButt.setOnClickListener(new OnClickListener() {// ������ť�����¼�

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						get4S();
					}

				});
		get4S();
	}

	/**
	 * �������ݣ���ȡ��������Ϣ
	 */
	public void get4S() {
		myDialog = ProgressDialog.show(DZDealersList.this, "���Ե�...",
				"���ݼ�����...", true);
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

	// ��Ȥ������Handler
	Handler poiHandler = new Handler() {
		public void handleMessage(Message msg) {
			setDealersAdapter();
		}
	};

	/**
	 * ����adapter
	 */
	private void setDealersAdapter() {
		OverrideAdapter adapter = new OverrideAdapter(DZDealersList.this,
				getData(), R.layout.dzdealerslistrow, new String[] {
						"dealerName", "dealerAddr", "dealerTel", }, new int[] {
						R.id.dealersName, R.id.dealersAddr, R.id.dealersTel });
		myListView.setAdapter(adapter);

	}

	/**
	 * ��ȡ�������б�����
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
	 * ʡ�������˵�
	 */
	public void prePro() {
		final String temp[] = StaticString.pro;
		new AlertDialog.Builder(DZDealersList.this).setTitle("ѡ��").setItems(
				temp, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichcountry) {
						dealerPro.setText("  " + temp[whichcountry]);
					}
				}).setNegativeButton("ȡ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface d, int which) {
						d.dismiss();
					}
				}).show();
	}
}
