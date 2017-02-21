package org.spring.kafka.producer;

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
	
	/**
	 Properties props = new Properties();
	 props.put("bootstrap.servers", "localhost:9092");
	 props.put("acks", "all"); //ack方式，all，会等所有的commit最慢的方式
	 props.put("retries", 0); //失败是否重试，设置会有可能产生重复数据
	 props.put("batch.size", 16384); //对于每个partition的batch buffer大小
	 props.put("linger.ms", 1);  //等多久，如果buffer没满，比如设为1，即消息发送会多1ms的延迟，如果buffer没满
	 props.put("buffer.memory", 33554432); //整个producer可以用于buffer的内存大小
	 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	
	 Producer<String, String> producer = new KafkaProducer<>(props);
	 for(int i = 0; i < 100; i++)
	     producer.send(new ProducerRecord<String, String>("my-topic", Integer.toString(i), Integer.toString(i)));
	
	 producer.close();
	 
	 对于 buffer.memory ： 
	producer所能buffer数据的大小，如果数据产生的比发送的快，那么这个buffer会耗尽，因为producer的send的异步的
	会先放到buffer，但是如果buffer满了，那么send就会被block，并且当达到 max.block.ms
	时会触发TimeoutException
	注意和batch.size的区别，这个是batch的大小 
	 */

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
