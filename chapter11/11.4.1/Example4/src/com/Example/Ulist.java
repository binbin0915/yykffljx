package com.Example;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;


public class Ulist extends ListActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		getClist();
	}
	
	private void getClist(){
		List<CityBean> ulist=new ConnectWeb().getCityList();
		List<String> titles = new ArrayList<String>(ulist.size());
		for(int i=0;i<ulist.size();i+=1){
			titles.add(ulist.get(i).getName()+"  ÓÊ±à:"+ulist.get(i).getCode());
		}
		ArrayAdapter<String> adapter = 
    		new ArrayAdapter<String>(this, R.layout.row,titles);
    	this.setListAdapter(adapter);
	}
}
