package com.tyhj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Route implements Serializable {
	private String id = "";// 编号
	private String name = "";// 名称
	private String wayPoint = "";// 途经点名称字符串
	private String startPointName = "";// 起点名称
	private String startPoint = "";// 起点经纬度
	private String endPointName = "";// 终点名称
	private String endPoint = "";// 终点经纬度
	private String city = "";// 城市
	private String mileage = "";// 里程
	private String roadToll = "";// 路桥费
	private String drivingTime = "";// 驾驶时间
	private String proposedItinerary = "";// 建议行程
	private String bestSeason = "";// 最佳季节
	private int recommend = 0;// 驾驶指数
	private int scenic = 0;// 风光指数
	private int renwen = 0;// 人文指数
	private int food = 0;// 美食指数
	private String tip = "";// 特别提示
	private String trend = "";// 线路走向
	private String image = "";// 线路图片
	private String highlights = "";// 亮点
	private String drivingTips = "";// 驾驶指引
	private String desc = "";// 描述
	private List<PoiPoint> pointList = new ArrayList<PoiPoint>();// PoiPoint集合
	private List<TrackPoint> trackPointList = new ArrayList<TrackPoint>();// TrackPoint集合

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getWayPoint() {
		return wayPoint;
	}

	public void setWayPoint(String wayPoint) {
		this.wayPoint = wayPoint;
	}

	public String getStartPointName() {
		return startPointName;
	}

	public void setStartPointName(String startPointName) {
		this.startPointName = startPointName;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPointName() {
		return endPointName;
	}

	public void setEndPointName(String endPointName) {
		this.endPointName = endPointName;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getRoadToll() {
		return roadToll;
	}

	public void setRoadToll(String roadToll) {
		this.roadToll = roadToll;
	}

	public String getDrivingTime() {
		return drivingTime;
	}

	public void setDrivingTime(String drivingTime) {
		this.drivingTime = drivingTime;
	}

	public String getProposedItinerary() {
		return proposedItinerary;
	}

	public void setProposedItinerary(String proposedItinerary) {
		this.proposedItinerary = proposedItinerary;
	}

	public String getBestSeason() {
		return bestSeason;
	}

	public void setBestSeason(String bestSeason) {
		this.bestSeason = bestSeason;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public int getScenic() {
		return scenic;
	}

	public void setScenic(int scenic) {
		this.scenic = scenic;
	}

	public int getRenwen() {
		return renwen;
	}

	public void setRenwen(int renwen) {
		this.renwen = renwen;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getTrend() {
		return trend;
	}

	public void setTrend(String trend) {
		this.trend = trend;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getHighlights() {
		return highlights;
	}

	public void setHighlights(String highlights) {
		this.highlights = highlights;
	}

	public String getDrivingTips() {
		return drivingTips;
	}

	public void setDrivingTips(String drivingTips) {
		this.drivingTips = drivingTips;
	}

	public List<PoiPoint> getPointList() {
		return pointList;
	}

	public void setPointList(List<PoiPoint> pointList) {
		this.pointList = pointList;
	}

	public List<TrackPoint> getTrackPointList() {
		return trackPointList;
	}

	public void setTrackPointList(List<TrackPoint> trackPointList) {
		this.trackPointList = trackPointList;
	}

}
