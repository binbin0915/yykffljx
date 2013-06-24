package com.light;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
public class MyLightSensor_Activity extends Activity 
{
	//SensorManager对象引用
	SensorManager mySensorManager;	
	//光照传感器
	Sensor myLightSensor; 
	TextView tvX;	//TextView对象引用	
	TextView info;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);	
        info= (TextView)findViewById(R.id.info);
        //获得SensorManager对象
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        myLightSensor=mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        
        String str="\n名字: "+myLightSensor.getName()+"\n电池 :"+myLightSensor.getPower()+
        "\n类型 :"+myLightSensor.getType()+"\nVendor: "+myLightSensor.getVendor()+
        "\n版本: "+myLightSensor.getVersion()+"\n幅度: "+myLightSensor.getMaximumRange();
        info.setText(str);//将信息字符串赋予名为info的TextView
    }
    @Override
	protected void onResume() //重写onResume方法
	{						
		mySensorManager.registerListener(mySensorListener, myLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
	}	
	@Override
	protected void onPause() 
	{									//重写onPause方法
		mySensorManager.unregisterListener(mySensorListener);	//取消注册监听器
		super.onPause();
	}
	//开发实现了SensorEventListener接口的传感器监听器
	private SensorEventListener mySensorListener = new SensorEventListener()
	{
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) 
		{
		}
		@Override
		public void onSensorChanged(SensorEvent event) 
		{
			float []values=event.values;
			tvX.setText("光强为:"+values[0]);		
		}		
	};
}