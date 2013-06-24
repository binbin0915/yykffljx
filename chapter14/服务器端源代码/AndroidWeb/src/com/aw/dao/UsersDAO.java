package com.aw.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.aw.bean.Users;

public class UsersDAO {
	
	public Users getUserByUid(String uid){
		Users user=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String hql="from Users where uid='"+uid+"'";
			Query query = session.createQuery(hql);
			user=(Users)query.uniqueResult();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			HibernateUtil.closeSession(session);
		}	
		return user;
	}
}
