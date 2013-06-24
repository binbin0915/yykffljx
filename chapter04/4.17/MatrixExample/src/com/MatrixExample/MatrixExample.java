package com.MatrixExample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MatrixExample extends Activity {
	private Bitmap myBitmap;// ����Bitmap���ͱ���
	private Matrix myMatrix = new Matrix();// ����������Matrix����
	private int width;// ����int���ͱ���
	private int height;// ����int���ͱ���

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);// ���ز����ļ�

		Button rotateLeftBut = (Button) this.findViewById(R.id.rotateLeft);// ��ȡXML�ļ��е�����Button
		Button rotateRightBut = (Button) this.findViewById(R.id.rotateRight);// ��ȡXML�ļ��е�����Button
		Button scaleBigBut = (Button) this.findViewById(R.id.scaleBig);// ��ȡXML�ļ��еķŴ�Button
		Button scaleSmallBut = (Button) this.findViewById(R.id.scaleSmall);// ��ȡXML�ļ��е���СButton
		// ��ȡ��Դ�ļ��е�p2ͼƬ��Bitmap,getResources()����������ȡӦ�ó����µ�ϵͳ��Դ
		myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.p2);
		// ��ȡͼƬ��ԭʼ�Ĵ�С
		width = myBitmap.getWidth();
		height = myBitmap.getHeight();

		rotateLeftBut.setOnClickListener(new OnClickListener() {// ����תButton�ĵ����¼�

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						myMatrix.postRotate(-90);// ��ʱ����ת90��
						// ����һ���µ�ͼƬ�����»�ͼ
						Bitmap newBitmap = Bitmap.createBitmap(myBitmap, 0, 0,
								width, height, myMatrix, true);

						// ����Bitmapת��ΪDrawable����ʹ�����ʹ����ImageView��ImageButton��
						BitmapDrawable newbmp = new BitmapDrawable(newBitmap);

						// ����ImageView�Ķ���
						ImageView imageView = (ImageView) MatrixExample.this
								.findViewById(R.id.pic);
						// ����ImageView�ı���ͼƬ
						imageView.setImageDrawable(newbmp);
					}

				});
		// ����ת
		rotateRightBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myMatrix.postRotate(90);// ˳ʱ����ת90��
				// ����һ���µ�ͼƬ�����»�ͼ
				Bitmap newBitmap = Bitmap.createBitmap(myBitmap, 0, 0, width,
						height, myMatrix, true);

				// ����Bitmapת��ΪDrawable����ʹ�����ʹ����ImageView��ImageButton��
				BitmapDrawable newbmp = new BitmapDrawable(newBitmap);

				// ����ImageView�Ķ���
				ImageView imageView = (ImageView) MatrixExample.this
						.findViewById(R.id.pic);
				// ����ImageView�ı���ͼƬ
				imageView.setImageDrawable(newbmp);
			}

		});
		// ��С
		scaleSmallBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// ����ͼƬ�Ķ�������ߵ����ű���Ϊ0.8
				myMatrix.postScale(0.8f, 0.8f);

				// ����һ���µ�ͼƬ�����»�ͼ
				Bitmap newBitmap = Bitmap.createBitmap(myBitmap, 0, 0, width,
						height, myMatrix, true);

				// ����Bitmapת��ΪDrawable����ʹ�����ʹ����ImageView��ImageButton��
				BitmapDrawable newbmp = new BitmapDrawable(newBitmap);

				// ����ImageView�Ķ���
				ImageView imageView = (ImageView) MatrixExample.this
						.findViewById(R.id.pic);
				// ����ImageView�ı���ͼƬ
				imageView.setImageDrawable(newbmp);
			}

		});
		// �Ŵ�
		scaleBigBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ����ͼƬ�Ķ�������ߵ����ű���Ϊ0.8
				myMatrix.postScale(1.2f, 1.2f);
			
				// ����һ���µ�ͼƬ�����»�ͼ
				Bitmap newBitmap = Bitmap.createBitmap(myBitmap, 0, 0, width,
						height, myMatrix, true);

				// ����Bitmapת��ΪDrawable����ʹ�����ʹ����ImageView��ImageButton��
				BitmapDrawable newbmp = new BitmapDrawable(newBitmap);

				// ����ImageView�Ķ���
				ImageView imageView = (ImageView) MatrixExample.this
						.findViewById(R.id.pic);
				// ����ImageView�ı���ͼƬ
				imageView.setImageDrawable(newbmp);
			}

		});

	}
}