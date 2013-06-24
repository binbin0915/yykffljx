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
        setContentView(R.layout.main);//����main.xml�ļ�
       
        result=(TextView)this.findViewById(R.id.result);//��ȡxml�ļ��еĶ����TextView���
        
        CheckBox china=(CheckBox)this.findViewById(R.id.ChinaFood);//��ȡ���й��ˡ�CheckBox���
        //��CheckBox�����ѡ�кͷ�ѡ��״̬�����仯ʱ���������¼�
        china.setOnCheckedChangeListener(new OnCheckedChangeListener(){
        	 /**
        	 * CheckBox���ѡ��״̬�ı�ʱ�����÷���
        	 * @param buttonView:������ǰ�¼������
        	 * @param isChecked:��ǰ�����ѡ��״̬��true��ѡ�У�false��δѡ��
        	 * @return
        	 */
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					chinaStr=(String) buttonView.getText();//��ȡ��ǰCheckBox����ʾ������
				}else{
					chinaStr="";
				}
				//�޸�result������ʾ������
				result.setText("��ϲ������ʳ�ǣ�"+chinaStr+","+jpanStr+","+italianStr);
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
				result.setText("��ϲ������ʳ�ǣ�"+chinaStr+","+jpanStr+","+italianStr);
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
				result.setText("��ϲ������ʳ�ǣ�"+chinaStr+","+jpanStr+","+italianStr);
			}
        	
        });
        
        
        
    }
}