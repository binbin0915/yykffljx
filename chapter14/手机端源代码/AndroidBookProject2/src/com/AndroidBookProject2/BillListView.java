package com.AndroidBookProject2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BillListView extends Activity {
	private LinearLayout myListLayout;//����LinearLayout���ͱ���
	private ListView tripListView;//����ListView���ͱ���
	private List<BillEntity> billList = new ArrayList<BillEntity>();//����List���ͱ���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.viewbilllist);//���ز�����Դ�ļ�
		myListLayout = (LinearLayout) this.findViewById(R.id.billList);//���ز�����Դ�ļ��е�LinearLayout���
		tripListView = new ListView(this);// ����ListView
		//�������ֲ���
		LinearLayout.LayoutParams tripListViewParam = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		tripListView.setCacheColorHint(Color.WHITE);
		myListLayout.addView(tripListView, tripListViewParam);//��ListView��ӵ�LinearLayout��
		getBillList();//��ȡ�����б�

	}

	// ��ȡ�����б�����
	private void getBillList() {
		final ProgressDialog myDialog = ProgressDialog.show(BillListView.this,
				"���Ե�...", "���ݼ�����...", true);
		new Thread() {
			public void run() {
				try {
					//�ӷ������˶�ȡ�û������б�
					billList = new ConnectWeb().getBillList(DataShare.user
							.getUid());
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
			if (billList.size() == 0) {
				return;
			}
			showBillList();//��䶩���б�������
		}
	};

	// ��䶩���б�������
	public void showBillList() {
		//����SimpleAdapter������
		SimpleAdapter adapter = new SimpleAdapter(this, getTripList(),
				R.layout.billlistrow, new String[] { "billNum", "billState",
						"billTime" }, new int[] { R.id.billNum, R.id.billState,
						R.id.billTime });
		tripListView.setAdapter(adapter);//ΪtripListView���������
	}

	public List<Map<String, Object>> getTripList() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < billList.size(); i += 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			BillEntity theBill = billList.get(i);
			String stateStr = "";
			if (theBill.getState().equals("waiting")) {
				stateStr = "������";
			}
			map.put("billNum", "������ţ�" + theBill.getId());
			map.put("billTime", "�µ�ʱ�䣺" + theBill.getCtime());
			map.put("billState", "����״̬��" + stateStr);

			list.add(map);
		}
		return list;
	}

}
