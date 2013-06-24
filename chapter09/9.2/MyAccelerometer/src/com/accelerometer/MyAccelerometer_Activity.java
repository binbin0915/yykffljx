package com.accelerometer;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
public class MyAccelerometer_Activity extends Activity
{
	SensorManager mySensorManager;//SensorManager��������	
	//���ٶȴ�����
	Sensor myAccelerometer; 
	TextView tvX;	//TextView��������	
	TextView tvY;	//TextView��������	
	TextView tvZ;	//TextView��������
	TextView info;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);	//������ʾx�᷽����ٶ�
        tvY = (TextView)findViewById(R.id.tvY);	//������ʾy�᷽����ٶ�	
        tvZ = (TextView)findViewById(R.id.tvZ); //������ʾz�᷽����ٶ�
        info= (TextView)findViewById(R.id.info);//������ʾ�ֻ��м��ٶȴ������������Ϣ
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);//���SensorManager����	
        myAccelerometer=mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        String str="\n����: "+myAccelerometer.getName()+"\n��� :"+myAccelerometer.getPower()+
        "\n���� :"+myAccelerometer.getType()+"\nVendor: "+myAccelerometer.getVendor()+
        "\n�汾: "+myAccelerometer.getVersion()+"\n����: "+myAccelerometer.getMaximumRange();
        info.setText(str);//����Ϣ�ַ���������Ϊinfo��TextView
    }
    @Override
	protected void onResume() //��дonResume����
	{
		super.onResume();
		mySensorManager.registerListener(mySensorListener, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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
			tvX.setText("x�᷽���ϵļ��ٶ�Ϊ��"+values[0]);		
			tvY.setText("y�᷽���ϵļ��ٶ�Ϊ��"+values[1]);		
			tvZ.setText("z�᷽���ϵļ��ٶ�Ϊ��"+values[2]);		
		}		
	};
}