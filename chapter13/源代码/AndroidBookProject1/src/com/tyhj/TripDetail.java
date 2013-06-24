package com.tyhj;

import java.io.IOException;
import java.io.InputStream;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * ��·���鴰��
 * */
public class TripDetail extends Activity {
	private TextView tripName;// ����TextView����
	private TextView startPos;// ����TextView����
	private TextView endPos;// ����TextView����
	private TextView tripPro;// ����TextView����
	private TextView distance;// ����TextView����
	private TextView suggest;// ����TextView����
	private TextView bestSeason;// ����TextView����
	private ImageView tripPic;// ����ImageView����
	private ImageView jsnd;// ����ImageView����
	private ImageView fgzs;// ����ImageView����
	private ImageView rwzs;// ����ImageView����
	private ImageView mszs;// ����ImageView����
	private TextView tripDesc;// ����TextView����
	private TextView tripTip;// ����TextView����
	private ImageButton xqButt;// ����ImageButton����,���鰴ť
	private ImageButton xqdButt;// ����ImageButton����,��Ȥ�㰴ť
	private ImageButton sdButt;// ����ImageButton����,����վ��ť
	private ImageView picImgView;// ����ImageView����,·��ͼƬ
	private TextView tgzs;// ����TextView����,ָ������
	private TextView js;// ����TextView����,·���������
	private TextView ts;// ����TextView����,·����ʾ����

	/**
	 * ��дActivity�е�onCreate�ķ����� �÷�������Activity����ʱ��ϵͳ���ã���һ��Activity�������ڵĿ�ʼ�� *
	 * 
	 * @param savedInstanceState
	 *            ������Activity��״̬�ġ� Bundle���͵�������Map���͵��������ƣ�������key-value����ʽ�洢���ݵ�
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.title);// ���ô������������
		this.setContentView(R.layout.tripdetail);// ����tripdetail.xml������Դ
		setTripDetail();
	}

	/**
	 * ��ȡ·����ϸ��Ϣ
	 * 
	 * @param
	 * @return
	 */
	public void setTripDetail() {
		Bundle tripObj = getIntent().getExtras();// ��ȡǰһ�����崫�ݹ����Ĳ���
		final Route route = (Route) tripObj.getSerializable("tripObj");// ������ת�������л�����
		tripName = (TextView) this.findViewById(R.id.tripName);// ��ȡ·�����Ƶ�TextView����
		tripName.setText(route.getName());// ����·������TextView�����ʾ����
		startPos = (TextView) this.findViewById(R.id.startPos);// ��ȡ����TextView����
		startPos.setText("��       �㣺" + route.getStartPointName());// �������TextView�����ʾ����
		endPos = (TextView) this.findViewById(R.id.endPos);// ��ȡ�յ��TextView����
		endPos.setText("��       �㣺" + route.getEndPointName());// �����յ�TextView�����ʾ����
		tripPro = (TextView) this.findViewById(R.id.tripPro);// ��ȡ����ʡ��TextView����
		tripPro.setText("��  �� ʡ��" + route.getCity());// ��������ʡTextView�����ʾ����
		distance = (TextView) this.findViewById(R.id.distance);// ��ȡ��̵�TextView����
		distance.setText("��       �̣�" + route.getMileage() + "����");// �������TextView�����ʾ����
		suggest = (TextView) this.findViewById(R.id.suggest);// ��ȡ�����г̵�TextView����
		suggest.setText("�����г̣�" + route.getProposedItinerary() + " ��");// ���ý����г�TextView�����ʾ����
		bestSeason = (TextView) this.findViewById(R.id.bestSeason);// ��ȡ��Ѽ��ڵ�TextView����
		bestSeason.setText("��Ѽ��ڣ�" + route.getBestSeason());// ������Ѽ���TextView�����ʾ����
		tripPic = (ImageView) this.findViewById(R.id.tripPic); // ��ȡ·��ͼƬ��ImageView����
		String tripImg = route.getImage().split("[.]")[0] + "_b.jpg";// ��ȡͼƬ��
		InputStream iso;
		try {
			iso = this.getAssets().open(route.getId() + "/" + tripImg);// ��assets�µ�ͼƬ
			Bitmap bitmap = null;
			bitmap = BitmapFactory.decodeStream(iso);
			tripPic.setImageBitmap(bitmap);// ����tripPic����ʾ��ͼƬ
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		tgzs = (TextView) this.findViewById(R.id.tgzs);// ��ȡ��·ָ��TextView���
		TextPaint tgzstp = tgzs.getPaint();
		tgzstp.setFakeBoldText(true);// ������·ָ��TextViewΪ������ʾ
		js = (TextView) this.findViewById(R.id.js);// ��ȡ��·����TextView���
		TextPaint jstp = js.getPaint();
		jstp.setFakeBoldText(true);// ������·����TextViewΪ������ʾ
		ts = (TextView) this.findViewById(R.id.ts);// ��ȡ��·��ʾTextView���
		TextPaint tstp = ts.getPaint();
		tstp.setFakeBoldText(true);// ������·��ʾTextViewΪ������ʾ

		// �ĸ�ָ��
		jsnd = (ImageView) this.findViewById(R.id.jsnd);// ��ȡ·��ָ��ImageView���
		// ����·��ָ����ʾ��ͼƬ��getPicDrawIdΪ�Զ��巽��,ʵ�ֽ�ָ������ת��Ϊ��Ӧ��ͼƬ
		jsnd.setBackgroundResource(getPicDrawId(route.getRecommend()));
		fgzs = (ImageView) this.findViewById(R.id.fgzs);// ��ȡ���ָ��ImageView���
		fgzs.setBackgroundResource(getPicDrawId(route.getScenic()));// ���÷��ָ����ʾ��ͼƬ
		rwzs = (ImageView) this.findViewById(R.id.rwzs);// ��ȡ����ָ��ImageView���
		rwzs.setBackgroundResource(getPicDrawId(route.getRenwen()));// ��������ָ����ʾ��ͼƬ
		mszs = (ImageView) this.findViewById(R.id.mszs);// ��ȡ��ʳָ��ImageView���
		mszs.setBackgroundResource(getPicDrawId(route.getFood()));// ������ʳָ����ʾ��ͼƬ
		tripDesc = (TextView) this.findViewById(R.id.tripDesc);// ��ȡ����TextView���
		String tripDescStr = route.getDesc().replace("##", "\n\n");// ����������##Ϊ�ֶα�־�������滻�ɻ��з�
		tripDescStr = tripDescStr.replace("\"", "��");// ������ַ��滻��ȫ���ַ���ʾ
		tripDescStr = tripDescStr.replace(",", "��");
		tripDescStr = tripDescStr.replace("(", "��");
		tripDescStr = tripDescStr.replace(")", "��");
		tripDescStr = tripDescStr.replace(";", "��");
		tripDesc.setText(tripDescStr);// ���ý���TextView�����ʾ������
		tripTip = (TextView) this.findViewById(R.id.tripTip);// ��ȡ��ʾTextView���
		String tripTipStr = route.getTip().replace("##", "\n\n");// ��ʾ������##Ϊ�ֶα�־�������滻�ɻ��з�
		tripTipStr = tripTipStr.replace("\"", "��");// ������ַ��滻��ȫ���ַ���ʾ
		tripTipStr = tripTipStr.replace(",", "��");
		tripTipStr = tripTipStr.replace("(", "��");
		tripTipStr = tripTipStr.replace(")", "��");
		tripTipStr = tripTipStr.replace(";", "��");
		tripTip.setText(tripTipStr + "\n");// ������ʾTextView�����ʾ������

		xqButt = (ImageButton) this.findViewById(R.id.xqButt);// ��ȡ����ImageButton���
		xqButt.setOnClickListener(new OnClickListener() {// ����ImageButton����ĵ���¼�
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it = new Intent();// ʵ����Intent
						Bundle tripMsg = new Bundle();// ʵ����Bundle
						it.setClass(TripDetail.this, TripSegment.class);// ����Class
						tripMsg.putString("tripId", route.getId());// ��·��id���浽Bundle��
						tripMsg.putString("tripName", route.getName());// ��·�����ֱ��浽Bundle��
						it.putExtras(tripMsg);// ��tripMsg������Ϊ�������ݸ���һ������
						startActivity(it);// ����Activity
					}

				});
		xqdButt = (ImageButton) this.findViewById(R.id.xqdButt);// ��ȡ��Ȥ��ImageButton���
		xqdButt.setOnClickListener(new OnClickListener() {// ��Ȥ��ImageButton����ĵ���¼�

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it = new Intent();// ʵ����Intent
						Bundle tripMsg = new Bundle(); // ʵ����Bundle
						it.setClass(TripDetail.this, TripPoiList.class);// ����Class
						tripMsg.putString("tripId", route.getId());// ��·��id���浽Bundle��
						tripMsg.putString("tripName", route.getName());// ��·�����ֱ��浽Bundle��
						it.putExtras(tripMsg); // ��tripMsg������Ϊ�������ݸ���һ������
						startActivity(it);// ����Activity
					}

				});
		sdButt = (ImageButton) this.findViewById(R.id.sdButt);// ��ȡ������ImageButton���
		sdButt.setOnClickListener(new OnClickListener() {// ������ImageButton����ĵ���¼�

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it = new Intent();// ʵ����Intent
						it.setClass(TripDetail.this, DZDealersList.class);// ����Class
						startActivity(it);// ����Activity
					}

				});
		picImgView = (ImageView) this.findViewById(R.id.tripPic);// ��ȡ·��ͼƬImageView���
		picImgView.setOnClickListener(new OnClickListener() {// ·��ͼƬImageView����ĵ���¼�

					@Override
					public void onClick(View v) {// ���·��ͼƬ����ʾ·��ͼƬ��ԭ�ߴ�Ч��
						// TODO Auto-generated method stub
						Intent it = new Intent();// ʵ����Intent
						Bundle tripDetailPic = new Bundle(); // ʵ����Bundle
						it.setClass(TripDetail.this, TripDetailPic.class);// ����Class
						String tripImg = route.getId() + "/y_"
								+ route.getImage().split("[.]")[0] + ".jpg";// ��ͼͼƬ��·��
						tripDetailPic.putString("tripImgPath", tripImg);// ��ͼƬ·�����浽tripDetailPic��
						it.putExtras(tripDetailPic);// ��tripDetailPic������Ϊ�������ݸ���һ������
						startActivity(it);// ����Activity
					}

				});
	}

	/**
	 * ��ȡָ�����ֶ�Ӧ��ָ��ͼƬ
	 * 
	 * @param zs
	 *            ��ָ��������Ϣ
	 * @return
	 */
	public int getPicDrawId(int zs) {
		int zsint = 0;
		switch (zs) {
		case 0:
			zsint = R.drawable.xing0;
			break;
		case 1:
			zsint = R.drawable.xing1;
			break;
		case 2:
			zsint = R.drawable.xing2;
			break;
		case 3:
			zsint = R.drawable.xing3;
			break;
		case 4:
			zsint = R.drawable.xing4;
			break;
		case 5:
			zsint = R.drawable.xing5;
			break;
		case 6:
			zsint = R.drawable.xing6;
			break;
		case 7:
			zsint = R.drawable.xing7;
			break;
		case 8:
			zsint = R.drawable.xing8;
			break;
		case 9:
			zsint = R.drawable.xing9;
			break;
		case 10:
			zsint = R.drawable.xing10;
			break;
		}
		return zsint;
	}

}
