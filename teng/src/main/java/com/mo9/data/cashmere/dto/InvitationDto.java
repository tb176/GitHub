package com.mo9.data.cashmere.dto;

import java.util.Date;

import com.mo9.data.cashmere.bean.CashInvitation.InvitationStatus;

/**
 * 邀请关系记录信息
 * @author beiteng
 *
 */
public class InvitationDto {
	public String selfmobile;// 邀请人手机号
	public String targetmobile;// 被邀请人手机号
	public String paltform;// 被邀请人注册平台
	public String deviceID;// 被邀请人的设备号
	public Date inviteTime;// 被邀请时间
	/**
	 * 两种状态
	 * pending("未邀请成功"),
	 * invited("邀请成功");
	 */
	public InvitationStatus status;// 邀请状态
	public String ip;// 被邀请人填写手机号码时候的ip
	public String field;//预留字段
	public String remark;//备注
	public Date createTime; // 创建时间
	public Date updateTime; // 更新时间
}
