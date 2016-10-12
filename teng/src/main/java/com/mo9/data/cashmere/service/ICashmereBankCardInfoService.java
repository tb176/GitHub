package com.mo9.data.cashmere.service;

import java.util.List;

import com.mo9.data.cashmere.bean.CashBankCardInfo;

/**
 *
 * 邀请有礼绑卡信息
 * Created by sun on 16/8/31.
 */
public interface ICashmereBankCardInfoService{

    
    /**
     * 根据手机号来查询这个用户卡列表信息
     * 包含对应的手续费
     *@0param dto
     */
    public abstract List<CashBankCardInfo> findListByTel(String mobile);
    
    /**
	 * 赠送有礼用户银行卡信息保存
	 * @param CashBankCardInfo
	 * @return
	 */
    public abstract CashBankCardInfo save(CashBankCardInfo cardInfo);
    
    
    /**
     * 根据银行卡号来查询这个用户卡列表信息
     * 包含对应的手续费
     *@0param dto
     */
    public abstract List<CashBankCardInfo> findListByCard(String card);
    
    public  CashBankCardInfo getById(Long id);
    
    public void saveOrUpdate(CashBankCardInfo cashBankCardInfo);
}
