package com.mo9.data.cashmere.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository(value="baseCreditDao")//所有dao的基类
public abstract class BaseRiskDaoImpl<T> extends BaseDaoImpl<T>{
	@Resource(name="sessionFactory") 
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	
	
}
