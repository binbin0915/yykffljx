package com.tyhj;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 兴趣点详情窗体 
 * */
public class PoiDetail extends Activity{
	private TextView detailPoiName;//声明TextView变量
	private TextView detailPoiAddr;//声明TextView变量
	private TextView detailPoiTel;//声明TextView变量
	private TextView detailPoiDesc;//声明TextView变量
	private ImageView detailPoiPic;//声明ImageView变量
	private LinearLayout detailPicLayout;//声明LinearLayout变量
	private LinearLayout layoutPoiButs;//声明LinearLayout变量
	private LinearLayout addrAndTelLayout;//声明LinearLayout变量
	private LocationManager locationManager;//声明LocationManager变量
	private PoiPoint poi;//声明PoiPoint变量
	private MediaPlayer player = new MediaPlayer();//声明MediaPlayer变量
	/**
	 * 重写Activity中的onCreate的方法。
	 * 该方法是在Activity创建时被系统调用，是一个Activity生命周期的开始。
	 * @param savedInstanceState：保存Activity的状态的。
	 *        Bundle类型的数据与Map类型的数据相似，都是以key-value的形式存储数据的
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.title);//设置标题
		this.setContentView(R.layout.poidetail);//加载布局资源
		setTripDetail();//获取兴趣点详情
	}
	
	/**
	 * 获取兴趣点详情
	 */
	public void setTripDetail(){
		Bundle poiMsg=getIntent().getExtras();
		poi=(PoiPoint) poiMsg.getSerializable("poiObj");//获取PoiPoint对象
		final String tripId=poiMsg.getString("tripId");//获取路书id
		String TripName=poiMsg.getString("tripName");//获取路书名称
		detailPoiName=(TextView)this.findViewById(R.id.detailPoiName);//获取兴趣点名称TextView组件
		detailPoiName.setText(poi.getName());//设置兴趣点名称		
		addrAndTelLayout=(LinearLayout) this.findViewById(R.id.addrAndTelLayout);//兴趣点电话，布局
		
		String addr=poi.getAddress();//获取兴趣点的地址
		String tel=poi.getTel();//获取兴趣点电话
		if(!addr.equals("")){//地址非空，显示地址信息
			detailPoiAddr=new TextView(this);//创建TextView对象
			//创建LayoutParams对象
			LinearLayout.LayoutParams detailPoiAddrParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			detailPoiAddr.setTextColor(Color.BLACK);//设置兴趣点地址文字颜色
			detailPoiAddr.setText("地址："+addr);//显示兴趣点地址
			addrAndTelLayout.addView(detailPoiAddr);//将兴趣点地址添加到addrAndTelLayout布局上
		}
		if(!tel.equals("")){
			detailPoiTel=new TextView(this);//创建TextView对象
			//创建LayoutParams对象
			LinearLayout.LayoutParams detailPoiTelParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			detailPoiTel.setTextColor(Color.BLACK);//设置兴趣点电话文字颜色
			detailPoiTel.setText("电话： "+poi.getTel().replace(" ", ","));//将电话中的空格替换为逗号
			addrAndTelLayout.addView(detailPoiTel);//将兴趣点电话添加到addrAndTelLayout布局上
		}
		detailPoiDesc=(TextView)this.findViewById(R.id.detailPoiDesc);//获取兴趣点描述的TextView组件
		detailPoiDesc.setText(poi.getDesc().replaceAll("</br>", "\n\n"));//将兴趣点描述中的</br>替换为换行符
		
		detailPicLayout=(LinearLayout) this.findViewById(R.id.poiDetailPicLayout);//获取兴趣点图片所在的LinearLayout组件
		
		detailPoiPic=new ImageView(this);//创建兴趣点图片ImageView
		//创建布局参数
		LinearLayout.LayoutParams detailPoiPicLayoutParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		detailPoiPic.setLayoutParams(detailPoiPicLayoutParam);//为detailPoiPic设置布局参数detailPoiPicLayoutParam
		if(poi.getImgList().size()!=0){
			String poiImg=poi.getImgList().get(0).split("[.]")[0]+"_m.jpg";//获取图片名
			InputStream iso;
			try {
				iso = this.getAssets().open(tripId+"/SmallRoute"+"/"+poi.getRouteId()+"/images/"+poiImg);//打开assets下的图片
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeStream(iso);
				detailPoiPic.setImageBitmap(bitmap);
				detailPicLayout.addView(detailPoiPic);//将图片添加到detailPicLayout上
				
				detailPicLayout.setOnClickListener(new OnClickListener(){//图片的点击事件

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it=new Intent();
						Bundle tripDetailPic=new Bundle();				
						it.setClass(PoiDetail.this, TripDetailPic.class);
						String tripImg=tripId+"/SmallRoute"+"/"+poi.getRouteId()+"/images/"+"y_"+poi.getImgList().get(0).split("[.]")[0]+".jpg";
						tripDetailPic.putString("tripImgPath",tripImg);
						it.putExtras(tripDetailPic);
				    	startActivity(it);
					}
					
				});
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		//获取当前位置
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.0001f, locationListener);
		
		layoutPoiButs=(LinearLayout) this.findViewById(R.id.poibuts);//获取兴趣点详情页上按钮的LinearLayout
		int []pic={R.drawable.daiwoqu,R.drawable.zhidian,R.drawable.bofang};//定义带我去，致电，播放按钮数组
		List<ImageButton> imgButtLists=new ArrayList();//创建List对象
		for(int i=0;i<3;i+=1){//动态摆放三个按钮
			final ImageButton imgBut=new ImageButton(this);//创建ImageButton按钮
			if(i==0){
				imgBut.setOnClickListener(new Button.OnClickListener() {//带我去按钮的单击事件
					public void onClick(View arg0) {		
						Criteria criteria = new Criteria();  
						criteria.setAccuracy(Criteria.ACCURACY_FINE);  
						criteria.setAltitudeRequired(false);  
						criteria.setBearingRequired(false);  
						criteria.setCostAllowed(false);  
						criteria.setPowerRequirement(Criteria.POWER_LOW);  
						
						Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true)); 
						if (location == null) {
							Toast.makeText(PoiDetail.this, "没有GPS信号",
									Toast.LENGTH_SHORT).show();
						} else {
						Intent intent = new Intent();
		        		intent.setAction(android.content.Intent.ACTION_VIEW); 
		        		intent.setData(Uri.parse("http://maps.google.com/maps?f=d&saddr="+(location.getLatitude()+","+location.getLongitude())+"&daddr="+(poi.getLat()+","+poi.getLon())+"&hl=cn"));
		        		startActivity(intent); 
						}
					}
				});	
				imgBut.setBackgroundResource(pic[i]);
				imgButtLists.add(imgBut);
			}
			if(i==1 && !"".equals(poi.getTel())){	
				imgBut.setOnClickListener(new Button.OnClickListener() {//致电按钮的单击事件
					public void onClick(View arg0) {		
						prePoiTel(poi.getTel());
					}
				});	
				imgBut.setBackgroundResource(pic[i]);//设置致电按钮的背景图片
				imgButtLists.add(imgBut);
			}
			if(i==2 && !"".equals(poi.getMp3Path())){
				imgBut.setOnClickListener(new Button.OnClickListener() {//播放按钮的单击事件
					public void onClick(View arg0) {	
						try{		
							if(player.isPlaying()){
								player.reset();	
								imgBut.setBackgroundResource(R.drawable.bofang);
							}
							else{
								player.reset();	
								
								AssetFileDescriptor afd = PoiDetail.this.getAssets().openFd(tripId+"/SmallRoute"+"/"+poi.getRouteId()+"/mp3/"+poi.getMp3Path().toLowerCase());
								player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
								player.prepare();
								player.start();
								imgBut.setBackgroundResource(R.drawable.stop);
							}
							player.setOnCompletionListener(new OnCompletionListener(){
								public void onCompletion(MediaPlayer mp) {
									imgBut.setBackgroundResource(R.drawable.bofang);
								}
							});
							
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				});	
				imgBut.setBackgroundResource(pic[i]);	
				imgButtLists.add(imgBut);
			}
			
		}
		
		//统一设置按钮间距
		for(int i=0;i<imgButtLists.size();i++){
			
			if(imgButtLists.size()==4){
				LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				margin.setMargins(0, 0, 5, 0);
				imgButtLists.get(i).setLayoutParams(margin);
				layoutPoiButs.addView(imgButtLists.get(i));
			}
			if(imgButtLists.size()==3){
				LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				margin.setMargins(10, 0,20, 0);
				imgButtLists.get(i).setLayoutParams(margin);
				layoutPoiButs.addView(imgButtLists.get(i));
			}
			if(imgButtLists.size()==2){
				LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				margin.setMargins(10, 0,40, 0);
				imgButtLists.get(i).setLayoutParams(margin);
				layoutPoiButs.addView(imgButtLists.get(i));
			}
			
		}
	}
	
	/** 
	 * 撤销关闭mp3
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==4 && player.isPlaying()){
			player.reset();
		}	
		return super.onKeyDown(keyCode, event);
	}
	
	
	//位置监听器
	private final LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location location) {}

		public void onProviderDisabled(String provider) {}

		public void onProviderEnabled(String provider) {}

		public void onStatusChanged(String provider, int status, Bundle extras) {}	
	};

	
	/**
	 * 关闭位置监听
	 */
	protected void onStop() {
		locationManager.removeUpdates(locationListener);
		super.onStop();
	}
	
	/**
	 * 打开位置监听
	 */
	protected void onRestart() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.0001f, locationListener);
		super.onRestart();
	}
		
	/**
	 * @param tel:电话
	 */
	public void prePoiTel(String tel){
		final String temp[]=tel.split(" ");
		if(temp.length>1){		
			new AlertDialog.Builder(PoiDetail.this)
            .setTitle("选择")
            .setItems(temp,
            new DialogInterface.OnClickListener(){
              public void onClick(DialogInterface dialog, int whichcountry){
            	  telToPoi(temp[whichcountry]);
              }
            })
            .setNegativeButton("取消", new DialogInterface.OnClickListener(){  
              public void onClick(DialogInterface d, int which){ 
                d.dismiss(); 
              } 
            })
            .show();
		}
		else{
			telToPoi(temp[0]);
		}
	}
		
	/**
	 * 致电兴趣点
	 * @param tel：电话
	 */
	public void telToPoi(String tel){
		Intent myIntentDial = new Intent("android.intent.action.DIAL",Uri.parse("tel:"+tel));
		startActivity(myIntentDial);
	}
}
