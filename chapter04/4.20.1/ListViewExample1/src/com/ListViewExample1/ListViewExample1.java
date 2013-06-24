package com.ListViewExample1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ListViewExample1 extends Activity {
	private LinearLayout myLayout;//声明LinearLayout类型变量
	private ListView myListView;//声明ListView类型变量

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//加载布局资源
		myLayout = (LinearLayout) this.findViewById(R.id.myLayout);//获取布局资源中的LinearLayout

		myListView = new ListView(this);//创建ListView对象
		//创建ArrayAdapter适配器。构造函数中三个参数的含义分别为：
		//第一个参数：上下文Context
		//第二个参数：每一行的布局资源文件，android.R.layout.simple_expandable_list_item_1：系统定义好的，只显示一行文字
		//第三个参数：数据源，是一个List集合
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getMyData());
		
		myListView.setAdapter(adapter);//为myListView添加适配器

		myLayout.addView(myListView);//将myListView添加到myLayout上
	}

	/**
	 * 获取数据
	 * @return List
	 */
	public List<String> getMyData() {
		//创建List对象，并向其添加数据
		List<String> myList = new ArrayList<String>();
		myList.add("数据项1");
		myList.add("数据项2");
		myList.add("数据项3");
		myList.add("数据项4");
		myList.add("数据项5");
		return myList;
	}
}