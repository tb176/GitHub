package com.mo9.data.cashmere.service;

import java.util.Map;

import com.mo9.data.cashmere.bean.CashOutOrder;
import com.mo9.data.cashmere.dto.CashOutOrderDto;

/**
 *  提现订单处理引擎
 * Created by sun on 16/8/30.
 */
public interface ICashOutOrderEngine {
    /**
     *
     *  提现订单下单方法
     *   业务描述
     *   1.检查参数
     *   2.生成订单号
     *   3.新建提现订单
     *   4.返回订单号
     *
     * @return 订单号
     * 如有错误,抛出RuntimeException
     */
    public Map<String, String> generateOutOrder(CashOutOrderDto dto,Long id);

    /**
     * 处理提现
     * 业务描述
     * 1.获取订单,并检查订单参数,如订单状态必须为pending
     * 2.检查用户累计放款金额达到上限,则订单状态设置为reject
     * 3.扣款,并将订单状态置为paying
     * 注意,扣款和订单状态变更必须在同一事务中被完成,并且不能因为放款失败而被回滚
     * @param dealcode 订单
     * 如有错误,抛出RuntimeException
     */
    public  void pay(String dealcode);

	/**
	 * 提现成功结果通知
	 */
    public void successed(String dealcode);

	/**
	 * 提现失败结果通知
	 */
    public void failed(String dealcode);
    
    /**
	 * 根据dealcode查CashOutOrder
	 */
    public  CashOutOrder getCashOutOrderByDealcode(String dealCode);

}
