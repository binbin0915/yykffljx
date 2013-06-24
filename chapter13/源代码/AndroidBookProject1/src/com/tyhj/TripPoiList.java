package com.tyhj;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;
/**
 * ��Ȥ���б���
 * */
public class TripPoiList extends Activity {
	private LinearLayout poiListLayout;//����LinearLayout����
	private ListView myListView;//����ListView����
	private TextView tripName;//����TextView����
	private List<PoiPoint> poiLists;//����List����
	private String tripId="";//����String����
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
		this.setContentView(R.layout.trippoilist);//���ز�����Դ
		setTripPoiList();//��ʾ��Ȥ���б�
	}
	/**
	 * ��Ȥ���б�չʾ
	 * @param
	 * @return
	 */
	public void setTripPoiList(){
		//��Ȥ���б��ϵĻ�����Ϣ
		Bundle bundle = getIntent().getExtras();//��ȡBundle
		tripId=bundle.getString("tripId");//��ȡ·��id����
		final String TripName=bundle.getString("tripName");//��ȡ·�����ֲ���
		tripName=(TextView)this.findViewById(R.id.tripName);//��ȡ·������TextView���
		tripName.setText(TripName);//��ʾ·������
		
		poiListLayout = (LinearLayout) this.findViewById(R.id.poiListLayout);//��ȡ��Ȥ���б��LinearLayout

		
		myListView = new ListView(this);//����ListView����
		//�������ֲ�������
		LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		//����myListView������ʾ����ɫ��Ĭ���Ǻ�ɫ����������Ϊ��ɫ
		myListView.setCacheColorHint(Color.WHITE);
		poiListLayout.addView(myListView, param3);//��myListView��ӵ�poiListLayout�����ϣ���param3���ֲ���
		//����������
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.trippoilistrow, new String[] { "title", "tel", "img",
						 }, new int[] { R.id.poiTitle, R.id.poiTel,
						R.id.poiImg });
		myListView.setAdapter(adapter);//ΪmyListView���������
		// setViewBinder()��������binder���ڰ����ݵ���ͼ ���������ڰ����ݵ���ͼ��binder
		adapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View arg0, Object arg1,
					String textRepresentation) {
				// TODO Auto-generated method stub
				if ((arg0 instanceof ImageView) & (arg1 instanceof Bitmap)) {
					ImageView imageView = (ImageView) arg0;
					Bitmap bitmap = (Bitmap) arg1;
					imageView.setImageBitmap(bitmap);
					return true;
				} else {
					return false;
				}

			}
		});
		//myListView�б������¼�
		myListView.setOnItemClickListener(new OnItemClickListener(){
			// position ָ��ListView���λ�� ��id��view����Դid��
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent it=new Intent();//ʵ����Intent
				Bundle poiMsg=new Bundle();	//ʵ����Bundle
				it.setClass(TripPoiList.this, PoiDetail.class);//����Class
				// ����ǰListView���������Ŀ�Ķ���ŵ�Bundle��
				poiMsg.putSerializable("poiObj", (Serializable) poiLists.get(position));
				poiMsg.putString("tripId", tripId);//��·��id�ŵ�Bundle��
				poiMsg.putString("tripName", TripName);//��·�����Ʒŵ�Bundle��
				it.putExtras(poiMsg);// ���ö�����Ϊ�������ݸ���һ������
				startActivity(it);//����Activity
			}
			
		});
	}
	/**
	 * ��ȡ��Ȥ���б�
	 * @return
	 */
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//����List����		
		WAnalysisFile readFile=new WAnalysisFile();//�����Զ�����WAnalysisFile����
		List<PoiPoint> poiList=readFile.getBigPoiPointList(this,tripId);//��ȡ��Ȥ���б�
		poiLists=poiList;
		for (int i = 0; i < poiList.size(); i += 1) {//ѭ����ȡ��Ȥ����Ϣ
			Map<String, Object> map = new HashMap<String, Object>();
			PoiPoint poi=poiList.get(i);
			map.put("title", poi.getName());//��Ȥ�����洢��map��
			String[] poiTel=poi.getTel().split(" ");//��Ȥ�����绰�ÿո�ֿ���
			String poiTels="";
			for(int j=0;j<poiTel.length;j++){
				if(poiTel.length>2){
					break;
				}
				poiTels=poiTels+poiTel[j]+"��";//ƴ����Ȥ��绰�ַ���
			}
			if(!"".equals(poiTels)){
				poiTels=poiTels.substring(0, poiTels.length()-1);//ȥ��ĩβ�Ķ���
			}
			map.put("tel", poiTels);//��Ȥ��绰�洢��map��
			
			List<String> poiImgList=poi.getImgList();
			if(poiImgList.size()!=0){				
				String tripImg=poi.getImgList().get(0).split("[.]")[0]+".jpg";
				InputStream iso = null;
				try {
					//����ͼƬ
					iso = this.getAssets().open(tripId+"/SmallRoute"+"/"+poi.getRouteId()+"/images/"+tripImg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeStream(iso);				
				map.put("img",bitmap);
			}else{
				int picnum=0;	
				map.put("img",R.drawable.nocolor);
			}
			list.add(map);
		}
		return list;
	}
}
