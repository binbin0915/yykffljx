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
        
        myGallery=(Gallery) this.findViewById(R.id.mygallery);//��XML�ļ��л�ȡGallery
        myImg=(ImageView) this.findViewById(R.id.myImg);//��XML�ļ��л�ȡImageView
        try {  
        	myGallery.setAdapter(new ImageAdapter(this)); //ΪGallery���������
        } catch (IllegalArgumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
     
        myGallery.setOnItemClickListener(new OnItemClickListener() {   //Gallery�����¼�
            public void onItemClick(AdapterView parent, View v, int position, long id) {  
            	GalleryExample.this.setTitle(String.valueOf(position)); //����ǰ���ͼƬ��λ����ʾ�ڴ�������� 
            	try {
            		//����ǰ�����ͼƬ��ʾ��ImageView�У�imgList�Ǵ洢ͼƬ�ļ���
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
            //��ȡ��Դ�е�ͼƬID�ͳߴ� ��ͨ�����������ʵ��
            Field[] myFields = R.drawable.class.getDeclaredFields();  
            for(int i=0;i<myFields.length;i+=1)
            {  
                if (!"icon".equals(myFields[i].getName()))//����icon֮���ͼƬ  
                {   
                	//��ȡͼƬID
                    int index=myFields[i].getInt(R.drawable.class); 
                    //����ͼƬID �� myImgList��
                    myImgList.add(index);  
                    //����ͼƬ��С  ��myImgSize��
                    int size[]=new int[2];  
                    Bitmap bmImg=BitmapFactory.decodeResource(getResources(),index);  
                    size[0]=bmImg.getWidth();
                    size[1]=bmImg.getHeight();  
                    myImgSize.add(size);  
                }  
            }  
        }  
        @Override  
        public int getCount() {  //��ȡͼƬ�ĸ���  
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
            //��imgListȡ��ͼƬID  
            imageView.setImageResource(myImgList.get(position).intValue()); 
//            ���ñ�������  
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);  
            //��myImgSizeȡ��ͼƬ��С  
            int size[]= new int[2];  
            size=(int[]) myImgSize.get(position);  
          //���ò��� ͼƬԭ�ߴ��С��ʾ 
            imageView.setLayoutParams(new Gallery.LayoutParams(size[0], size[1]));  
            return imageView;  
        }  
          
    }
}