package com.mvw.kafka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class TestKafkaConsumer {

	private final Consumer<String, String> consumer;

	private TestKafkaConsumer() {

		Properties props = new Properties();
		
		props.put("bootstrap.servers", "localhost:9092");//服务器，多个“,”号隔开
		
		props.put("group.id", "testConsumer");//组
		
		props.put("enable.auto.commit", "true");//设置`enable.auto.commit`,偏移量由`auto.commit.interval.ms`控制自动提交的频率。
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");//停止心跳的时间超过`session.timeout.ms`,那么就会认为是故障的，它的分区将被分配到别的进程
		
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		consumer = new KafkaConsumer<>(props);
	}

	void consume() {
		Collection<String> topics = new ArrayList<>();
		topics.add(TestKafkaProducer.TOPIC);

		consumer.subscribe(topics);//订阅topic

		//轮询消费
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
			    System.out.println();
			}
		}
	}

	public static void main(String[] args) {
		new TestKafkaConsumer().consume();
	}
	
//	/**
//		不依赖于定期提交偏移量，你可以自己控制偏移量，当消息认为已消费过了，这个时候再去提交它们的偏移量。
//		这个是很有用的，当消费的消息结合了一些处理逻辑，这个消息就不应该认为是已经消费的，直到它完成了整个处理
//	 */
//	@Test
//	public void test1(){
//		Properties props = new Properties();
//	     props.put("bootstrap.servers", "localhost:9092");
//	     props.put("group.id", "test");
//	     props.put("enable.auto.commit", "false");
//	     props.put("auto.commit.interval.ms", "1000");
//	     props.put("session.timeout.ms", "30000");
//	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//	     consumer.subscribe(Arrays.asList("foo", "bar"));
//	     final int minBatchSize = 200;
//	     List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
//	     while (true) {
//	         ConsumerRecords<String, String> records = consumer.poll(100);
//	         for (ConsumerRecord<String, String> record : records) {
//	             buffer.add(record);
//	         }
//	         if (buffer.size() >= minBatchSize) {
//	             //insertIntoDb(buffer);
//	             consumer.commitSync();//所有收到的消息为”已提交"
//	             buffer.clear();
//	         }
//	     }
//	     
//	     /*
//		 try {
//	         while(running) {
//	             ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
//	             for (TopicPartition partition : records.partitions()) {
//	                 List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
//	                 for (ConsumerRecord<String, String> record : partitionRecords) {
//	                     System.out.println(record.offset() + ": " + record.value());
//	                 }
//	                 long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
//	                 consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));//你应该添加最后一条消息的偏移量
//	             }
//	         }
//	     } finally {
//	       consumer.close();
//	     }
//	      */
//	}
}