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
        setContentView(R.layout.main);//加载main.xml布局文件文件
        showTime=(TextView) this.findViewById(R.id.showTime);//获取用来显示当前时间的TextView组件
        setTime=(Button) this.findViewById(R.id.setTime);//获取设置时间Button组件
     
     
        Calendar myCalendar = Calendar.getInstance(Locale.CHINA); //初始化Calendar日历对象。
        Date myDate=new Date();//获取当前日期Date对象
        myCalendar.setTime(myDate);//为Calendar对象设置时间为当前日期
        
        year=myCalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=myCalendar.get(Calendar.MONTH);//获取Calendar对象中的月，0表示1月，1表示2月....
        day=myCalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        hour=myCalendar.get(Calendar.HOUR_OF_DAY);//获取小时信息
        minus=myCalendar.get(Calendar.MINUTE);//获取分钟信息
        showTime.setText(year+"-"+(month+1)+"-"+day+" "+hour+":"+minus);//设置TextView组件上显示的日期信息
        setTime.setOnClickListener(new OnClickListener(){//“设置日期”按钮的单击事件

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
			    //创建TimePickerDialog对象
				//构造函数原型：TimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView)
				//参数含义依次为context：组件运行Activity,TimePickerDialog.OnTimeSetListener:选择时间事件
				//hourOfDay:当前组件上显示小时，minute：当前组件上显示的分钟，is24HourView：是否是24小时方式显示，或者AM/PM方式显示
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