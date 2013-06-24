package com.tyhj;

import java.io.IOException;
import android.view.View.OnClickListener;
import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ��·�ֶ�չʾ����
 * */
public class TripSegment extends Activity {

	private TextView tripSegName;// ����TextView����
	private LinearLayout tripSegsLayout;// ����LinearLayout����
	private LinearLayout titleSegLayout;// ����LinearLayout����
	private TextView titleSegTitle;// ����TextView����
	private TextView titleSegTitleNum;// ����TextView����
	private ImageButton mapButt;// ����ImageButton����
	private TextView tripDescSeg;// ����TextView����
	private ImageView tripPicSeg;// ����ImageView����

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
		this.setTitle(R.string.title);// ���ñ�������
		this.setContentView(R.layout.tripsegment);// ���ز�����Դ
		getTripSegments();// ��ȡ·��ķֶ���Ϣ����ʾ
	}

	/**
	 * ��ȡ·��ķֶ���Ϣ����ʾ
	 * 
	 * @param
	 * @return
	 */
	public void getTripSegments() {
		Bundle bundle = getIntent().getExtras();// ��ȡBundle
		final String TripName = bundle.getString("tripName");// ��ȡ·��������
		final String tripId = bundle.getString("tripId");// ��ȡ·��id����
		tripSegName = (TextView) this.findViewById(R.id.tripName);// ��ȡ·��ֶ�����TextView���
		tripSegName.setText(TripName);// ��·��ֶ�������ʾ��tripSegName
		// ��̬����·��ֶ���Ϣ
		tripSegsLayout = (LinearLayout) this.findViewById(R.id.tripSegs);// ��ȡ·��ֶ���Ϣ��LinearLayout
		WAnalysisFile readFile = new WAnalysisFile();// ����WAnalysisFile�����
		List<Route> routList = readFile.getRouteList(this, tripId);// ��ȡ�ֶ���Ϣ�б�
		for (int i = 0; i < routList.size(); i += 1) {// ѭ����ȡ�ֶ���Ϣ
			final Route route = routList.get(i);
			// ��̬���ɱ���͵�ͼ��ť�������ڵ�layout
			titleSegLayout = new LinearLayout(this);// ����LinearLayout����������ʾ�ֶ���Ϣ
			titleSegLayout.setOrientation(LinearLayout.HORIZONTAL);// ���ò��ַ�ʽΪˮƽ����
			// �������ֲ���
			LinearLayout.LayoutParams titleSegLayoutParam = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			titleSegLayoutParam.setMargins(0, 8, 0, 0);// ���ñ߾�
			titleSegLayout.setLayoutParams(titleSegLayoutParam);// titleSegLayout���titleSegLayoutParam���ֲ���
			// ����ǰ�����
			titleSegTitleNum = new TextView(this);// ����TextView���
			// �������ֲ���
			LinearLayout.LayoutParams titleSegNumParam = new LinearLayout.LayoutParams(
					26, LayoutParams.WRAP_CONTENT);
			titleSegTitleNum.setLayoutParams(titleSegNumParam);// titleSegTitleNum���titleSegNumParam���ֲ���
			titleSegTitleNum.setText((i + 1) + ".");// ����titleSegTitleNum��ʾ������
			titleSegTitleNum.setTextColor(Color.BLACK);// ����titleSegTitleNum���ֵ���ɫ
			TextPaint xhtp = titleSegTitleNum.getPaint();
			xhtp.setFakeBoldText(true);// ����titleSegTitleNum���ִ�����ʾ
			// ����
			titleSegTitle = new TextView(this);// ����TextView���
			// �������ֲ���
			LinearLayout.LayoutParams titleSegParam = new LinearLayout.LayoutParams(
					207, LayoutParams.WRAP_CONTENT);
			titleSegTitle.setLayoutParams(titleSegParam);// titleSegTitle���titleSegParam���ֲ���
			titleSegTitle.setText(route.getName().replaceAll("����", "��") + " , "
					+ route.getMileage() + "����");
			titleSegTitle.setTextColor(Color.BLACK);// ����titleSegTitle���ֵ���ɫ
			TextPaint bttp = titleSegTitle.getPaint();
			bttp.setFakeBoldText(true);// ����titleSegTitle���ִ�����ʾ

			// ��ͼ��ť
			mapButt = new ImageButton(this);// ����ImageButton���
			// �������ֲ���
			LinearLayout.LayoutParams mapButtParam = new LinearLayout.LayoutParams(
					50, 23);
			mapButtParam.setMargins(5, 0, 0, 0);// ���ñ߾�
			mapButt.setLayoutParams(mapButtParam);// mapButt���mapButtParam���ֲ���
			mapButt.setBackgroundResource(R.drawable.mapbut);// ����mapButt��ʾ��ͼƬ
			mapButt.setOnClickListener(new Button.OnClickListener() {// mapButt����¼�
						public void onClick(View arg0) {

							Bundle bl = new Bundle();// ʵ����Bundle
							bl.putString("roadId", route.getId());// ����·��id
							bl.putString("loc", route.getStartPoint());// �������λ��
							bl.putString("loc2", route.getEndPoint());// �����յ�λ��
							bl.putString("dis", route.getMileage());// �������
							bl.putBoolean("pan", true);
							bl.putString("tripId", tripId);// ����ֶ�id
							bl.putString("tripName", TripName);// ����ֶ�����
							Intent it = new Intent();// ʵ����Intent
							it.setClass(TripSegment.this, RoadMapView.class);// ����Class
							it.putExtras(bl);// ���ö�����Ϊ�������ݸ���һ������
							startActivity(it);// ����Activity
						}
					});

			// ���ֶα�����ţ��ֶα���͵�ͼ��ť��ӵ���titleSegLayout��
			titleSegLayout.addView(titleSegTitleNum);
			titleSegLayout.addView(titleSegTitle);
			titleSegLayout.addView(mapButt);

			// �ֶ�·����·����
			tripDescSeg = new TextView(this);// ʵ����TextView
			// �������ֲ���
			LinearLayout.LayoutParams tripDescSegParam = new LinearLayout.LayoutParams(
					267, LayoutParams.WRAP_CONTENT);
			tripDescSegParam.setMargins(18, 6, 0, 8);// ���ñ߾�
			tripDescSeg.setLayoutParams(tripDescSegParam);// ΪtripDescSeg��Ӳ��ֲ���tripDescSegParam
			tripDescSeg.setText(route.getDesc().replace("##", "\n\n"));// �ֶ������е�##����ʾʱ�滻Ϊ����
			tripDescSeg.setTextColor(Color.BLACK);// ����������ɫ
			tripDescSeg.setLineSpacing(1.0f, 1.1f);// �������ּ��
			// �ֶ�·��ͼƬ
			tripPicSeg = new ImageView(this);// ʵ����ImageView
			// �������ֲ���
			LinearLayout.LayoutParams tripPicSegParam = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			tripPicSegParam.setMargins(25, 0, 0, 20);// ���ñ߾�
			tripPicSeg.setLayoutParams(tripPicSegParam);// ΪtripPicSeg��Ӳ��ֲ���tripPicSegParam

			// ��ͼƬ�ļ�����ʾ
			String tripImg = route.getImage().split("[.]")[0] + "_b.jpg";
			InputStream iso;
			try {
				iso = this.getAssets().open(
						tripId + "/SmallRoute" + "/" + route.getId()
								+ "/images/" + tripImg);// ��assets�µ�ͼƬ
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeStream(iso);
				tripPicSeg.setImageBitmap(bitmap);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// tripPicSegͼƬ�����¼�
			tripPicSeg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent it = new Intent();
					Bundle tripDetailPic = new Bundle();
					it.setClass(TripSegment.this, TripDetailPic.class);
					String tripImg = tripId + "/SmallRoute" + "/"
							+ route.getId() + "/images/" + "y_"
							+ route.getImage().split("[.]")[0] + ".jpg";
					tripDetailPic.putString("tripImgPath", tripImg);
					it.putExtras(tripDetailPic);
					startActivity(it);
				}

			});

			// ����̬���ɵ���ӵ�layout��
			tripSegsLayout.addView(titleSegLayout);
			tripSegsLayout.addView(tripDescSeg);
			tripSegsLayout.addView(tripPicSeg);
		}
	}

}
