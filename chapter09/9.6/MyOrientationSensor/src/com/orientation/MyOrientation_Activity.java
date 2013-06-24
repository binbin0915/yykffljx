package com.orientation;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
public class MyOrientation_Activity extends Activity {
	SensorManager mySensorManager;//SensorManager��������		
	Sensor myOrientation_Sensor;//���򴫸��� 
	TextView tvX,tvY,tvZ,info;	//TextView��������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);	
        tvY = (TextView)findViewById(R.id.tvY);	
        tvZ = (TextView)findViewById(R.id.tvZ);	
        info=(TextView)findViewById(R.id.info);
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        myOrientation_Sensor=mySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION); 
        String str="\n����: "+myOrientation_Sensor.getName()+"\n��� :"+myOrientation_Sensor.getPower()+
        "\n���� :"+myOrientation_Sensor.getType()+"\nVendor: "+myOrientation_Sensor.getVendor()+
        "\n�汾: "+myOrientation_Sensor.getVersion()+"\n����: "+myOrientation_Sensor.getMaximumRange();
        info.setText(str);//����Ϣ�ַ���������Ϊinfo��TextView
    }
    @Override
	protected void onResume()//��дonResume����
	{						
		mySensorManager.registerListener(mySensorListener, 
				myOrientation_Sensor, SensorManager.SENSOR_DELAY_NORMAL);
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
			System.out.println(values.length+"=======");
			tvX.setText("�ֻ���Yaw��ת���ĽǶ�Ϊ:"+values[0]);	
			tvY.setText("�ֻ���Pitch��ת���ĽǶ�Ϊ:"+values[1]);	
			tvZ.setText("�ֻ���Roll��ת���ĽǶ�Ϊ:"+values[2]);	
		}		
	};
}