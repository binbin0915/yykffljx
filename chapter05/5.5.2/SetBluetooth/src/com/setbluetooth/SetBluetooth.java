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
	private BluetoothAdapter bluetoothAdapter = null;// ��������������
	private TextView statusText = null;// ����״̬��ʾTextView�������
	private Button openButton = null;// ������Button�������
	private Button closeButton = null;// �ر�����Button�������

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		statusText = (TextView) findViewById(R.id.status_text);//ʵ��������״̬��ʾTextView�������
		openButton = (Button) findViewById(R.id.open_button);//ʵ����������Button�������
		closeButton = (Button) findViewById(R.id.close_button);//ʵ�����ر�����Button�������
		openButton.setOnClickListener(new Button.OnClickListener() {//ΪopenButton��ӵ���¼�����

			@Override
			public void onClick(View arg0) {
				openBluetooth();// ���ÿ�����������
				statusText.setText("����״̬������");// ��������״̬��ʾ
			}
		});
		closeButton.setOnClickListener(new Button.OnClickListener() {//ΪcloseButton��ӵ���¼�����

			@Override
			public void onClick(View arg0) {
				closeBluetooth();// ���ùر���������
				statusText.setText("����״̬���ر�");// ��������״̬��ʾ
			}
		});
		// �õ�һ����������������, getDefaultAdapter()�������ڻ�ȡ������������������
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter == null) {// ���������Ϊnull����֧������
			Toast.makeText(this, "���豸��֧������", Toast.LENGTH_LONG).show();
			finish();// �رճ���
			return;
		}
		if (bluetoothAdapter.isEnabled()) {// �ж������Ƿ���
			statusText.setText("����״̬������");// ��������״̬��ʾ
		} else {
			statusText.setText("����״̬���ر�");// ��������״̬��ʾ
		}
	}

	/**
	 * ����������
	 */
	public void openBluetooth() {
		if (!bluetoothAdapter.isEnabled()) {// �ж������Ƿ��
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);// ������
			startActivity(enableIntent);
			// ��ʾ�������ڿ�����
			Toast.makeText(this, "����������......", Toast.LENGTH_SHORT).show();
		} else {
			// ��ʾ�����Ѿ�����
			Toast.makeText(this, "�����Ѿ�����", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * �ر���������
	 */
	public void closeBluetooth() {
		if (bluetoothAdapter.isEnabled()) {// �ж������Ƿ��
			bluetoothAdapter.disable();// �ر�����
			// ��ʾ�����Ѿ��ر�
			Toast.makeText(this, "�����Ѿ��ر�", Toast.LENGTH_SHORT).show();
		} else {
			// ��ʾ�����ǹرյ�
			Toast.makeText(this, "�����ǹرյ�", Toast.LENGTH_SHORT).show();
		}
	}
}