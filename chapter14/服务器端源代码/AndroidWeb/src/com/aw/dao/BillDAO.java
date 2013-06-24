package com.aw.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.aw.bean.Bill;
import com.aw.entity.BillEntity;
import com.aw.entity.GoodsListEntity;

public class BillDAO {
	
	public List<BillEntity> getBillListByUid(String uid){
		List<BillEntity> blist=new ArrayList<BillEntity>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String hql="from Bill where uid='"+uid+"'";
			Query query = session.createQuery(hql);
			List<Bill> mylist=query.list();
			for(int i=0;i<mylist.size();i+=1){
				BillEntity be=new BillEntity();
				be.setId(mylist.get(i).getId());
				be.setState(mylist.get(i).getState());
				be.setBtime(mylist.get(i).getBtime());
				be.setBtype(mylist.get(i).getBtype());
				be.setCtime(mylist.get(i).getCtime());
				be.setAddress(mylist.get(i).getAddress());
				String gids=mylist.get(i).getGids();
				String gnums[]=mylist.get(i).getGnums().split(",");
				List<GoodsListEntity> glist=new ArrayList<GoodsListEntity>();
				hql="select brand from Goods where id in ("+gids+")";
				List<String> temp=session.createQuery(hql).list();
				for(int j=0;j<temp.size();j+=1){
					GoodsListEntity ge=new GoodsListEntity();
					ge.setGname(temp.get(j));
					ge.setGnum(Integer.valueOf(gnums[j]));
					glist.add(ge);
				}
				be.setGlist(glist);
				blist.add(be);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			HibernateUtil.closeSession(session);
		}	
		return blist;
	}
	
	public boolean addBill(Bill bill){
		boolean pan=true;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();	
			session.saveOrUpdate(bill);
			session.getTransaction().commit();
		}
		catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			pan=false;
		}
		finally{
			HibernateUtil.closeSession(session);
		}
		
		return pan;
	}
}
