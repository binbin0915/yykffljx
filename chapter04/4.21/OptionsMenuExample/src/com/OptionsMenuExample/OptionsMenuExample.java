package com.OptionsMenuExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

public class OptionsMenuExample extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//加载资源文件
	}
	//点击Menu时，系统会调用该方法，初始化选项菜单（OptionsMenu）
	//并传入一个menu对象供你使用
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//add()方法是添加一个新的菜单项到menu中，四个参数含义为
		//第一个参数：组标识，如果不分组的话值为Menu.NONE
		//第二个参数：菜单项id，是菜单项的唯一标识，可以通过它判断操作了哪个菜单项
		//第三个参数：菜单项摆放的顺序
		//第四个参数：菜单项上显示的文字
		MenuItem homeMenuItem = menu.add(Menu.NONE, 0, 0, "主页");//添加菜单项
		homeMenuItem.setIcon(R.drawable.home);//为菜单项设置图标
		 
		MenuItem printMenuItem = menu.add(Menu.NONE, 1, 1, "打印");
		printMenuItem.setIcon(R.drawable.print);

		MenuItem saveMenuItem = menu.add(Menu.NONE, 2, 2, "保存");
		saveMenuItem.setIcon(R.drawable.save);
		
		
		MenuItem searchMenuItem = menu.add(Menu.NONE, 3, 3, "搜索");
		searchMenuItem.setIcon(R.drawable.search);
		
		MenuItem delMenuItem = menu.add(Menu.NONE, 4, 4, "删除");
		delMenuItem.setIcon(R.drawable.del);
		
		MenuItem settingMenuItem = menu.add(Menu.NONE, 5, 5, "设置");
		settingMenuItem.setIcon(R.drawable.setting);
		
		MenuItem aboutMenuItem = menu.add(Menu.NONE, 6, 6, "关于");
		aboutMenuItem.setIcon(R.drawable.about);

		return super.onCreateOptionsMenu(menu);
	}

	// 菜单项被选择触发该方法
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		
		switch (item.getItemId()) {//获取菜单项的id
		case 0:
			//item.getTitle()：获取菜单项上显示的文字
			Toast.makeText(this, "点击了’"+item.getTitle()+"‘菜单", Toast.LENGTH_LONG).show();
			break;
		case 1:
			Toast.makeText(this, "点击了‘"+item.getTitle()+"’菜单", Toast.LENGTH_LONG).show();
			break;
		case 2:
			Toast.makeText(this, "点击了‘"+item.getTitle()+"’菜单", Toast.LENGTH_LONG).show();
			break;
		case 3:
			Toast.makeText(this, "点击了‘"+item.getTitle()+"’菜单", Toast.LENGTH_LONG).show();
			break;
		case 4:
			Toast.makeText(this, "点击了‘"+item.getTitle()+"’菜单", Toast.LENGTH_LONG).show();
			break;
		case 5:
			Toast.makeText(this, "点击了‘"+item.getTitle()+"’菜单", Toast.LENGTH_LONG).show();
			break;
		case 6:
			Toast.makeText(this, "点击了‘"+item.getTitle()+"’菜单", Toast.LENGTH_LONG).show();
			break;
		}
		return true;
	}
	//选项菜单（OptionsMenu）被关闭时触发该方法，三种情况下选项菜单（OptionsMenu）会被关闭
	//1.back按钮被点击，2.menu按钮被再次按下，3.选择了某一个菜单项
	@Override
	public void onOptionsMenuClosed(Menu menu) {
		Toast.makeText(this, "选项菜单（OptionsMenu）被关闭了", Toast.LENGTH_LONG).show();
	}

	//选项菜单（OptionsMenu）显示之前调用该方法
	//返回值：false：此方法就把用户点击menu的动作给屏蔽了，onCreateOptionsMenu方法将不会被调用
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		Toast.makeText(this,
				"选项菜单（OptionsMenu）显示之前onPrepareOptionsMenu方法会被调用",
				Toast.LENGTH_LONG).show();
		return true;
	}
}