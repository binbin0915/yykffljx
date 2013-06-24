package com.Example;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ConnectWeb {

	public List<CityBean> getCityList(){
		List<CityBean> clist=null;
		try{
			String url="http://192.168.1.8:8080/AndroidWeb/JSONServlet";
			HttpPost request = new HttpPost(url); 
			HttpResponse response = new DefaultHttpClient().execute(request); 
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				String str = EntityUtils.toString(response.getEntity());
				clist=getCList(str);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return clist;
	}
	
	private List<CityBean> getCList(String str){
		List<CityBean> clist=new ArrayList<CityBean>();
		try{
			JSONArray jay=new JSONArray(str);
			for(int i=0;i<jay.length();i+=1){
				JSONObject temp=(JSONObject)jay.get(i);
				CityBean city=new CityBean();
				city.setName(temp.getString("city"));
				city.setCode(temp.getString("postcode"));
				clist.add(city);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return clist;
	}
	
	
}
