package org.spring.async_message;

/**
 * 想法:
 * 
 *  业务直接submit任务给线程池
 *  线程池负责，将消息异步发出
 *  
 *  容错处理:每个消息，设置提交时间，发送时间，消费时间，重试次数(超过重试次数的消息，写如坏消息队列)
 *  问题:
 *     服务停止的时候，不能kill掉而丢数据
 *     
 * @author gaotingping
 *
 * 2016年11月3日 下午4:32:38
 */
public class Demo1 {

}
