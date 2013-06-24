package com.tyhj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class RoadMapView extends MapActivity {

	private LocationManager locationManager; // 定义LocationManager对象
	private String roadId; // 定义roadId变量
	private MapView map; // 定义MapView对象
	private Marker marker0; // 定义Marker对象
	private Marker marker1; // 定义Marker对象
	private Marker marker2; // 定义Marker对象
	private Marker marker3; // 定义Marker对象
	private Marker marker4; // 定义Marker对象
	private Marker marker5; // 定义Marker对象
	private Marker marker6; // 定义Marker对象
	private Marker marker7; // 定义Marker对象
	private Marker marker8; // 定义Marker对象
	private Marker marker9; // 定义Marker对象
	private Marker marker10; // 定义Marker对象
	private Marker marker11; // 定义Marker对象

	private Marker loc; // 定义Marker对象

	private TextOverlay to; // 定义TextOverlay对象
	private boolean panLoc = false; // 定义boolean变量
	private boolean pan = false; // 定义boolean变量

	private Line line; // 定义Line对象
	private ProgressDialog myDialog; // 定义ProgressDialog对象
	private List<TrackPoint> trackList; // 定义List对象
	private List<PoiPoint> poiList;// 定义List对象

	private int xy[] = new int[4]; // 定义坐标数组
	private String poiId = ""; // 定义poiId变量
	private List<Mp3Point> mp3List;// 定义List对象
	// 定义ConcurrentLinkedQueue对象
	private ConcurrentLinkedQueue<Mp3Point> playerList = new ConcurrentLinkedQueue<Mp3Point>();
	private MediaPlayer player = new MediaPlayer();// 定义MediaPlayer对象

	private String TripName;// 定义TripName变量
	private String tripId;// 定义tripId变量

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.roadmapview); // 加载xml配置文件
		this.setTitle("天涯海角旅游网");// 设置标题

		Bundle bl = this.getIntent().getExtras(); // 获取Bundle对象
		TripName = bl.getString("tripName"); // 获取TripName的值
		tripId = bl.getString("tripId"); // 获取tripId的值
		String loc[] = bl.getString("loc").split(" "); // 获取loc的值
		pan = bl.getBoolean("pan");// 获取pan的值

		GeoPoint center = null; // 定义GeoPoint对象
		if (pan) { // 中心点条件判断
			String loc2[] = bl.getString("loc2").split(" "); // 获取loc2的值
			double lat = (Double.valueOf(loc[0]) + Double.valueOf(loc2[0])) / 2; // 计算中心点lat值
			double lon = (Double.valueOf(loc[1]) + Double.valueOf(loc2[1])) / 2;// 计算中心点lon值
			center = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));// 设置中心点坐标
		} else {
			center = new GeoPoint((int) (Double.valueOf(loc[0]) * 1E6),
					(int) (Double.valueOf(loc[1]) * 1E6));// 设置中心点坐标
		}

		map = (MapView) findViewById(R.id.tmap); // 实例化map对象
		map.setSatellite(false);// 设置Satellite
		map.setStreetView(false);// 设置StreetView
		map.setBuiltInZoomControls(true); // 设置地图控件

		MapController mcontrol = map.getController(); // 实例化MapController对象
		mcontrol.setCenter(center);// 地图控件添加

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);// 实例化locationManager对象
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0.0001f, locationListener); // 设置locationManager对象

		player.setOnCompletionListener(new OnCompletionListener() { // 设置MediaPlayer完成监听
					public void onCompletion(MediaPlayer mp) { // 当播放完毕时执行的方法
						playMp3();
					}
				});

		double dis = Double.valueOf(bl.getString("dis"));// 获取dis的值
		if (dis > 200) {
			mcontrol.setZoom(8);// 设置地图缩放级别
		} else {
			mcontrol.setZoom(10);// 设置地图缩放级别
		}
		roadId = bl.getString("roadId");// 获取roadId的值
		getMapLine();

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	// 获取路书线路信息
	public void getMapLine() {
		myDialog = ProgressDialog.show(RoadMapView.this, "请稍等...", "执行运算中...",
				true);// 实例化myDialog
		new Thread() {// 开启新的线程
			public void run() {
				try {
					// 实例化trackList
					trackList = new WAnalysisFile().getTrackPointListByRouteId(
							RoadMapView.this, roadId, tripId);
					Message m = new Message(); // 实例化Message对象
					RoadMapView.this.lineHandler.sendMessage(m); // 调用lineHandler
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					myDialog.dismiss();
				}
			}
		}.start();
	}

	// 线路Handler
	Handler lineHandler = new Handler() { // 定义Handler对象lineHandler
		public void handleMessage(Message msg) {
			preToLine();
			getMapPois();
		}
	};

	// 准备数据开始画线
	public void preToLine() {
		try {
			List<GeoPoint> points = new ArrayList<GeoPoint>();// 实例化points对象
			for (int i = 0; i < trackList.size(); i += 1) {// 循环trackList
				String loc = trackList.get(i).getTrackPoints();// 获取坐标
				if (!"".equals(loc)) {
					String str[] = loc.split(",");// 拆分坐标
					for (int j = 0; j < str.length; j += 1) {// 循环坐标段
						String stemp[] = str[j].split(" ");// 拆分坐标点
						GeoPoint point = new GeoPoint((int) (Double
								.valueOf(stemp[0]) * 1E6), (int) (Double
								.valueOf(stemp[1]) * 1E6));// 实例化GeoPoint对象
						points.add(point);// 添加坐标点
					}
				}
			}

			line = new Line(points);// 实例化line对象
			map.getOverlays().add(line);// 添加line到地图
			map.invalidate();// 更新地图
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取兴趣点信息
	public void getMapPois() {
		myDialog = ProgressDialog.show(RoadMapView.this, "请稍等...", "执行运算中...",
				true);// 实例化myDialog对象
		new Thread() {// 开启新的线程
			public void run() {
				try {
					// 获取poiList对象
					poiList = new WAnalysisFile().getSmallPoiPointList(
							RoadMapView.this, roadId, tripId);
					mp3List = new ArrayList<Mp3Point>();// 实例化mp3List对象
					for (int i = 0; i < poiList.size(); i += 1) {// 循环poiList
						PoiPoint poi = poiList.get(i);
						if (!"".equals(poi.getMp3Path())) {// 判断是否有mp3属性
							Mp3Point mp3 = new Mp3Point();// 实例化Mp3Point对象
							mp3.setLat(poi.getLat());// 设置坐标lat值
							mp3.setLon(poi.getLon());// 设置坐标lon值
							mp3.setMp3Id(poi.getMp3Id());// 设置Mp3Id
							mp3.setMp3Path(poi.getMp3Path());// 设置Mp3Path
							mp3.setPan(true);// 设置是否播放
							mp3.setMp3Range(poi.getMp3Range());// 设置Range
							mp3List.add(mp3);// 添加对象到mp3List
						}
					}

					Message m = new Message();// 实例化Message对象
					RoadMapView.this.poiHandler.sendMessage(m);// 调用poiHandler
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					myDialog.dismiss();
				}
			}
		}.start();
	}

	// 兴趣点Handler
	Handler poiHandler = new Handler() {// 实例化Handler对象poiHandler
		public void handleMessage(Message msg) {
			preToPoiMarker();
			showIco();
		}
	};

	// 准备数据开始标记兴趣点
	public void preToPoiMarker() {
		for (int i = 0; i < poiList.size(); i += 1) {//循环poiList
			PoiPoint poi = poiList.get(i);//实例化PoiPoint对象
			if ("Forest".equals(poi.getCategoryId())//判断类别
					|| "Park".equals(poi.getCategoryId())) {
				if (marker1 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t1);//实例化Drawable对象
					marker1 = new Marker(drawable); //实例化marker1
				}
				//实例化GeoPoint对象
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//实例化OverlayItem对象
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//添加overlayitem到marker1
				marker1.addOverlay(overlayitem);
			} else if ("Museum".equals(poi.getCategoryId())//判断类别
					|| "Letter B, Red".equals(poi.getCategoryId())
					|| "Flag, Red".equals(poi.getCategoryId())
					|| "Church".equals(poi.getCategoryId())
					|| "Letter A, Red".equals(poi.getCategoryId())) {
				if (marker2 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t2);//实例化Drawable对象
					marker2 = new Marker(drawable);//实例化marker1
				}
				//实例化GeoPoint对象
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//实例化OverlayItem对象
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//添加overlayitem到marker2
				marker2.addOverlay(overlayitem);
			} else if ("Zoo".equals(poi.getCategoryId())//判断类别
					|| "Crossing".equals(poi.getCategoryId())
					|| "Marina".equals(poi.getCategoryId())
					|| "City (Large)".equals(poi.getCategoryId())
					|| "City (Medium)".equals(poi.getCategoryId())
					|| "City (Small)".equals(poi.getCategoryId())
					|| "Letter C, Green".equals(poi.getCategoryId())
					|| "Horn".equals(poi.getCategoryId())) {
				if (marker3 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t3);//实例化Drawable对象
					marker3 = new Marker(drawable);//实例化marker1
				}
				//实例化GeoPoint对象
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//实例化OverlayItem对象
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//添加overlayitem到marker3
				marker3.addOverlay(overlayitem);
			} else if ("Shopping Center".equals(poi.getCategoryId())) {//判断类别
				if (marker4 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t4);//实例化Drawable对象
					marker4 = new Marker(drawable);//实例化marker1
				}
				//实例化GeoPoint对象
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//实例化OverlayItem对象
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//添加overlayitem到marker4
				marker4.addOverlay(overlayitem);
			} else if ("Scenic Area".equals(poi.getCategoryId())) {//判断类别
				if (marker5 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t5);//实例化Drawable对象
					marker5 = new Marker(drawable);//实例化marker1
				}
				//实例化GeoPoint对象
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//实例化OverlayItem对象
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//添加overlayitem到marker5
				marker5.addOverlay(overlayitem);
			} else if ("Gas Station".equals(poi.getCategoryId())) {//判断类别
				if (marker6 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t6);//实例化Drawable对象
					marker6 = new Marker(drawable);//实例化marker1
				}
				//实例化GeoPoint对象
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//实例化OverlayItem对象
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//添加overlayitem到marker6
				marker6.addOverlay(overlayitem);
			} else if ("Lodging".equals(poi.getCategoryId())) {//判断类别
				if (marker7 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t7);//实例化Drawable对象
					marker7 = new Marker(drawable);//实例化marker1
				}
				//实例化GeoPoint对象
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//实例化OverlayItem对象
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//添加overlayitem到marker7
				marker7.addOverlay(overlayitem);
			} else if ("Restaurant".equals(poi.getCategoryId())) {//判断类别
				if (marker8 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t8);//实例化Drawable对象
					marker8 = new Marker(drawable);//实例化marker1
				}
				//实例化GeoPoint对象
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//实例化OverlayItem对象
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//添加overlayitem到marker8
				marker8.addOverlay(overlayitem);
			} else if ("Waypoint".equals(poi.getCategoryId())) {//判断类别
				if (marker9 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t9);//实例化Drawable对象
					marker9 = new Marker(drawable);//实例化marker1
				}
				//实例化GeoPoint对象
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//实例化OverlayItem对象
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//添加overlayitem到marker9
				marker9.addOverlay(overlayitem);
			} else if ("Toll Booth".equals(poi.getCategoryId())) {//判断类别
				if (marker10 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t10);//实例化Drawable对象
					marker10 = new Marker(drawable);//实例化marker1
				}
				//实例化GeoPoint对象
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//实例化OverlayItem对象
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//添加overlayitem到marker10
				marker10.addOverlay(overlayitem);
			} else if ("Parking Area".equals(poi.getCategoryId())) {//判断类别
				if (marker11 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t11);//实例化Drawable对象
					marker11 = new Marker(drawable);//实例化marker1
				}
				//实例化GeoPoint对象
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//实例化OverlayItem对象
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//添加overlayitem到marker11
				marker11.addOverlay(overlayitem);
			} else {//判断默认类别
				if (marker0 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.mountain);//实例化Drawable对象
					marker0 = new Marker(drawable);//实例化marker1
				}
				//实例化GeoPoint对象
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//实例化OverlayItem对象
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//添加overlayitem到marker0
				marker0.addOverlay(overlayitem);
			}
		}

	}

	// 显示图标
	public void showIco() {
		if (marker0 != null) {
			map.getOverlays().add(marker0);
		}
		if (marker1 != null) {
			map.getOverlays().add(marker1);
		}
		if (marker2 != null) {
			map.getOverlays().add(marker2);
		}
		if (marker3 != null) {
			map.getOverlays().add(marker3);
		}
		if (marker4 != null) {
			map.getOverlays().add(marker4);
		}
		if (marker5 != null) {
			map.getOverlays().add(marker5);
		}
		if (marker6 != null) {
			map.getOverlays().add(marker6);
		}
		if (marker7 != null) {
			map.getOverlays().add(marker7);
		}
		if (marker8 != null) {
			map.getOverlays().add(marker8);
		}
		if (marker9 != null) {
			map.getOverlays().add(marker9);
		}
		if (marker10 != null) {
			map.getOverlays().add(marker10);
		}
		if (marker11 != null) {
			map.getOverlays().add(marker11);
		}
		map.invalidate();
	}

	// 隐藏图标
	public void hideIco() {
		if (marker0 != null) {
			map.getOverlays().remove(marker0);
		}
		if (marker1 != null) {
			map.getOverlays().remove(marker1);
		}
		if (marker2 != null) {
			map.getOverlays().remove(marker2);
		}
		if (marker3 != null) {
			map.getOverlays().remove(marker3);
		}
		if (marker4 != null) {
			map.getOverlays().remove(marker4);
		}
		if (marker5 != null) {
			map.getOverlays().remove(marker5);
		}
		if (marker6 != null) {
			map.getOverlays().remove(marker6);
		}
		if (marker7 != null) {
			map.getOverlays().remove(marker7);
		}
		if (marker8 != null) {
			map.getOverlays().remove(marker8);
		}
		if (marker9 != null) {
			map.getOverlays().remove(marker9);
		}
		if (marker10 != null) {
			map.getOverlays().remove(marker10);
		}
		if (marker11 != null) {
			map.getOverlays().remove(marker11);
		}
		map.invalidate();
	}

	// 地图页面菜单
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem mnuAbout = menu.add(0, 0, 0, "返回"); //定义MenuItem对象
		mnuAbout.setIcon(R.drawable.aboutmenu);//设置图标

		MenuItem mnuHome = menu.add(0, 1, 1, "隐藏兴趣点");//定义MenuItem对象
		mnuHome.setIcon(R.drawable.homemenu);//设置图标

		MenuItem mnuFanhui = menu.add(0, 2, 2, "显示当前位置");//定义MenuItem对象
		mnuFanhui.setIcon(R.drawable.fanhuimenu);//设置图标

		return super.onCreateOptionsMenu(menu);
	}

	// 菜单响应事件
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case 0:
			RoadMapView.this.finish();//结束当前MapActivity
			break;
		case 1:
			if ("隐藏兴趣点".equals(item.getTitle().toString())) {//判断菜单文字
				item.setTitle("显示兴趣点");//设置菜单文字
				hideIco();//隐藏图标
			} else {
				item.setTitle("隐藏兴趣点");//设置菜单文字
				showIco();//显示图标
			}
			break;
		case 2:
			if ("显示当前位置".equals(item.getTitle().toString())) {//判断菜单文字
				showNowLoc();//显示位置
				if (panLoc) {
					item.setTitle("隐藏当前位置");//设置菜单文字
				}
			} else {
				panLoc = false;
				item.setTitle("显示当前位置");//设置菜单文字
				map.getOverlays().remove(loc);//移除位置图标
				map.invalidate();//更新地图
				if (player.isPlaying()) {//判断播放器状态
					player.reset();//重置播放器
				}
			}
			break;
		}
		return true;
	}

	// 显示当前位置
	public void showNowLoc() {

		Criteria criteria = new Criteria();//定义Criteria对象
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//设置Accuracy
		criteria.setAltitudeRequired(false);//设置AltitudeRequired
		criteria.setBearingRequired(false);//设置BearingRequired
		criteria.setCostAllowed(false);//设置CostAllowed
		criteria.setPowerRequirement(Criteria.POWER_LOW);//设置PowerRequirement

		Location location = locationManager
				.getLastKnownLocation(locationManager.getBestProvider(criteria,
						true));//实例化Location

		if (location == null) {//判断信号
			Toast.makeText(RoadMapView.this, "没有GPS信号", Toast.LENGTH_SHORT)
					.show();
		} else {
			panLoc = true;
			double trueLat = location.getLatitude();//获取坐标Lat值
			double trueLon = location.getLongitude();//获取坐标Lon值
			if (loc != null) {
				map.getOverlays().remove(loc);//移除位置图标
				map.invalidate();//更新地图
			}
			Drawable drawable = this.getResources().getDrawable(
					R.drawable.weizhi);//实例化Drawable对象
			loc = new Marker(drawable);
			//实例化GeoPoint
			GeoPoint point = new GeoPoint((int) (trueLat * 1E6),
					(int) (trueLon * 1E6));
			//实例化OverlayItem
			OverlayItem overlayitem = new OverlayItem(point, "", "");
			loc.addOverlay(overlayitem);
			map.getOverlays().add(loc);//添加loc到地图
			map.invalidate();//更新地图
		}

	}

	// 准备播放mp3列表
	public void preMp3List(Location location) {
		double trueLat = location.getLatitude();//获取坐标Lat值
		double trueLon = location.getLongitude();//获取坐标Lon值
		for (int i = 0; i < mp3List.size(); i += 1) {//循环mp3List
			Mp3Point mp3 = mp3List.get(i);//获取Mp3Point对象
			if (mp3.isPan()) {
				float[] results = new float[1];
				//获取两点间距离
				Location.distanceBetween(trueLat, trueLon, Double.valueOf(mp3
						.getLat()), Double.valueOf(mp3.getLon()), results);
				int dis = (int) results[0];
				if (dis <= mp3.getMp3Range()) {//判断距离值
					mp3.setPan(false);//设置播放状态
					playerList.add(mp3);//加入播放列表
					if (!player.isPlaying()) {//判断播放状态
						playMp3();
					}
					break;
				}
			}
		}
	}

	// 开始播放mp3序列
	public void playMp3() {
		try {
			Mp3Point mp3 = playerList.poll();//获取播放对象
			if (mp3 != null) {
				player.reset();//重置播放弃
				//获取播放资源
				AssetFileDescriptor afd = RoadMapView.this.getAssets().openFd(
						tripId + "/SmallRoute" + "/" + roadId + "/mp3/"
								+ mp3.getMp3Path().toLowerCase());
				//设置播放资源
				player.setDataSource(afd.getFileDescriptor(), afd
						.getStartOffset(), afd.getLength());
				player.prepare();//播放器准备
				player.start();//开始播放
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 撤销关闭mp3
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == 4 && player.isPlaying()) {//判断按键
			player.reset();//重置播放弃
		}
		return super.onKeyDown(keyCode, event);
	}

	// 位置监听器
	private final LocationListener locationListener = new LocationListener() {//实例化位置监听器

		public void onLocationChanged(Location location) {//档位置改变时触发
			if (panLoc) {
				showNowLoc();//显示位置
				preMp3List(location);//播放mp3
			}
		}

		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
	};

	// 关闭位置监听
	protected void onStop() {
		locationManager.removeUpdates(locationListener);//移除位置监听器
		super.onStop();
	}

	// 打开位置监听
	protected void onRestart() {
		//重起位置监听器
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0.0001f, locationListener);
		super.onRestart();
	}

	// 获取详情框坐标像素点
	public void setXY(int x1, int y1, int x2, int y2) {
		xy[0] = x1;
		xy[1] = x2;
		xy[2] = y1;
		xy[3] = y2;
	}

	// 取消详情框
	public boolean dispatchTouchEvent(MotionEvent ev) {

		int cx = (int) ev.getX();//获取屏幕点击x坐标
		int cy = (int) ev.getY();//获取屏幕点击一坐标
		//判断点击位置范围
		if ((cx < xy[0] || cx > xy[1]) || (cy < xy[2] || cy > xy[3])) {
			if (to != null) {//移除点击窗口
				map.getOverlays().remove(to);
				to = null;
				poiId = "";
				map.invalidate();
			}
		} else {
			if (!"".equals(poiId)) {
				geoPoi(poiId);//切换屏幕
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	// 去兴趣点详情
	private void geoPoi(String temp) {
		Bundle bl = new Bundle();  //实例化Bundle对象
		bl.putSerializable("poiObj", (Serializable) getPoi(temp));//插入PoiPoint数据
		bl.putString("tripId", tripId);//插入tripId
		bl.putString("tripName", TripName);//插入tripName
		Intent it = new Intent();//实例化Intent
		it.setClass(RoadMapView.this, PoiDetail.class);//设置Class
		it.putExtras(bl);//设置Extras
		startActivity(it);//启动Activity
	}

	private PoiPoint getPoi(String id) {
		PoiPoint poi = new PoiPoint();//实例化PoiPoint对象
		for (int i = 0; i < poiList.size(); i += 1) {//循环 poiList
			if (poiList.get(i).getId().equals(id)) {//判断符合条件
				poi = poiList.get(i);//获取PoiPoint值
				break;
			}
		}
		return poi;
	}

	// 内部类,线段
	public class Line extends Overlay {

		private List<GeoPoint> points;//定义List对象
		private Paint paint;//定义Paint对象
		private boolean pan = true;//定义pan变量
		private int count = 0;//定义count变量

		public Line(List<GeoPoint> points) {
			this.points = points;
			paint = new Paint();//实例化paint对象

			paint.setARGB(250, 249, 105, 8);//设置ARGB
			paint.setAntiAlias(true);//设置AntiAlias
			paint.setStyle(Paint.Style.FILL_AND_STROKE);//设置Style
			paint.setStrokeWidth(4);//设置StrokeWidth
		}

		public Line(List<GeoPoint> points, Paint paint) {
			this.points = points;
			this.paint = paint;
		}

		//点击触发
		public boolean onTouchEvent(MotionEvent e, MapView mapView) {
			if (e.getAction() == MotionEvent.ACTION_DOWN) {
				pan = false;
			} else if (e.getAction() == MotionEvent.ACTION_UP) {
				pan = true;
			}
			return false;
		}

		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			count = count + 1;//累加count变量
			if (pan) {
				if (count % 2 == 0) {
					if (!shadow) {
						Projection projection = mapView.getProjection();//获取Projection对象
						if (points != null) {
							if (points.size() >= 2) {
								Point start = projection.toPixels(
										points.get(0), null);//获取前Point对象
								for (int i = 1; i < points.size(); i++) {
									Point end = projection.toPixels(points
											.get(i), null);//获取后Point对象
									canvas.drawLine(start.x, start.y, end.x,
											end.y, paint);//两点间画线
									start = end;//后点变前点
								}
							}
						}
					}
				}

			}
		}
	}

	// 内部类,标记
	public class Marker extends ItemizedOverlay<OverlayItem> {
		//实例化markerList
		private ArrayList<OverlayItem> markerList = new ArrayList<OverlayItem>();

		public Marker(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
		}

		@Override
		protected OverlayItem createItem(int arg0) {
			//获取某个markerList中元素
			return markerList.get(arg0);
		}

		@Override
		public int size() {
			//获取markerList大小
			return markerList.size();
		}

		public void addOverlay(OverlayItem overlay) {
			markerList.add(overlay);//添加元素
			populate();//调用初始化
		}

		protected boolean onTap(int i) {

			if (createItem(i).getTitle().equals("poiinfo")) {
				geoPoi(createItem(i).getSnippet());//调用页面切换
				return false;
			}

			if (to != null) {
				map.getOverlays().remove(to);//移除显示框
			}

			poiId = createItem(i).getSnippet();//获取poiId
			String str = createItem(i).getTitle();//获取标题
			if (str.length() > 8) {
				str = str.substring(0, 4) + "…"
						+ str.substring(str.length() - 1, str.length());//字符截取
			}
			to = new TextOverlay(str, createItem(i).getPoint());//实例化显示框
			map.getOverlays().add(to);//添加显示框到地图

			return false;
		}

	}

	// 文字提示框
	public class TextOverlay extends Overlay {
		private String str; //定义str对象
		private GeoPoint p;//定义GeoPoint对象

		public TextOverlay(String str, GeoPoint p) {
			this.str = str;
			this.p = p;
		}

		public boolean draw(Canvas arg0, MapView arg1, boolean arg2, long arg3) {
			if (!arg2) {
				Paint paint = new Paint();//实例化Paint对象
				paint.setTextSize(14);//设置TextSize
				paint.setColor(Color.WHITE);//设置Color
				paint.setAntiAlias(true);//设置AntiAlias
				paint.setFakeBoldText(true);//设置FakeBoldText
				Paint bpaint = new Paint();//实例化Paint对象
				bpaint.setColor(Color.BLACK);//设置Color
				bpaint.setAntiAlias(true);//设置AntiAlias
				bpaint.setAlpha(150);//设置Alpha

				Point temp = map.getProjection().toPixels(p, null);//实例化Point对象
				int x1 = temp.x + 15;//计算对象x坐标
				int y1 = temp.y - 30;//计算对象y坐标

				int count = str.length();//显示文字个数
				int x2 = x1 + (count + 3) * 14;//计算所需长度
				RectF boval = new RectF(x1, y1, x2, y1 + 30);//定义圆角矩形
				setXY(x1, y1 + 50, x2, y1 + 80);//保存区域范围

				arg0.drawRoundRect(boval, 10, 10, bpaint);//绘制圆角矩形
				arg0.drawText(str + "  >", x1 + 14, y1 + 20, paint);//绘制文字

			}
			return super.draw(arg0, arg1, arg2, arg3);
		}

	}
}
