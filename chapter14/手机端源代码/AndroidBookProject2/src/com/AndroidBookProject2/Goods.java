package com.AndroidBookProject2;

import java.io.Serializable;

public class Goods implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;  //���ݿ���
	private String brand;//����
	private Float price;//�۸�
	private Float discount;//�ۿ�
	private Integer bcount;//�������
	private String des;//����
	private String pic;//ͼƬ����
	private String dir;//ͼƬ·��
	private String gid;//��Ʒ���
	private Integer type;//��Ʒ����
	private Integer pop;//�Ƿ��Ƽ�
	private Integer buyCount;//��ǰ�û�����Ʒʵ�ʹ������
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public Integer getBcount() {
		return bcount;
	}
	public void setBcount(Integer bcount) {
		this.bcount = bcount;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPop() {
		return pop;
	}
	public void setPop(Integer pop) {
		this.pop = pop;
	}
	public Integer getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
	
}
