package com.example;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;


public class MyWebView extends MapActivity {
	private MapView map;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		map = (MapView) findViewById(R.id.map);
		map.setBuiltInZoomControls(true);
		GeoPoint center = new GeoPoint((int) (39.9067452 * 1E6),
				(int) (116.391177 * 1E6));
		final MapController mcontrol = map.getController();
		mcontrol.setCenter(center);
		mcontrol.setZoom(12);
		
		List<GeoPoint> points=new ConnectWeb().getPointList();
		Line line = new Line(points); 
        map.getOverlays().add(line); 
        map.invalidate();
	}

	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public class Line extends Overlay {

		private List<GeoPoint> points;  
		private Paint paint; 
		public Line(List<GeoPoint> points) {  
			this.points = points;
			paint = new Paint();
			paint.setARGB(250, 0, 0, 200);
			paint.setAntiAlias(true);  
			paint.setStyle(Paint.Style.FILL_AND_STROKE);  
			paint.setStrokeWidth(4);  
		}  

		public void draw(Canvas canvas, MapView mapView, boolean shadow) { 
			if (!shadow) {  
				Projection projection = mapView.getProjection(); 
				if (points != null) {  
					if (points.size() >= 2) {  
						Point start = projection.toPixels(points.get(0), null);
						for (int i = 1; i < points.size(); i++) {  
							Point end = projection.toPixels(points.get(i), null);  
							canvas.drawLine(start.x, start.y, end.x, end.y, paint);	
							start = end; 
						}  
					}  
				}  
			} 	
		}
	}
}
