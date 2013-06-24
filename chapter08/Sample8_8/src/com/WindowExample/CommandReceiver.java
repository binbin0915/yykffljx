package com.WindowExample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CommandReceiver extends BroadcastReceiver {
    int status;//״ֵ̬
    public static final String UPDATE_STATUS="UPDATE";
	@Override
	public void onReceive(final Context context, Intent intent) {
		updateUI(context);
	}
	public void updateUI(Context context)
	{
		try
		{
			SampleActivity.tv.setTextSize(20);
			SampleActivity.tv.setText(ZMDUtil.next());
		}catch(Exception e)
		{
			
		}		
	}
}
