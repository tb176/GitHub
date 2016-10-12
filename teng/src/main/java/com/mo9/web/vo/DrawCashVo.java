package com.mo9.web.vo;

public class DrawCashVo {
	/**
	 * 提现金额
	 */
	public Integer amount;
	
	/**
	 * 持卡人
	 */
	public String name;

	/**
	 * 身份证
	 */
	public String idCardNo;
	
	/**
	 * 银行卡号
	 */
	public String bankCardNo;
	
	/**
	 * 预留银行手机号
	 */
	public String mobile;
	
	/**
	 * 手续费
	 */
	public Integer fee;
	
	/**
	 * 会员手机号
	 */
	public String userMobile;
	
	/**
	 * token防止重复提交
	 */
	private String token;
	
	/**
	 * 短信验证码
	 * @return
	 */
	private String pinCode;
	
	/**
	 * 签名
	 */
	private String sign;
	
	/**
	 * 图形验证码
	 */
	private String validateCode;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

}
