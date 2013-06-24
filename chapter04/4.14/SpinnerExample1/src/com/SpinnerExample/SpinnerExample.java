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
		setContentView(R.layout.main);// 加载main.xml文件
		// 第一步：获取XML中的Spinner组件
		mySpinner = (Spinner) this.findViewById(R.id.mySpinner);
		// 第二步：为下拉列表项定义适配器
		// createFromResource (Context context, int textArrayResId, int
		// textViewResId) 参数的含义为
		// 1.context:应用上下文
		// 2.textArrayResId：适配器的数据源，这里的R.array.colors是在res/values/arrays.xml中定义的数组
		// 3.Spinner上显示数据的视图。这里用Android自带的简单的下拉菜单方式
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.colors, android.R.layout.simple_spinner_item);
		// 第三步：设置当Spinner按下时在下拉列表里显示数据视图。
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 第四步：为Spinner添加适配器
		mySpinner.setAdapter(adapter);
		// 第五步：为Spinner添加事件监听，setOnItemSelectedListener该事件在菜单被选中时触发
		mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			/*
			 * 功能：Spinner的项被选择时触发该方法 parent 当前被选择的对象所在的 AdapterView view
			 * 在AdapterView中被点击的View position 当前点击项在View的位置，从0开始，0是第一项 id
			 * 被选择项的id
			 */
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// 将当前点击项的坐标和id显示出来
				// getSelectedItem():获取选中项的值
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