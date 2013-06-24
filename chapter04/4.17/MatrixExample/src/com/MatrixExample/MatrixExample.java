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
	private Bitmap myBitmap;// 声明Bitmap类型变量
	private Matrix myMatrix = new Matrix();// 声明并创建Matrix对象
	private int width;// 声明int类型变量
	private int height;// 声明int类型变量

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);// 加载布局文件

		Button rotateLeftBut = (Button) this.findViewById(R.id.rotateLeft);// 获取XML文件中的左旋Button
		Button rotateRightBut = (Button) this.findViewById(R.id.rotateRight);// 获取XML文件中的右旋Button
		Button scaleBigBut = (Button) this.findViewById(R.id.scaleBig);// 获取XML文件中的放大Button
		Button scaleSmallBut = (Button) this.findViewById(R.id.scaleSmall);// 获取XML文件中的缩小Button
		// 获取资源文件中的p2图片的Bitmap,getResources()方法用来获取应用程序下的系统资源
		myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.p2);
		// 获取图片的原始的大小
		width = myBitmap.getWidth();
		height = myBitmap.getHeight();

		rotateLeftBut.setOnClickListener(new OnClickListener() {// 左旋转Button的单击事件

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						myMatrix.postRotate(-90);// 逆时针旋转90度
						// 创建一个新的图片，重新绘图
						Bitmap newBitmap = Bitmap.createBitmap(myBitmap, 0, 0,
								width, height, myMatrix, true);

						// 创建Bitmap转换为Drawable对象，使其可以使用在ImageView和ImageButton中
						BitmapDrawable newbmp = new BitmapDrawable(newBitmap);

						// 创建ImageView的对象
						ImageView imageView = (ImageView) MatrixExample.this
								.findViewById(R.id.pic);
						// 设置ImageView的背景图片
						imageView.setImageDrawable(newbmp);
					}

				});
		// 右旋转
		rotateRightBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myMatrix.postRotate(90);// 顺时针旋转90度
				// 创建一个新的图片，重新绘图
				Bitmap newBitmap = Bitmap.createBitmap(myBitmap, 0, 0, width,
						height, myMatrix, true);

				// 创建Bitmap转换为Drawable对象，使其可以使用在ImageView和ImageButton中
				BitmapDrawable newbmp = new BitmapDrawable(newBitmap);

				// 创建ImageView的对象
				ImageView imageView = (ImageView) MatrixExample.this
						.findViewById(R.id.pic);
				// 设置ImageView的背景图片
				imageView.setImageDrawable(newbmp);
			}

		});
		// 缩小
		scaleSmallBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// 缩放图片的动作，宽高的缩放比例为0.8
				myMatrix.postScale(0.8f, 0.8f);

				// 创建一个新的图片，重新绘图
				Bitmap newBitmap = Bitmap.createBitmap(myBitmap, 0, 0, width,
						height, myMatrix, true);

				// 创建Bitmap转换为Drawable对象，使其可以使用在ImageView和ImageButton中
				BitmapDrawable newbmp = new BitmapDrawable(newBitmap);

				// 创建ImageView的对象
				ImageView imageView = (ImageView) MatrixExample.this
						.findViewById(R.id.pic);
				// 设置ImageView的背景图片
				imageView.setImageDrawable(newbmp);
			}

		});
		// 放大
		scaleBigBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 缩放图片的动作，宽高的缩放比例为0.8
				myMatrix.postScale(1.2f, 1.2f);
			
				// 创建一个新的图片，重新绘图
				Bitmap newBitmap = Bitmap.createBitmap(myBitmap, 0, 0, width,
						height, myMatrix, true);

				// 创建Bitmap转换为Drawable对象，使其可以使用在ImageView和ImageButton中
				BitmapDrawable newbmp = new BitmapDrawable(newBitmap);

				// 创建ImageView的对象
				ImageView imageView = (ImageView) MatrixExample.this
						.findViewById(R.id.pic);
				// 设置ImageView的背景图片
				imageView.setImageDrawable(newbmp);
			}

		});

	}
}