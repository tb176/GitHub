package com.mo9.web;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.mortbay.jetty.security.Credential.MD5;

import jxl.common.Logger;

import com.mo9.utils.md5.Md5Encrypt;
import com.mo9.web.GetRequest;



public class HttpLong2shortTest {
private static Logger logger = Logger.getLogger(HttpLong2shortTest.class);
private static final String KEY = "2F2C02B40BF96352";// 可以自定义生成 MD5 加密字符传前的混合 KEY
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String url = "https://github.com/tb176/GitHub/tree/master/teng/src/main/java/com/mo9";
		Long2short(url);
		
	}
	
	
	public static String Long2short(String url) throws IOException{
			//生成短连接
			String reqUrl = "http://980.so/api.php?url="+ URLEncoder.encode(url,"UTF-8");
			logger.info("------转换之前的reqUrl="+reqUrl);
			Map<String,String> map = new HashMap<String,String>();
			String tinyUrl = GetRequest.getRequest(reqUrl,map);
			if(tinyUrl != null && tinyUrl.startsWith("http://980.so/"))
			{
				url = tinyUrl;
			}
			logger.info("------转换之后的url="+url);
			return url;
	}
	

}
