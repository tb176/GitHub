package com.mo9.data.cashmere.dao;

import com.mo9.data.cashmere.base.IBaseDao;
import com.mo9.data.cashmere.bean.CashUser;

public interface ICashmereUserDao extends IBaseDao<CashUser>{
	
	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	public CashUser getUserByTel(String mobile);
	
	
	/**
	 * 根据手机号锁表
	 * @param mobile
	 * @return
	 */
	public CashUser lockUserByTel(String mobile);
}
