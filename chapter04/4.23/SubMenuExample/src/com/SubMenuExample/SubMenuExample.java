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
		setContentView(R.layout.main);//����main.xml��Դ�ļ�
	}
	//���Menuʱ��ϵͳ����ø÷�������ʼ��ѡ��˵���OptionsMenu��
	//������һ��menu������ʹ��
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		SubMenu fileMenu = menu.addSubMenu(1,1,1,"File");//��menu����Ӳ˵�
		fileMenu.setHeaderIcon(R.drawable.file);//�����Ӳ˵�������ı���ͼ��
		fileMenu.setHeaderTitle("File");//�����Ӳ˵�������ı�������
		fileMenu.setIcon(R.drawable.file);//�����Ӳ˵���ͼ��

		fileMenu.add(2,11,11,"New");//Ϊ�Ӳ˵���Ӷ����˵�
		fileMenu.add(2,12,12,"Save");//Ϊ�Ӳ˵���Ӷ����˵�
		fileMenu.add(2,13,13,"Close");//Ϊ�Ӳ˵���Ӷ����˵�

		SubMenu editMenu = menu.addSubMenu(1,2,2,"Edit");
		editMenu.setHeaderIcon(R.drawable.edit);
		editMenu.setHeaderTitle("Edit");
		editMenu.setIcon(R.drawable.edit);

		editMenu.add(2,21,21,"Redo");
		editMenu.add(2,22,22,"Undo Typing");
		return super.onCreateOptionsMenu(menu);

	}
	// �˵��ѡ�񴥷��÷���
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case 1:
			Toast.makeText(this, "����ˡ�"+item.getTitle()+"���˵�", Toast.LENGTH_LONG).show();
			break;
		case 2:
			Toast.makeText(this, "����ˡ�"+item.getTitle()+"���˵�", Toast.LENGTH_LONG).show();
			break;
		case 11:
			Toast.makeText(this, "�����File�Ӳ˵���"+item.getTitle()+"��", Toast.LENGTH_LONG).show();
			break;
		case 12:
			Toast.makeText(this, "�����File�Ӳ˵���"+item.getTitle()+"��", Toast.LENGTH_LONG).show();
			break;
		case 13:
			Toast.makeText(this, "�����File�Ӳ˵���"+item.getTitle()+"��", Toast.LENGTH_LONG).show();
			break;
		case 21:
			Toast.makeText(this, "�����Edit�Ӳ˵���"+item.getTitle()+"��", Toast.LENGTH_LONG).show();
			break;
		case 22:
			Toast.makeText(this, "�����Edit�Ӳ˵���"+item.getTitle()+"��", Toast.LENGTH_LONG).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}