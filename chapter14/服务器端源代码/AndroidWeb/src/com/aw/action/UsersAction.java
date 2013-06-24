package com.aw.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.aw.bean.Users;
import com.aw.biz.UsersBIZ;
import com.opensymphony.xwork2.ActionSupport;

public class UsersAction extends ActionSupport {
	
	private Users user;
	
	public String execute() throws Exception {	
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request=ServletActionContext.getRequest();
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
	
		String uid=request.getParameter("uid");
		String pwd=request.getParameter("pwd");
		
		this.setUser(new UsersBIZ().userLogin(uid, pwd));
		
		return SUCCESS;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}
