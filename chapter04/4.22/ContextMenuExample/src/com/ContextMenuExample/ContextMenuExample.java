package com.ContextMenuExample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ContextMenuExample extends Activity {
	private LinearLayout myLayout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		myLayout = (LinearLayout) this.findViewById(R.id.myLayout);// 获取LinearLayout对象

		this.registerForContextMenu(myLayout);// 为myLayout注册ContextMenu事件
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderIcon(R.drawable.tinfo);// 设置上下文菜单标题的图标
		menu.setHeaderTitle("设置背景颜色");// 设置上文菜单的标题
		MenuItem homeMenuItem = menu.add(Menu.NONE, 0, 0, "绿色");// 添加菜单项
		MenuItem printMenuItem = menu.add(Menu.NONE, 1, 1, "蓝色");
		
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {// 获取菜单项的id
		case 0:
			// item.getTitle()：获取菜单项上显示的文字
			Toast.makeText(this, "点击了’" + item.getTitle() + "‘菜单",
					Toast.LENGTH_LONG).show();
			myLayout.setBackgroundColor(Color.GREEN);// 设置背景颜色
			break;
		case 1:
			Toast.makeText(this, "点击了‘" + item.getTitle() + "’菜单",
					Toast.LENGTH_LONG).show();
			myLayout.setBackgroundColor(Color.BLUE);
			break;

		}
		return super.onContextItemSelected(item);
	}

}