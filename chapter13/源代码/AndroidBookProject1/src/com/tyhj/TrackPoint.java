package com.tyhj;


public class TrackPoint {
	private String id = "";//编号
	private String name = "";//名称
	private String routeId = "";//route编号
	private String desc = "";//描述
	private String time = "";//时间
	private String lat = "";//维度
	private String lon = "";//经度
	private String categoryId = "";//类型
	private String trackPoints="";//经纬度字符串

	public String getTrackPoints() {
		return trackPoints;
	}

	public void setTrackPoints(String trackPoints) {
		this.trackPoints = trackPoints;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
