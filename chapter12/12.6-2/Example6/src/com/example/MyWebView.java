package com.example;

import java.util.List;
import java.util.Locale;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;


public class MyWebView extends MapActivity {
	private MapView map;
	private GeoPoint center;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		map = (MapView) findViewById(R.id.map);
		map.setBuiltInZoomControls(true);
		center = new GeoPoint((int) (39.9067452 * 1E6),
				(int) (116.391177 * 1E6));
		final MapController mcontrol = map.getController();
		mcontrol.setCenter(center);
		mcontrol.setZoom(10);
		
		getLocationByAddress();
	}

	protected boolean isRouteDisplayed() {
		return false;
	}
	
	
	public void getAddressByLocation(){
		Geocoder gc = new Geocoder(this, Locale.CHINA);
		try{
			List<Address> address =gc.getFromLocation(39.9067452,116.391177, 1);
			StringBuilder sb = new StringBuilder();			
			if (address.size() > 0){
	           Address adsLocation = address.get(0);
		      sb.append(adsLocation.getAdminArea()).append("\n");
		      sb.append(adsLocation.getCountryCode()).append("\n");
		      sb.append(adsLocation.getCountryName()).append("\n");
		      sb.append(adsLocation.getFeatureName()).append("\n");
		      sb.append(adsLocation.getLocality()).append("\n");
		      sb.append(adsLocation.getPhone()).append("\n");
		      sb.append(adsLocation.getPostalCode()).append("\n");
		      sb.append(adsLocation.getPremises()).append("\n");
		      sb.append(adsLocation.getThoroughfare());
	        }		
			Toast.makeText(MyWebView.this, sb.toString(),Toast.LENGTH_LONG).show();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public void getLocationByAddress(){
		Geocoder gc = new Geocoder(this, Locale.CHINA);
		try{
			List<Address> address =gc.getFromLocationName("人大会堂西路", 1);
			StringBuilder sb = new StringBuilder();			
			if (address.size() > 0){
	          Address adsLocation = address.get(0);
		      sb.append(adsLocation.getLatitude()).append("\n");
		      sb.append(adsLocation.getLongitude());
	        }		
			Toast.makeText(MyWebView.this, sb.toString(),Toast.LENGTH_LONG).show();	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
