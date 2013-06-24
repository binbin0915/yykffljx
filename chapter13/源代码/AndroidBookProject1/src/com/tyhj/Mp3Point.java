package com.tyhj;

public class Mp3Point {
	private String lat = "";
	private String lon = "";
	private String mp3Id = "";
	private String mp3Path = "";
	private boolean pan=true;
	private int mp3Range=0;
	public int getMp3Range() {
		return mp3Range;
	}
	public void setMp3Range(int mp3Range) {
		this.mp3Range = mp3Range;
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
	public boolean isPan() {
		return pan;
	}
	public void setPan(boolean pan) {
		this.pan = pan;
	}
}
