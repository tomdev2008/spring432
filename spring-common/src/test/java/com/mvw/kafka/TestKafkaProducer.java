package com.mvw.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;


/**
 * 推荐:org.apache.kafka.clients.producer.KafkaProducer
 * 
 * @author gaotingping
 *
 * 2016年8月31日 上午11:24:48
 */
public class TestKafkaProducer {

	private final Producer<String, String> producer;
	
	/**
	 * 
	 *#先创建topic
	 	kafka-topics.bat --zookeeper 127.0.0.1:2181 --create --topic TEST-TOPIC --partitions 1 --replication-factor 1
	 */
	public final static String TOPIC = "talent_topic";

	private TestKafkaProducer() {
		
		Map<String,Object> props=new HashMap<String,Object>();
		
		props.put("bootstrap.servers", "127.0.0.1:9092");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");// 配置value的序列化类
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");// 配置key的序列化类
		
		props.put("acks", "-1");//0 1 -1
		
		producer=new KafkaProducer<String, String>(props);
	}

	void produce() throws InterruptedException, ExecutionException {
		long s = System.currentTimeMillis();
		
		for(int i=0;i<10;i++){
			String data = "{\"curTime\":1474878960035,\"talentId\":1303517}";
			ProducerRecord<String, String> record=new ProducerRecord<String, String>(TOPIC, data);
			Future<RecordMetadata> result = producer.send(record);
			System.out.println(result.get());
		}
		
		System.out.println("耗时="+(System.currentTimeMillis()-s));
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		new TestKafkaProducer().produce();
	}
}
