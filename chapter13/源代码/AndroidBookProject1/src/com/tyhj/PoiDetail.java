package com.tyhj;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ��Ȥ�����鴰�� 
 * */
public class PoiDetail extends Activity{
	private TextView detailPoiName;//����TextView����
	private TextView detailPoiAddr;//����TextView����
	private TextView detailPoiTel;//����TextView����
	private TextView detailPoiDesc;//����TextView����
	private ImageView detailPoiPic;//����ImageView����
	private LinearLayout detailPicLayout;//����LinearLayout����
	private LinearLayout layoutPoiButs;//����LinearLayout����
	private LinearLayout addrAndTelLayout;//����LinearLayout����
	private LocationManager locationManager;//����LocationManager����
	private PoiPoint poi;//����PoiPoint����
	private MediaPlayer player = new MediaPlayer();//����MediaPlayer����
	/**
	 * ��дActivity�е�onCreate�ķ�����
	 * �÷�������Activity����ʱ��ϵͳ���ã���һ��Activity�������ڵĿ�ʼ��
	 * @param savedInstanceState������Activity��״̬�ġ�
	 *        Bundle���͵�������Map���͵��������ƣ�������key-value����ʽ�洢���ݵ�
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.title);//���ñ���
		this.setContentView(R.layout.poidetail);//���ز�����Դ
		setTripDetail();//��ȡ��Ȥ������
	}
	
	/**
	 * ��ȡ��Ȥ������
	 */
	public void setTripDetail(){
		Bundle poiMsg=getIntent().getExtras();
		poi=(PoiPoint) poiMsg.getSerializable("poiObj");//��ȡPoiPoint����
		final String tripId=poiMsg.getString("tripId");//��ȡ·��id
		String TripName=poiMsg.getString("tripName");//��ȡ·������
		detailPoiName=(TextView)this.findViewById(R.id.detailPoiName);//��ȡ��Ȥ������TextView���
		detailPoiName.setText(poi.getName());//������Ȥ������		
		addrAndTelLayout=(LinearLayout) this.findViewById(R.id.addrAndTelLayout);//��Ȥ��绰������
		
		String addr=poi.getAddress();//��ȡ��Ȥ��ĵ�ַ
		String tel=poi.getTel();//��ȡ��Ȥ��绰
		if(!addr.equals("")){//��ַ�ǿգ���ʾ��ַ��Ϣ
			detailPoiAddr=new TextView(this);//����TextView����
			//����LayoutParams����
			LinearLayout.LayoutParams detailPoiAddrParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			detailPoiAddr.setTextColor(Color.BLACK);//������Ȥ���ַ������ɫ
			detailPoiAddr.setText("��ַ��"+addr);//��ʾ��Ȥ���ַ
			addrAndTelLayout.addView(detailPoiAddr);//����Ȥ���ַ��ӵ�addrAndTelLayout������
		}
		if(!tel.equals("")){
			detailPoiTel=new TextView(this);//����TextView����
			//����LayoutParams����
			LinearLayout.LayoutParams detailPoiTelParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			detailPoiTel.setTextColor(Color.BLACK);//������Ȥ��绰������ɫ
			detailPoiTel.setText("�绰�� "+poi.getTel().replace(" ", ","));//���绰�еĿո��滻Ϊ����
			addrAndTelLayout.addView(detailPoiTel);//����Ȥ��绰��ӵ�addrAndTelLayout������
		}
		detailPoiDesc=(TextView)this.findViewById(R.id.detailPoiDesc);//��ȡ��Ȥ��������TextView���
		detailPoiDesc.setText(poi.getDesc().replaceAll("</br>", "\n\n"));//����Ȥ�������е�</br>�滻Ϊ���з�
		
		detailPicLayout=(LinearLayout) this.findViewById(R.id.poiDetailPicLayout);//��ȡ��Ȥ��ͼƬ���ڵ�LinearLayout���
		
		detailPoiPic=new ImageView(this);//������Ȥ��ͼƬImageView
		//�������ֲ���
		LinearLayout.LayoutParams detailPoiPicLayoutParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		detailPoiPic.setLayoutParams(detailPoiPicLayoutParam);//ΪdetailPoiPic���ò��ֲ���detailPoiPicLayoutParam
		if(poi.getImgList().size()!=0){
			String poiImg=poi.getImgList().get(0).split("[.]")[0]+"_m.jpg";//��ȡͼƬ��
			InputStream iso;
			try {
				iso = this.getAssets().open(tripId+"/SmallRoute"+"/"+poi.getRouteId()+"/images/"+poiImg);//��assets�µ�ͼƬ
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeStream(iso);
				detailPoiPic.setImageBitmap(bitmap);
				detailPicLayout.addView(detailPoiPic);//��ͼƬ��ӵ�detailPicLayout��
				
				detailPicLayout.setOnClickListener(new OnClickListener(){//ͼƬ�ĵ���¼�

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it=new Intent();
						Bundle tripDetailPic=new Bundle();				
						it.setClass(PoiDetail.this, TripDetailPic.class);
						String tripImg=tripId+"/SmallRoute"+"/"+poi.getRouteId()+"/images/"+"y_"+poi.getImgList().get(0).split("[.]")[0]+".jpg";
						tripDetailPic.putString("tripImgPath",tripImg);
						it.putExtras(tripDetailPic);
				    	startActivity(it);
					}
					
				});
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		//��ȡ��ǰλ��
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.0001f, locationListener);
		
		layoutPoiButs=(LinearLayout) this.findViewById(R.id.poibuts);//��ȡ��Ȥ������ҳ�ϰ�ť��LinearLayout
		int []pic={R.drawable.daiwoqu,R.drawable.zhidian,R.drawable.bofang};//�������ȥ���µ磬���Ű�ť����
		List<ImageButton> imgButtLists=new ArrayList();//����List����
		for(int i=0;i<3;i+=1){//��̬�ڷ�������ť
			final ImageButton imgBut=new ImageButton(this);//����ImageButton��ť
			if(i==0){
				imgBut.setOnClickListener(new Button.OnClickListener() {//����ȥ��ť�ĵ����¼�
					public void onClick(View arg0) {		
						Criteria criteria = new Criteria();  
						criteria.setAccuracy(Criteria.ACCURACY_FINE);  
						criteria.setAltitudeRequired(false);  
						criteria.setBearingRequired(false);  
						criteria.setCostAllowed(false);  
						criteria.setPowerRequirement(Criteria.POWER_LOW);  
						
						Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true)); 
						if (location == null) {
							Toast.makeText(PoiDetail.this, "û��GPS�ź�",
									Toast.LENGTH_SHORT).show();
						} else {
						Intent intent = new Intent();
		        		intent.setAction(android.content.Intent.ACTION_VIEW); 
		        		intent.setData(Uri.parse("http://maps.google.com/maps?f=d&saddr="+(location.getLatitude()+","+location.getLongitude())+"&daddr="+(poi.getLat()+","+poi.getLon())+"&hl=cn"));
		        		startActivity(intent); 
						}
					}
				});	
				imgBut.setBackgroundResource(pic[i]);
				imgButtLists.add(imgBut);
			}
			if(i==1 && !"".equals(poi.getTel())){	
				imgBut.setOnClickListener(new Button.OnClickListener() {//�µ簴ť�ĵ����¼�
					public void onClick(View arg0) {		
						prePoiTel(poi.getTel());
					}
				});	
				imgBut.setBackgroundResource(pic[i]);//�����µ簴ť�ı���ͼƬ
				imgButtLists.add(imgBut);
			}
			if(i==2 && !"".equals(poi.getMp3Path())){
				imgBut.setOnClickListener(new Button.OnClickListener() {//���Ű�ť�ĵ����¼�
					public void onClick(View arg0) {	
						try{		
							if(player.isPlaying()){
								player.reset();	
								imgBut.setBackgroundResource(R.drawable.bofang);
							}
							else{
								player.reset();	
								
								AssetFileDescriptor afd = PoiDetail.this.getAssets().openFd(tripId+"/SmallRoute"+"/"+poi.getRouteId()+"/mp3/"+poi.getMp3Path().toLowerCase());
								player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
								player.prepare();
								player.start();
								imgBut.setBackgroundResource(R.drawable.stop);
							}
							player.setOnCompletionListener(new OnCompletionListener(){
								public void onCompletion(MediaPlayer mp) {
									imgBut.setBackgroundResource(R.drawable.bofang);
								}
							});
							
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				});	
				imgBut.setBackgroundResource(pic[i]);	
				imgButtLists.add(imgBut);
			}
			
		}
		
		//ͳһ���ð�ť���
		for(int i=0;i<imgButtLists.size();i++){
			
			if(imgButtLists.size()==4){
				LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				margin.setMargins(0, 0, 5, 0);
				imgButtLists.get(i).setLayoutParams(margin);
				layoutPoiButs.addView(imgButtLists.get(i));
			}
			if(imgButtLists.size()==3){
				LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				margin.setMargins(10, 0,20, 0);
				imgButtLists.get(i).setLayoutParams(margin);
				layoutPoiButs.addView(imgButtLists.get(i));
			}
			if(imgButtLists.size()==2){
				LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				margin.setMargins(10, 0,40, 0);
				imgButtLists.get(i).setLayoutParams(margin);
				layoutPoiButs.addView(imgButtLists.get(i));
			}
			
		}
	}
	
	/** 
	 * �����ر�mp3
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==4 && player.isPlaying()){
			player.reset();
		}	
		return super.onKeyDown(keyCode, event);
	}
	
	
	//λ�ü�����
	private final LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location location) {}

		public void onProviderDisabled(String provider) {}

		public void onProviderEnabled(String provider) {}

		public void onStatusChanged(String provider, int status, Bundle extras) {}	
	};

	
	/**
	 * �ر�λ�ü���
	 */
	protected void onStop() {
		locationManager.removeUpdates(locationListener);
		super.onStop();
	}
	
	/**
	 * ��λ�ü���
	 */
	protected void onRestart() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.0001f, locationListener);
		super.onRestart();
	}
		
	/**
	 * @param tel:�绰
	 */
	public void prePoiTel(String tel){
		final String temp[]=tel.split(" ");
		if(temp.length>1){		
			new AlertDialog.Builder(PoiDetail.this)
            .setTitle("ѡ��")
            .setItems(temp,
            new DialogInterface.OnClickListener(){
              public void onClick(DialogInterface dialog, int whichcountry){
            	  telToPoi(temp[whichcountry]);
              }
            })
            .setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){  
              public void onClick(DialogInterface d, int which){ 
                d.dismiss(); 
              } 
            })
            .show();
		}
		else{
			telToPoi(temp[0]);
		}
	}
		
	/**
	 * �µ���Ȥ��
	 * @param tel���绰
	 */
	public void telToPoi(String tel){
		Intent myIntentDial = new Intent("android.intent.action.DIAL",Uri.parse("tel:"+tel));
		startActivity(myIntentDial);
	}
}
