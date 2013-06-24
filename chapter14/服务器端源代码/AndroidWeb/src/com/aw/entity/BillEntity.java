package com.aw.entity;

import java.util.ArrayList;
import java.util.List;

public class BillEntity {
	private Integer id=0;
	private String state="";
	private String btime="";
	private String btype="";
	private String ctime="";
	private String address="";
	private List<GoodsListEntity> glist=new ArrayList<GoodsListEntity>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBtime() {
		return btime;
	}
	public void setBtime(String btime) {
		this.btime = btime;
	}
	public String getBtype() {
		return btype;
	}
	public void setBtype(String btype) {
		this.btype = btype;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public List<GoodsListEntity> getGlist() {
		return glist;
	}
	public void setGlist(List<GoodsListEntity> glist) {
		this.glist = glist;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
