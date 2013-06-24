package com.orientation;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
public class MyOrientation_Activity extends Activity {
	SensorManager mySensorManager;//SensorManager对象引用		
	Sensor myOrientation_Sensor;//方向传感器 
	TextView tvX,tvY,tvZ,info;	//TextView对象引用
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);	
        tvY = (TextView)findViewById(R.id.tvY);	
        tvZ = (TextView)findViewById(R.id.tvZ);	
        info=(TextView)findViewById(R.id.info);
        //获得SensorManager对象
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        myOrientation_Sensor=mySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION); 
        String str="\n名字: "+myOrientation_Sensor.getName()+"\n电池 :"+myOrientation_Sensor.getPower()+
        "\n类型 :"+myOrientation_Sensor.getType()+"\nVendor: "+myOrientation_Sensor.getVendor()+
        "\n版本: "+myOrientation_Sensor.getVersion()+"\n幅度: "+myOrientation_Sensor.getMaximumRange();
        info.setText(str);//将信息字符串赋予名为info的TextView
    }
    @Override
	protected void onResume()//重写onResume方法
	{						
		mySensorManager.registerListener(mySensorListener, 
				myOrientation_Sensor, SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
	}	
	@Override
	protected void onPause()									//重写onPause方法
	{									
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
			System.out.println(values.length+"=======");
			tvX.setText("手机沿Yaw轴转过的角度为:"+values[0]);	
			tvY.setText("手机沿Pitch轴转过的角度为:"+values[1]);	
			tvZ.setText("手机沿Roll轴转过的角度为:"+values[2]);	
		}		
	};
}