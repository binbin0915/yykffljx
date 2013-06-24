package com.LayoutParams2;

import com.LayoutParams2.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class LayoutParams2 extends Activity {
	/** Called when the activity is first created. */
	LinearLayout liearLayoutMain; // ����LinearLayout����

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); // ����main.xml�����ļ�
		
		liearLayoutMain = (LinearLayout) this.findViewById(R.id.layout); // ��ȡmain.xml��idΪlayout��liearLayout����

		TextView bookName = new TextView(this);// ����һ��TextView����
		ViewGroup.LayoutParams bookNameParams = new ViewGroup.LayoutParams(// ����ViewGroup.LayoutParams����
				ViewGroup.LayoutParams.FILL_PARENT, // ���ò��ֲ�������Ŀ��
				ViewGroup.LayoutParams.WRAP_CONTENT); // ���ò��ֲ�������ĸ�
		bookName.setLayoutParams(bookNameParams);// ����bookName�Ĳ��ַ�ʽ
		bookName.setText("������ˮ������"); // ����bookName������ʾ������
		bookName.setTextColor(Color.BLACK);// ����bookName��������ɫ
		bookName.setGravity(Gravity.CENTER);// ����bookName���־�����ʾ
		TextPaint bttp = bookName.getPaint();// ��ȡTextPaint����
		bttp.setFakeBoldText(true);// ����bookName�Ӵ���ʾ

		TextView bookDesc = new TextView(this);// ����һ��TextView����
		LinearLayout.LayoutParams bookDescParams = new LinearLayout.LayoutParams(// ����ViewGroup.LayoutParams����
				ViewGroup.LayoutParams.WRAP_CONTENT, // ���ò��ֲ�������Ŀ��
				ViewGroup.LayoutParams.WRAP_CONTENT); // ���ò��ֲ�������ĸ߶�
		bookDesc.setLayoutParams(bookDescParams);// ����bookDesc�Ĳ��ַ�ʽ
		bookDesc.setText(R.string.desc); // ����txtFont������ʾ������
		bookDesc.setTextColor(Color.BLACK);

		ImageView bookPic = new ImageView(this);// ����һ��ImageView����
		LinearLayout.LayoutParams bookPicParams = new LinearLayout.LayoutParams(
				251, 183);
		bookPicParams.setMargins(25, 0, 0, 5);// ���ñ߾࣬����ԭ��void setMargins (int
												// left, int top, int right, int
												// bottom)
		bookPic.setLayoutParams(bookPicParams);
		bookPic.setBackgroundResource(R.drawable.guilin);// ����ImageView�ı���ͼ

		liearLayoutMain.addView(bookName); // ��bookName��ӵ�liearLayoutMain������
		liearLayoutMain.addView(bookPic);// ��bookPic��ӵ�liearLayoutMain������
		liearLayoutMain.addView(bookDesc);// ��bookDesc��ӵ�liearLayoutMain������
	}
}