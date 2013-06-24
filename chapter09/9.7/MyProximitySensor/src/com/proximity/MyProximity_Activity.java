package com.proximity;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
public class MyProximity_Activity extends Activity {
	SensorManager mySensorManager;//SensorManager��������		
	Sensor myProximity_Sensor;//���봫���� 
	TextView tvX,info;	//TextView��������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);	
        info=(TextView)findViewById(R.id.info);
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        myProximity_Sensor=mySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY); 
        String str="\n����: "+myProximity_Sensor.getName()+"\n��� :"+myProximity_Sensor.getPower()+
        "\n���� :"+myProximity_Sensor.getType()+"\nVendor: "+myProximity_Sensor.getVendor()+
        "\n�汾: "+myProximity_Sensor.getVersion()+"\n����: "+myProximity_Sensor.getMaximumRange();
        info.setText(str);//����Ϣ�ַ���������Ϊinfo��TextView
    }
    @Override
	protected void onResume()//��дonResume����
	{						
		mySensorManager.registerListener(mySensorListener, 
				myProximity_Sensor, SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
	}	
	@Override
	protected void onPause()									//��дonPause����
	{									
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
			tvX.setText("�ֻ���������ľ���Ϊ:"+values[0]);		
		}		
	};
}