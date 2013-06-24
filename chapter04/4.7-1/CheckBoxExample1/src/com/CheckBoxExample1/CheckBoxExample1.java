package com.CheckBoxExample1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CheckBoxExample1 extends Activity {
	private TextView result;
	private String chinaStr="";
	private String jpanStr="";
	private String italianStr="";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//加载main.xml文件
       
        result=(TextView)this.findViewById(R.id.result);//获取xml文件中的定义的TextView组件
        
        CheckBox china=(CheckBox)this.findViewById(R.id.ChinaFood);//获取“中国菜”CheckBox组件
        //当CheckBox组件的选中和非选中状态发生变化时，触发该事件
        china.setOnCheckedChangeListener(new OnCheckedChangeListener(){
        	 /**
        	 * CheckBox组件选中状态改变时触发该方法
        	 * @param buttonView:触发当前事件的组件
        	 * @param isChecked:当前组件的选中状态，true：选中，false：未选中
        	 * @return
        	 */
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					chinaStr=(String) buttonView.getText();//获取当前CheckBox上显示的文字
				}else{
					chinaStr="";
				}
				//修改result上面显示的文字
				result.setText("你喜欢的美食是："+chinaStr+","+jpanStr+","+italianStr);
			}
        	
        });
        CheckBox jpan=(CheckBox)this.findViewById(R.id.JapaneseFood);
        jpan.setOnCheckedChangeListener(new OnCheckedChangeListener(){
       	 
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					jpanStr=(String) buttonView.getText();
				}else{
					jpanStr="";
				}
				result.setText("你喜欢的美食是："+chinaStr+","+jpanStr+","+italianStr);
			}
        	
        });
        CheckBox italian=(CheckBox)this.findViewById(R.id.ItalianFood);
        italian.setOnCheckedChangeListener(new OnCheckedChangeListener(){
          	 
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					italianStr=(String) buttonView.getText();
				}else{
					italianStr="";
				}
				result.setText("你喜欢的美食是："+chinaStr+","+jpanStr+","+italianStr);
			}
        	
        });
        
        
        
    }
}