package com.alibaba.rocketmq.tools;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.MQVersion;
import com.alibaba.rocketmq.common.UtilAll;
import com.alibaba.rocketmq.common.admin.TopicStatsTable;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.body.Connection;
import com.alibaba.rocketmq.common.protocol.body.ConsumerConnection;
import com.alibaba.rocketmq.common.protocol.heartbeat.SubscriptionData;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;
import com.alibaba.rocketmq.tools.admin.api.MessageTrack;

/**
 * 问题:
 *    实际中找问题没有一个工具，特别不方便
 * 
 * 计划是做个rocketmq web admin
 * 
 * 前期关注功能是: 
 * 1.有多少个topic topicList 
 * 2.消息整体情况 
 * 3.cluster机器状态
 * 4.查询消息的消费情况  queryMsgById queryMsgByKey queryMsgByOffset 
 * 5.查询组内的消费者    consumerConnection,producerConnection
 * 
 * 即:
 *  1.整体消费情况查看
 *  2.消费组或生产组查看
 *  3.消息查看 msgId,key
 *  4.消息消费轨迹查看
 *  5.发或收消息测试[只接收不消费或接收消费]
 */
public class TestAdmin {

	private DefaultMQAdminExt d = null;

	@Before
	public void init() {
		d = new DefaultMQAdminExt();
		d.setNamesrvAddr("192.168.1.149:9876");
		try {
			d.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}

	@After
	public void close() {
		if (d != null) {
			d.shutdown();
		}
	}

	@Test// 查看组的消费者情况
	public void test1() throws Exception, MQBrokerException, RemotingException, MQClientException{
		ConsumerConnection cc = d.examineConsumerConnectionInfo("tc_consume_metaq_group_test_2_PAY_ORDER_ENABLE");
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
	}

	@Test // 查看消息详情 by id
	public void test2() throws Exception {

		MessageExt msg = d.viewMessage("C0A801CD00002A9F0000000458A6050A");
		System.out.println(msg);

		// 存储消息 body 到指定路径
		String bodyTmpFilePath = createBodyFile(msg);

		System.out.printf("%-20s %s\n", //
				"Topic:", //
				msg.getTopic()//
		);

		System.out.printf("%-20s %s\n", //
				"Tags:", //
				"[" + msg.getTags() + "]"//
		);

		System.out.printf("%-20s %s\n", //
				"Keys:", //
				"[" + msg.getKeys() + "]"//
		);

		System.out.printf("%-20s %d\n", //
				"Queue ID:", //
				msg.getQueueId()//
		);

		System.out.printf("%-20s %d\n", //
				"Queue Offset:", //
				msg.getQueueOffset()//
		);

		System.out.printf("%-20s %d\n", //
				"CommitLog Offset:", //
				msg.getCommitLogOffset()//
		);

		System.out.printf("%-20s %d\n", //
				"Reconsume Times:", //
				msg.getReconsumeTimes()//
		);

		System.out.printf("%-20s %s\n", //
				"Born Timestamp:", //
				UtilAll.timeMillisToHumanString2(msg.getBornTimestamp())//
		);

		System.out.printf("%-20s %s\n", //
				"Store Timestamp:", //
				UtilAll.timeMillisToHumanString2(msg.getStoreTimestamp())//
		);

		System.out.printf("%-20s %s\n", //
				"Born Host:", //
				RemotingHelper.parseSocketAddressAddr(msg.getBornHost())//
		);

		System.out.printf("%-20s %s\n", //
				"Store Host:", //
				RemotingHelper.parseSocketAddressAddr(msg.getStoreHost())//
		);

		System.out.printf("%-20s %d\n", //
				"System Flag:", //
				msg.getSysFlag()//
		);

		System.out.printf("%-20s %s\n", //
				"Properties:", //
				msg.getProperties() != null ? msg.getProperties().toString() : ""//
		);

		System.out.printf("%-20s %s\n", //
				"Message Body Path:", //
				bodyTmpFilePath//
		);

		try {
			//消费轨迹
			List<MessageTrack> mtdList = d.messageTrackDetail(msg);
			if (mtdList.isEmpty()) {
				System.out.println("\n\nWARN: No Consumer");
			} else {
				System.out.println("\n\n");
				for (MessageTrack mt : mtdList) {
					System.out.println(mt);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String createBodyFile(MessageExt msg) throws IOException {
		DataOutputStream dos = null;

		try {
			String bodyTmpFilePath = "/tmp/rocketmq/msgbodys";
			File file = new File(bodyTmpFilePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			bodyTmpFilePath = bodyTmpFilePath + "/" + msg.getMsgId();
			dos = new DataOutputStream(new FileOutputStream(bodyTmpFilePath));
			dos.write(msg.getBody());
			return bodyTmpFilePath;
		} finally {
			if (dos != null)
				dos.close();
		}
	}
	
	@Test // 查询消息详情 by key[比按照msgId来说更贴近实际,因为实际中key比msgId容器获取]
	public void test3() throws Exception {
		String topic = "PAY", key = "1";
		QueryResult queryResult = d.queryMessage(topic, key, 64, 0, Long.MAX_VALUE);
		System.out.printf("%-50s %4s %40s\n", //
				"#Message ID", //
				"#QID", //
				"#Offset");
		for (MessageExt msg : queryResult.getMessageList()) {
			System.out.printf("%-50s %4d %40d\n", msg.getMsgId(), msg.getQueueId(), msg.getQueueOffset());
		}
		/**
		 * #Message ID #QID #Offset C0A801CE00002A9F000000082EC798BC 0 619
		 * C0A801CE00002A9F000000082FD757ED 0 621
		 */
	}
	
	/**
	 * {
	    "offsetTable": {
	        {
	            "brokerName": "broker-a",
	            "queueId": 1,
	            "topic": "TRADE"
	        }: {
	            "lastUpdateTimestamp": 1473836236954,
	            "maxOffset": 1435,
	            "minOffset": 0
	        },
	        {
	            "brokerName": "broker-a",
	            "queueId": 0,
	            "topic": "TRADE"
	        }: {
	            "lastUpdateTimestamp": 1473836225230,
	            "maxOffset": 1472,
	            "minOffset": 0
	        },
	        {
	            "brokerName": "broker-a",
	            "queueId": 3,
	            "topic": "TRADE"
	        }: {
	            "lastUpdateTimestamp": 1473836225059,
	            "maxOffset": 1376,
	            "minOffset": 0
	        },
	        {
	            "brokerName": "broker-a",
	            "queueId": 2,
	            "topic": "TRADE"
	        }: {
	            "lastUpdateTimestamp": 1473836334897,
	            "maxOffset": 1406,
	            "minOffset": 0
	        }
	    }
	}
	 */
	@Test
	public void test4() throws Exception{
		
		TopicStatsTable s = d.examineTopicStats("TRADE");
		System.out.println(s.toJson());
		System.out.println(s.getOffsetTable());
		
	}
}
