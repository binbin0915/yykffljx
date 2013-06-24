package com.tyhj;

import java.io.IOException;
import java.io.InputStream;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 线路详情窗体
 * */
public class TripDetail extends Activity {
	private TextView tripName;// 声明TextView变量
	private TextView startPos;// 声明TextView变量
	private TextView endPos;// 声明TextView变量
	private TextView tripPro;// 声明TextView变量
	private TextView distance;// 声明TextView变量
	private TextView suggest;// 声明TextView变量
	private TextView bestSeason;// 声明TextView变量
	private ImageView tripPic;// 声明ImageView变量
	private ImageView jsnd;// 声明ImageView变量
	private ImageView fgzs;// 声明ImageView变量
	private ImageView rwzs;// 声明ImageView变量
	private ImageView mszs;// 声明ImageView变量
	private TextView tripDesc;// 声明TextView变量
	private TextView tripTip;// 声明TextView变量
	private ImageButton xqButt;// 声明ImageButton变量,详情按钮
	private ImageButton xqdButt;// 声明ImageButton变量,兴趣点按钮
	private ImageButton sdButt;// 声明ImageButton变量,服务站按钮
	private ImageView picImgView;// 声明ImageView变量,路书图片
	private TextView tgzs;// 声明TextView变量,指数文字
	private TextView js;// 声明TextView变量,路书介绍文字
	private TextView ts;// 声明TextView变量,路书提示文字

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
		this.setTitle(R.string.title);// 设置窗体标题栏文字
		this.setContentView(R.layout.tripdetail);// 加载tripdetail.xml布局资源
		setTripDetail();
	}

	/**
	 * 获取路书详细信息
	 * 
	 * @param
	 * @return
	 */
	public void setTripDetail() {
		Bundle tripObj = getIntent().getExtras();// 获取前一个窗体传递过来的参数
		final Route route = (Route) tripObj.getSerializable("tripObj");// 将参数转化成序列化对象
		tripName = (TextView) this.findViewById(R.id.tripName);// 获取路书名称的TextView对象
		tripName.setText(route.getName());// 设置路书名称TextView组件显示文字
		startPos = (TextView) this.findViewById(R.id.startPos);// 获取起点的TextView对象
		startPos.setText("起       点：" + route.getStartPointName());// 设置起点TextView组件显示文字
		endPos = (TextView) this.findViewById(R.id.endPos);// 获取终点的TextView对象
		endPos.setText("终       点：" + route.getEndPointName());// 设置终点TextView组件显示文字
		tripPro = (TextView) this.findViewById(R.id.tripPro);// 获取所在省的TextView对象
		tripPro.setText("所  在 省：" + route.getCity());// 设置所在省TextView组件显示文字
		distance = (TextView) this.findViewById(R.id.distance);// 获取里程的TextView对象
		distance.setText("里       程：" + route.getMileage() + "公里");// 设置里程TextView组件显示文字
		suggest = (TextView) this.findViewById(R.id.suggest);// 获取建议行程的TextView对象
		suggest.setText("建议行程：" + route.getProposedItinerary() + " 天");// 设置建议行程TextView组件显示文字
		bestSeason = (TextView) this.findViewById(R.id.bestSeason);// 获取最佳季节的TextView对象
		bestSeason.setText("最佳季节：" + route.getBestSeason());// 设置最佳季节TextView组件显示文字
		tripPic = (ImageView) this.findViewById(R.id.tripPic); // 获取路书图片的ImageView对象
		String tripImg = route.getImage().split("[.]")[0] + "_b.jpg";// 获取图片称
		InputStream iso;
		try {
			iso = this.getAssets().open(route.getId() + "/" + tripImg);// 打开assets下的图片
			Bitmap bitmap = null;
			bitmap = BitmapFactory.decodeStream(iso);
			tripPic.setImageBitmap(bitmap);// 设置tripPic上显示的图片
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		tgzs = (TextView) this.findViewById(R.id.tgzs);// 获取线路指数TextView组件
		TextPaint tgzstp = tgzs.getPaint();
		tgzstp.setFakeBoldText(true);// 设置线路指数TextView为粗体显示
		js = (TextView) this.findViewById(R.id.js);// 获取线路介绍TextView组件
		TextPaint jstp = js.getPaint();
		jstp.setFakeBoldText(true);// 设置线路介绍TextView为粗体显示
		ts = (TextView) this.findViewById(R.id.ts);// 获取线路提示TextView组件
		TextPaint tstp = ts.getPaint();
		tstp.setFakeBoldText(true);// 设置线路提示TextView为粗体显示

		// 四个指数
		jsnd = (ImageView) this.findViewById(R.id.jsnd);// 获取路况指数ImageView组件
		// 设置路况指数显示的图片，getPicDrawId为自定义方法,实现将指数数字转化为对应的图片
		jsnd.setBackgroundResource(getPicDrawId(route.getRecommend()));
		fgzs = (ImageView) this.findViewById(R.id.fgzs);// 获取风光指数ImageView组件
		fgzs.setBackgroundResource(getPicDrawId(route.getScenic()));// 设置风光指数显示的图片
		rwzs = (ImageView) this.findViewById(R.id.rwzs);// 获取人文指数ImageView组件
		rwzs.setBackgroundResource(getPicDrawId(route.getRenwen()));// 设置人文指数显示的图片
		mszs = (ImageView) this.findViewById(R.id.mszs);// 获取美食指数ImageView组件
		mszs.setBackgroundResource(getPicDrawId(route.getFood()));// 设置美食指数显示的图片
		tripDesc = (TextView) this.findViewById(R.id.tripDesc);// 获取介绍TextView组件
		String tripDescStr = route.getDesc().replace("##", "\n\n");// 介绍文字中##为分段标志，所以替换成换行符
		tripDescStr = tripDescStr.replace("\"", "”");// 将半角字符替换成全角字符显示
		tripDescStr = tripDescStr.replace(",", "，");
		tripDescStr = tripDescStr.replace("(", "（");
		tripDescStr = tripDescStr.replace(")", "）");
		tripDescStr = tripDescStr.replace(";", "；");
		tripDesc.setText(tripDescStr);// 设置介绍TextView组件显示的文字
		tripTip = (TextView) this.findViewById(R.id.tripTip);// 获取提示TextView组件
		String tripTipStr = route.getTip().replace("##", "\n\n");// 提示文字中##为分段标志，所以替换成换行符
		tripTipStr = tripTipStr.replace("\"", "”");// 将半角字符替换成全角字符显示
		tripTipStr = tripTipStr.replace(",", "，");
		tripTipStr = tripTipStr.replace("(", "（");
		tripTipStr = tripTipStr.replace(")", "）");
		tripTipStr = tripTipStr.replace(";", "；");
		tripTip.setText(tripTipStr + "\n");// 设置提示TextView组件显示的文字

		xqButt = (ImageButton) this.findViewById(R.id.xqButt);// 获取详情ImageButton组件
		xqButt.setOnClickListener(new OnClickListener() {// 详情ImageButton组件的点击事件
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it = new Intent();// 实例化Intent
						Bundle tripMsg = new Bundle();// 实例化Bundle
						it.setClass(TripDetail.this, TripSegment.class);// 设置Class
						tripMsg.putString("tripId", route.getId());// 将路书id保存到Bundle中
						tripMsg.putString("tripName", route.getName());// 将路书名字保存到Bundle中
						it.putExtras(tripMsg);// 将tripMsg对象作为参数传递给下一个窗体
						startActivity(it);// 启动Activity
					}

				});
		xqdButt = (ImageButton) this.findViewById(R.id.xqdButt);// 获取兴趣点ImageButton组件
		xqdButt.setOnClickListener(new OnClickListener() {// 兴趣点ImageButton组件的点击事件

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it = new Intent();// 实例化Intent
						Bundle tripMsg = new Bundle(); // 实例化Bundle
						it.setClass(TripDetail.this, TripPoiList.class);// 设置Class
						tripMsg.putString("tripId", route.getId());// 将路书id保存到Bundle中
						tripMsg.putString("tripName", route.getName());// 将路书名字保存到Bundle中
						it.putExtras(tripMsg); // 将tripMsg对象作为参数传递给下一个窗体
						startActivity(it);// 启动Activity
					}

				});
		sdButt = (ImageButton) this.findViewById(R.id.sdButt);// 获取服务区ImageButton组件
		sdButt.setOnClickListener(new OnClickListener() {// 服务区ImageButton组件的点击事件

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it = new Intent();// 实例化Intent
						it.setClass(TripDetail.this, DZDealersList.class);// 设置Class
						startActivity(it);// 启动Activity
					}

				});
		picImgView = (ImageView) this.findViewById(R.id.tripPic);// 获取路书图片ImageView组件
		picImgView.setOnClickListener(new OnClickListener() {// 路书图片ImageView组件的点击事件

					@Override
					public void onClick(View v) {// 点击路书图片，显示路书图片的原尺寸效果
						// TODO Auto-generated method stub
						Intent it = new Intent();// 实例化Intent
						Bundle tripDetailPic = new Bundle(); // 实例化Bundle
						it.setClass(TripDetail.this, TripDetailPic.class);// 设置Class
						String tripImg = route.getId() + "/y_"
								+ route.getImage().split("[.]")[0] + ".jpg";// 大图图片的路径
						tripDetailPic.putString("tripImgPath", tripImg);// 将图片路径保存到tripDetailPic中
						it.putExtras(tripDetailPic);// 将tripDetailPic对象作为参数传递给下一个窗体
						startActivity(it);// 启动Activity
					}

				});
	}

	/**
	 * 获取指数数字对应的指数图片
	 * 
	 * @param zs
	 *            ：指数数字信息
	 * @return
	 */
	public int getPicDrawId(int zs) {
		int zsint = 0;
		switch (zs) {
		case 0:
			zsint = R.drawable.xing0;
			break;
		case 1:
			zsint = R.drawable.xing1;
			break;
		case 2:
			zsint = R.drawable.xing2;
			break;
		case 3:
			zsint = R.drawable.xing3;
			break;
		case 4:
			zsint = R.drawable.xing4;
			break;
		case 5:
			zsint = R.drawable.xing5;
			break;
		case 6:
			zsint = R.drawable.xing6;
			break;
		case 7:
			zsint = R.drawable.xing7;
			break;
		case 8:
			zsint = R.drawable.xing8;
			break;
		case 9:
			zsint = R.drawable.xing9;
			break;
		case 10:
			zsint = R.drawable.xing10;
			break;
		}
		return zsint;
	}

}
