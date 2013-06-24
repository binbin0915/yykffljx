package com.SpinnerExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SpinnerExample extends Activity {
	private Spinner mySpinner;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);// ����main.xml�ļ�
		// ��һ������ȡXML�е�Spinner���
		mySpinner = (Spinner) this.findViewById(R.id.mySpinner);
		// �ڶ�����Ϊ�����б����������
		// createFromResource (Context context, int textArrayResId, int
		// textViewResId) �����ĺ���Ϊ
		// 1.context:Ӧ��������
		// 2.textArrayResId��������������Դ�������R.array.colors����res/values/arrays.xml�ж��������
		// 3.Spinner����ʾ���ݵ���ͼ��������Android�Դ��ļ򵥵������˵���ʽ
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.colors, android.R.layout.simple_spinner_item);
		// �����������õ�Spinner����ʱ�������б�����ʾ������ͼ��
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ���Ĳ���ΪSpinner���������
		mySpinner.setAdapter(adapter);
		// ���岽��ΪSpinner����¼�������setOnItemSelectedListener���¼��ڲ˵���ѡ��ʱ����
		mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			/*
			 * ���ܣ�Spinner���ѡ��ʱ�����÷��� parent ��ǰ��ѡ��Ķ������ڵ� AdapterView view
			 * ��AdapterView�б������View position ��ǰ�������View��λ�ã���0��ʼ��0�ǵ�һ�� id
			 * ��ѡ�����id
			 */
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// ����ǰ�����������id��ʾ����
				// getSelectedItem():��ȡѡ�����ֵ
				Toast.makeText(
						SpinnerExample.this,
						"position:" + position + " id:" + id + " value:"
								+ mySpinner.getSelectedItem().toString(),
						Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(SpinnerExample.this, "unselected",
						Toast.LENGTH_SHORT).show();
			}

		});

	}
}