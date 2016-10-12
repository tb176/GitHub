package com.mo9.data.cashmere.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CashInvitation 邀请关系记录
 */
@Entity
@Table(name = "t_cash_invitation", catalog = "riskdb")
public class CashInvitation implements java.io.Serializable {

	private static final long serialVersionUID = 9122844779168863762L;
	
	private long id;
	private String selfmobile;// 邀请人手机号
	private String targetmobile;// 被邀请人手机号
	private String paltform;// 被邀请人注册平台
	private String deviceID;// 被邀请人的设备号
	private Date inviteTime;// 被邀请时间
	private InvitationStatus status;// 邀请状态
	private String ip;// 被邀请人填写手机号码时候的ip
	private String field;//预留字段
	private String remark;//备注
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间

	public static enum InvitationStatus {
		pending("未邀请成功"),
		invited("邀请成功");

		InvitationStatus(String status) {
			this.status = status;
		}

		private String status;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "self_mobile", length = 20)
	public String getSelfmobile() {
		return selfmobile;
	}

	public void setSelfmobile(String selfmobile) {
		this.selfmobile = selfmobile;
	}

	@Column(name = "target_mobile", length = 20)
	public String getTargetmobile() {
		return targetmobile;
	}

	public void setTargetmobile(String targetmobile) {
		this.targetmobile = targetmobile;
	}

	@Column(name = "paltform", length = 20)
	public String getPaltform() {
		return paltform;
	}

	public void setPaltform(String paltform) {
		this.paltform = paltform;
	}

	@Column(name = "deviceID", length = 20)
	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invite_time", length = 19)
	public Date getInviteTime() {
		return inviteTime;
	}

	public void setInviteTime(Date inviteTime) {
		this.inviteTime = inviteTime;
	}

	@Column(name = "status", length = 20)
	@Enumerated(value=EnumType.STRING)
	public InvitationStatus getStatus() {
		return status;
	}

	public void setStatus(InvitationStatus status) {
		this.status = status;
	}

	@Column(name = "ip", length = 20)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 19)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", length = 19)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "field")
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
