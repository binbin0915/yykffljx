package com.SpinnerExample2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SpinnerExample2 extends Activity {
	private Spinner mySpinner;
	private Button addBut;
	private Button removeBut;
	private EditText newCityEdit;
	private ArrayAdapter<String> adapter;
	private List<String> allCitys;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		addBut = (Button) this.findViewById(R.id.add);// ��XML�����ļ��л�ȡ��Ӱ�ť����
		removeBut = (Button) this.findViewById(R.id.remove);// ��XML�����ļ��л�ȡɾ����ť����
		newCityEdit = (EditText) this.findViewById(R.id.newCity);// ��XML�����ļ��л�ȡ������е��ı������

		allCitys = new ArrayList<String>();// ��������ArrayList�����������Ԫ��
		allCitys.add("����");
		allCitys.add("�Ϻ�");
		allCitys.add("����");

		mySpinner = (Spinner) this.findViewById(R.id.mySpinner);// ��XML�����ļ��л�ȡSpinner����
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, allCitys);
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// ���������˵�������Ĳ���
		mySpinner.setAdapter(adapter);// ΪSpinner���������
		// ��Ӱ�ť�����¼�
		addBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String newCity = newCityEdit.getText().toString();// ��ȡ�ı���������ĳ���
				for (int i = 0; i < adapter.getCount(); i++) {
					if (newCity.equals(adapter.getItem(i))) {// �жϵ�ǰѡ�е�����ı�����������Ƿ���ͬ
						Toast.makeText(SpinnerExample2.this, "�����Ѵ���",
								Toast.LENGTH_SHORT).show();
						return;
					}
				}
				if (!newCity.trim().equals("")) {// �ı�������ݲ�Ϊ����ʱ
					adapter.add(newCity);// ���ı������������Ϣ��ӵ�adapter��
					int position = adapter.getPosition(newCity);// ��ȡnewCity��ArrayAdapter�е�λ��
					mySpinner.setSelection(position);// ѡ�������˵����±�Ϊposition����
					newCityEdit.setText("");// ����ı���
				}
			}

		});
		// ɾ����ť�����¼�
		removeBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mySpinner.getSelectedItem() != null) {
					adapter.remove(mySpinner.getSelectedItem().toString());// ��adapter��ɾ����ǰѡ�е���Ŀ
					newCityEdit.setText("");
					if (adapter.getCount() == 0) {// ���adapter��û����Ŀ����ʾ�û�
						Toast.makeText(SpinnerExample2.this, "û����Ŀ�����Ƴ�",
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});
		// ��ѡ��mySpinner����Ŀʱ�������¼�
		mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				newCityEdit.setText(parent.getSelectedItem().toString());// ����ǰѡ�е���Ŀ��ʾ��newCityEdit��
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

	}
}