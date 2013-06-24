package com.temperature;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
public class MyTemperatureSensor_Activity extends Activity 
{
	//SensorManager对象引用
	SensorManager mySensorManager;	
	//温度传感器
	Sensor myTemperatureSensor; 
	TextView tvX,info;	//TextView对象引用	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);	
        info=(TextView)findViewById(R.id.info);
        //获得SensorManager对象
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        myTemperatureSensor=mySensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        
        String str="\n名字: "+myTemperatureSensor.getName()+"\n电池 :"+myTemperatureSensor.getPower()+
        "\n类型 :"+myTemperatureSensor.getType()+"\nVendor: "+myTemperatureSensor.getVendor()+
        "\n版本: "+myTemperatureSensor.getVersion()+"\n幅度: "+myTemperatureSensor.getMaximumRange();
        info.setText(str);//将信息字符串赋予名为info的TextView
    }
    @Override
	protected void onResume()//重写onResume方法
	{						
		mySensorManager.registerListener(mySensorListener, myTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
			tvX.setText("温度为:"+values[0]);		
		}		
	};
}