package com.example;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class Marker extends Overlay{
	private GeoPoint point;
	private Bitmap bmp;
	public Marker(GeoPoint point, Bitmap bmp){
		this.point = point;
        this.bmp = bmp;
	}

	public void draw(Canvas canvas, MapView mapView, boolean shadow) {	
		Projection projection = mapView.getProjection();
		Point pos = projection.toPixels(point, null);
		canvas.drawBitmap(bmp, pos.x, pos.y, null);
	}
}
