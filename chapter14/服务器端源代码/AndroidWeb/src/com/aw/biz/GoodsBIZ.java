package com.aw.biz;

import java.util.List;

import com.aw.bean.Goods;
import com.aw.dao.GoodsDAO;

public class GoodsBIZ {
	
	public List<Goods> getGoodsPopList(){
		String hql="from Goods where pop=1";
		List<Goods> glist=new GoodsDAO().getGoodsByHql(hql);
		return glist;
	}
	
	public List<Goods> getGoodsListByType(int type){
		String hql="from Goods where type="+type;
		
		List<Goods> glist=new GoodsDAO().getGoodsByHql(hql);
		return glist;
	}
}
