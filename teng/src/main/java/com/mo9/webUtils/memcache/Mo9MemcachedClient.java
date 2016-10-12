/**
 * 
 */
package com.mo9.webUtils.memcache;

import java.io.IOException;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author huangjian
 * 
 */
public class Mo9MemcachedClient {
	private static Log log = LogFactory.getLog(Mo9MemcachedClient.class);
	private static int EXPIRE = 24 * 60 * 60;// 24小时,xmemcached对应的是秒
	public static int EXPIRE_5M = 5 * 60;// 5分钟,xmemcached对应的是秒
	public static int EXPIRE_1M = 1 * 60; // 1分钟,xmemcached对应的是秒
	private MemcachedClient mc;

	/** 获取原始client */
	public MemcachedClient getClient() {
		return mc;
	}

	public Mo9MemcachedClient(MemcachedClient client) {
		mc = client;
	}

	public <T> T get(final String key) {
		try {
			return mc.get(key);
		} catch (Exception e) {
			log.error("memcached get error:key=" + key + "|" + e.getMessage());
		}
		return null;
	}

	public boolean replace(final String key, final Object value, int timeout) {
		try {
			return mc.replace(key, timeout, value);
		} catch (Exception e) {
			log.error("memcached replace error:key=" + key, e);
		}
		return false;
	}

	public boolean replace(final String key, final Object value) {
		return replace(key, value, EXPIRE);
	}

	public boolean add(final String key, final Object value, int timeout) {
		try {
			return mc.add(key, timeout, value);
		} catch (Exception e) {
			log.error("memcached add error:key=" + key, e);
		}
		return false;
	}

	public boolean add(final String key, final Object value) {
		return add(key, value, EXPIRE);
	}

	public boolean set(final String key, final Object value, int timeout) {
		try {
			return mc.set(key, timeout, value);
		} catch (Exception e) {
			log.error("memcached set error:key=" + key, e);
		}
		return false;
	}

	public boolean set(final String key, final Object value) {
		return set(key, value, EXPIRE);
	}

	public boolean delete(final String key) {
		try {
			return mc.delete(key);
		} catch (Exception e) {
			log.error("memcached delete error:key=" + key, e);
		}
		return false;
	}

	public void shutdown() throws IOException {
		mc.shutdown();
	}
	
	/**
	 * 如无特殊说明，一般格式为 前缀+用户手机号
	 * @author lma
	 *
	 */
	public static class MemcachedKey{
		/** 老用户提交订单金额*/
		public static final String submitOldAmount = "submitOldAmount_";
		/** 老用户提交订单服务费金额*/
		public static final String submitOldCostAmount = "submitOldCostAmount_";
		/** 老用户提交订单天数*/
		public static final String submitOldDays = "submitOldDays_";
		/** 老用户提交订单免息券Id*/
		public static final String submitOldCouponId = "submitOldCouponId_";
		/** 老用户提交订单设备Id*/
		public static final String submitOldDeviceId = "submitOldDeviceId_";
		
		/** 芝麻信用分，用于后台信审显示, 格式为前缀+用户ID*/
		public static final String zmxyScore = "zmscore_";
		/** 芝麻信用授权状态*/
		public static final String zmxy = "zmxy_";
		/** 聚信立报告摘要，用于后台信审显示*/
		public static final String jxlJson = "jxlJson_";
		/** mo9爬虫报告摘要，用于后台信审显示*/
		public static final String mo9crawler = "mo9crawler";
		/** 用户命中芝麻信用反欺诈*/
		public static final String zmxyIvsHit = "zmxyIvsHit";
		
		/** 已拒绝用户*/
		public static final String rejectRiskBuyer = "rejectRiskBuyer";
		/** 用户正处于复审通过中*/
		public static final String passingRiskBuyer = "passingRiskBuyer";
		/** 用户正处于取消申请中, 格式为前缀+用户ID*/
		public static final String cancelRiskBuyer = "cancelRiskBuyer";
		/** 优质老用户*/
		public static final String riskBuyerBetter = "riskBuyerBetter";
		/** 用户正在下单*/
		public static final String orderingRiskBuyer = "orderingRiskBuyer";
		
		/** 订单已被延期(还清), 格式为前缀+订单dealcode*/
		public static final String payoffRiskOrderByDelay = "payoffRiskOrderByDelay";
	}
}
