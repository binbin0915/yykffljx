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
	//SensorManager��������
	SensorManager mySensorManager;	
	//�¶ȴ�����
	Sensor myTemperatureSensor; 
	TextView tvX,info;	//TextView��������	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);	
        info=(TextView)findViewById(R.id.info);
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        myTemperatureSensor=mySensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        
        String str="\n����: "+myTemperatureSensor.getName()+"\n��� :"+myTemperatureSensor.getPower()+
        "\n���� :"+myTemperatureSensor.getType()+"\nVendor: "+myTemperatureSensor.getVendor()+
        "\n�汾: "+myTemperatureSensor.getVersion()+"\n����: "+myTemperatureSensor.getMaximumRange();
        info.setText(str);//����Ϣ�ַ���������Ϊinfo��TextView
    }
    @Override
	protected void onResume()//��дonResume����
	{						
		mySensorManager.registerListener(mySensorListener, myTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
			tvX.setText("�¶�Ϊ:"+values[0]);		
		}		
	};
}