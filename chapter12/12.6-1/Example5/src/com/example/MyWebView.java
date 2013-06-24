package com.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyWebView extends MapActivity {
	private MapView map;
	private LocationManager locationManager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		map = (MapView) findViewById(R.id.map);
		map.setBuiltInZoomControls(true);
		GeoPoint center = new GeoPoint((int) (39.9067452 * 1E6),
				(int) (116.391177 * 1E6));
		MapController mcontrol = map.getController();
		mcontrol.setCenter(center);
		mcontrol.setZoom(10);

		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0.0001f, locationListener);

	}

	public void getMyLoc() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		Location location = locationManager
				.getLastKnownLocation(locationManager.getBestProvider(criteria,true));
		if (location == null) {
			Toast.makeText(MyWebView.this, "Ã»ÓÐGPSÐÅºÅ", Toast.LENGTH_SHORT).show();
		} else {
			GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6),
					(int) (location.getLongitude() * 1E6));
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.flagred);
			Marker marker = new Marker(point, bmp);
			map.getOverlays().add(marker);
			map.invalidate();
		}
	}

	private final LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location location) {
			getMyLoc();
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	protected boolean isRouteDisplayed() {
		return false;
	}

	public class Marker extends Overlay {
		private GeoPoint point;
		private Bitmap bmp;

		public Marker(GeoPoint point, Bitmap bmp) {
			this.point = point;
			this.bmp = bmp;
		}

		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			Projection projection = mapView.getProjection();
			Point pos = projection.toPixels(point, null);
			canvas.drawBitmap(bmp, pos.x, pos.y, null);
		}
	}
}
