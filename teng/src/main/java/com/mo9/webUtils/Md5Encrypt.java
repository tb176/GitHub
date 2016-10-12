/**
 * File: Md5Encrypt.java
 * Description: Md5加密数据
 * Copyright 2010 mo9. All rights reserved
 *  
 */
package com.mo9.webUtils;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mo9.utils.md5.BytesHexTransform;






/**
 * 
 * 用Md5进行数据加密 
 * @author Jacky Zhou
 */
public class Md5Encrypt {

	private static Logger logger = Logger.getLogger(Md5Encrypt.class.getName());

	private static Md5Encrypt md5 = new Md5Encrypt();
	
	private Md5Encrypt(){		
	}

	public static Md5Encrypt getInstance(){
		return md5;
	}
	
	/**
	 * 对所有商户的参数进行加密
	 * @param map 所有将加密的参数
	 * @param privateKey 需要对参数加密的私钥
	 * @return    加密以后得到的字符串对象
	 */
	public static String sign(Map<String, String> params, String privateKey){	
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = "";
//			try {
//				value = URLDecoder.decode((String) params.get(key), "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			value = (String) params.get(key);
			if (key == null || key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")){
				continue;
			}
			content.append(key + "=" + value + "&");
		}
		String linkedContent = content.toString().substring(0, content.lastIndexOf("&"));
		String signcontent = linkedContent + privateKey;		
		return encrypt(signcontent);
		
	}
	
	/**
	 * 对商户传进来的金币选择参数进行签名 
	 * <p>字符串拼接顺序: business + notify_url +  payer_id + merchant_fields + coinsPackage + privateKey</p>
	 * @param business 商户邮箱
	 * @param notify_url  商户Notify_Url 
	 * @param payer_id 玩家在商户系统中的注册id
	 * @param merchant_fields 商户宽展参数
	 * @param coinsPackage 商户传进来的金币数组 : {农民币,欧元,Gamax币}
	 * @param privateKey 商户私钥
	 * @return 签名后的字符串
	 */
	public static String sellerCoinsChoiceSign(String business, String notify_url, String payer_id, String  merchant_fields, String coinsPackage ,String privateKey) {	
		StringBuffer buffer = new StringBuffer();
		buffer.append(business);
		buffer.append(notify_url);
		buffer.append(payer_id);
		buffer.append(merchant_fields);
		buffer.append(coinsPackage);
		buffer.append(privateKey);
		return encrypt(buffer.toString());
	}
	
	/**
	 * 对商户传进来的金币选择参数进行签名 
	 * <p>字符串拼接顺序: business + notify_url +  payer_id + merchant_fields + coinsPackage + privateKey</p>
	 * @param business 商户邮箱
	 * @param notify_url  商户Notify_Url 
	 * @param payer_id 玩家在商户系统中的注册id
	 * @param merchant_fields 商户宽展参数
	 * @param coinsPackage 商户传进来的金币数组 : {农民币,欧元,Gamax币}
	 * @param privateKey 商户私钥
	 * @return 签名后的字符串
	 */
	public static String sellerCoinsPackageSign(String business, String notify_url, String payer_id, String  merchant_fields, String coinsPackage , String currency_code, String privateKey) {	
		StringBuffer buffer = new StringBuffer();
		buffer.append(business);
		buffer.append(notify_url);
		buffer.append(payer_id);
		buffer.append(merchant_fields);
		buffer.append(coinsPackage);
		buffer.append(currency_code);
		buffer.append(privateKey);
		return encrypt(buffer.toString());
	}
	
	/**
	 * 对商户传进来的参数进行签名
	 * <p>字符串拼接顺序: merchantEmail + invoiceNo + amount + privateKey</p>
	 * @param invoiceNo 商户系统中的唯一订单号
	 * @param merchantEmail 商户在mo9中注册的邮箱号
	 * @param amount 此次交易的金额
	 * @param privateKey 商户的私钥
	 * @return 签名后的字符串
	 */
	public static String sellerRequestParamsSign(String invoiceNo, String merchantEmail, String amount, String privateKey){	
		StringBuffer buffer = new StringBuffer();
		buffer.append(merchantEmail);
		buffer.append(invoiceNo);
		buffer.append(amount);
		buffer.append(privateKey);
		return encrypt(buffer.toString());
	}
	
	/**
	 * 对mo9处理完后回传给商户的参数进行签名
	 * <p>字符串拼接顺序:merchantEmail + txNumber + amount + timestamp + privateKey</p>
	 * @param merchantEmail  商户在mo9中注册的邮箱号
	 * @param amount 此次交易的金额
	 * @param privateKey 商户的私钥
	 * @param txNumber 在mo9中生成的唯一交易号
	 * @param timestamp 在mo9中交易完成时生成的时间戳
	 * @return 签名后的字符串
	 */
	public static String gamaxReturnParamsSign(String merchantEmail, String amount, String txNumber, String timestamp, String privateKey){	
		StringBuffer buffer = new StringBuffer();
		buffer.append(merchantEmail);
		buffer.append(txNumber);
		buffer.append(amount);
		buffer.append(timestamp);
		buffer.append(privateKey);
		return encrypt(buffer.toString());
	}
	
	/**
	 * 
	 * @param paytoemail 商家的email
	 * @param payerId    玩家在商家系统中唯一的id
	 * @param amount     交易的金额
	 * @param currency   交易的货币类型
	 * @param tradeNo    交易的订单号
	 * @param key        商家的签名key
	 * @return           MD5签名
	 */
	public static String getSign(String paytoemail, String payerId, String amount, String currency, String tradeNo, String key){	
		StringBuffer buffer = new StringBuffer();
		buffer.append(paytoemail);
		buffer.append(payerId);
		buffer.append(amount);
		buffer.append(currency);
		buffer.append(tradeNo);
		buffer.append(key);
		return encrypt(buffer.toString());		
	};

	
	
	
	
	/**
	 * 对传入的字符串数据进行MD5加密
	 * @param source	字符串数据
	 * @return   加密以后的数据
	 */
	public static String encrypt(String source) {
		MessageDigest md = null;		
		byte[] bt = null;
		try {
			bt = source.getBytes("UTF-8");
			md = MessageDigest.getInstance("MD5");
			md.update(bt);
			return BytesHexTransform.bytesToHexString(md.digest()); 
		} catch (NoSuchAlgorithmException e) {
			logger.error("非法摘要算法", e);
			throw new RuntimeException(e);	
		}catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对传入的字符串数据进行MD5加密
	 * @param source	字符串数据
	 * @return   加密以后的数据
	 */
	public static String encryptGBK(String source) {
		MessageDigest md = null;		
		byte[] bt = null;
		try {
			bt = source.getBytes("GBK");
			md = MessageDigest.getInstance("MD5");
			md.update(bt);
			return BytesHexTransform.bytesToHexString(md.digest()); 
		} catch (NoSuchAlgorithmException e) {
			logger.error("非法摘要算法", e);
			throw new RuntimeException(e);	
		}catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		String key = "15395182106"+"87BAF0B0229047BF985ED56281096FD8"+"ios"+"cashmere";
		String sign = Md5Encrypt.encrypt(key);
		System.out.println(sign);
		//String url = "amount=100.00&app_init_credit=5.00&app_init_item=35mojo&currency=CNY&invoice=205906236_1348528248399_cn_1&item_name=MOJO1000&lc=CN&pay_to_email=liyi@camel4u.com&payer_id=205906236&req_amount=100.00&req_currency=CNY&trade_no=GJACJRHPBDFRQOBD&trade_status=TRADE_SUCCESS687EBF887E15515F911E915FAD9EC154";
		/*String url4 = "amount=1&app_init_credit=0&currency=CNY&extra_param=00000102&invoice=24583f79-98f6-451d-b3b9-8989bc5307e1&item_name=测试商品&lc=CN&notify_url=http://wap.wan8.mobi/interface/mo9/mo9.php&payer_id=9774d56d682e549c&pay_to_email=merchant@huayu.com&return_url=http://wap.wan8.mobi/interface/mo9/mo9.php&version=2"
			&sign=5380A75C115F6D3BF3CFA3DE86760473*/
		// String url2 = "amount=3.00&app_init_credit=0&currency=CNY&extra_param=AFABDAF&invoice=12313131&item_name=游戏商品&lc=CN&pay_to_email=merchant@huayu.com&payer_id=12331&req_amount=3.00&req_currency=CNY&trade_no=BACDMADAF&trade_status=TRADE_SUCCESS035B8ED1F47668B84F7A2D91EAF311E1";
		
		
		//String mysig = Md5Encrypt.encrypt("amount=3.00&app_init_credit=0&currency=CNY&extra_param=AFABDAF&invoice=12313131&item_name=游戏商品&pay_to_email=merchant@huayu.com&payer_id=12331&req_amount=3.00&req_currency=CNY&trade_no=BACDMADAF&trade_status=TRADE_SUCCESS035B8ED1F47668B84F7A2D91EAF311E1");
		
		//System.out.println(URLDecoder.decode("6%E5%85%83%E7%9A%84%E9%81%93%E5%85%B7%2C02", "UTF-8"));
		
		//String mysig = Md5Encrypt.encrypt("14000011111GDACJJERDRRAJOOA10001BBD886460827015E5D605ED44252251");
		//&sign=0091A4EE2A572844EEE36ED73F619401
		//String mysig = Md5Encrypt.encrypt(url);
		//pay_to_email=merchant@morefuntek.com&version=2&notify_url=http://114.80.125.146/ChargeCenter/ump/notifyBank.jsp&invoice=120926175958117990&payer_id=17990&amount=100&lc=CN&currency=CNY&item_name=%E6%B8%B8%E6%88%8F%E5%B8%81&app_id=11&app_init_credit=5&app_init_item=%E6%B8%B8%E6%88%8F%E5%B8%81&sign=0502AFEC011B7AC9D728B551D9ACA6AC
		//String url = "amount=100&app_id=11&app_init_credit=5&app_init_item=游戏币&currency=CNY&invoice=120926182716111752&item_name=游戏币&lc=CN&notify_url=http://114.80.125.146/ChargeCenter/recvcop/notifyMojiu.jsp&pay_to_email=merchant@morefuntek.com&payer_id=11752&version=2A865B734BF1D22F4E0FAAD4B7F420186";
		
		//String url   =  "amount=1&app_init_credit=10&currency=CNY&extra_param=11112202&invoice=82f63018-09d6-4435-bb22-2d3ddf0242fa&item_name=6元的道具,02&lc=CN&notify_url=http://wap.wan8.mobi/interface/mo9/mo9.php?&pay_to_email=merchant@huayu.com&payer_id=86dd0352ba9dd5d1&version=21CA396EBB5C1F889026546C123A4111B";
		//String url = "amount=1&app_id=14&app_init_credit=5&app_init_item="+URLDecoder.decode("%E6%B8%B8%E6%88%8F%E5%B8%81", "UTF-8")+"&currency=USD&invoice=120927204429148669&item_name="+URLDecoder.decode("%E6%B8%B8%E6%88%8F%E5%B8%81", "UTF-8")+"&lc=CN&notify_url=http://fee.kter.com.cn:8018/ChargeCenter/recvcop/notifyMojiu.jsp&pay_to_email=merchant@morefuntek.com&payer_id=158&version=238D3F72F4229772F64775FC69871FBC7";
		
		
		//String mysig = Md5Encrypt.encrypt("123444GJACGCLFPPJJEOBJ1001BBD886460827015E5D605ED44252251");
		
	    
//		String mysig = Md5Encrypt.encrypt("amount=1.00&app_id=001&currency=CNY&extra_param=&invoice=9750039000602921khp9bpkit&item_name=qq&lc=CN&pay_to_email=zifu.pan@yunyoyo.cn&payer_id=47852&req_amount=1.00&req_currency=CNY&trade_no=GAADBRKOHQFFQOAC&trade_status=TRADE_SUCCESS7e310a3096db4cf78872be7f74765a42");
//		//&&&&&sign=464d7fd78a3a844a1b59e4963525dfbe
//		System.out.println(mysig);
		
		
		
		
		
		/*Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("amount", "1");
		params.put("app_init_credit", "10");
		params.put("amount", "1");
		params.put("app_init_credit", "10");
		params.put("amount", "1");
		params.put("app_init_credit", "10");
		params.put("amount", "1");
		params.put("app_init_credit", "10");
		params.put("amount", "1");
		params.put("app_init_credit", "10");
		params.put("amount", "1");
		params.put("app_init_credit", "10");
		params.put("amount", "1");
		params.put("app_init_credit", "10");*/
	}
}
