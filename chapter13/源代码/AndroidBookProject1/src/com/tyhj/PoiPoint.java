package com.tyhj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PoiPoint implements Serializable{
	private String id = "";//编号
	private String name = "";//名称
	private String routeId = "";//路书编号
	private String time = "";//时间
	private String lat = "";//纬度
	private String lon = "";//经度
	private String categoryId = "";//类型编号
	private String mp3Id = "";//mp3名称
	private String mp3Path = "";//mp3全名
	private int mp3Range=0;//范围
	private String tel="";//电话
	private String desc="";//描述
	private String address="";//地址
	private List<String> imgList = new ArrayList<String>();//图片集合

	
	

	public int getMp3Range() {
		return mp3Range;
	}

	public void setMp3Range(int mp3Range) {
		this.mp3Range = mp3Range;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMp3Id() {
		return mp3Id;
	}

	public void setMp3Id(String mp3Id) {
		this.mp3Id = mp3Id;
	}

	public String getMp3Path() {
		return mp3Path;
	}

	public void setMp3Path(String mp3Path) {
		this.mp3Path = mp3Path;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
