//package org.spring.kafka;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Future;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.clients.producer.RecordMetadata;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.alibaba.dubbo.common.utils.CollectionUtils;
//import com.alibaba.fastjson.JSONObject;
//import com.yimayhd.user.client.enums.OnlineStatus;
//import com.yimayhd.user.entity.UserStatusInfo;
//
///**
// * 将达人在线状态通过kafka推送给数据中心
// * 
// * @author gaotingping
// *
// * 2016年9月23日 下午5:05:41
// */
//public class PushTalentStatusService {
//
//	private static final Logger logger = LoggerFactory.getLogger(PushTalentStatusService.class);
//
//	private KafkaProducer<String,String>  producer;
//
//	public String server;
//
//	private String topic;
//
//	public void init() {
//		try {
//			// 设置配置属性
//			Map<String, Object> props=new HashMap<String, Object>();
//			props.put("bootstrap.servers", server);
//			props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//			props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//			
//			producer=new KafkaProducer<String,String>(props);
//			
//		} catch (Exception e) {
//			logger.error("Push talent status init errir", e);
//		}
//	}
//
//	public void destroy() {
//		if (producer != null) {
//			producer.close();
//		}
//	}
//
//	public void push(List<UserStatusInfo> list) {
//		if (CollectionUtils.isNotEmpty(list)) {
//			for (UserStatusInfo user : list) {
//				if (OnlineStatus.ONLINE.getValue() == user.status) {
//					doPush(user.userId, new Date().getTime());
//				}
//			}
//		}
//	}
//
//	public void doPush(long userId, long time) {
//		try {
//			JSONObject data = new JSONObject();
//			data.put("talentId", userId+"");
//			data.put("curTime", time+"");
//			ProducerRecord<String, String> record=new ProducerRecord<String, String>(topic, data.toJSONString());
//			Future<RecordMetadata> result = producer.send(record);
//			RecordMetadata r = result.get(1, TimeUnit.SECONDS);
//			logger.info("RecordMetadata offset="+r.offset());
//		} catch (Exception e) {
//			logger.error("Push talent status error!", e);
//		}
//	}
//
//	public String getServer() {
//		return server;
//	}
//
//	public void setServer(String server) {
//		this.server = server;
//	}
//
//	public String getTopic() {
//		return topic;
//	}
//
//	public void setTopic(String topic) {
//		this.topic = topic;
//	}
//}
