package com.aw.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.aw.biz.BillBIZ;
import com.aw.entity.BillEntity;
import com.opensymphony.xwork2.ActionSupport;

public class BillAction extends ActionSupport{
	private List<BillEntity> blist;
	public String execute() throws Exception {	
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request=ServletActionContext.getRequest();
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		String type=request.getParameter("type");
		if("list".equals(type)){
			String uid=request.getParameter("uid");
			this.setBlist(new BillBIZ().getBillListByUid(uid));
		}
		else if("add".equals(type)){
			String uid=request.getParameter("uid");
			String gids=request.getParameter("gids");
			String gnums=request.getParameter("gnums");
			String btime=request.getParameter("btime");
			String btype=request.getParameter("btype");
			String address=request.getParameter("address");
			boolean pan=new BillBIZ().addBill(uid, gids, gnums, btime, btype,address);
			try {
				response.getWriter().println("{\"msg\":\""+pan+"\"}");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		return SUCCESS;
	}
	public List<BillEntity> getBlist() {
		return blist;
	}
	public void setBlist(List<BillEntity> blist) {
		this.blist = blist;
	}
}
