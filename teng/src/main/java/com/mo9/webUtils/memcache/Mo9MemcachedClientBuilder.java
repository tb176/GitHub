/**
 * 
 */
package com.mo9.webUtils.memcache;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MemcachedClient 构造器
 * 
 * @author huangjian
 * 
 */
public class Mo9MemcachedClientBuilder {

	private static Log log = LogFactory.getLog(Mo9MemcachedClientBuilder.class);

	private String servers;

	private boolean failover;

	public void init() {

		// InputStream fisConfig = null;
		// File file = new File(fileUri);
		// if (file.exists()) {
		// try {
		// fisConfig = new FileInputStream(file);
		// } catch (FileNotFoundException e) {
		// log.warn("file not exists!", e);
		// }
		// } else {
		// fisConfig =
		// Mo9MemcachedClientBuilder.class.getResourceAsStream("memcached.properties");
		// }
		// fisConfig =
		// Mo9MemcachedClientBuilder.class.getResourceAsStream("memcached.properties");
		// Properties props = new Properties();
		// if (fisConfig != null) {
		// try {
		// props.load(fisConfig);
		// log.info("memcached.properties加载成功");
		// } catch (Exception e) {
		// log.info("memcached.properties加载失败");
		// throw new
		// IllegalStateException(Mo9MemcachedClientBuilder.class.getName() +
		// "初始化失败", e);
		// }
		// }
		// fileUri = props.getProperty("file.uri");
		// if (fileUri != null) {
		// // 从指定的file加载memcached服务器
		// File file = new File(fileUri);
		// if (file.exists()) {
		// try {
		// fisConfig = new FileInputStream(file);
		// if (fisConfig != null) {
		// try {
		// props.load(fisConfig);
		// log.info(fileUri + "加载成功");
		// } catch (Exception e) {
		// log.info(fileUri + "加载失败");
		// throw new
		// IllegalStateException(Mo9MemcachedClientBuilder.class.getName() +
		// "初始化失败", e);
		// }
		// }
		// } catch (FileNotFoundException e) {
		// log.warn("file not exists!", e);
		// }
		// }
		// }
		// servers = props.getProperty("memcached.servers");
		// failover =
		// Boolean.parseBoolean(props.getProperty("memcached.failover",
		// "true"));
	}

	public Mo9MemcachedClient build() {

		Mo9MemcachedClient mo9Client = null;
		// String servers =
		// DictUtil.getDictNameByCd(DictContants.MEMCACHED_PROPERTIES,
		// DictContants.memcachedPropties.servers);
		// String servers = "192.168.0.230:11211,192.168.0.230:11213";
		if ( StringUtils.isNotBlank(servers) ) {
			try {
				CustomerSerializingTranscoder  transcoder = new CustomerSerializingTranscoder ();
				MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddressMap(servers));
				// builder.setConnectionPoolSize(5);
				builder.setTranscoder(transcoder);
				builder.setFailureMode(failover);// 启用主备,必须设置该属性
				// repcached-2.3.1-1.4.13.patch.gz不支持该模式同步
				// builder.setCommandFactory(new BinaryCommandFactory());
				MemcachedClient mc = builder.build();
				mo9Client = new Mo9MemcachedClient(mc);
			} catch (Exception e) {
				log.error("init MemcachedClient error", e);
			}
		} else {
			log.warn("please set memcache servers!");
		}
		return mo9Client;
	}

	public void setServers(String servers) {

		this.servers = servers;
	}

	public void setFailover(boolean failover) {

		this.failover = failover;
	}
}
