package com.mo9.data.cashmere.dto;

/**
 * 提现订单下单参数
 * Created by sun on 16/8/30.
 */
public class CashOutOrderDto {
    /**
     * 提现用户手机号码
     */
    public String mobile;
    /**
     * 提现金额,单位 人民币分
     */
    public int amount;
    /**
     * 手续费
     */
    public int fee;
    /**
     * 提现卡号
     */
    public String cardNo;
    /**
     * 提现银行卡用户名
     */
    public String cardUserName;
    /**
     * 提现银行卡身份证
     */
    public String idCard;
    /**
     * 银行卡绑定的手机号码
     */
    public String bankCardMobile;
    /**
     * 订单备注
     */
    public String remark;
    
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getFee() {
		return fee;
	}
	
	public void setFee(int fee) {
		this.fee = fee;
	}
	
	public String getCardNo() {
		return cardNo;
	}
	
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getCardUserName() {
		return cardUserName;
	}
	
	public void setCardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
	}
	
	public String getIdCard() {
		return idCard;
	}
	
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	public String getBankCardMobile() {
		return bankCardMobile;
	}
	
	public void setBankCardMobile(String bankCardMobile) {
		this.bankCardMobile = bankCardMobile;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
  
}
