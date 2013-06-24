package com.tyhj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DZDealersDetail extends Activity {
	private TextView detailDealerName;
	private TextView detailDealerAddr;
	private TextView detailDealerPerson;
	private TextView detailDealerTel;
	private Beetle s4;

	private LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.title);
		this.setContentView(R.layout.dzdealersdetail);
		setDealersDetail();
	}

	public void setDealersDetail() {

		Bundle beanMsg = getIntent().getExtras();
		s4 = (Beetle) beanMsg.getSerializable("beanObj");

		detailDealerName = (TextView) this.findViewById(R.id.detailDealerName);
		detailDealerName.setText(s4.getName());

		detailDealerAddr = (TextView) this.findViewById(R.id.detailDealerAddr);
		detailDealerAddr.setText(s4.getAddress());

		detailDealerPerson = (TextView) this
				.findViewById(R.id.detailDealerPerson);
		detailDealerPerson.setText(s4.getContacts());

		detailDealerTel = (TextView) this.findViewById(R.id.detailDealerTel);
		detailDealerTel.setText(s4.getTel().replace("  ", "，"));

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0.0001f, locationListener);

		ImageButton go = (ImageButton) this
				.findViewById(R.id.detailDealerTakeme);
		go.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				Criteria criteria = new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_FINE);
				criteria.setAltitudeRequired(false);
				criteria.setBearingRequired(false);
				criteria.setCostAllowed(false);
				criteria.setPowerRequirement(Criteria.POWER_LOW);

				Location location = locationManager
						.getLastKnownLocation(locationManager.getBestProvider(
								criteria, true));
				if (location == null) {
					Toast.makeText(DZDealersDetail.this, "没有GPS信号",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent();
					intent.setAction(android.content.Intent.ACTION_VIEW);
					intent.setData(Uri
							.parse("http://maps.google.com/maps?f=d&saddr="
									+ (location.getLatitude() + "," + location
											.getLongitude()) + "&daddr="
									+ (s4.getLat() + "," + s4.getLon())
									+ "&hl=cn"));
					startActivity(intent);
				}
			}
		});
		ImageButton tel = (ImageButton) this.findViewById(R.id.detailDealerZD);
		tel.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				prePoiTel(s4.getTel());
			}
		});
		ImageButton map = (ImageButton) this.findViewById(R.id.detailDealerMap);
		map.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				Bundle bl = new Bundle();
				bl.putString("id", s4.getId());
				bl.putString("name", s4.getName());
				bl.putString("loc", s4.getLat() + " " + s4.getLon());
				bl.putBoolean("pan", false);
				Intent it = new Intent();
				it.setClass(DZDealersDetail.this, RoadMapView.class);
				it.putExtras(bl);
				startActivity(it);
			}
		});

	}

	// 位置监听器
	private final LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location location) {
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
		locationManager.removeUpdates(locationListener);
		super.onStop();
	}

	// 打开位置监听
	protected void onRestart() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0.0001f, locationListener);
		super.onRestart();
	}

	// 准备电话
	public void prePoiTel(String tel) {
		final String temp[] = tel.split("  ");
		if (temp.length > 1) {
			new AlertDialog.Builder(DZDealersDetail.this).setTitle("选择")
					.setItems(temp, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichcountry) {
							telToPoi(temp[whichcountry]);
						}
					}).setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface d, int which) {
									d.dismiss();
								}
							}).show();
		} else {
			telToPoi(temp[0]);
		}
	}

	// 致电兴趣点
	public void telToPoi(String tel) {
		Intent myIntentDial = new Intent("android.intent.action.DIAL", Uri
				.parse("tel:" + tel));
		startActivity(myIntentDial);
	}
}
