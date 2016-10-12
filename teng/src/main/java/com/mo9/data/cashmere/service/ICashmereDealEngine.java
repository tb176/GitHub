package com.mo9.data.cashmere.service;

import com.mo9.data.cashmere.bean.CashDeal;
import com.mo9.data.cashmere.dto.DealAcctingDto;
import com.mo9.webUtils.Page;

/**
 *
 * 邀请有礼流水处理引擎
 * Created by sun on 16/8/31.
 */
public interface ICashmereDealEngine {

    /**
     * 流水记账接口
     * 业务描述:
     * 1.业务锁表
     * 2.获取最近一次的一笔流水,在上一笔流水当前余额基础进行计算
     * 3.新建一笔当前流水,并将当前流水的isLastDea的标志位为true
     * 4.将之前最后一笔流水的isLastDeal标志位设置为false
     * 5.更新用户的当前余额
     * 6.提交事务
     *
     * @param dto
     */
    public abstract void accounting(DealAcctingDto dto);
    
    
    /**
     * 根据手机号来查询这个用户提现总额
     * 包含对应的手续费
     *@0param mobile
     */
    public abstract int totalCashAmountByTel(String mobile);
    
    /**
     * 根据手机号来查询这个用户当天提现总额
     * 包含对应的手续费
     *@0param mobile
     */
    public abstract int todayCashAmountByTel(String mobile);
    
    /**
     * 根据手机号来查询这个用户所赚的钱
     *@0param mobile
     */
    public abstract int totalAmountByTel(String mobile);
    
    /**
     * 根据手机号来查询这个用户奖励详细
     * 包含对应的手续费
     *@0param mobile
     */
    public abstract Page<CashDeal> getAwardDetialByTel(Page page,String mobile);
}
