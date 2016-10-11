/**  
 * Project Name : commonutil  
 * File Name	: HttpClient.java  
 * Package Name : com.mo9.commonutil.web  
 * Create Date  : 2015-4-26下午12:27:12  
 *
 * Copyright ©2011 moKredit Inc. All Rights Reserved
 */
package com.mo9.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * <p>TITLE</p>
 * 
 * <p>DESC</p>
 * 
 ******************************************************** 
 * Date				Author 		Changes 
 * 2015-4-26		Eric Cao	创建
 ******************************************************** 
 */
public class HttpClient
{
    private static Logger log = Logger.getLogger(HttpClient.class);
    
    /** 
     * POST方式提交表单. 
     *  
     * @param url 待处理的URL
     * @param parmas 表单参数.
     */
    public static String postForm(String url, Map<String, String> parmas, Boolean... useTLSv2)
    {
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = getClient(url, useTLSv2);
        UrlEncodedFormEntity uefEntity;
        CloseableHttpResponse response = null;
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        if (parmas != null)
        {
            for (String key : parmas.keySet())
            {
                formparams.add(new BasicNameValuePair(key, parmas.get(key)));
            }
        }
        
        try
        {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            switch (statusLine.getStatusCode())
            {
                case 200:
                    return EntityUtils.toString(entity, "UTF-8");
                case 302:
                    for (Header h : response.getAllHeaders())
                    {
                        if ("Location".equalsIgnoreCase(h.getName()))
                        {
                            url = h.getValue();
                            //跟踪重定向处理.
                            return loadUrl(url);
                        }
                    }
                    break;
                default:
                    throw new RuntimeException("暂时不支持该HTTP应答码处理.code:" + statusLine.getStatusCode());
            }
        } catch (Exception e)
        {
            log.warn("HTTP表单提交失败.URL:"+url, e);
            throw new RuntimeException("HTTP表单提交失败." ,e);
        } finally
        {
            // 关闭连接,释放资源    
            try
            {
                response.close();
                httpclient.close();
            } catch (Exception e)
            {
            }
        }
        //默认返回空串.
        return null;
    }
    
    
    
    
    private static CloseableHttpClient createTrustAllSSLClient() {
    	try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
				@Override
				public boolean isTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
					return true;
				}
            }).build();
            
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        	
            return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(getRequestConfig()).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return  HttpClients.createDefault();

	}




	/**
     * 用GET方法加载URL. 
     * @param url
     * @return
     */
    public static String loadUrl(String url, Boolean... useTLSv2)
    {
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = getClient(url, useTLSv2);
        CloseableHttpResponse response = null;
        HttpGet httpget = new HttpGet(url);
        try
        {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            switch (statusLine.getStatusCode())
            {
                case 200:
                    /**输出消息头*/
                    return EntityUtils.toString(entity, "UTF-8");
                case 302:
                    for (Header h : response.getAllHeaders())
                    {
                        if ("Location".equalsIgnoreCase(h.getName()))
                        {
                            url = h.getValue();
                            //跟踪重定向处理.
                            return loadUrl(url);
                        }
                    }
                    break;
                case 404:
                	log.warn("URL404:"+url);
                    break;
                default:
                    throw new RuntimeException(url + "暂时不支持该HTTP应答码处理.code:" + statusLine.getStatusCode());
            }
        } catch (Exception e)
        {
            log.warn("HTTP表单提交失败.URL:"+url, e);
            throw new RuntimeException("HTTP表单提交失败." ,e);
        } finally
        {
            // 关闭连接,释放资源    
            try
            {
                response.close();
                httpclient.close();
            } catch (Exception e)
            {
            }
        }
        //默认返回空串.
        return null;
    }
    
    public static void httpReqUrl(String url,String json, Boolean... useTLSv2)  
            throws ClientProtocolException, IOException {
   
        CloseableHttpClient httpClient = getClient(url, useTLSv2);
  
        HttpPost method = new HttpPost(url);
                  
        StringEntity entity = new StringEntity(json,"utf-8");//解决中文乱码问题    
        entity.setContentEncoding("UTF-8");    
        entity.setContentType("application/json");    
        method.setEntity(entity);
        HttpResponse result = httpClient.execute(method);  
        String resData = EntityUtils.toString(result.getEntity());  
              
    }
    
    /** 
     * POST方式提交jsons数据. 
     *  
     * @param url 待处理的URL
     * @param json 数据参数.
     */
    public static String postJson(String url, String json, Boolean... useTLSv2){
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = getClient(url, useTLSv2);
        StringEntity uefEntity = null;
        CloseableHttpResponse response = null;
        HttpPost httppost = new HttpPost(url);
        
        try{
        		    
            uefEntity = new StringEntity(json, "UTF-8"); 
            uefEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            httppost.setEntity(uefEntity);
            response = httpclient.execute(httppost);
            
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            switch (statusLine.getStatusCode()){
                case 201:
                case 200:
                    return EntityUtils.toString(entity, "UTF-8");
                default:
                    throw new RuntimeException("暂时不支持该HTTP应答码处理.code:" + statusLine.getStatusCode());
            }
        } catch (Exception e){
            log.warn("json数据提交失败.URL:"+url, e);
            throw new RuntimeException("json数据提交失败." ,e);
        } finally{
            // 关闭连接,释放资源    
            try{
                response.close();
                httpclient.close();
            } catch (Exception e){}
        }
    }
    
    /** 
     * HTTP DELETE. 
     *  
     * @param url 待处理的URL
     */
    public static String httpDelete(String url, Boolean... useTLSv2){
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = getClient(url, useTLSv2);
        CloseableHttpResponse response = null;
        HttpDelete httpdelete = new HttpDelete(url);
        
        try{
            response = httpclient.execute(httpdelete);
            
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            switch (statusLine.getStatusCode()){
                case 200:
                    return EntityUtils.toString(entity, "UTF-8");
                default:
                    throw new RuntimeException("暂时不支持该HTTP应答码处理.code:" + statusLine.getStatusCode());
            }
        } catch (Exception e){
            log.warn("json数据提交失败.URL:"+url, e);
            throw new RuntimeException("json数据提交失败." ,e);
        } finally{
            // 关闭连接,释放资源    
            try{
                response.close();
                httpclient.close();
            } catch (Exception e){}
        }
    }    
    
    /**
     * 下载文件
     * @param url  
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static byte[] downloadFile(String url, Boolean... useTLSv2) {

    	CloseableHttpClient httpclient = getClient(url, useTLSv2);
        HttpGet httpget = new HttpGet(url);
        //InputStream in = null;
        //ByteArrayOutputStream baos = null;
        byte[] ret = null;
        try {
            HttpResponse response = httpclient.execute(httpget);
            
            ret = getFileFromResponse(response, httpclient);
        } catch(Throwable t){
        	log.error(t);
        } finally {
            try {
                //in.close();
				//baos.close();
	            httpclient.close();
			} catch (IOException e) {}
        }
        
        return ret;
    }
    
    /**
     * 下载图片
     * @param url 
     * @param cookies 动态验证码图片需要先通过getCookies(url)获得cookies
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static byte[] downloadImageFile(String url, Map<String, BasicClientCookie> cookies, Boolean... useTLSv2) {

    	CloseableHttpClient httpclient = getClient(url, useTLSv2);
        HttpGet httpget = new HttpGet(url);
        
        BasicCookieStore bcs = new BasicCookieStore();
        for(Entry<String, BasicClientCookie> ent : cookies.entrySet()){
        	bcs.addCookie(ent.getValue());
        }

        BasicHttpContext bcc = new BasicHttpContext();
        bcc.setAttribute(HttpClientContext.COOKIE_STORE, bcs);
        
        HttpResponse response;
        byte[] ret = null;
        //InputStream in = null;
		try {
			response = httpclient.execute(httpget, bcc);	        
			
			//in = getResponseStream(response);	        
	        //BufferedImage bi = ImageIO.read(in); 
	        //ret = ((DataBufferByte) bi.getData().getDataBuffer()).getData();
			
			ret = getFileFromResponse(response, httpclient);
		} catch (Throwable e) {
			log.error(e);
		} finally {
            try {
                //in.close();
				httpclient.close();
			} catch (IOException e) {}
        }
        return ret;
    }
	
	private static SSLContext getTLSV2SSLContext()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException{
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        } };
		//
        // "TLSv1.2"不正确的话，会出现"Received fatal alert: handshake_failure"异常
        // Ref：http://logback.qos.ch/manual/usingSSL_ja.html
		//
        SSLContext sc = SSLContext.getInstance("TLSv1.2");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
		return sc;
	}
	
	private static CloseableHttpClient createTLSV2SSLClient() {
    	try {
            SSLContext sslContext = getTLSV2SSLContext();            
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        	
            return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(getRequestConfig()).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return  HttpClients.createDefault();
	}
	 
	public static Map<String, BasicClientCookie> getCookies(String url, Boolean... useTLSv2){
		Map<String, BasicClientCookie> ret = new HashMap<String, BasicClientCookie>();
		CloseableHttpClient httpclient = getClient(url, useTLSv2);
		HttpGet getMethod = new HttpGet(url);
		try {
			CookieStore cookieStore = new BasicCookieStore();
			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
			
			HttpResponse response = httpclient.execute(getMethod, localContext);
			List<Cookie> cookies = cookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				Cookie ck = cookies.get(i);
				log.debug("get response cookie [" + ck.getName() + ":" + ck.getValue() + "]");
				BasicClientCookie bcc = new BasicClientCookie(ck.getName(), ck.getValue());
				bcc.setDomain(ck.getDomain());
				bcc.setPath(ck.getPath());
				bcc.setExpiryDate(ck.getExpiryDate());
				bcc.setSecure(ck.isSecure());
			    ret.put(ck.getName(), bcc);
			}			
		}catch(Throwable t){
			log.error(t);
		}
		return ret;
	}
	
	private static InputStream getResponseStream(HttpResponse response){
		HttpEntity entity = response.getEntity(); 
		InputStream in = null;
		try {
			in = entity.getContent();
	        if (entity instanceof GzipDecompressingEntity) { 
	        	//GzipDecompressingEntity gde = (GzipDecompressingEntity)entity;
	        	//in = gde.getContent();
	        }
		} catch (Throwable e) {
			log.error(e);
		}
        return in;
	}
	
	private static byte[] getFileFromResponse(HttpResponse response, CloseableHttpClient httpclient) throws IOException{
        InputStream in = getResponseStream(response);        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] ret = null;
        
        try {
            int l = -1;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
            	baos.write(tmp, 0, l);
            }
            ret = baos.toByteArray();
        } finally {
            in.close();
            baos.close();
        }
        return ret;
	}
	
	public static CloseableHttpClient getClient(String url, Boolean... useTLSv2){
		return url.startsWith("https")? 
			   ((useTLSv2 != null && useTLSv2.length > 0 && useTLSv2[0])?createTLSV2SSLClient() : createTrustAllSSLClient()) : 
			   HttpClientBuilder.create().setDefaultRequestConfig(getRequestConfig()).build();
	}
	
	private static RequestConfig getRequestConfig(){
		return RequestConfig.custom()
    		    .setSocketTimeout(-1)
    		    .setConnectTimeout(-1)
    		    .setConnectionRequestTimeout(-1)
    		    .setStaleConnectionCheckEnabled(true)
    		    .build();
	}
    
}
