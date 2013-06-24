package com.aw.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.aw.bean.Goods;
import com.aw.biz.GoodsBIZ;
import com.opensymphony.xwork2.ActionSupport;

public class GoodsAction extends ActionSupport{
	List<Goods> glist;
	
	public String execute() throws Exception {	
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request=ServletActionContext.getRequest();
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		String type=request.getParameter("type");
		if(type.equals("pop")){
			this.setGlist(new GoodsBIZ().getGoodsPopList());
		}
		else if(type.equals("type")){
			int gtype=Integer.valueOf(request.getParameter("gtype"));
			this.setGlist(new GoodsBIZ().getGoodsListByType(gtype));
		}
		return SUCCESS;
	}
	
	public List<Goods> getGlist() {
		return glist;
	}

	public void setGlist(List<Goods> glist) {
		this.glist = glist;
	}
	
	
}
