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
        setContentView(R.layout.main);//����main.xml��Դ�ļ�
        
        result=(TextView)this.findViewById(R.id.result);  //��ȡ  result���    
        rb1=(RadioButton)this.findViewById(R.id.RB1);//��ȡRB1���
        rb2=(RadioButton)this.findViewById(R.id.RB2);//��ȡRB2���
        rb3=(RadioButton)this.findViewById(R.id.RB3);//��ȡRB3���        
        
        radioGroup=(RadioGroup)this.findViewById(R.id.radioGroup);//��ȡRadioGroup���
        //��RadioGroup�е�RadioButton ״̬�ı�ʱ��ִ�и��¼�
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			/* 
			 * RadioButton״̬�ı�ʱ���ø÷���
			 * group��������ǰ�¼���RadioGroup
			 * checkedId��������ǰ�¼���RadioButton��id��
			 */
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==rb1.getId()){//�жϵ�ǰ�����¼�������Ƿ���rb1���
					result.setText("��ѡ����ǣ�"+rb1.getText());//��rb1�ϵ�������ʾ��result��
				}
				if(checkedId==rb2.getId()){
					result.setText("��ѡ����ǣ�"+rb2.getText());
				}
				if(checkedId==rb3.getId()){
					result.setText("��ѡ����ǣ�"+rb3.getText());
				}				
			}        	
        });
       //��ȡclear���
        Button clear=(Button)this.findViewById(R.id.clear);
        //��������¼�
        clear.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radioGroup.clearCheck();//���RadioGroup�����״̬
				result.setText("");
			}        	
        });
        
    }
}