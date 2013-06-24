package com.yroscope;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
public class MyGyroscope_Activity extends Activity {
	SensorManager mySensorManager;//SensorManager��������	
	Sensor myGyroscope; //�����Ǵ�����
	TextView tvX;	//TextView��������	
	TextView tvY;	//TextView��������	
	TextView tvZ;	//TextView��������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);	//������ʾx�᷽����ٶ�
        tvY = (TextView)findViewById(R.id.tvY);	//������ʾy�᷽����ٶ�	
        tvZ = (TextView)findViewById(R.id.tvZ); //������ʾz�᷽����ٶ�
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);//���SensorManager����	
        myGyroscope=mySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }
    @Override
	protected void onResume() //��дonResume����
	{
		super.onResume();
		mySensorManager.registerListener(mySensorListener, myGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
	}	
	@Override
	protected void onPause()//��дonPause����
	{
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);//ȡ��ע�������
	}
	private SensorEventListener mySensorListener = new SensorEventListener()
	{//����ʵ����SensorEventListener�ӿڵĴ�����������
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) 
		{
		}
		@Override
		public void onSensorChanged(SensorEvent event) 
		{
			float []values=event.values;//��ȡ�����᷽����ϵļ��ٶ�ֵ
			System.out.println(values.length);
			tvX.setText("��x����ת�Ľ��ٶ�Ϊ��"+values[0]);	
			tvY.setText("��y����ת�Ľ��ٶ�Ϊ��"+values[1]);		
			tvZ.setText("��z����ת�Ľ��ٶ�Ϊ��"+values[2]);
		}		
	};
}