package com.mo9.data.cashmere.dto;

import com.mo9.data.cashmere.bean.CashDeal;

/**
 *
 * 账户流水记账参数
 * Created by sun on 16/8/31.
 */
public class DealAcctingDto {
    /**
     *  账户手机
     */
    public String mobile;
    /**
     *  流水金额 单位 人民币 分 ,只能为正数s
     */
    public int amount;
    /**
     * 流水操作类型
     */
    public CashDeal.CashmereDealType type;
    
    /**
     * 被邀请人手机号(type=register时填充)
     */
    public String targetmobile;
    
    /**
     *  江湖救急订单号(type=repayment时填充)
     */
    public String riskOrder;
    /**
     * 提现订单号(type=cash时填充)
     */
    public String cashOutOrder;
}
