package com.tyhj;

import java.io.IOException;
import java.io.InputStream;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * �����·�����е�ͼƬ��չʾ����
 * */
public class TripDetailPic extends Activity {
	private ImageView tripPic;// ����ImageView����

	/**
	 * ��дActivity�е�onCreate�ķ����� �÷�������Activity����ʱ��ϵͳ���ã���һ��Activity�������ڵĿ�ʼ�� *
	 * 
	 * @param savedInstanceState
	 *            ������Activity��״̬�ġ� Bundle���͵�������Map���͵��������ƣ�������key-value����ʽ�洢���ݵ�
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.tripdetailpic);// ���ô��岼����Դ

		tripPic = (ImageView) this.findViewById(R.id.tripDetailPic);// ��ȡͼƬ��ImageView���

		Bundle tripDetailPic = getIntent().getExtras();// ��ȡ���ݹ�����Bundle

		String tripDetailName = tripDetailPic.getString("tripName");// ��ȡ·��������

		String tripPicPath = tripDetailPic.getString("tripImgPath");// ��ȡ·��ͼƬ·��

		InputStream iso;
		try {
			iso = this.getAssets().open(tripPicPath);// ��assets�µ�ͼƬ
			Bitmap bitmap = null;
			bitmap = BitmapFactory.decodeStream(iso);
			tripPic.setImageBitmap(bitmap);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
