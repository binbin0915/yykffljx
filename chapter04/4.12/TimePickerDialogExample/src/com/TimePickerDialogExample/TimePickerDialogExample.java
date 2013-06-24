package com.TimePickerDialogExample;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimePickerDialogExample extends Activity {
	private TextView showTime;
	private Button setTime;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minus;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//����main.xml�����ļ��ļ�
        showTime=(TextView) this.findViewById(R.id.showTime);//��ȡ������ʾ��ǰʱ���TextView���
        setTime=(Button) this.findViewById(R.id.setTime);//��ȡ����ʱ��Button���
     
     
        Calendar myCalendar = Calendar.getInstance(Locale.CHINA); //��ʼ��Calendar��������
        Date myDate=new Date();//��ȡ��ǰ����Date����
        myCalendar.setTime(myDate);//ΪCalendar��������ʱ��Ϊ��ǰ����
        
        year=myCalendar.get(Calendar.YEAR); //��ȡCalendar�����е���
        month=myCalendar.get(Calendar.MONTH);//��ȡCalendar�����е��£�0��ʾ1�£�1��ʾ2��....
        day=myCalendar.get(Calendar.DAY_OF_MONTH);//��ȡ����µĵڼ���
        hour=myCalendar.get(Calendar.HOUR_OF_DAY);//��ȡСʱ��Ϣ
        minus=myCalendar.get(Calendar.MINUTE);//��ȡ������Ϣ
        showTime.setText(year+"-"+(month+1)+"-"+day+" "+hour+":"+minus);//����TextView�������ʾ��������Ϣ
        setTime.setOnClickListener(new OnClickListener(){//���������ڡ���ť�ĵ����¼�

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
			    //����TimePickerDialog����
				//���캯��ԭ�ͣ�TimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView)
				//������������Ϊcontext���������Activity,TimePickerDialog.OnTimeSetListener:ѡ��ʱ���¼�
				//hourOfDay:��ǰ�������ʾСʱ��minute����ǰ�������ʾ�ķ��ӣ�is24HourView���Ƿ���24Сʱ��ʽ��ʾ������AM/PM��ʽ��ʾ
				TimePickerDialog tpd=new TimePickerDialog(TimePickerDialogExample.this,new TimePickerDialog.OnTimeSetListener(){

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int myminute) {
						// TODO Auto-generated method stub
						showTime.setText(year+"-"+(month+1)+"-"+day+" "+hourOfDay+":"+myminute);
						hour=hourOfDay;
				        minus=myminute;
					}
					
				},hour,minus,false);
			        	
				tpd.show();		
			}
        	
        });
    }
}