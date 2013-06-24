package com.magnetic_field;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
public class MyMagneticFieldSensor_Activity extends Activity 
{
	//SensorManager��������
	SensorManager mySensorManager;	
	//�¶ȴ�����
	Sensor myMagnetic_field_Sensor; 
	TextView tvX,tvY,tvZ,info;	//TextView��������	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);	
        tvY = (TextView)findViewById(R.id.tvY);	
        tvZ = (TextView)findViewById(R.id.tvZ);	
        info=(TextView)findViewById(R.id.info);
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        myMagnetic_field_Sensor=mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD); 
        String str="\n����: "+myMagnetic_field_Sensor.getName()+"\n��� :"+myMagnetic_field_Sensor.getPower()+
        "\n���� :"+myMagnetic_field_Sensor.getType()+"\nVendor: "+myMagnetic_field_Sensor.getVendor()+
        "\n�汾: "+myMagnetic_field_Sensor.getVersion()+"\n����: "+myMagnetic_field_Sensor.getMaximumRange();
        info.setText(str);//����Ϣ�ַ���������Ϊinfo��TextView
    }
    @Override
	protected void onResume()//��дonResume����
	{						
		mySensorManager.registerListener(mySensorListener, myMagnetic_field_Sensor, SensorManager.SENSOR_DELAY_NORMAL);
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
			System.out.println(values.length+"=======");
			tvX.setText("X�᷽��ų�Ϊ:"+values[0]);	
			tvY.setText("Y�᷽��ų�Ϊ:"+values[1]);	
			tvZ.setText("Z�᷽��ų�Ϊ:"+values[2]);	
		}		
	};
}