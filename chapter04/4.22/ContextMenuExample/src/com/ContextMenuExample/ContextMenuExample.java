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

		myLayout = (LinearLayout) this.findViewById(R.id.myLayout);// ��ȡLinearLayout����

		this.registerForContextMenu(myLayout);// ΪmyLayoutע��ContextMenu�¼�
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderIcon(R.drawable.tinfo);// ���������Ĳ˵������ͼ��
		menu.setHeaderTitle("���ñ�����ɫ");// �������Ĳ˵��ı���
		MenuItem homeMenuItem = menu.add(Menu.NONE, 0, 0, "��ɫ");// ��Ӳ˵���
		MenuItem printMenuItem = menu.add(Menu.NONE, 1, 1, "��ɫ");
		
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {// ��ȡ�˵����id
		case 0:
			// item.getTitle()����ȡ�˵�������ʾ������
			Toast.makeText(this, "����ˡ�" + item.getTitle() + "���˵�",
					Toast.LENGTH_LONG).show();
			myLayout.setBackgroundColor(Color.GREEN);// ���ñ�����ɫ
			break;
		case 1:
			Toast.makeText(this, "����ˡ�" + item.getTitle() + "���˵�",
					Toast.LENGTH_LONG).show();
			myLayout.setBackgroundColor(Color.BLUE);
			break;

		}
		return super.onContextItemSelected(item);
	}

}