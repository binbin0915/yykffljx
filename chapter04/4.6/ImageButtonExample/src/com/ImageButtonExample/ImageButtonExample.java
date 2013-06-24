package com.ImageButtonExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.ImageButton;

public class ImageButtonExample extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageButton imgBut2=(ImageButton)this.findViewById(R.id.imgbut2); //获取XML中的ImageButton对象
        //在组件上按下按键时触发该事件，例如用上下方向键选择按钮
        imgBut2.setOnKeyListener(new OnKeyListener(){
        	
        	/**
        	 * 但在组件上按下键盘按键时触发该方法
        	 * @param v:触发当前事件的组件
        	 * @param keyCode:被按下的物理按键的编码
        	 * @param event:KeyEvent对象
        	 * @return
        	 */
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				//设置ImageButton的背景图片，方法原型为setImageDrawable(Drawable draw)
				//getResources():返回当前应用程序包下的所有资源
				((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.pinkrose));
				setTitle(" "+keyCode);  //将当前按键的keyCode显示到标题栏中，方便测试观察
				
				// TODO Auto-generated method stub
				return false;
			}
        	
        });
        //单击事件，当单击组件时触发该事件
        imgBut2.setOnClickListener(new OnClickListener(){

        	/**
        	 * 单击组件时触发该方法
        	 * @param v:触发当前事件的组件
        	 * @return
        	 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.bluerose));
			}
        	
        });       
        //焦点改变事件，当组件焦点发生变化时触发该事件
        imgBut2.setOnFocusChangeListener(new OnFocusChangeListener(){
        	
        	 /**
        	 * 组件的焦点发生变化时触发该方法
        	 * @param v:触发当前事件的组件
        	 * @param hasFocus:当前组件的焦点状态，true：获取焦点，false：失去焦点
        	 * @return
        	 */
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub				
				if(hasFocus){
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.redrose));
					setTitle("Focus  redrose");
				}else{
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.whiterose));
					setTitle("Focus  whilterose");
				}
				
			}
        	
        });
    }
}