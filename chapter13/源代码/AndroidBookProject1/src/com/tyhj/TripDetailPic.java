package com.tyhj;

import java.io.IOException;
import java.io.InputStream;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * 点击线路详情中的图片，展示窗体
 * */
public class TripDetailPic extends Activity {
	private ImageView tripPic;// 声明ImageView变量

	/**
	 * 重写Activity中的onCreate的方法。 该方法是在Activity创建时被系统调用，是一个Activity生命周期的开始。 *
	 * 
	 * @param savedInstanceState
	 *            ：保存Activity的状态的。 Bundle类型的数据与Map类型的数据相似，都是以key-value的形式存储数据的
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.tripdetailpic);// 设置窗体布局资源

		tripPic = (ImageView) this.findViewById(R.id.tripDetailPic);// 获取图片的ImageView组件

		Bundle tripDetailPic = getIntent().getExtras();// 获取传递过来的Bundle

		String tripDetailName = tripDetailPic.getString("tripName");// 获取路书名参数

		String tripPicPath = tripDetailPic.getString("tripImgPath");// 获取路书图片路径

		InputStream iso;
		try {
			iso = this.getAssets().open(tripPicPath);// 打开assets下的图片
			Bitmap bitmap = null;
			bitmap = BitmapFactory.decodeStream(iso);
			tripPic.setImageBitmap(bitmap);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
