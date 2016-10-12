package com.mo9.data.cashmere.dao;

import java.util.List;

import com.mo9.data.cashmere.base.IBaseDao;
import com.mo9.data.cashmere.bean.CashInvitation;
/**
 * 邀请有礼为邀请者计算奖励接口
 * @author beiteng
 *
 */
public interface ICashmereActionHookDao extends IBaseDao<CashInvitation>{
	/**
	 * 根据手机号码获取用户邀请关系记录---手机号为被邀请人手机号码
	 * @param tel
	 * @return
	 */
	public CashInvitation getUserByTel(String tel);
	/**
	 * 根据被邀请人的设备号查询用户邀请关系记录
	 * @param deviceId
	 * @return
	 */
	public List<CashInvitation> getUserByDeviceId(String deviceId);
	
	/**
	 * 根据被邀请人手机号码更新记录
	 * @param cashInvitation
	 */
	public void updateCashInvitation(CashInvitation cashInvitation);
	
	
}
