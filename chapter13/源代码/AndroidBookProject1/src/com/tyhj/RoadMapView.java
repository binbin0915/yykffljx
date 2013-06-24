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

	private LocationManager locationManager; // ����LocationManager����
	private String roadId; // ����roadId����
	private MapView map; // ����MapView����
	private Marker marker0; // ����Marker����
	private Marker marker1; // ����Marker����
	private Marker marker2; // ����Marker����
	private Marker marker3; // ����Marker����
	private Marker marker4; // ����Marker����
	private Marker marker5; // ����Marker����
	private Marker marker6; // ����Marker����
	private Marker marker7; // ����Marker����
	private Marker marker8; // ����Marker����
	private Marker marker9; // ����Marker����
	private Marker marker10; // ����Marker����
	private Marker marker11; // ����Marker����

	private Marker loc; // ����Marker����

	private TextOverlay to; // ����TextOverlay����
	private boolean panLoc = false; // ����boolean����
	private boolean pan = false; // ����boolean����

	private Line line; // ����Line����
	private ProgressDialog myDialog; // ����ProgressDialog����
	private List<TrackPoint> trackList; // ����List����
	private List<PoiPoint> poiList;// ����List����

	private int xy[] = new int[4]; // ������������
	private String poiId = ""; // ����poiId����
	private List<Mp3Point> mp3List;// ����List����
	// ����ConcurrentLinkedQueue����
	private ConcurrentLinkedQueue<Mp3Point> playerList = new ConcurrentLinkedQueue<Mp3Point>();
	private MediaPlayer player = new MediaPlayer();// ����MediaPlayer����

	private String TripName;// ����TripName����
	private String tripId;// ����tripId����

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.roadmapview); // ����xml�����ļ�
		this.setTitle("���ĺ���������");// ���ñ���

		Bundle bl = this.getIntent().getExtras(); // ��ȡBundle����
		TripName = bl.getString("tripName"); // ��ȡTripName��ֵ
		tripId = bl.getString("tripId"); // ��ȡtripId��ֵ
		String loc[] = bl.getString("loc").split(" "); // ��ȡloc��ֵ
		pan = bl.getBoolean("pan");// ��ȡpan��ֵ

		GeoPoint center = null; // ����GeoPoint����
		if (pan) { // ���ĵ������ж�
			String loc2[] = bl.getString("loc2").split(" "); // ��ȡloc2��ֵ
			double lat = (Double.valueOf(loc[0]) + Double.valueOf(loc2[0])) / 2; // �������ĵ�latֵ
			double lon = (Double.valueOf(loc[1]) + Double.valueOf(loc2[1])) / 2;// �������ĵ�lonֵ
			center = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));// �������ĵ�����
		} else {
			center = new GeoPoint((int) (Double.valueOf(loc[0]) * 1E6),
					(int) (Double.valueOf(loc[1]) * 1E6));// �������ĵ�����
		}

		map = (MapView) findViewById(R.id.tmap); // ʵ����map����
		map.setSatellite(false);// ����Satellite
		map.setStreetView(false);// ����StreetView
		map.setBuiltInZoomControls(true); // ���õ�ͼ�ؼ�

		MapController mcontrol = map.getController(); // ʵ����MapController����
		mcontrol.setCenter(center);// ��ͼ�ؼ����

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);// ʵ����locationManager����
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0.0001f, locationListener); // ����locationManager����

		player.setOnCompletionListener(new OnCompletionListener() { // ����MediaPlayer��ɼ���
					public void onCompletion(MediaPlayer mp) { // ���������ʱִ�еķ���
						playMp3();
					}
				});

		double dis = Double.valueOf(bl.getString("dis"));// ��ȡdis��ֵ
		if (dis > 200) {
			mcontrol.setZoom(8);// ���õ�ͼ���ż���
		} else {
			mcontrol.setZoom(10);// ���õ�ͼ���ż���
		}
		roadId = bl.getString("roadId");// ��ȡroadId��ֵ
		getMapLine();

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	// ��ȡ·����·��Ϣ
	public void getMapLine() {
		myDialog = ProgressDialog.show(RoadMapView.this, "���Ե�...", "ִ��������...",
				true);// ʵ����myDialog
		new Thread() {// �����µ��߳�
			public void run() {
				try {
					// ʵ����trackList
					trackList = new WAnalysisFile().getTrackPointListByRouteId(
							RoadMapView.this, roadId, tripId);
					Message m = new Message(); // ʵ����Message����
					RoadMapView.this.lineHandler.sendMessage(m); // ����lineHandler
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					myDialog.dismiss();
				}
			}
		}.start();
	}

	// ��·Handler
	Handler lineHandler = new Handler() { // ����Handler����lineHandler
		public void handleMessage(Message msg) {
			preToLine();
			getMapPois();
		}
	};

	// ׼�����ݿ�ʼ����
	public void preToLine() {
		try {
			List<GeoPoint> points = new ArrayList<GeoPoint>();// ʵ����points����
			for (int i = 0; i < trackList.size(); i += 1) {// ѭ��trackList
				String loc = trackList.get(i).getTrackPoints();// ��ȡ����
				if (!"".equals(loc)) {
					String str[] = loc.split(",");// �������
					for (int j = 0; j < str.length; j += 1) {// ѭ�������
						String stemp[] = str[j].split(" ");// ��������
						GeoPoint point = new GeoPoint((int) (Double
								.valueOf(stemp[0]) * 1E6), (int) (Double
								.valueOf(stemp[1]) * 1E6));// ʵ����GeoPoint����
						points.add(point);// ��������
					}
				}
			}

			line = new Line(points);// ʵ����line����
			map.getOverlays().add(line);// ���line����ͼ
			map.invalidate();// ���µ�ͼ
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ȡ��Ȥ����Ϣ
	public void getMapPois() {
		myDialog = ProgressDialog.show(RoadMapView.this, "���Ե�...", "ִ��������...",
				true);// ʵ����myDialog����
		new Thread() {// �����µ��߳�
			public void run() {
				try {
					// ��ȡpoiList����
					poiList = new WAnalysisFile().getSmallPoiPointList(
							RoadMapView.this, roadId, tripId);
					mp3List = new ArrayList<Mp3Point>();// ʵ����mp3List����
					for (int i = 0; i < poiList.size(); i += 1) {// ѭ��poiList
						PoiPoint poi = poiList.get(i);
						if (!"".equals(poi.getMp3Path())) {// �ж��Ƿ���mp3����
							Mp3Point mp3 = new Mp3Point();// ʵ����Mp3Point����
							mp3.setLat(poi.getLat());// ��������latֵ
							mp3.setLon(poi.getLon());// ��������lonֵ
							mp3.setMp3Id(poi.getMp3Id());// ����Mp3Id
							mp3.setMp3Path(poi.getMp3Path());// ����Mp3Path
							mp3.setPan(true);// �����Ƿ񲥷�
							mp3.setMp3Range(poi.getMp3Range());// ����Range
							mp3List.add(mp3);// ��Ӷ���mp3List
						}
					}

					Message m = new Message();// ʵ����Message����
					RoadMapView.this.poiHandler.sendMessage(m);// ����poiHandler
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					myDialog.dismiss();
				}
			}
		}.start();
	}

	// ��Ȥ��Handler
	Handler poiHandler = new Handler() {// ʵ����Handler����poiHandler
		public void handleMessage(Message msg) {
			preToPoiMarker();
			showIco();
		}
	};

	// ׼�����ݿ�ʼ�����Ȥ��
	public void preToPoiMarker() {
		for (int i = 0; i < poiList.size(); i += 1) {//ѭ��poiList
			PoiPoint poi = poiList.get(i);//ʵ����PoiPoint����
			if ("Forest".equals(poi.getCategoryId())//�ж����
					|| "Park".equals(poi.getCategoryId())) {
				if (marker1 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t1);//ʵ����Drawable����
					marker1 = new Marker(drawable); //ʵ����marker1
				}
				//ʵ����GeoPoint����
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//ʵ����OverlayItem����
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//���overlayitem��marker1
				marker1.addOverlay(overlayitem);
			} else if ("Museum".equals(poi.getCategoryId())//�ж����
					|| "Letter B, Red".equals(poi.getCategoryId())
					|| "Flag, Red".equals(poi.getCategoryId())
					|| "Church".equals(poi.getCategoryId())
					|| "Letter A, Red".equals(poi.getCategoryId())) {
				if (marker2 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t2);//ʵ����Drawable����
					marker2 = new Marker(drawable);//ʵ����marker1
				}
				//ʵ����GeoPoint����
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//ʵ����OverlayItem����
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//���overlayitem��marker2
				marker2.addOverlay(overlayitem);
			} else if ("Zoo".equals(poi.getCategoryId())//�ж����
					|| "Crossing".equals(poi.getCategoryId())
					|| "Marina".equals(poi.getCategoryId())
					|| "City (Large)".equals(poi.getCategoryId())
					|| "City (Medium)".equals(poi.getCategoryId())
					|| "City (Small)".equals(poi.getCategoryId())
					|| "Letter C, Green".equals(poi.getCategoryId())
					|| "Horn".equals(poi.getCategoryId())) {
				if (marker3 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t3);//ʵ����Drawable����
					marker3 = new Marker(drawable);//ʵ����marker1
				}
				//ʵ����GeoPoint����
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//ʵ����OverlayItem����
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//���overlayitem��marker3
				marker3.addOverlay(overlayitem);
			} else if ("Shopping Center".equals(poi.getCategoryId())) {//�ж����
				if (marker4 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t4);//ʵ����Drawable����
					marker4 = new Marker(drawable);//ʵ����marker1
				}
				//ʵ����GeoPoint����
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//ʵ����OverlayItem����
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//���overlayitem��marker4
				marker4.addOverlay(overlayitem);
			} else if ("Scenic Area".equals(poi.getCategoryId())) {//�ж����
				if (marker5 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t5);//ʵ����Drawable����
					marker5 = new Marker(drawable);//ʵ����marker1
				}
				//ʵ����GeoPoint����
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//ʵ����OverlayItem����
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//���overlayitem��marker5
				marker5.addOverlay(overlayitem);
			} else if ("Gas Station".equals(poi.getCategoryId())) {//�ж����
				if (marker6 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t6);//ʵ����Drawable����
					marker6 = new Marker(drawable);//ʵ����marker1
				}
				//ʵ����GeoPoint����
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//ʵ����OverlayItem����
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//���overlayitem��marker6
				marker6.addOverlay(overlayitem);
			} else if ("Lodging".equals(poi.getCategoryId())) {//�ж����
				if (marker7 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t7);//ʵ����Drawable����
					marker7 = new Marker(drawable);//ʵ����marker1
				}
				//ʵ����GeoPoint����
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//ʵ����OverlayItem����
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//���overlayitem��marker7
				marker7.addOverlay(overlayitem);
			} else if ("Restaurant".equals(poi.getCategoryId())) {//�ж����
				if (marker8 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t8);//ʵ����Drawable����
					marker8 = new Marker(drawable);//ʵ����marker1
				}
				//ʵ����GeoPoint����
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//ʵ����OverlayItem����
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//���overlayitem��marker8
				marker8.addOverlay(overlayitem);
			} else if ("Waypoint".equals(poi.getCategoryId())) {//�ж����
				if (marker9 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t9);//ʵ����Drawable����
					marker9 = new Marker(drawable);//ʵ����marker1
				}
				//ʵ����GeoPoint����
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//ʵ����OverlayItem����
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//���overlayitem��marker9
				marker9.addOverlay(overlayitem);
			} else if ("Toll Booth".equals(poi.getCategoryId())) {//�ж����
				if (marker10 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t10);//ʵ����Drawable����
					marker10 = new Marker(drawable);//ʵ����marker1
				}
				//ʵ����GeoPoint����
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//ʵ����OverlayItem����
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//���overlayitem��marker10
				marker10.addOverlay(overlayitem);
			} else if ("Parking Area".equals(poi.getCategoryId())) {//�ж����
				if (marker11 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.t11);//ʵ����Drawable����
					marker11 = new Marker(drawable);//ʵ����marker1
				}
				//ʵ����GeoPoint����
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//ʵ����OverlayItem����
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//���overlayitem��marker11
				marker11.addOverlay(overlayitem);
			} else {//�ж�Ĭ�����
				if (marker0 == null) {
					Drawable drawable = this.getResources().getDrawable(
							R.drawable.mountain);//ʵ����Drawable����
					marker0 = new Marker(drawable);//ʵ����marker1
				}
				//ʵ����GeoPoint����
				GeoPoint gpoint = new GeoPoint((int) (Double.valueOf(poi
						.getLat()) * 1E6),
						(int) (Double.valueOf(poi.getLon()) * 1E6));
				//ʵ����OverlayItem����
				OverlayItem overlayitem = new OverlayItem(gpoint,
						poi.getName(), poi.getId());
				//���overlayitem��marker0
				marker0.addOverlay(overlayitem);
			}
		}

	}

	// ��ʾͼ��
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

	// ����ͼ��
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

	// ��ͼҳ��˵�
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem mnuAbout = menu.add(0, 0, 0, "����"); //����MenuItem����
		mnuAbout.setIcon(R.drawable.aboutmenu);//����ͼ��

		MenuItem mnuHome = menu.add(0, 1, 1, "������Ȥ��");//����MenuItem����
		mnuHome.setIcon(R.drawable.homemenu);//����ͼ��

		MenuItem mnuFanhui = menu.add(0, 2, 2, "��ʾ��ǰλ��");//����MenuItem����
		mnuFanhui.setIcon(R.drawable.fanhuimenu);//����ͼ��

		return super.onCreateOptionsMenu(menu);
	}

	// �˵���Ӧ�¼�
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case 0:
			RoadMapView.this.finish();//������ǰMapActivity
			break;
		case 1:
			if ("������Ȥ��".equals(item.getTitle().toString())) {//�жϲ˵�����
				item.setTitle("��ʾ��Ȥ��");//���ò˵�����
				hideIco();//����ͼ��
			} else {
				item.setTitle("������Ȥ��");//���ò˵�����
				showIco();//��ʾͼ��
			}
			break;
		case 2:
			if ("��ʾ��ǰλ��".equals(item.getTitle().toString())) {//�жϲ˵�����
				showNowLoc();//��ʾλ��
				if (panLoc) {
					item.setTitle("���ص�ǰλ��");//���ò˵�����
				}
			} else {
				panLoc = false;
				item.setTitle("��ʾ��ǰλ��");//���ò˵�����
				map.getOverlays().remove(loc);//�Ƴ�λ��ͼ��
				map.invalidate();//���µ�ͼ
				if (player.isPlaying()) {//�жϲ�����״̬
					player.reset();//���ò�����
				}
			}
			break;
		}
		return true;
	}

	// ��ʾ��ǰλ��
	public void showNowLoc() {

		Criteria criteria = new Criteria();//����Criteria����
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//����Accuracy
		criteria.setAltitudeRequired(false);//����AltitudeRequired
		criteria.setBearingRequired(false);//����BearingRequired
		criteria.setCostAllowed(false);//����CostAllowed
		criteria.setPowerRequirement(Criteria.POWER_LOW);//����PowerRequirement

		Location location = locationManager
				.getLastKnownLocation(locationManager.getBestProvider(criteria,
						true));//ʵ����Location

		if (location == null) {//�ж��ź�
			Toast.makeText(RoadMapView.this, "û��GPS�ź�", Toast.LENGTH_SHORT)
					.show();
		} else {
			panLoc = true;
			double trueLat = location.getLatitude();//��ȡ����Latֵ
			double trueLon = location.getLongitude();//��ȡ����Lonֵ
			if (loc != null) {
				map.getOverlays().remove(loc);//�Ƴ�λ��ͼ��
				map.invalidate();//���µ�ͼ
			}
			Drawable drawable = this.getResources().getDrawable(
					R.drawable.weizhi);//ʵ����Drawable����
			loc = new Marker(drawable);
			//ʵ����GeoPoint
			GeoPoint point = new GeoPoint((int) (trueLat * 1E6),
					(int) (trueLon * 1E6));
			//ʵ����OverlayItem
			OverlayItem overlayitem = new OverlayItem(point, "", "");
			loc.addOverlay(overlayitem);
			map.getOverlays().add(loc);//���loc����ͼ
			map.invalidate();//���µ�ͼ
		}

	}

	// ׼������mp3�б�
	public void preMp3List(Location location) {
		double trueLat = location.getLatitude();//��ȡ����Latֵ
		double trueLon = location.getLongitude();//��ȡ����Lonֵ
		for (int i = 0; i < mp3List.size(); i += 1) {//ѭ��mp3List
			Mp3Point mp3 = mp3List.get(i);//��ȡMp3Point����
			if (mp3.isPan()) {
				float[] results = new float[1];
				//��ȡ��������
				Location.distanceBetween(trueLat, trueLon, Double.valueOf(mp3
						.getLat()), Double.valueOf(mp3.getLon()), results);
				int dis = (int) results[0];
				if (dis <= mp3.getMp3Range()) {//�жϾ���ֵ
					mp3.setPan(false);//���ò���״̬
					playerList.add(mp3);//���벥���б�
					if (!player.isPlaying()) {//�жϲ���״̬
						playMp3();
					}
					break;
				}
			}
		}
	}

	// ��ʼ����mp3����
	public void playMp3() {
		try {
			Mp3Point mp3 = playerList.poll();//��ȡ���Ŷ���
			if (mp3 != null) {
				player.reset();//���ò�����
				//��ȡ������Դ
				AssetFileDescriptor afd = RoadMapView.this.getAssets().openFd(
						tripId + "/SmallRoute" + "/" + roadId + "/mp3/"
								+ mp3.getMp3Path().toLowerCase());
				//���ò�����Դ
				player.setDataSource(afd.getFileDescriptor(), afd
						.getStartOffset(), afd.getLength());
				player.prepare();//������׼��
				player.start();//��ʼ����
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �����ر�mp3
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == 4 && player.isPlaying()) {//�жϰ���
			player.reset();//���ò�����
		}
		return super.onKeyDown(keyCode, event);
	}

	// λ�ü�����
	private final LocationListener locationListener = new LocationListener() {//ʵ����λ�ü�����

		public void onLocationChanged(Location location) {//��λ�øı�ʱ����
			if (panLoc) {
				showNowLoc();//��ʾλ��
				preMp3List(location);//����mp3
			}
		}

		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
	};

	// �ر�λ�ü���
	protected void onStop() {
		locationManager.removeUpdates(locationListener);//�Ƴ�λ�ü�����
		super.onStop();
	}

	// ��λ�ü���
	protected void onRestart() {
		//����λ�ü�����
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0.0001f, locationListener);
		super.onRestart();
	}

	// ��ȡ������������ص�
	public void setXY(int x1, int y1, int x2, int y2) {
		xy[0] = x1;
		xy[1] = x2;
		xy[2] = y1;
		xy[3] = y2;
	}

	// ȡ�������
	public boolean dispatchTouchEvent(MotionEvent ev) {

		int cx = (int) ev.getX();//��ȡ��Ļ���x����
		int cy = (int) ev.getY();//��ȡ��Ļ���һ����
		//�жϵ��λ�÷�Χ
		if ((cx < xy[0] || cx > xy[1]) || (cy < xy[2] || cy > xy[3])) {
			if (to != null) {//�Ƴ��������
				map.getOverlays().remove(to);
				to = null;
				poiId = "";
				map.invalidate();
			}
		} else {
			if (!"".equals(poiId)) {
				geoPoi(poiId);//�л���Ļ
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	// ȥ��Ȥ������
	private void geoPoi(String temp) {
		Bundle bl = new Bundle();  //ʵ����Bundle����
		bl.putSerializable("poiObj", (Serializable) getPoi(temp));//����PoiPoint����
		bl.putString("tripId", tripId);//����tripId
		bl.putString("tripName", TripName);//����tripName
		Intent it = new Intent();//ʵ����Intent
		it.setClass(RoadMapView.this, PoiDetail.class);//����Class
		it.putExtras(bl);//����Extras
		startActivity(it);//����Activity
	}

	private PoiPoint getPoi(String id) {
		PoiPoint poi = new PoiPoint();//ʵ����PoiPoint����
		for (int i = 0; i < poiList.size(); i += 1) {//ѭ�� poiList
			if (poiList.get(i).getId().equals(id)) {//�жϷ�������
				poi = poiList.get(i);//��ȡPoiPointֵ
				break;
			}
		}
		return poi;
	}

	// �ڲ���,�߶�
	public class Line extends Overlay {

		private List<GeoPoint> points;//����List����
		private Paint paint;//����Paint����
		private boolean pan = true;//����pan����
		private int count = 0;//����count����

		public Line(List<GeoPoint> points) {
			this.points = points;
			paint = new Paint();//ʵ����paint����

			paint.setARGB(250, 249, 105, 8);//����ARGB
			paint.setAntiAlias(true);//����AntiAlias
			paint.setStyle(Paint.Style.FILL_AND_STROKE);//����Style
			paint.setStrokeWidth(4);//����StrokeWidth
		}

		public Line(List<GeoPoint> points, Paint paint) {
			this.points = points;
			this.paint = paint;
		}

		//�������
		public boolean onTouchEvent(MotionEvent e, MapView mapView) {
			if (e.getAction() == MotionEvent.ACTION_DOWN) {
				pan = false;
			} else if (e.getAction() == MotionEvent.ACTION_UP) {
				pan = true;
			}
			return false;
		}

		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			count = count + 1;//�ۼ�count����
			if (pan) {
				if (count % 2 == 0) {
					if (!shadow) {
						Projection projection = mapView.getProjection();//��ȡProjection����
						if (points != null) {
							if (points.size() >= 2) {
								Point start = projection.toPixels(
										points.get(0), null);//��ȡǰPoint����
								for (int i = 1; i < points.size(); i++) {
									Point end = projection.toPixels(points
											.get(i), null);//��ȡ��Point����
									canvas.drawLine(start.x, start.y, end.x,
											end.y, paint);//����仭��
									start = end;//����ǰ��
								}
							}
						}
					}
				}

			}
		}
	}

	// �ڲ���,���
	public class Marker extends ItemizedOverlay<OverlayItem> {
		//ʵ����markerList
		private ArrayList<OverlayItem> markerList = new ArrayList<OverlayItem>();

		public Marker(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
		}

		@Override
		protected OverlayItem createItem(int arg0) {
			//��ȡĳ��markerList��Ԫ��
			return markerList.get(arg0);
		}

		@Override
		public int size() {
			//��ȡmarkerList��С
			return markerList.size();
		}

		public void addOverlay(OverlayItem overlay) {
			markerList.add(overlay);//���Ԫ��
			populate();//���ó�ʼ��
		}

		protected boolean onTap(int i) {

			if (createItem(i).getTitle().equals("poiinfo")) {
				geoPoi(createItem(i).getSnippet());//����ҳ���л�
				return false;
			}

			if (to != null) {
				map.getOverlays().remove(to);//�Ƴ���ʾ��
			}

			poiId = createItem(i).getSnippet();//��ȡpoiId
			String str = createItem(i).getTitle();//��ȡ����
			if (str.length() > 8) {
				str = str.substring(0, 4) + "��"
						+ str.substring(str.length() - 1, str.length());//�ַ���ȡ
			}
			to = new TextOverlay(str, createItem(i).getPoint());//ʵ������ʾ��
			map.getOverlays().add(to);//�����ʾ�򵽵�ͼ

			return false;
		}

	}

	// ������ʾ��
	public class TextOverlay extends Overlay {
		private String str; //����str����
		private GeoPoint p;//����GeoPoint����

		public TextOverlay(String str, GeoPoint p) {
			this.str = str;
			this.p = p;
		}

		public boolean draw(Canvas arg0, MapView arg1, boolean arg2, long arg3) {
			if (!arg2) {
				Paint paint = new Paint();//ʵ����Paint����
				paint.setTextSize(14);//����TextSize
				paint.setColor(Color.WHITE);//����Color
				paint.setAntiAlias(true);//����AntiAlias
				paint.setFakeBoldText(true);//����FakeBoldText
				Paint bpaint = new Paint();//ʵ����Paint����
				bpaint.setColor(Color.BLACK);//����Color
				bpaint.setAntiAlias(true);//����AntiAlias
				bpaint.setAlpha(150);//����Alpha

				Point temp = map.getProjection().toPixels(p, null);//ʵ����Point����
				int x1 = temp.x + 15;//�������x����
				int y1 = temp.y - 30;//�������y����

				int count = str.length();//��ʾ���ָ���
				int x2 = x1 + (count + 3) * 14;//�������賤��
				RectF boval = new RectF(x1, y1, x2, y1 + 30);//����Բ�Ǿ���
				setXY(x1, y1 + 50, x2, y1 + 80);//��������Χ

				arg0.drawRoundRect(boval, 10, 10, bpaint);//����Բ�Ǿ���
				arg0.drawText(str + "  >", x1 + 14, y1 + 20, paint);//��������

			}
			return super.draw(arg0, arg1, arg2, arg3);
		}

	}
}
