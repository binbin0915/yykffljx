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
 * 兴趣点列表窗体
 * */
public class TripPoiList extends Activity {
	private LinearLayout poiListLayout;//声明LinearLayout变量
	private ListView myListView;//声明ListView变量
	private TextView tripName;//声明TextView变量
	private List<PoiPoint> poiLists;//声明List变量
	private String tripId="";//声明String变量
	/**
	 * 重写Activity中的onCreate的方法。
	 * 该方法是在Activity创建时被系统调用，是一个Activity生命周期的开始。
	 * @param savedInstanceState：保存Activity的状态的。
	 *        Bundle类型的数据与Map类型的数据相似，都是以key-value的形式存储数据的
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.title);//设置标题
		this.setContentView(R.layout.trippoilist);//加载布局资源
		setTripPoiList();//显示兴趣点列表
	}
	/**
	 * 兴趣点列表展示
	 * @param
	 * @return
	 */
	public void setTripPoiList(){
		//兴趣点列表上的基本信息
		Bundle bundle = getIntent().getExtras();//获取Bundle
		tripId=bundle.getString("tripId");//获取路书id参数
		final String TripName=bundle.getString("tripName");//获取路书名字参数
		tripName=(TextView)this.findViewById(R.id.tripName);//获取路书名字TextView组件
		tripName.setText(TripName);//显示路书名字
		
		poiListLayout = (LinearLayout) this.findViewById(R.id.poiListLayout);//获取兴趣点列表的LinearLayout

		
		myListView = new ListView(this);//创建ListView对象
		//创建布局参数对象
		LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		//设置myListView高亮显示的颜色，默认是黑色，这里设置为白色
		myListView.setCacheColorHint(Color.WHITE);
		poiListLayout.addView(myListView, param3);//将myListView添加到poiListLayout布局上，用param3布局参数
		//创建适配器
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.trippoilistrow, new String[] { "title", "tel", "img",
						 }, new int[] { R.id.poiTitle, R.id.poiTel,
						R.id.poiImg });
		myListView.setAdapter(adapter);//为myListView添加适配器
		// setViewBinder()方法设置binder用于绑定数据到视图 参数：用于绑定数据到视图的binder
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
		//myListView列表项点击事件
		myListView.setOnItemClickListener(new OnItemClickListener(){
			// position 指在ListView里的位置 ，id是view的资源id。
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent it=new Intent();//实例化Intent
				Bundle poiMsg=new Bundle();	//实例化Bundle
				it.setClass(TripPoiList.this, PoiDetail.class);//设置Class
				// 将当前ListView被点击的项目的对象放到Bundle中
				poiMsg.putSerializable("poiObj", (Serializable) poiLists.get(position));
				poiMsg.putString("tripId", tripId);//将路书id放到Bundle中
				poiMsg.putString("tripName", TripName);//将路数名称放到Bundle中
				it.putExtras(poiMsg);// 将该对象作为参数传递给下一个窗体
				startActivity(it);//启动Activity
			}
			
		});
	}
	/**
	 * 获取兴趣点列表
	 * @return
	 */
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//创建List对象		
		WAnalysisFile readFile=new WAnalysisFile();//创建自定义类WAnalysisFile对象
		List<PoiPoint> poiList=readFile.getBigPoiPointList(this,tripId);//获取兴趣点列表
		poiLists=poiList;
		for (int i = 0; i < poiList.size(); i += 1) {//循环获取兴趣点信息
			Map<String, Object> map = new HashMap<String, Object>();
			PoiPoint poi=poiList.get(i);
			map.put("title", poi.getName());//兴趣点标题存储到map中
			String[] poiTel=poi.getTel().split(" ");//兴趣点多个电话用空格分开的
			String poiTels="";
			for(int j=0;j<poiTel.length;j++){
				if(poiTel.length>2){
					break;
				}
				poiTels=poiTels+poiTel[j]+"，";//拼接兴趣点电话字符串
			}
			if(!"".equals(poiTels)){
				poiTels=poiTels.substring(0, poiTels.length()-1);//去掉末尾的逗号
			}
			map.put("tel", poiTels);//兴趣点电话存储到map中
			
			List<String> poiImgList=poi.getImgList();
			if(poiImgList.size()!=0){				
				String tripImg=poi.getImgList().get(0).split("[.]")[0]+".jpg";
				InputStream iso = null;
				try {
					//加载图片
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
