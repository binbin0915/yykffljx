package com.ButtonEventExample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ButtonEventExample extends Activity {
	private LinearLayout layout;//声明LinearLayout类型变量
	private Button redBut;//声明Button类型变量
	private Button blueBut;//声明Button类型变量
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//加载main.xml布局文件
        layout=(LinearLayout)this.findViewById(R.id.layout);//通过id获取布局文件中的LinearLayout对象
        redBut=(Button)this.findViewById(R.id.redBut);//通过id获取布局文件中的Button对象
        blueBut=(Button)this.findViewById(R.id.blueBut);//通过id获取布局文件中的Button对象
        
        redBut.setOnClickListener(new OnClickListener(){ //按钮的单击事件
			@Override
			public void onClick(View v) {//按钮的单击事件
				// TODO Auto-generated method stub
				layout.setBackgroundColor(Color.RED);//修改layout的背景颜色
				((Button)v).setText("背景红了");		//修改Button按钮上的文字
			}        	
        });
        blueBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layout.setBackgroundColor(Color.BLUE);
				((Button)v).setText("背景蓝了");
			}        	
        });
        
        
    }
}