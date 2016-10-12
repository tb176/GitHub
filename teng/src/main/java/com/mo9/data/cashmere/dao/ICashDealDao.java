package com.mo9.data.cashmere.dao;

import com.mo9.data.cashmere.base.IBaseDao;
import com.mo9.data.cashmere.bean.CashDeal;
import com.mo9.data.cashmere.bean.CashDeal.CashmereDealType;
import com.mo9.webUtils.Page;


public interface ICashDealDao extends IBaseDao<CashDeal>{
	/**
	 * 订单号查询检查订单
	 * @param dealcode
	 * @return
	 */
	public CashDeal getCashDealByDealcode(String riskOrder,CashmereDealType dealType);
	
	/**
	 * 冲正check
	 * @param dealcode
	 * @return
	 */
	public CashDeal getCashDealByCashOutOrder(String cashoutorder,CashmereDealType dealType);
	/**
	 * 流水表 根据以下参数确定唯一一条记录流水
	 * @param selfMoble
	 * @param targetMobile
	 * @param dealType
	 * @return
	 */
	public CashDeal getCashDeal(String selfMoble,String targetMobile,CashmereDealType dealType);
	
	/**
     * 根据手机号来查询这个用户提现总额
     * 包含对应的手续费
     *@0param mobile
     */
    public int totalCashAmountByTel(String mobile);
    
    /**
     * 根据手机号来查询这个用户当天提现总额
     * 包含对应的手续费
     *@0param mobile
     */
    public int todayCashAmountByTel(String mobile);
    
    /**
     * 根据手机号来查询这个用户所赚的钱
     *@0param mobile
     */
    public int totalAmountByTel(String mobile);
    
    /**
     * 根据手机号来查询这个用户奖励详细
     * 包含对应的手续费
     *@0param mobile
     */
    public Page<CashDeal> getAwardDetialByTel(Page<CashDeal> page,String mobile);
	
}
