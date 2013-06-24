package com.aw.biz;

import com.aw.bean.Users;
import com.aw.dao.UsersDAO;

public class UsersBIZ {
	
	public Users userLogin(String uid,String pwd){
		Users user=new UsersDAO().getUserByUid(uid);
		return user;
	}
}
