package com.ListViewExample3;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListViewExample3 extends Activity {
	private ListView listView;//����ListView����
	private LinearLayout myLayout;//����LinearLayout����

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//���ز�����Դ
		myLayout = (LinearLayout) this.findViewById(R.id.myLayout);//��ȡLinearLayout����
		listView = new ListView(this);//����ListView����
		//��ȡͨѶ¼���ݿ��е����ݵ�Cursor���󣬾������ݿ�������ڵ�7����ϸ����
		Cursor cursor = getContentResolver().query(People.CONTENT_URI, null,
				null, null, null);
		//��Cursor����Activity��������Cursor�������ڿ��Ժ�Activity�Զ�ͬ��
		startManagingCursor(cursor);
		//����SimpleCursorAdapter���󡣹��캯�������������һ��Ϊ��
		//��һ��������������Context
		//�ڶ���������ÿһ�еĲ�����Դ�ļ���android.R.layout.simple_expandable_list_item_1��ϵͳ����õģ�ֻ��ʾһ������
		//������������Cursor������Ϊ����Դ
		//���ĸ�������String���飬���ݿ���ֶ���Ϣ
		//�����������int���飬���������ļ���Ӧ��id
		ListAdapter listAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_expandable_list_item_1, cursor,
				new String[] { People.NAME }, new int[] { android.R.id.text1 });
		listView.setAdapter(listAdapter);//Ϊ�б�listView���������
		myLayout.addView(listView);//���б�listView��ӵ�����myLayout��
	}
}