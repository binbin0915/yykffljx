package com.AndroidBookProject2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BillDetailView extends Activity{
	private Spinner payMoneyWaySpinner;//����Spinner���ͱ���
	private Spinner sendTimeSpinner;//����Spinner���ͱ���
	private TextView payMoneyTextView;//����TextView���ͱ���
	private EditText personAddr;//����EditText���ͱ���
	private Button submitBill;//����Button���ͱ���
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.viewbilldetail);//���ز�����Դ�ļ�viewbilldetail.xml
		
		//���ʽ��Spinner
		payMoneyWaySpinner=(Spinner) this.findViewById(R.id.payMoneyWay);//��ȡ��Դ�ļ��е�Spinner���
		//����ArrayAdapter����������������Դ������pay_MoneyWayArr
		ArrayAdapter<CharSequence> payMoneyadapter = ArrayAdapter.createFromResource(
				this, R.array.pay_MoneyWayArr, android.R.layout.simple_spinner_item);
		payMoneyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		payMoneyWaySpinner.setAdapter(payMoneyadapter);//ΪpayMoneyWaySpinner���������
		
		//�ͻ�ʱ���Spinner
		sendTimeSpinner=(Spinner) this.findViewById(R.id.sendTime);//��ȡ��Դ�ļ��е�Spinner���
		//����ArrayAdapter����������������Դ������pay_MoneyWayArr
		ArrayAdapter<CharSequence> sendTimeadapter = ArrayAdapter.createFromResource(
				this, R.array.send_TimeArr, android.R.layout.simple_spinner_item);
		sendTimeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sendTimeSpinner.setAdapter(sendTimeadapter);//ΪsendTimeSpinner���������

		//��ȡӦ�����TextView
		payMoneyTextView=(TextView) this.findViewById(R.id.payMoney);//��ȡ��Դ�ļ��е�TextView���
		payMoneyTextView.setText(DataShare.getCartListMoney()+"Ԫ");//����payMoneyTextView�����ʾ����
		
		//��ȡ��ַ��Ϣ
		personAddr=(EditText) this.findViewById(R.id.personAddr);//��ȡ��Դ�ļ��е�EditText���
		
		//�ύ����
		submitBill=(Button) this.findViewById(R.id.submitBill);//��ȡ��Դ�ļ��е�Button���
		submitBill.setOnClickListener(new OnClickListener(){//����Button�ĵ����¼�

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//gids���涩������ַ�������������ַ�����ʽΪ���1�����2
				//gnums���湺������ַ�������������ַ�����ʽΪ����1������2
				String gids="",gnums="";
				for(int i=0;i<DataShare.shopList.size();i++){
					gids=gids+DataShare.shopList.get(i).getId()+",";
					gnums=gnums+DataShare.shopList.get(i).getBuyCount()+",";
				}
				if(gids.length()>0){
					gids=gids.substring(0, gids.length()-1);
				}
				if(gnums.length()>0){
					gnums=gnums.substring(0, gnums.length()-1);
				}
				//��Ӷ���
				new ConnectWeb().addBill(DataShare.user.getUid(), gids, gnums, ShopUtils.changeToUnicode(sendTimeSpinner.getSelectedItem().toString()), ShopUtils.changeToUnicode(payMoneyWaySpinner.getSelectedItem().toString()),ShopUtils.changeToUnicode(personAddr.getText().toString()) );
				Toast.makeText(BillDetailView.this, "�����ύ���", Toast.LENGTH_LONG).show();
			}
			
		});
	}
	// �˵�
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem mnudt = menu.add(0, 0, 0, "��������");
		mnudt.setIcon(R.drawable.bill);
		return super.onCreateOptionsMenu(menu);
	}

	// �˵���Ӧ�¼�
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {		
		case 0:
			Intent it1 = new Intent();
			it1.setClass(BillDetailView.this, BillListView.class);
			startActivity(it1);
			break;
		}
		return true;
	}
	

}
