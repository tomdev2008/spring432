package com.mvw.rocketmq;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

public class Consumer {
	public static void main(String[] args) {

		System.out.println("Consumer");

		// 默认消费者:设置组
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer");
		consumer.setNamesrvAddr("192.168.1.206:9876");
		try {
			// 订阅:topic,subExpression[过滤标签]
			//consumer.subscribe("TRADE","TRADE_SUCCESS");
			consumer.subscribe("TRADE","TRADE_SUCCESS");
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
						ConsumeConcurrentlyContext Context) {

					for (MessageExt m : list) {
						System.out.println(m);
						System.out.println(new String(m.getBody()));
						System.out.println();
					}

					//return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}
			});
			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
