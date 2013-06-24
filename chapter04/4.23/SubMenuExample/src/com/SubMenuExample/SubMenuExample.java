package com.SubMenuExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

public class SubMenuExample extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//加载main.xml资源文件
	}
	//点击Menu时，系统会调用该方法，初始化选项菜单（OptionsMenu）
	//并传入一个menu对象供你使用
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		SubMenu fileMenu = menu.addSubMenu(1,1,1,"File");//给menu添加子菜单
		fileMenu.setHeaderIcon(R.drawable.file);//设置子菜单弹出框的标题图标
		fileMenu.setHeaderTitle("File");//设置子菜单弹出框的标题文字
		fileMenu.setIcon(R.drawable.file);//设置子菜单的图标

		fileMenu.add(2,11,11,"New");//为子菜单添加二级菜单
		fileMenu.add(2,12,12,"Save");//为子菜单添加二级菜单
		fileMenu.add(2,13,13,"Close");//为子菜单添加二级菜单

		SubMenu editMenu = menu.addSubMenu(1,2,2,"Edit");
		editMenu.setHeaderIcon(R.drawable.edit);
		editMenu.setHeaderTitle("Edit");
		editMenu.setIcon(R.drawable.edit);

		editMenu.add(2,21,21,"Redo");
		editMenu.add(2,22,22,"Undo Typing");
		return super.onCreateOptionsMenu(menu);

	}
	// 菜单项被选择触发该方法
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case 1:
			Toast.makeText(this, "点击了’"+item.getTitle()+"‘菜单", Toast.LENGTH_LONG).show();
			break;
		case 2:
			Toast.makeText(this, "点击了’"+item.getTitle()+"‘菜单", Toast.LENGTH_LONG).show();
			break;
		case 11:
			Toast.makeText(this, "点击了File子菜单’"+item.getTitle()+"‘", Toast.LENGTH_LONG).show();
			break;
		case 12:
			Toast.makeText(this, "点击了File子菜单’"+item.getTitle()+"‘", Toast.LENGTH_LONG).show();
			break;
		case 13:
			Toast.makeText(this, "点击了File子菜单’"+item.getTitle()+"‘", Toast.LENGTH_LONG).show();
			break;
		case 21:
			Toast.makeText(this, "点击了Edit子菜单’"+item.getTitle()+"‘", Toast.LENGTH_LONG).show();
			break;
		case 22:
			Toast.makeText(this, "点击了Edit子菜单’"+item.getTitle()+"‘", Toast.LENGTH_LONG).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}