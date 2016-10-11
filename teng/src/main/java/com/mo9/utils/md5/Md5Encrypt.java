/**
 * File: Md5Encrypt.java
 * Description: Md5加密数据
 * Copyright 2010 GamaxPay. All rights reserved
 *  
 */
package com.mo9.utils.md5;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 
 * 用Md5进行数据加密
 * 
 * @author tengb
 */
public class Md5Encrypt {

	private static Md5Encrypt md5 = new Md5Encrypt();

	private Md5Encrypt() {
	}

	public static Md5Encrypt getInstance() {
		return md5;
	}

	/**
	 * 对传入的字符串数据进行MD5加密
	 * 
	 * @param source
	 *            字符串数据
	 * @return 加密以后的数据
	 */
	public static String encrypt(String source) {
		MessageDigest md = null;
		byte[] bt = source.getBytes();
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(bt);
			return BytesHexTransform.bytesToHexString(md.digest());
		} catch (NoSuchAlgorithmException e) {

			throw new RuntimeException(e);
		}
	}

	/**
	 *江湖救急加密
	 *ascii码排序  拼接键值对  追加privateKey
	 * @param map
	 *            所有将加密的参数
	 * @param privateKey
	 *            需要对参数加密的私钥
	 * @return 加密以后得到的字符串对象
	 */
	public static String FastLoanSign(Map<String, String> params, String privateKey) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);
			if (key == null || key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			content.append(key + "=" + value + "&");
		}
		String linkedContent = content.toString().substring(0,
				content.lastIndexOf("&"));
		String signcontent = linkedContent + privateKey;
		try {
			signcontent = URLDecoder.decode(signcontent, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encrypt(signcontent);

	}

	public static void main(String[] args) {

		String mysig = Md5Encrypt.encrypt("hello123");
		System.out.println(mysig);
	}
}
