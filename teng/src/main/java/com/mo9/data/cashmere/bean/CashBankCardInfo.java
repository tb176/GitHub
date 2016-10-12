package com.mo9.data.cashmere.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CashBankCardInfo 绑卡记录
 */
@Entity
@Table(name = "t_cash_bankcard_info", catalog = "riskdb")
public class CashBankCardInfo implements java.io.Serializable {
	
	private static final long serialVersionUID = -6768912980422975046L;
	
	private long id;
	private String mobile;//赠送有礼用户手机号
	private String cardMobile;//银行卡预留手机号码
	private String idCardNum;//身份证号
	private String cardUserName;//姓名
	private String cardNum;//银行卡号
	private boolean enable;//是伐有效
	private String field;//预留字段
	private String remark;//备注
	private Date createTime; //创建时间
	private Date updateTime; //更新时间
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "mobile", length = 20)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "card_mobile", length = 20)
	public String getCardMobile() {
		return cardMobile;
	}
	public void setCardMobile(String cardMobile) {
		this.cardMobile = cardMobile;
	}
	
	@Column(name = "idcard_num", length = 40)
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	
	@Column(name = "card_username", length = 40)
	public String getCardUserName() {
		return cardUserName;
	}
	public void setCardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
	}
	
	@Column(name = "card_num", length = 40)
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	@Column(name = "enable")
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
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
	
}
