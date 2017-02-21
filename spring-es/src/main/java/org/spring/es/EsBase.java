package org.spring.es;

import java.net.InetSocketAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * es学习
 * 
 * #es和关系数据库的比较
	关系型数据库 -> 索引
	关系型数据表 -> 类型
	关系型数据项 -> 文档
	字段 -> 字段
 * 
 * @author gaotingping
 *
 * 2017年2月4日 下午2:20:24
 */
public class EsBase {

	protected Client c = getClient();
	
	protected String INDEX_NAME="index_test_1";
	
	/**
	 * es初始化
	 *
	 * @return
	 */
	public static Client getClient() {

		TransportClient client = null;

		try {
			Settings settings = Settings.builder().put("cluster.name", "elasticsearch") // 名称
					.put("client.transport.sniff", true) // 自动嗅探整个集群的状态
					.put("client.transport.ignore_cluster_name", true).put("client.transport.ping_timeout", "5s")
					.put("client.transport.nodes_sampler_interval", "5s").build();

			client = new PreBuiltTransportClient(settings);
			client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("127.0.0.1", 9300)));
		} catch (Exception e) {
			e.printStackTrace();
			if (client != null) {
				client.close();
			}
		}

		return client;
	}
}
