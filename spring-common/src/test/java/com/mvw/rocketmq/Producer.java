package com.mvw.rocketmq;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;  
  
public class Producer {  
    public static void main(String[] args){  
    	
        DefaultMQProducer producer = new DefaultMQProducer("Producer");  
        
        producer.setNamesrvAddr("192.168.1.206:9876");   
        
        try {  
            producer.start();  
            for(int i=0;i<50;i++){
	            //No route info of this topic, PushTopic
	            /*
	             * 发消息:
	             * String topic, String tags, String keys
	             */
	            Message msg = new Message("TRADE","TRADE_SUCCESS","1","7".getBytes());  
	            SendResult result = producer.send(msg);  
	            System.out.println("id:" + result.getMsgId() +  " result:" + result.getSendStatus());  
	            
	            Thread.sleep(3000);
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            producer.shutdown();  
        }  
    }  
}  