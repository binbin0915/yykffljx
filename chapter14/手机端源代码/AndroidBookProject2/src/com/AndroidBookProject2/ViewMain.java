package com.AndroidBookProject2;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class ViewMain extends TabActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final TabHost tabHost = getTabHost();//��ȡTabHost����
	    tabHost.addTab(tabHost.newTabSpec("tab1")
	            .setIndicator("�Ƽ���Ʒ", getResources().getDrawable(R.drawable.popular))
	            .setContent(new Intent(this, ViewTuiJian.class)));
	    tabHost.addTab(tabHost.newTabSpec("tab2")
	            .setIndicator("���õ���", getResources().getDrawable(R.drawable.home))
	            .setContent(new Intent(this, ViewJiaDian.class)));
	    tabHost.addTab(tabHost.newTabSpec("tab3")
	            .setIndicator("�ֻ�����", getResources().getDrawable(R.drawable.mobile))
	            .setContent(new Intent(this, ViewShouJi.class)));
	    tabHost.addTab(tabHost.newTabSpec("tab4")
	            .setIndicator("���԰칫", getResources().getDrawable(R.drawable.computer))
	            .setContent(new Intent(this, ViewDianNao.class)));
	    tabHost.setCurrentTab(0);
	}
	
}
