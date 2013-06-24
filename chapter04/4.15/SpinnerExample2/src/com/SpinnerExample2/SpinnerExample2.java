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

		addBut = (Button) this.findViewById(R.id.add);// 从XML布局文件中获取添加按钮对象
		removeBut = (Button) this.findViewById(R.id.remove);// 从XML布局文件中获取删除按钮对象
		newCityEdit = (EditText) this.findViewById(R.id.newCity);// 从XML布局文件中获取输入城市的文本框对象

		allCitys = new ArrayList<String>();// 创建城市ArrayList，并添加三个元素
		allCitys.add("北京");
		allCitys.add("上海");
		allCitys.add("深圳");

		mySpinner = (Spinner) this.findViewById(R.id.mySpinner);// 从XML布局文件中获取Spinner对象
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, allCitys);
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉菜单下拉项的布局
		mySpinner.setAdapter(adapter);// 为Spinner添加适配器
		// 添加按钮单击事件
		addBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String newCity = newCityEdit.getText().toString();// 获取文本框中输入的城市
				for (int i = 0; i < adapter.getCount(); i++) {
					if (newCity.equals(adapter.getItem(i))) {// 判断当前选中的项和文本框中输入的是否相同
						Toast.makeText(SpinnerExample2.this, "该项已存在",
								Toast.LENGTH_SHORT).show();
						return;
					}
				}
				if (!newCity.trim().equals("")) {// 文本框的内容不为“”时
					adapter.add(newCity);// 将文本框中输入的信息添加到adapter中
					int position = adapter.getPosition(newCity);// 获取newCity在ArrayAdapter中的位置
					mySpinner.setSelection(position);// 选中下拉菜单中下标为position的项
					newCityEdit.setText("");// 清空文本框
				}
			}

		});
		// 删除按钮单击事件
		removeBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mySpinner.getSelectedItem() != null) {
					adapter.remove(mySpinner.getSelectedItem().toString());// 从adapter中删除当前选中的项目
					newCityEdit.setText("");
					if (adapter.getCount() == 0) {// 如果adapter中没有项目，提示用户
						Toast.makeText(SpinnerExample2.this, "没有项目可以移除",
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});
		// 当选择mySpinner中项目时触发该事件
		mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				newCityEdit.setText(parent.getSelectedItem().toString());// 将当前选中的项目显示在newCityEdit上
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

	}
}