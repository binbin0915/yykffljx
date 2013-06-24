package com.RadioButtonExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class RadioButtonExample extends Activity {
	private TextView result;
	private RadioGroup radioGroup;
	private RadioButton rb1;
	private RadioButton rb2;
	private RadioButton rb3;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//加载main.xml资源文件
        
        result=(TextView)this.findViewById(R.id.result);  //获取  result组件    
        rb1=(RadioButton)this.findViewById(R.id.RB1);//获取RB1组件
        rb2=(RadioButton)this.findViewById(R.id.RB2);//获取RB2组件
        rb3=(RadioButton)this.findViewById(R.id.RB3);//获取RB3组件        
        
        radioGroup=(RadioGroup)this.findViewById(R.id.radioGroup);//获取RadioGroup组件
        //当RadioGroup中的RadioButton 状态改变时，执行该事件
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			/* 
			 * RadioButton状态改变时调用该方法
			 * group：触发当前事件的RadioGroup
			 * checkedId：触发当前事件的RadioButton的id。
			 */
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==rb1.getId()){//判断当前触发事件的组件是否是rb1组件
					result.setText("您选择的是："+rb1.getText());//将rb1上的文字显示在result上
				}
				if(checkedId==rb2.getId()){
					result.setText("您选择的是："+rb2.getText());
				}
				if(checkedId==rb3.getId()){
					result.setText("您选择的是："+rb3.getText());
				}				
			}        	
        });
       //获取clear组件
        Button clear=(Button)this.findViewById(R.id.clear);
        //组件单击事件
        clear.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radioGroup.clearCheck();//清除RadioGroup上组件状态
				result.setText("");
			}        	
        });
        
    }
}