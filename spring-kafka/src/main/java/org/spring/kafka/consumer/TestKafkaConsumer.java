package org.spring.kafka.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;
import org.spring.kafka.producer.TestKafkaProducer;

public class TestKafkaConsumer {

	private final Consumer<String, String> consumer;

	private TestKafkaConsumer() {

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");// 服务器，多个“,”号隔开
		props.put("group.id", "testConsumer");// 组
		props.put("auto.offset.reset", "latest");//首次开始消费数据时的offset:earliest、latest、none、anything else
		props.put("enable.auto.commit", "true");// 设置`enable.auto.commit`,偏移量由`auto.commit.interval.ms`控制自动提交的频率。
		props.put("auto.commit.interval.ms", "5000");//Consumer的offset自动commit时的周期 
		props.put("session.timeout.ms", "30000");// 停止心跳的时间超过`session.timeout.ms`,那么就会认为是故障的，它的分区将被分配到别的进程
		//如果在这个时间内没有收到consumer的心跳信息，就认为Consumer失败了 
		
		/*
		 * heartbeat.interval.ms  3000
		         当使用Kafka的group管理机制时，consumer向coordinator发送心跳的间隔，
		         这个值要比session.timeout.ms小，最好不要超过
		   session.timeout.ms的/frac{1}{3} 
		 */
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		consumer = new KafkaConsumer<>(props);
	}
	
	/**
	 * 问题1:新版的consumer，通过poll来表示consumer的活性，即
	 * 如果consumer是不断的在调用poll的，那么我们就认为这个consumer是正常的
	 * 这样有个问题，如果你的操作时间比较长，或是取得的records数目太多，会
	 * 导致poll的间隔比较长导致超时；当然也可以设置上配置
	 * 可以配置 session.timeout.ms ，让timeout的时候长些
	 * 也可以通过 max.poll.records ，限制一次poll的条目数 
	 * 
	 * 问题2：
	 * 你也可以把真正的逻辑，放在其他线程去做，然后尽量快点去poll；但这里注意，在处理完后需要commit；
	 * 如果要保证数据不丢，往往不会依赖auto commit，而是当逻辑处理完后，再手动的commit；如果处理延迟太长，
	 * 该consumer已经超时，此时去做commit，会报 CommitFailedException 异常
	 * 
	 * 自动offset commit的例子，
     Properties props = new Properties();
     props.put("bootstrap.servers", "localhost:9092");
     props.put("group.id", "test");
     props.put("enable.auto.commit", "true");  //自动commit
     props.put("auto.commit.interval.ms", "1000"); //定时commit的周期
     props.put("session.timeout.ms", "30000"); //consumer活性超时时间
     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
     consumer.subscribe(Arrays.asList("foo", "bar")); //subscribe，foo，bar，两个topic
     while (true) {
         ConsumerRecords<String, String> records = consumer.poll(100);  //poll 100 条 records
         for (ConsumerRecord<String, String> record : records)
             System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
     }
     
              手工commit offset，
     Properties props = new Properties();
     props.put("bootstrap.servers", "localhost:9092");
     props.put("group.id", "test");
     props.put("enable.auto.commit", "false"); //关闭自动commit
     props.put("session.timeout.ms", "30000");
     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
     consumer.subscribe(Arrays.asList("foo", "bar"));
     final int minBatchSize = 200;
     List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
     while (true) {
         ConsumerRecords<String, String> records = consumer.poll(100);
         for (ConsumerRecord<String, String> record : records) {
             buffer.add(record);
         }
         if (buffer.size() >= minBatchSize) {
             insertIntoDb(buffer); 
             consumer.commitSync(); //批量完成写入后，手工sync offset
             buffer.clear();
         }
     }
     
               更细粒度的commitSync
       try {
         while(running) {
             ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
             for (TopicPartition partition : records.partitions()) { //按partition处理
                 List<ConsumerRecord<String, String>> partitionRecords = records.records(partition); //取出partition对应的Records
                 for (ConsumerRecord<String, String> record : partitionRecords) { //处理每条record
                     System.out.println(record.offset() + ": " + record.value());
                 }
                 long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset(); //取出last offset
                 consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1))); //独立的sync每个partition的offset
             }
         }
     } finally {
       consumer.close();
     }
	  这里为何lastOffset要加1，因为你要commit的是，你下一条要读的log的offset，所以一定是当前的offset+1
	  
	  问题3：
	   seek(TopicPartition, long) to specify the new position. 
	         重置某个partition的offset 
	         
	 
	 */

	void consume() {
		Collection<String> topics = new ArrayList<>();
		topics.add(TestKafkaProducer.TOPIC);

		consumer.subscribe(topics);// 订阅topic

		// 轮询消费
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

	/**
	 * 不依赖于定期提交偏移量，你可以自己控制偏移量，当消息认为已消费过了，这个时候再去提交它们的偏移量。
	 * 这个是很有用的，当消费的消息结合了一些处理逻辑，这个消息就不应该认为是已经消费的，直到它完成了整个处理
	 */
	@Test
	public void test1() {

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "false");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

		consumer.subscribe(Arrays.asList("foo", "bar"));// 订阅topic

		final int minBatchSize = 200;
		List<ConsumerRecord<String, String>> buffer = new ArrayList<>();

		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					buffer.add(record);
				}
				if (buffer.size() >= minBatchSize) {
					// insertIntoDb(buffer);
					consumer.commitSync();// 所有收到的消息为”已提交"
					buffer.clear();
				}
			}
		} catch (Exception e) {
			consumer.close();
		}

		// try {
		// while(true) {
		// ConsumerRecords<String, String> records =
		// consumer.poll(Long.MAX_VALUE);
		// for (TopicPartition partition : records.partitions()) {
		// List<ConsumerRecord<String, String>> partitionRecords =
		// records.records(partition);
		// for (ConsumerRecord<String, String> record : partitionRecords) {
		// System.out.println(record.offset() + ": " + record.value());
		// }
		// long lastOffset = partitionRecords.get(partitionRecords.size() -
		// 1).offset();
		// consumer.commitSync(Collections.singletonMap(partition, new
		// OffsetAndMetadata(lastOffset + 1)));
		// //你应该添加最后一条消息的偏移量
		// }
		// }
		// } finally {
		// consumer.close();
		// }
	}
}