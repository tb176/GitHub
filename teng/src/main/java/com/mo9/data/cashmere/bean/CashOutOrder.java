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
 * CashOutOrder 提现订单
 */
@Entity
@Table(name = "t_cash_out_order", catalog = "riskdb")
public class CashOutOrder implements java.io.Serializable {
	
	private static final long serialVersionUID = -1555204140039376798L;
	
	private long id;
	private String mobile;//提现人的手机号码
	private String openBank;//开户行
	private String dealcode;//cashOutOrder订单号
	private String bankCardMobile;//银行卡预留手机号码
	private String idCardNo;//身份证号
	private String cardUserName;//姓名
	private String cardNo;//银行卡号
	private Integer amount;//提现金额,单位分
	private Integer fee;//手续费，分
	private Date cashTime;//提现时间
	private String field;// 预留字段
	private String payResult;//下单返回的结果
	private String notifyResult;//异步通知结果
	private String remark;// 备注
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	private CashOutOrderStatus status;//订单状态
	private Long bankCardInfoId;//卡列表的id

	public static enum CashOutOrderStatus {
		pending("待处理"),
		submit("已提交"),
		reject("拒绝"),
		success("成功"),
		fail("失败"),
		cancel("冲正");

		CashOutOrderStatus(String displayName) {
			this.displayName = displayName;
		}

		private String displayName;

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
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

	@Column(name = "mobile",length = 20 , nullable = false)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "openBank")
	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	@Column(name = "dealcode", length = 40,unique = true, nullable = false)
	public String getDealcode() {
		return dealcode;
	}

	public void setDealcode(String dealcode) {
		this.dealcode = dealcode;
	}
	
	@Column(name = "idCardNo",length = 20,nullable = false)
	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	@Column(name = "cardUserName",length = 100,nullable = false)
	public String getCardUserName() {
		return cardUserName;
	}

	public void setCardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
	}

	@Column(name = "cardNo",length = 50,nullable = false)
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Enumerated(EnumType.STRING)
	@Column(name= "status",nullable = false)
	public CashOutOrderStatus getStatus() {
		return status;
	}

	public void setStatus(CashOutOrderStatus status) {
		this.status = status;
	}

	@Column(name = "bankCardMobile", length = 30,nullable = false)
	public String getBankCardMobile() {
		return bankCardMobile;
	}

	public void setBankCardMobile(String bankCardMobile) {
		this.bankCardMobile = bankCardMobile;
	}

	@Column(name = "amount", length = 40,nullable = false)
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Column(name = "fee", length = 40,nullable = false)
	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cash_time", length = 19)
	public Date getCashTime() {
		return cashTime;
	}

	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
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
	
	@Column(name = "payResult")
	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	@Column(name = "notifyResult")
	public String getNotifyResult() {
		return notifyResult;
	}

	public void setNotifyResult(String notifyResult) {
		this.notifyResult = notifyResult;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "bankCardInfoId")
	public Long getBankCardInfoId() {
		return bankCardInfoId;
	}

	public void setBankCardInfoId(Long bankCardInfoId) {
		this.bankCardInfoId = bankCardInfoId;
	}

}
