package com.example;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;



public class MyWebView extends MapActivity {
	private MapView map;
	private TextOverlay to;
	private int xy[]=new int[4];
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
		Marker marker = new Marker(drawable);
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

	public void setXY(int x1,int y1,int x2,int y2){
		xy[0]=x1;
		xy[1]=x2;
		xy[2]=y1;
		xy[3]=y2;
	}

	public boolean dispatchTouchEvent(MotionEvent ev) {
		int cx=(int)ev.getX();
		int cy=(int)ev.getY();
		if((cx<xy[0] || cx>xy[1]) || (cy<xy[2] || cy>xy[3])){
			if(to!=null){
				map.getOverlays().remove(to);
				to=null;
			}
		}	
		return super.dispatchTouchEvent(ev);
	}
	
	
	public class Marker extends ItemizedOverlay<OverlayItem> {
		private ArrayList<OverlayItem> markerList = new ArrayList<OverlayItem>();

		public Marker(Drawable defaultMarker) {  
			super(boundCenterBottom(defaultMarker));
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
			to=new TextOverlay(createItem(i).getTitle(),createItem(i).getPoint());
			map.getOverlays().add(to);
			return false;
		}

	}
	
	public class TextOverlay extends Overlay{
		private String str;
		private GeoPoint p;
		public TextOverlay(String str,GeoPoint p){
			this.str=str;
			this.p=p;
		}
		
		public boolean draw(Canvas arg0, MapView arg1, boolean arg2, long arg3) {
			if(!arg2){
				Paint paint=new Paint();
				paint.setTextSize(14);
				paint.setColor(Color.WHITE);
				paint.setAntiAlias(true);
				paint.setFakeBoldText(true);
				Paint bpaint=new Paint();
				bpaint.setColor(Color.BLACK);
				bpaint.setAntiAlias(true);
				bpaint.setAlpha(150);
				
				Point temp=map.getProjection().toPixels(p, null);
				int x1=temp.x+15;
				int y1=temp.y-30;
				
				int count=str.length();
				int x2=x1+(count+2)*14;
				RectF boval=new RectF(x1,y1,x2,y1+30);
				setXY(x1,y1+50,x2,y1+80);
				
				arg0.drawRoundRect(boval, 10, 10, bpaint);
				arg0.drawText(str, x1+14, y1+20, paint);
				
			}		
			return super.draw(arg0, arg1, arg2, arg3);
		}
		
	}
}
