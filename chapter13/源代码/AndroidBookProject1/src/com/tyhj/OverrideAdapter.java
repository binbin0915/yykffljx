package com.tyhj;
/*
 * ¶Á sd¿¨ÖÐµÄÍ¼Æ¬
 * */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.SimpleAdapter;


public class OverrideAdapter extends SimpleAdapter {

	public OverrideAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	
	
	public void setViewImage(ImageView v, String value) {
		// TODO Auto-generated method stub
		File file = new File(value);
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		((ImageView) v).setImageBitmap(bitmap); 
	}

}
