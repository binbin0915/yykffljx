package com.example;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.OverlayItem;
import com.google.android.maps.ItemizedOverlay;

public class Marker extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> markerList = new ArrayList<OverlayItem>();
	private Context context;
	
	public Marker(Drawable defaultMarker,Context context) {  
		super(boundCenterBottom(defaultMarker));
		this.context=context;
	}

	protected OverlayItem createItem(int arg0) {
		return markerList.get(arg0);
	}
	 
	public int size() { 
		return markerList.size();
	}

	public void addOverlay(OverlayItem overlay) {       
		markerList.add(overlay); 
		populate(); 
	}

	protected boolean onTap(int i) {
		Toast.makeText(context, createItem(i).getTitle(),Toast.LENGTH_LONG).show();
		return false;
	}

}
