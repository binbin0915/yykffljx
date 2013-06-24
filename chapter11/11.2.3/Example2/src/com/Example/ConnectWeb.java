package com.Example;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class ConnectWeb {

	public boolean sendGetRequest(String username,String password,String email){
		boolean pan=false;
		try{
			String url="http://192.168.1.8:8080/AndroidWeb/MyServlet?username="+
			username+"&password="+password+"&email="+email;
			HttpGet request = new HttpGet(url); 
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request); 
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				
				pan=true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return pan;
	}
	
	public boolean sendPostRequest(String username,String password,String email){
		boolean pan=false;
		try{
			String url="http://192.168.1.8:8080/AndroidWeb/MyServlet";
			HttpPost request = new HttpPost(url); 
			List <NameValuePair> params = new ArrayList <NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("email", email));
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse response = new DefaultHttpClient().execute(request); 
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				pan=true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return pan;
	}
}
