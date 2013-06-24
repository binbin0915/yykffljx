package com.WindowExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class WindowExample extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        this.setTitle("Window Icon"); //设置标题栏上的文字
        Window window=this.getWindow();	//获取当前Activity的Window
        this.requestWindowFeature(Window.FEATURE_LEFT_ICON); //标题栏左侧显示图标
        setContentView(R.layout.main);//设置当前窗体的布局管理文件
        this.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.webicon);//设置左侧的图标
        
        
    }
}