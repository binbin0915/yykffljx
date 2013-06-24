package com.LongClickListenerExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LongClickListenerExample extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);// ���ز�����Դ�ļ�

		Button longBut = (Button) this.findViewById(R.id.longBut);// ����Դ�ļ��л�ȡButton
		longBut.setOnLongClickListener(new OnLongClickListener() {// ������ʱ����ʱ��
					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						//
						Toast.makeText(LongClickListenerExample.this,
								"OnLongClickListener�¼�", Toast.LENGTH_LONG)
								.show();
						return false;
					}

				});
	}
}