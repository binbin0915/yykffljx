package com.GalleryExample;

import java.lang.reflect.Field;  
import java.util.ArrayList;  
  
import android.app.Activity;  
import android.content.Context;  
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.os.Bundle;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.AdapterView;  
import android.widget.BaseAdapter;  
import android.widget.Gallery;  
import android.widget.ImageView;  
import android.widget.AdapterView.OnItemClickListener; 

public class GalleryExample extends Activity {
	private Gallery myGallery;
	private ImageView myImg;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        myGallery=(Gallery) this.findViewById(R.id.mygallery);//从XML文件中获取Gallery
        myImg=(ImageView) this.findViewById(R.id.myImg);//从XML文件中获取ImageView
        try {  
        	myGallery.setAdapter(new ImageAdapter(this)); //为Gallery添加适配器
        } catch (IllegalArgumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
     
        myGallery.setOnItemClickListener(new OnItemClickListener() {   //Gallery单击事件
            public void onItemClick(AdapterView parent, View v, int position, long id) {  
            	GalleryExample.this.setTitle(String.valueOf(position)); //将当前点击图片的位置显示在窗体标题栏 
            	try {
            		//将当前点击的图片显示在ImageView中，imgList是存储图片的集合
					myImg.setImageResource(new ImageAdapter(GalleryExample.this).myImgList.get(position).intValue());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }  
        });  
        
    }
    private class ImageAdapter extends BaseAdapter{  
        private Context mContext;  
        private ArrayList<Integer> myImgList=new ArrayList<Integer>();  
        private ArrayList<Object> myImgSize=new ArrayList<Object>();  
        public ImageAdapter(Context c) throws IllegalArgumentException, IllegalAccessException{  
            mContext = c;                
            //获取资源中的图片ID和尺寸 ，通过反射机制来实现
            Field[] myFields = R.drawable.class.getDeclaredFields();  
            for(int i=0;i<myFields.length;i+=1)
            {  
                if (!"icon".equals(myFields[i].getName()))//除了icon之外的图片  
                {   
                	//获取图片ID
                    int index=myFields[i].getInt(R.drawable.class); 
                    //保存图片ID 到 myImgList中
                    myImgList.add(index);  
                    //保存图片大小  到myImgSize中
                    int size[]=new int[2];  
                    Bitmap bmImg=BitmapFactory.decodeResource(getResources(),index);  
                    size[0]=bmImg.getWidth();
                    size[1]=bmImg.getHeight();  
                    myImgSize.add(size);  
                }  
            }  
        }  
        @Override  
        public int getCount() {  //获取图片的个数  
            // TODO Auto-generated method stub  
  
            return myImgList.size();  
        }  
  
        @Override  
        public Object getItem(int position) {  
            // TODO Auto-generated method stub  
  
            return position;  
        }  
  
        @Override  
        public long getItemId(int position) {  
            // TODO Auto-generated method stub  
  
            return position;  
        }  
  
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
            // TODO Auto-generated method stub  
  
            ImageView imageView = new ImageView (mContext);  
            //从imgList取得图片ID  
            imageView.setImageResource(myImgList.get(position).intValue()); 
//            设置比例类型  
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);  
            //从myImgSize取得图片大小  
            int size[]= new int[2];  
            size=(int[]) myImgSize.get(position);  
          //设置布局 图片原尺寸大小显示 
            imageView.setLayoutParams(new Gallery.LayoutParams(size[0], size[1]));  
            return imageView;  
        }  
          
    }
}