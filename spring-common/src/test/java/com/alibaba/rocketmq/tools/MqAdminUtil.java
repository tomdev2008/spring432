package com.alibaba.rocketmq.tools;

import java.util.Iterator;
import java.util.Map.Entry;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.MQVersion;
import com.alibaba.rocketmq.common.protocol.body.Connection;
import com.alibaba.rocketmq.common.protocol.body.ConsumerConnection;
import com.alibaba.rocketmq.common.protocol.heartbeat.SubscriptionData;
import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;

public class MqAdminUtil {

	private DefaultMQAdminExt mqAdmin = null;

	/**
	 * @param server=host:port
	 */
	public MqAdminUtil(String server) {
		init(server);
	}

	private void init(String server) {
		mqAdmin = new DefaultMQAdminExt();
		mqAdmin.setNamesrvAddr(server);
		mqAdmin.setAdminExtGroup("admin_ext_group_2");
		try {
			mqAdmin.start();// start
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (mqAdmin != null) {
			mqAdmin.shutdown();
		}
	}

	/**
	 * 获得消费组的订阅者信息(方便我们指定本消息组都有那些机器):查看Consumer网络连接、订阅关系
	 * 
	 * warn:区分大小写
	 * 
	 * @param group
	 * @param topic
	 * @param tags
	 */
	public void getConsumerByTopic(String group, String topic, String tags) {

		try {

			if (group == null || topic == null || tags == null) {
				throw new RuntimeException("Parameter is null");
			}

			// consumerGroup
			String consumerGroup = group + "_" + topic + "_" + tags;

			// 查看消费组的
			ConsumerConnection cc = mqAdmin.examineConsumerConnectionInfo(consumerGroup);
			int i = 1;
			for (Connection conn : cc.getConnectionSet()) {
				System.out.printf("%03d  %-32s %-22s %-8s %s\n", //
						i++, //
						conn.getClientId(), //
						conn.getClientAddr(), //
						conn.getLanguage(), //
						MQVersion.getVersionDesc(conn.getVersion())//
				);
			}

			System.out.println("\nBelow is subscription:");
			Iterator<Entry<String, SubscriptionData>> it = cc.getSubscriptionTable().entrySet().iterator();
			i = 1;
			while (it.hasNext()) {
				Entry<String, SubscriptionData> entry = it.next();
				SubscriptionData sd = entry.getValue();
				System.out.printf("%03d  Topic: %-40s SubExpression: %s\n", //
						i++, //
						sd.getTopic(), //
						sd.getSubString()//
				);
			}

			System.out.println("");
			System.out.printf("ConsumeType: %s\n", cc.getConsumeType());
			System.out.printf("MessageModel: %s\n", cc.getMessageModel());
			System.out.printf("ConsumeFromWhere: %s\n", cc.getConsumeFromWhere());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
