package com.mo9.data.cashmere.dao;

import java.util.List;

import com.mo9.data.cashmere.base.IBaseDao;
import com.mo9.data.cashmere.bean.CashBankCardInfo;

public interface ICashmereBankCardInfoDao extends IBaseDao<CashBankCardInfo>{
	
	
	/**
     * 根据手机号来查询这个用户卡列表信息
     * 包含对应的手续费
     *@0param mobile
     */
	public List<CashBankCardInfo> findListByTel(String mobile);
	
	
	/**
     * 根据银行卡号来查询这个用户卡列表信息
     * 包含对应的手续费
     *@0param mobile
     */
	public List<CashBankCardInfo> findListByCard(String card);
}
