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
	//SensorManager��������
	SensorManager mySensorManager;	
	//���մ�����
	Sensor myLightSensor; 
	TextView tvX;	//TextView��������	
	TextView info;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);	
        info= (TextView)findViewById(R.id.info);
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        myLightSensor=mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        
        String str="\n����: "+myLightSensor.getName()+"\n��� :"+myLightSensor.getPower()+
        "\n���� :"+myLightSensor.getType()+"\nVendor: "+myLightSensor.getVendor()+
        "\n�汾: "+myLightSensor.getVersion()+"\n����: "+myLightSensor.getMaximumRange();
        info.setText(str);//����Ϣ�ַ���������Ϊinfo��TextView
    }
    @Override
	protected void onResume() //��дonResume����
	{						
		mySensorManager.registerListener(mySensorListener, myLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
	}	
	@Override
	protected void onPause() 
	{									//��дonPause����
		mySensorManager.unregisterListener(mySensorListener);	//ȡ��ע�������
		super.onPause();
	}
	//����ʵ����SensorEventListener�ӿڵĴ�����������
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
			tvX.setText("��ǿΪ:"+values[0]);		
		}		
	};
}