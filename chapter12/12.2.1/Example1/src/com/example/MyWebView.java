package com.example;

import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class MyWebView extends MapActivity{
	private MapView map;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        map=(MapView)findViewById(R.id.map);
        map.setBuiltInZoomControls(true);
        GeoPoint center=new GeoPoint((int)(39.9067452*1E6), (int)(116.391177*1E6)); 
        MapController mcontrol=map.getController();
        mcontrol.setCenter(center);
        mcontrol.setZoom(10);
	}
	
	protected boolean isRouteDisplayed() {
		return false;
	}
}
