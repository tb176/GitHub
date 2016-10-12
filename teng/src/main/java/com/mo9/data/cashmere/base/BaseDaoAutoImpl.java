/**
 * 
 */
package com.mo9.data.cashmere.base;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author jian
 * 
 */
@Repository(value="baseDaoAuto")
public abstract class BaseDaoAutoImpl<T> extends BaseDaoImpl<T> {
	@Autowired
	public final void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
