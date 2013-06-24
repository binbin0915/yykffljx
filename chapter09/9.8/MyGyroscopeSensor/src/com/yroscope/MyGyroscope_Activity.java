package com.yroscope;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
public class MyGyroscope_Activity extends Activity {
	SensorManager mySensorManager;//SensorManager对象引用	
	Sensor myGyroscope; //陀螺仪传感器
	TextView tvX;	//TextView对象引用	
	TextView tvY;	//TextView对象引用	
	TextView tvZ;	//TextView对象引用
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);	//用于显示x轴方向加速度
        tvY = (TextView)findViewById(R.id.tvY);	//用于显示y轴方向加速度	
        tvZ = (TextView)findViewById(R.id.tvZ); //用于显示z轴方向加速度
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);//获得SensorManager对象	
        myGyroscope=mySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }
    @Override
	protected void onResume() //重写onResume方法
	{
		super.onResume();
		mySensorManager.registerListener(mySensorListener, myGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
	}	
	@Override
	protected void onPause()//重写onPause方法
	{
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);//取消注册监听器
	}
	private SensorEventListener mySensorListener = new SensorEventListener()
	{//开发实现了SensorEventListener接口的传感器监听器
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) 
		{
		}
		@Override
		public void onSensorChanged(SensorEvent event) 
		{
			float []values=event.values;//获取三个轴方向感上的加速度值
			System.out.println(values.length);
			tvX.setText("沿x轴旋转的角速度为："+values[0]);	
			tvY.setText("沿y轴旋转的角速度为："+values[1]);		
			tvZ.setText("沿z轴旋转的角速度为："+values[2]);
		}		
	};
}