package com.example;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyWebView extends MapActivity {
	private MapView map;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		map = (MapView) findViewById(R.id.map);
		map.setBuiltInZoomControls(true);
		GeoPoint center = new GeoPoint((int) (23.3497311 * 1E6),(int) (108.4699989 * 1E6));
		MapController mcontrol = map.getController();
		mcontrol.setCenter(center);
		mcontrol.setZoom(10);

		Drawable drawable = this.getResources().getDrawable(R.drawable.flaggreen);
		Marker marker = new Marker(drawable, MyWebView.this);
		GeoPoint point1 = new GeoPoint((int) (23.3497311 * 1E6),(int) (108.4699989 * 1E6));
		OverlayItem overlayitem1 = new OverlayItem(point1, "图标一号", "");
		GeoPoint point2 = new GeoPoint((int) (23.4727264 * 1E6),(int) (108.5281492 * 1E6));
		OverlayItem overlayitem2 = new OverlayItem(point2, "图标二号", "");
		marker.addOverlay(overlayitem1);
		marker.addOverlay(overlayitem2);
		map.getOverlays().add(marker);

	}

	protected boolean isRouteDisplayed() {
		return false;
	}
}
