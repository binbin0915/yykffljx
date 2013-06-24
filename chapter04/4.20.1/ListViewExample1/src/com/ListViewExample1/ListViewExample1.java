package com.ListViewExample1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ListViewExample1 extends Activity {
	private LinearLayout myLayout;//����LinearLayout���ͱ���
	private ListView myListView;//����ListView���ͱ���

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//���ز�����Դ
		myLayout = (LinearLayout) this.findViewById(R.id.myLayout);//��ȡ������Դ�е�LinearLayout

		myListView = new ListView(this);//����ListView����
		//����ArrayAdapter�����������캯�������������ĺ���ֱ�Ϊ��
		//��һ��������������Context
		//�ڶ���������ÿһ�еĲ�����Դ�ļ���android.R.layout.simple_expandable_list_item_1��ϵͳ����õģ�ֻ��ʾһ������
		//����������������Դ����һ��List����
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getMyData());
		
		myListView.setAdapter(adapter);//ΪmyListView���������

		myLayout.addView(myListView);//��myListView��ӵ�myLayout��
	}

	/**
	 * ��ȡ����
	 * @return List
	 */
	public List<String> getMyData() {
		//����List���󣬲������������
		List<String> myList = new ArrayList<String>();
		myList.add("������1");
		myList.add("������2");
		myList.add("������3");
		myList.add("������4");
		myList.add("������5");
		return myList;
	}
}