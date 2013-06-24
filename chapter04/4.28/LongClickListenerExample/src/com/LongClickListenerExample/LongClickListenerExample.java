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
		setContentView(R.layout.main);// 加载布局资源文件

		Button longBut = (Button) this.findViewById(R.id.longBut);// 从资源文件中获取Button
		longBut.setOnLongClickListener(new OnLongClickListener() {// 监听长时单击时间
					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						//
						Toast.makeText(LongClickListenerExample.this,
								"OnLongClickListener事件", Toast.LENGTH_LONG)
								.show();
						return false;
					}

				});
	}
}