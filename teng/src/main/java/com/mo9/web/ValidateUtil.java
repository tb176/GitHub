package com.mo9.web;

import java.util.regex.Pattern;

/**
 * 正则验证工具
 * @author beiteng
 *
 */
public class ValidateUtil {
	private static final String REG_MOBILE = "^1[0-9]{10}$";
	private static final String REG_PASSWORD = "^.{8,25}$";
	private static final String REG_EMAIL = "^\\w+((-\\w+)|(\\.\\w+)|(:\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

	public static boolean isMobile(String mobile) {
		return Pattern.matches(REG_MOBILE, mobile);
	}

	public static boolean isEmail(String email) {
		return Pattern.matches(REG_EMAIL, email);
	}
	public static boolean isPassword(String password){
		return Pattern.matches(REG_PASSWORD, password);
	}

	public static void main(String[] args) {
		System.out.println(ValidateUtil.isMobile("13636662711"));
		System.out.println(ValidateUtil.isPassword("136366627111as36366627111363"));
	}
}
