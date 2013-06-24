package com.aw.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.aw.bean.Goods;

public class GoodsDAO {
	
	public List<Goods> getGoodsByHql(String hql){
		List<Goods> glist=new ArrayList<Goods>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(hql);
			glist=query.list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			HibernateUtil.closeSession(session);
		}	
		return glist;	
	}
}
