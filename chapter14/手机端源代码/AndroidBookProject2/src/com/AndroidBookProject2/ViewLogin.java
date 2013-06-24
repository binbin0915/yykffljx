package com.AndroidBookProject2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewLogin extends Activity {
	private EditText username;//����EditText���ͱ���
	private EditText password;//����EditText���ͱ���
	private Button loginBut;//����Button���ͱ���
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.viewlogin);//���ز�����Դ�ļ�viewlogin.xml
		
		username=(EditText) this.findViewById(R.id.username);//���ز�����Դ�ļ��е�EditText���
		password=(EditText) this.findViewById(R.id.password);//���ز�����Դ�ļ��е�EditText���
		loginBut=(Button) this.findViewById(R.id.loginBut);//���ز�����Դ�ļ��е�Button���
		
		
		loginBut=(Button) this.findViewById(R.id.loginBut);//���ز�����Դ�ļ��е�Button���
		loginBut.setOnClickListener(new OnClickListener(){//Button����ĵ����¼�

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//��֤�û����������Ƿ���ȷ
				User theuser=new ConnectWeb().userLogin(username.getText().toString(),password.getText().toString());
				if(theuser!=null){
					DataShare.user.setId(theuser.getId());//�����û�id
					DataShare.user.setUid(theuser.getUid());//�����û��ǳ�
					DataShare.user.setUserPwd(theuser.getUserPwd());//�����û�����
					
					Toast.makeText(ViewLogin.this, "��¼�ɹ�", Toast.LENGTH_LONG).show();
					//��¼�ɹ�����빺�ﳵ
					Intent intent = new Intent();
					intent.setClass(ViewLogin.this, CartListView.class);
					startActivity(intent);
				}else{
					Toast.makeText(ViewLogin.this, "�û��������������", Toast.LENGTH_LONG).show();
				}
			}
			
		});
	}

}
