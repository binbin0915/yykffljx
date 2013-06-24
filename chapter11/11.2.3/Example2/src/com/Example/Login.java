package com.Example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {

	private EditText username;
	private EditText password;
	private EditText email;
	private Button login;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		
		username = (EditText) this.findViewById(R.id.username);
		password = (EditText) this.findViewById(R.id.password);
		email = (EditText) this.findViewById(R.id.email);
		
		login=(Button) this.findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				boolean pan=new ConnectWeb().sendPostRequest(username.getText().toString(), password.getText().toString(), email.getText().toString());
				showMsg(pan);
			}
		});
	}
	
	private void showMsg(boolean pan){
		String msg="信息发送成功";
		if(!pan){
			msg="信息发送失败";
		}
		new AlertDialog.Builder(Login.this).
	    setTitle("确认").
	    setMessage(msg).
	    setPositiveButton("返回",new DialogInterface.OnClickListener(){
	         public void onClick(DialogInterface dialoginterface, int i){
	        	 
	         }
	     	}
	       ).
		 show();
	}
}
