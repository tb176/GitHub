package com.mo9.data.cashmere.dao;

import com.mo9.data.cashmere.base.IBaseDao;
import com.mo9.data.cashmere.bean.CashOutOrder;

public interface ICashOutOrderDao extends IBaseDao<CashOutOrder>{
	
  public CashOutOrder getCashOutOrderByDealcode(String dealCode);
  
  public int getCashTime(String mobile);
  
  public void updateCashOutOrder(CashOutOrder cashOutOrder);
}
