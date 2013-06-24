package com.example;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

public class ConnectWeb {

	public List<GeoPoint> getPointList() {
		List<GeoPoint> clist = null;
		try {
			String url = "http://192.168.1.8:8080/AndroidWeb/LineServlet";
			HttpPost request = new HttpPost(url);
			HttpResponse response = new DefaultHttpClient().execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = EntityUtils.toString(response.getEntity());
				clist = getList(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clist;
	}

	private List<GeoPoint> getList(String str) {
		List<GeoPoint> clist = new ArrayList<GeoPoint>();
		try {
			JSONArray jay = new JSONArray(str);
			for (int i = 0; i < jay.length(); i += 1) {
				JSONObject temp = (JSONObject) jay.get(i);
				GeoPoint point = new GeoPoint((int) (Double.valueOf(temp
						.getString("lat")) * 1E6), (int) (Double.valueOf(temp
						.getString("lng")) * 1E6));
				clist.add(point);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clist;
	}
}
