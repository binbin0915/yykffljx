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
        setContentView(R.layout.main); //获取main.xml文件
        
        showDate=(TextView) this.findViewById(R.id.showDate);//获取用来显示当前日期的TextView组件
        setDate=(Button) this.findViewById(R.id.setDate);//获取Button组件
      //初始化Calendar日历对象。
        Calendar myCalendar = Calendar.getInstance(Locale.CHINA);
        Date myDate=new Date();//获取当前日期Date对象
        myCalendar.setTime(myDate);//为Calendar对象设置时间为当前日期
        
        year=myCalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=myCalendar.get(Calendar.MONTH);//获取Calendar对象中的月，0表示1月，1表示2月....
        day=myCalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        
        showDate.setText(year+"-"+(month+1)+"-"+day);//修改TextView显示的信息为当前的年月日
        
        setDate.setOnClickListener(new OnClickListener(){//“设置日期”按钮的单击事件

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
			        //创建DatePickerDialog对象。
					//构造函数原型:public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
					//参数含义依次为context：组件运行Activity,DatePickerDialog.OnDateSetListener:选择日期事件
					//year:当前组件上显示的年，monthOfYear：当前组件上显示的月，dayOfMonth：当前组件上显示的日
			        DatePickerDialog dpd=new DatePickerDialog(DatePickerDialogExample.this,new OnDateSetListener(){
			        	
						/* 
						 * view:该事件关联的组件
						 * myyear：当前选择的年
						 * monthOfYear:当前选择的月
						 * dayOfMonth：当前选择的日
						 */
						@Override
						public void onDateSet(DatePicker view, int myyear, int monthOfYear,
								int dayOfMonth) {
							// TODO Auto-generated method stub
							//在DatePickerDialog组件上设置日期后，同时修改TextView上的信息
							 showDate.setText(myyear+"-"+(monthOfYear+1)+"-"+dayOfMonth);
							 //修改year，month，day变量值，以便在依次单击按钮时DatePickerDialog上显示上一次修改后的值
							 year=myyear;
							 month=monthOfYear;
							 day=dayOfMonth;
							
						}},year,month,day);
			        
			        dpd.show();//显示DatePickerDialog组件
			}
        	
        });
       
    }
}