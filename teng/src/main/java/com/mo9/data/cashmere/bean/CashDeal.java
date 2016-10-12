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
import javax.persistence.Transient;

/**
 * CashDeal 赠送有礼交易流水
 */
@Entity
@Table(name = "t_cash_deal", catalog = "riskdb")
public class CashDeal implements java.io.Serializable {

	private static final long serialVersionUID = -5436900547535940317L;
	
	private long id;
	private String selfmobile;//邀请人手机号
	private String targetmobile;//被邀请人手机号(type=register时填充)
	private Integer amount;//交易金额
	private CashmereDealType dealType;//流水类型
	private Integer beforeBalance;//操作前用户账户余额
	private String riskOrder;//江湖救急订单号(type=repayment时填充)
	private String cashOutOrder;//提现订单号(type=cash时填充)
	private String field;//预留字段
	private String remark;//备注
	private Date createTime; //创建时间
	private Date updateTime; //更新时间
	
	private String targetMsg;//目标信息
	private String dealMsg;//目标状态
	
	public static enum CashmereDealType{
		register("注册奖励"),
		verify("过审奖励"),
		repayment("还款奖励"),
		cash("提取余额"),
		reverse("纠正冲正"),
		fee("手续费");
		
		CashmereDealType(String dealType){
			this.dealType = dealType;
		}
		
		private String dealType;
		public String getDealType() {
			return dealType;
		}

		public void setDealType(String dealType) {
			this.dealType = dealType;
		}
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
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

	@Column(name = "amount", length = 20)
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Column(name = "cashmere_dealtype", length = 20)
	@Enumerated(value=EnumType.STRING)
	public CashmereDealType getDealType() {
		return dealType;
	}
	
	public void setDealType(CashmereDealType dealType) {
		this.dealType = dealType;
	}

	@Column(name = "before_balance", length = 20)
	public Integer getBeforeBalance() {
		return beforeBalance;
	}

	public void setBeforeBalance(Integer beforeBalance) {
		this.beforeBalance = beforeBalance;
	}

	@Column(name = "risk_order", length = 20)
	public String getRiskOrder() {
		return riskOrder;
	}

	public void setRiskOrder(String riskOrder) {
		this.riskOrder = riskOrder;
	}

	@Column(name = "cashout_order", length = 20)
	public String getCashOutOrder() {
		return cashOutOrder;
	}

	public void setCashOutOrder(String cashOutOrder) {
		this.cashOutOrder = cashOutOrder;
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

	@Transient
	public String getTargetMsg() {
		return targetMsg;
	}

	public void setTargetMsg(String targetMsg) {
		this.targetMsg = targetMsg;
	}

	@Transient
	public String getDealMsg() {
		return dealMsg;
	}

	public void setDealMsg(String dealMsg) {
		this.dealMsg = dealMsg;
	}
	
}
