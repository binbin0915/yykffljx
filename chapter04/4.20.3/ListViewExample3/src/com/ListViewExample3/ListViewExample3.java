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
	private ListView listView;//声明ListView变量
	private LinearLayout myLayout;//声明LinearLayout变量

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//加载布局资源
		myLayout = (LinearLayout) this.findViewById(R.id.myLayout);//获取LinearLayout对象
		listView = new ListView(this);//创建ListView对象
		//获取通讯录数据库中的数据的Cursor对象，具体数据库操作将在第7章详细介绍
		Cursor cursor = getContentResolver().query(People.CONTENT_URI, null,
				null, null, null);
		//将Cursor交给Activity管理，这样Cursor声明周期可以和Activity自动同步
		startManagingCursor(cursor);
		//创建SimpleCursorAdapter对象。构造函数五个参数含义一次为：
		//第一个参数：上下文Context
		//第二个参数：每一行的布局资源文件，android.R.layout.simple_expandable_list_item_1：系统定义好的，只显示一行文字
		//第三个参数：Cursor对象作为数据源
		//第四个参数：String数组，数据库的字段信息
		//第五个参数：int数组，包含布局文件对应的id
		ListAdapter listAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_expandable_list_item_1, cursor,
				new String[] { People.NAME }, new int[] { android.R.id.text1 });
		listView.setAdapter(listAdapter);//为列表listView添加适配器
		myLayout.addView(listView);//将列表listView添加到布局myLayout上
	}
}