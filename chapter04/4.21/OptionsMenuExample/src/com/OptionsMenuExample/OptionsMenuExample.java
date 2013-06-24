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
		setContentView(R.layout.main);//������Դ�ļ�
	}
	//���Menuʱ��ϵͳ����ø÷�������ʼ��ѡ��˵���OptionsMenu��
	//������һ��menu������ʹ��
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//add()���������һ���µĲ˵��menu�У��ĸ���������Ϊ
		//��һ�����������ʶ�����������Ļ�ֵΪMenu.NONE
		//�ڶ����������˵���id���ǲ˵����Ψһ��ʶ������ͨ�����жϲ������ĸ��˵���
		//�������������˵���ڷŵ�˳��
		//���ĸ��������˵�������ʾ������
		MenuItem homeMenuItem = menu.add(Menu.NONE, 0, 0, "��ҳ");//��Ӳ˵���
		homeMenuItem.setIcon(R.drawable.home);//Ϊ�˵�������ͼ��
		 
		MenuItem printMenuItem = menu.add(Menu.NONE, 1, 1, "��ӡ");
		printMenuItem.setIcon(R.drawable.print);

		MenuItem saveMenuItem = menu.add(Menu.NONE, 2, 2, "����");
		saveMenuItem.setIcon(R.drawable.save);
		
		
		MenuItem searchMenuItem = menu.add(Menu.NONE, 3, 3, "����");
		searchMenuItem.setIcon(R.drawable.search);
		
		MenuItem delMenuItem = menu.add(Menu.NONE, 4, 4, "ɾ��");
		delMenuItem.setIcon(R.drawable.del);
		
		MenuItem settingMenuItem = menu.add(Menu.NONE, 5, 5, "����");
		settingMenuItem.setIcon(R.drawable.setting);
		
		MenuItem aboutMenuItem = menu.add(Menu.NONE, 6, 6, "����");
		aboutMenuItem.setIcon(R.drawable.about);

		return super.onCreateOptionsMenu(menu);
	}

	// �˵��ѡ�񴥷��÷���
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		
		switch (item.getItemId()) {//��ȡ�˵����id
		case 0:
			//item.getTitle()����ȡ�˵�������ʾ������
			Toast.makeText(this, "����ˡ�"+item.getTitle()+"���˵�", Toast.LENGTH_LONG).show();
			break;
		case 1:
			Toast.makeText(this, "����ˡ�"+item.getTitle()+"���˵�", Toast.LENGTH_LONG).show();
			break;
		case 2:
			Toast.makeText(this, "����ˡ�"+item.getTitle()+"���˵�", Toast.LENGTH_LONG).show();
			break;
		case 3:
			Toast.makeText(this, "����ˡ�"+item.getTitle()+"���˵�", Toast.LENGTH_LONG).show();
			break;
		case 4:
			Toast.makeText(this, "����ˡ�"+item.getTitle()+"���˵�", Toast.LENGTH_LONG).show();
			break;
		case 5:
			Toast.makeText(this, "����ˡ�"+item.getTitle()+"���˵�", Toast.LENGTH_LONG).show();
			break;
		case 6:
			Toast.makeText(this, "����ˡ�"+item.getTitle()+"���˵�", Toast.LENGTH_LONG).show();
			break;
		}
		return true;
	}
	//ѡ��˵���OptionsMenu�����ر�ʱ�����÷��������������ѡ��˵���OptionsMenu���ᱻ�ر�
	//1.back��ť�������2.menu��ť���ٴΰ��£�3.ѡ����ĳһ���˵���
	@Override
	public void onOptionsMenuClosed(Menu menu) {
		Toast.makeText(this, "ѡ��˵���OptionsMenu�����ر���", Toast.LENGTH_LONG).show();
	}

	//ѡ��˵���OptionsMenu����ʾ֮ǰ���ø÷���
	//����ֵ��false���˷����Ͱ��û����menu�Ķ����������ˣ�onCreateOptionsMenu���������ᱻ����
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		Toast.makeText(this,
				"ѡ��˵���OptionsMenu����ʾ֮ǰonPrepareOptionsMenu�����ᱻ����",
				Toast.LENGTH_LONG).show();
		return true;
	}
}