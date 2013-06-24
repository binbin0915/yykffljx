package com.setbluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SetBluetooth extends Activity {
	private BluetoothAdapter bluetoothAdapter = null;// 本地蓝牙适配器
	private TextView statusText = null;// 蓝牙状态显示TextView组件对象
	private Button openButton = null;// 打开蓝牙Button组件对象
	private Button closeButton = null;// 关闭蓝牙Button组件对象

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		statusText = (TextView) findViewById(R.id.status_text);//实例化蓝牙状态显示TextView组件对象
		openButton = (Button) findViewById(R.id.open_button);//实例化打开蓝牙Button组件对象
		closeButton = (Button) findViewById(R.id.close_button);//实例化关闭蓝牙Button组件对象
		openButton.setOnClickListener(new Button.OnClickListener() {//为openButton添加点击事件监听

			@Override
			public void onClick(View arg0) {
				openBluetooth();// 调用开启蓝牙方法
				statusText.setText("蓝牙状态：开启");// 设置蓝牙状态提示
			}
		});
		closeButton.setOnClickListener(new Button.OnClickListener() {//为closeButton添加点击事件监听

			@Override
			public void onClick(View arg0) {
				closeBluetooth();// 调用关闭蓝牙方法
				statusText.setText("蓝牙状态：关闭");// 设置蓝牙状态提示
			}
		});
		// 得到一个本地蓝牙适配器, getDefaultAdapter()函数用于获取本地蓝牙适配器　　
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter == null) {// 如果适配器为null，则不支持蓝牙
			Toast.makeText(this, "该设备不支持蓝牙", Toast.LENGTH_LONG).show();
			finish();// 关闭程序
			return;
		}
		if (bluetoothAdapter.isEnabled()) {// 判断蓝牙是否开启
			statusText.setText("蓝牙状态：开启");// 设置蓝牙状态提示
		} else {
			statusText.setText("蓝牙状态：关闭");// 设置蓝牙状态提示
		}
	}

	/**
	 * 打开蓝牙方法
	 */
	public void openBluetooth() {
		if (!bluetoothAdapter.isEnabled()) {// 判断蓝牙是否打开
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);// 打开蓝牙
			startActivity(enableIntent);
			// 提示蓝牙正在开启中
			Toast.makeText(this, "蓝牙开启中......", Toast.LENGTH_SHORT).show();
		} else {
			// 提示蓝牙已经开启
			Toast.makeText(this, "蓝牙已经开启", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 关闭蓝牙方法
	 */
	public void closeBluetooth() {
		if (bluetoothAdapter.isEnabled()) {// 判断蓝牙是否打开
			bluetoothAdapter.disable();// 关闭蓝牙
			// 提示蓝牙已经关闭
			Toast.makeText(this, "蓝牙已经关闭", Toast.LENGTH_SHORT).show();
		} else {
			// 提示蓝牙是关闭的
			Toast.makeText(this, "蓝牙是关闭的", Toast.LENGTH_SHORT).show();
		}
	}
}