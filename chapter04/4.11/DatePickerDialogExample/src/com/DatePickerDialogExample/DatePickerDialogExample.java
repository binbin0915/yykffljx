package com.DatePickerDialogExample;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerDialogExample extends Activity {
	private TextView showDate;
	private Button setDate;
	private int year;
	private int month;
	private int day;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); //��ȡmain.xml�ļ�
        
        showDate=(TextView) this.findViewById(R.id.showDate);//��ȡ������ʾ��ǰ���ڵ�TextView���
        setDate=(Button) this.findViewById(R.id.setDate);//��ȡButton���
      //��ʼ��Calendar��������
        Calendar myCalendar = Calendar.getInstance(Locale.CHINA);
        Date myDate=new Date();//��ȡ��ǰ����Date����
        myCalendar.setTime(myDate);//ΪCalendar��������ʱ��Ϊ��ǰ����
        
        year=myCalendar.get(Calendar.YEAR); //��ȡCalendar�����е���
        month=myCalendar.get(Calendar.MONTH);//��ȡCalendar�����е��£�0��ʾ1�£�1��ʾ2��....
        day=myCalendar.get(Calendar.DAY_OF_MONTH);//��ȡ����µĵڼ���
        
        showDate.setText(year+"-"+(month+1)+"-"+day);//�޸�TextView��ʾ����ϢΪ��ǰ��������
        
        setDate.setOnClickListener(new OnClickListener(){//���������ڡ���ť�ĵ����¼�

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
			        //����DatePickerDialog����
					//���캯��ԭ��:public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
					//������������Ϊcontext���������Activity,DatePickerDialog.OnDateSetListener:ѡ�������¼�
					//year:��ǰ�������ʾ���꣬monthOfYear����ǰ�������ʾ���£�dayOfMonth����ǰ�������ʾ����
			        DatePickerDialog dpd=new DatePickerDialog(DatePickerDialogExample.this,new OnDateSetListener(){
			        	
						/* 
						 * view:���¼����������
						 * myyear����ǰѡ�����
						 * monthOfYear:��ǰѡ�����
						 * dayOfMonth����ǰѡ�����
						 */
						@Override
						public void onDateSet(DatePicker view, int myyear, int monthOfYear,
								int dayOfMonth) {
							// TODO Auto-generated method stub
							//��DatePickerDialog������������ں�ͬʱ�޸�TextView�ϵ���Ϣ
							 showDate.setText(myyear+"-"+(monthOfYear+1)+"-"+dayOfMonth);
							 //�޸�year��month��day����ֵ���Ա������ε�����ťʱDatePickerDialog����ʾ��һ���޸ĺ��ֵ
							 year=myyear;
							 month=monthOfYear;
							 day=dayOfMonth;
							
						}},year,month,day);
			        
			        dpd.show();//��ʾDatePickerDialog���
			}
        	
        });
       
    }
}