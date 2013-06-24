package com.ToastExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ToastExample extends Activity {
	Button defaultToast;
	Button defineToast;
	Button iconToast;
	Button defineAllToast;
	Toast toast;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Ĭ��Toast
		defaultToast = (Button) this.findViewById(R.id.defaultToast);
		defaultToast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// makeText����������������һ��������Context���ڶ�����������ʾ��Ϣ��
				// ��������������Ϣ����ʧ��ʽ ��������ȡֵToast.LENGTH_SHORT���ڶ�ʱ������ʧ����
				// Toast.LENGTH_LONG(�ϳ�ʱ����ʧ)
				Toast.makeText(ToastExample.this, R.string.ToastText,
						Toast.LENGTH_SHORT).show();
			}

		});
		// �Զ�����ʾλ��Toast
		defineToast = (Button) this.findViewById(R.id.defineToast);
		defineToast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toast = Toast.makeText(ToastExample.this, R.string.ToastText,
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);// ��ʾ����ֵ�λ�ã�����1��λ��ͨ��Gravity�����ã�����2��xƫ����������3��yƫ����
				toast.show();// ��ʾToast
			}
		});
		// ��ͼ���Toast
		iconToast = (Button) this.findViewById(R.id.IconToast);
		iconToast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toast = Toast.makeText(ToastExample.this, R.string.ToastText,
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout view = (LinearLayout) toast.getView();// getView():��ȡToast��View����
				ImageView imgView = new ImageView(ToastExample.this);// ����ImageView����
				imgView.setImageResource(R.drawable.icon);// ����imgView�ı���ͼƬ
				view.addView(imgView);// ��imgView��ӵ�View��
				toast.setView(view);// ��view��ʾ��Toast��
				toast.show();// ��ʾToast
			}

		});
		// ��ȫ�Զ���Toast
		defineAllToast = (Button) this.findViewById(R.id.defineAllToast);
		defineAllToast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toast = new Toast(ToastExample.this);
				LayoutInflater inflater = getLayoutInflater();// ��ȡLayoutInflater����
				// inflate():��Layout�ļ�ת��ΪView�������ǽ�definetoast.xml�е�myToastLayout���ת��ΪView
				View myToastLayout = inflater.inflate(R.layout.definetoast,
						(ViewGroup) findViewById(R.id.myToastLayout));
				// ������ʾ��Ϣ���ֵ�λ��
				toast.setGravity(Gravity.RIGHT | Gravity.BOTTOM, 40, 40);
				toast.setDuration(Toast.LENGTH_LONG);// ���������ʾ��ʾ��Ϣ

				toast.setView(myToastLayout);// ��myToastLayout��ʾ��Toast��
				toast.show();// ��ʾToast

			}

		});

	}
}