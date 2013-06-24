package com.tyhj;

import java.io.IOException;
import android.view.View.OnClickListener;
import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 线路分段展示窗体
 * */
public class TripSegment extends Activity {

	private TextView tripSegName;// 声明TextView变量
	private LinearLayout tripSegsLayout;// 声明LinearLayout变量
	private LinearLayout titleSegLayout;// 声明LinearLayout变量
	private TextView titleSegTitle;// 声明TextView变量
	private TextView titleSegTitleNum;// 声明TextView变量
	private ImageButton mapButt;// 声明ImageButton变量
	private TextView tripDescSeg;// 声明TextView变量
	private ImageView tripPicSeg;// 声明ImageView变量

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
		this.setTitle(R.string.title);// 设置标题文字
		this.setContentView(R.layout.tripsegment);// 加载布局资源
		getTripSegments();// 获取路书的分段信息并显示
	}

	/**
	 * 获取路书的分段信息并显示
	 * 
	 * @param
	 * @return
	 */
	public void getTripSegments() {
		Bundle bundle = getIntent().getExtras();// 获取Bundle
		final String TripName = bundle.getString("tripName");// 获取路书名参数
		final String tripId = bundle.getString("tripId");// 获取路书id参数
		tripSegName = (TextView) this.findViewById(R.id.tripName);// 获取路书分段名称TextView组件
		tripSegName.setText(TripName);// 将路书分段名称显示在tripSegName
		// 动态加载路书分段信息
		tripSegsLayout = (LinearLayout) this.findViewById(R.id.tripSegs);// 获取路书分段信息的LinearLayout
		WAnalysisFile readFile = new WAnalysisFile();// 声明WAnalysisFile类对象
		List<Route> routList = readFile.getRouteList(this, tripId);// 获取分段信息列表
		for (int i = 0; i < routList.size(); i += 1) {// 循环获取分段信息
			final Route route = routList.get(i);
			// 动态生成标题和地图按钮，及所在的layout
			titleSegLayout = new LinearLayout(this);// 创建LinearLayout对象，用来显示分段信息
			titleSegLayout.setOrientation(LinearLayout.HORIZONTAL);// 设置布局方式为水平布局
			// 创建布局参数
			LinearLayout.LayoutParams titleSegLayoutParam = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			titleSegLayoutParam.setMargins(0, 8, 0, 0);// 设置边距
			titleSegLayout.setLayoutParams(titleSegLayoutParam);// titleSegLayout添加titleSegLayoutParam布局参数
			// 标题前的序号
			titleSegTitleNum = new TextView(this);// 创建TextView组件
			// 创建布局参数
			LinearLayout.LayoutParams titleSegNumParam = new LinearLayout.LayoutParams(
					26, LayoutParams.WRAP_CONTENT);
			titleSegTitleNum.setLayoutParams(titleSegNumParam);// titleSegTitleNum添加titleSegNumParam布局参数
			titleSegTitleNum.setText((i + 1) + ".");// 设置titleSegTitleNum显示的文字
			titleSegTitleNum.setTextColor(Color.BLACK);// 设置titleSegTitleNum文字的颜色
			TextPaint xhtp = titleSegTitleNum.getPaint();
			xhtp.setFakeBoldText(true);// 设置titleSegTitleNum文字粗体显示
			// 标题
			titleSegTitle = new TextView(this);// 创建TextView组件
			// 创建布局参数
			LinearLayout.LayoutParams titleSegParam = new LinearLayout.LayoutParams(
					207, LayoutParams.WRAP_CONTENT);
			titleSegTitle.setLayoutParams(titleSegParam);// titleSegTitle添加titleSegParam布局参数
			titleSegTitle.setText(route.getName().replaceAll("——", "—") + " , "
					+ route.getMileage() + "公里");
			titleSegTitle.setTextColor(Color.BLACK);// 设置titleSegTitle文字的颜色
			TextPaint bttp = titleSegTitle.getPaint();
			bttp.setFakeBoldText(true);// 设置titleSegTitle文字粗体显示

			// 地图按钮
			mapButt = new ImageButton(this);// 创建ImageButton组件
			// 创建布局参数
			LinearLayout.LayoutParams mapButtParam = new LinearLayout.LayoutParams(
					50, 23);
			mapButtParam.setMargins(5, 0, 0, 0);// 设置边距
			mapButt.setLayoutParams(mapButtParam);// mapButt添加mapButtParam布局参数
			mapButt.setBackgroundResource(R.drawable.mapbut);// 设置mapButt显示的图片
			mapButt.setOnClickListener(new Button.OnClickListener() {// mapButt点击事件
						public void onClick(View arg0) {

							Bundle bl = new Bundle();// 实例化Bundle
							bl.putString("roadId", route.getId());// 保存路书id
							bl.putString("loc", route.getStartPoint());// 保存起点位置
							bl.putString("loc2", route.getEndPoint());// 保存终点位置
							bl.putString("dis", route.getMileage());// 保存里程
							bl.putBoolean("pan", true);
							bl.putString("tripId", tripId);// 保存分段id
							bl.putString("tripName", TripName);// 保存分段名称
							Intent it = new Intent();// 实例化Intent
							it.setClass(TripSegment.this, RoadMapView.class);// 设置Class
							it.putExtras(bl);// 将该对象作为参数传递给下一个窗体
							startActivity(it);// 启动Activity
						}
					});

			// 将分段标题序号，分段标题和地图按钮添加到该titleSegLayout上
			titleSegLayout.addView(titleSegTitleNum);
			titleSegLayout.addView(titleSegTitle);
			titleSegLayout.addView(mapButt);

			// 分段路书线路介绍
			tripDescSeg = new TextView(this);// 实例化TextView
			// 创建布局参数
			LinearLayout.LayoutParams tripDescSegParam = new LinearLayout.LayoutParams(
					267, LayoutParams.WRAP_CONTENT);
			tripDescSegParam.setMargins(18, 6, 0, 8);// 设置边距
			tripDescSeg.setLayoutParams(tripDescSegParam);// 为tripDescSeg添加布局参数tripDescSegParam
			tripDescSeg.setText(route.getDesc().replace("##", "\n\n"));// 分段描述中的##，显示时替换为换行
			tripDescSeg.setTextColor(Color.BLACK);// 设置文字颜色
			tripDescSeg.setLineSpacing(1.0f, 1.1f);// 设置文字间距
			// 分段路数图片
			tripPicSeg = new ImageView(this);// 实例化ImageView
			// 创建布局参数
			LinearLayout.LayoutParams tripPicSegParam = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			tripPicSegParam.setMargins(25, 0, 0, 20);// 设置边距
			tripPicSeg.setLayoutParams(tripPicSegParam);// 为tripPicSeg添加布局参数tripPicSegParam

			// 读图片文件并显示
			String tripImg = route.getImage().split("[.]")[0] + "_b.jpg";
			InputStream iso;
			try {
				iso = this.getAssets().open(
						tripId + "/SmallRoute" + "/" + route.getId()
								+ "/images/" + tripImg);// 打开assets下的图片
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeStream(iso);
				tripPicSeg.setImageBitmap(bitmap);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// tripPicSeg图片单击事件
			tripPicSeg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent it = new Intent();
					Bundle tripDetailPic = new Bundle();
					it.setClass(TripSegment.this, TripDetailPic.class);
					String tripImg = tripId + "/SmallRoute" + "/"
							+ route.getId() + "/images/" + "y_"
							+ route.getImage().split("[.]")[0] + ".jpg";
					tripDetailPic.putString("tripImgPath", tripImg);
					it.putExtras(tripDetailPic);
					startActivity(it);
				}

			});

			// 将动态生成的添加到layout上
			tripSegsLayout.addView(titleSegLayout);
			tripSegsLayout.addView(tripDescSeg);
			tripSegsLayout.addView(tripPicSeg);
		}
	}

}
