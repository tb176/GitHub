package com.mo9.data.cashmere.service;

import java.util.List;

import com.mo9.data.cashmere.bean.CashInvitation;
import com.mo9.data.cashmere.bean.CashInvitation.InvitationStatus;

public interface ICashInvitationService {
	/**
	 * 根据邀请人手机号查询邀请关系
	 * @param mobile
	 * @return
	 */
	public List<CashInvitation> getInvitationBySelfMobile(String mobile);
	
	/**
	 * 根据邀请人手机号查询邀请关系并且为invited
	 * @param mobile
	 * @return
	 */
	public List<CashInvitation> getSuccessInvitationBySelfMobile(String mobile);
	
	/**
	 * 根据被邀请人手机号查询邀请关系
	 * @param mobile
	 * @return
	 */
	public CashInvitation getInvitationByTargetMobile(String mobile);
	
	
	/**
	 * 保存邀请人和被邀请人关系
	 */
	public void addInvitation(CashInvitation cashInvitation);
	/**
	 * 更新邀请人和被邀请人关系
	 * @param cashInvitation
	 */
	public void updateInvitation(CashInvitation cashInvitation);
	/**
	 * 根据ip和状态查询邀请列表
	 * @param ip
	 * @param status
	 * @return
	 */
	public List<CashInvitation> getSameIpInvitations(String selfMobile,String ip,InvitationStatus status);
}
