package com.WindowExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class WindowExample extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        this.setTitle("progressing...."); //设置标题栏上的文字
        Window window=this.getWindow();	//获取当前Activity的Window
        this.requestWindowFeature(Window.FEATURE_PROGRESS); //标题栏显示滚动条        
        setContentView(R.layout.main);//设置当前窗体的布局管理文件
        this.setProgressBarVisibility(true);//设置进度条可见
        this.setProgress(1800);//设置第一进度条的长度
        this.setSecondaryProgress(8000);//设置第二进度条的长度
        
    }
}