package com.AndroidBookProject2;

import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShangPinDetailView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.shangpindetailview);//���ز�����Դ�ļ�
		getGoodDetail();//��ȡ��Ʒ��ϸ��Ϣ
	}

	/**
	 * ��ȡ��Ʒ��ϸ��Ϣ
	 */
	public void getGoodDetail() {
		Bundle bundle = this.getIntent().getExtras();//��ȡBundle
		final Goods theGood = (Goods) bundle.getSerializable("GoodObj");//��ȡBundle�е���Ʒ����
		// ��ʾ������Ϣ		
		TextView goodsName = (TextView) this.findViewById(R.id.goodsName);//��ȡ��Դ�ļ��е�TextView
		goodsName.setText(theGood.getBrand());//����Ʒ������ʾ��TextView
		//��ƷͼƬ
		ImageView goodsPic = (ImageView) this.findViewById(R.id.goodsPic);
		try {
			URL picUrl = new URL(theGood.getDir() + "/" + theGood.getPic());
			Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
			goodsPic.setImageBitmap(pngBM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ��Ʒ���
		TextView goodsNum = (TextView) this.findViewById(R.id.goodsNum);
		goodsNum.setText("��Ʒ���:" + theGood.getGid());
		// �۸�
		TextView goodsPrice = (TextView) this.findViewById(R.id.goodsPrice);
		goodsPrice.setText("��       ��:" + "��" + theGood.getPrice().toString());
		// ����
		TextView goodsDcount = (TextView) this.findViewById(R.id.goodsDcount);
		goodsDcount.setText("��       ��:" + theGood.getBcount().toString());
		// �ۿ�
		TextView goodsDiscount = (TextView) this
				.findViewById(R.id.goodsDiscount);
		goodsDiscount.setText("��       ��:" + theGood.getDiscount().toString());
		// ����
		TextView descTip = (TextView) this.findViewById(R.id.descTip);
		TextPaint textPaint = descTip.getPaint();
		textPaint.setFakeBoldText(true);
		TextView goodsDes = (TextView) this.findViewById(R.id.goodsDes);
		goodsDes.setText(theGood.getDes());
		// ���빺�ﳵ��ť
		Button addToCard = (Button) this.findViewById(R.id.addToCard);
		addToCard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ShangPinDetailView.this, "���빺�ﳵ�ɹ�",
						Toast.LENGTH_LONG).show();
				// TODO Auto-generated method stub
				int index = DataShare.isExistGoods(theGood.getId());
				if (index != -1) {// ����ӹ�����Ʒ
					DataShare.shopList.get(index).setBuyCount(
							DataShare.shopList.get(index).getBuyCount() + 1);
				} else {
					theGood.setBuyCount(1);
					DataShare.shopList.add(theGood);
				}
			}
		});
	}

	// �˵�
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem mnuxq = menu.add(0, 0, 0, "���ﳵ");
		mnuxq.setIcon(R.drawable.cart);
		return super.onCreateOptionsMenu(menu);
	}

	// �˵���Ӧ�¼�
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case 0:
			Intent it = new Intent();
			it.setClass(ShangPinDetailView.this, CartListView.class);
			startActivity(it);
			break;		
		}
		return true;
	}
}
