package com.mo9.data.cashmere.base;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository(value="baseReportDao")//所有dao的基类
public abstract class BaseReportDaoImpl<T> extends BaseDaoImpl<T>{
	@Resource(name="reportSessionFactory")
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	
}
