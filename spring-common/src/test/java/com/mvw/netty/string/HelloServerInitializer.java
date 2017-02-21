package com.mvw.netty.string;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * 数据包的处理:
 *	FixedLengthFrameDecoder 固定长度
 *
 *	DelimiterBasedFrameDecoder 分隔符  
 *	            基于分隔符的帧解码器。参数有两个，一个是最大帧长度，另外一个是定义分隔符
 *
 *  LengthFieldBasedFrameDecoder 头指定长度
	 *  maxFrameLength：解码的帧的最大长度
		lengthFieldOffset ：长度属性的起始位（偏移位），包中存放有整个大数据包长度的字节，这段字节的其实位置
		lengthFieldLength：长度属性的长度，即存放整个大数据包长度的字节所占的长度
		lengthAdjustmen：长度调节值，在总长被定义为包含包头长度时，修正信息长度。initialBytesToStrip：跳过的字节数，根据需要我们跳过
		                lengthFieldLength个字节，以便接收端直接接受到不含“长度属性”的内容
		failFast ：为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常		
 * 		
 * @author gaotingping
 *
 * 2016年7月21日 上午9:32:50
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		ChannelPipeline pipeline = ch.pipeline();

		/**
		 * 数据以流的方式传送，但是我们需要的数据包或帧，这样才有意义
		 * 才能和业务扯上关系
		 * 
		 * ByteToMessageDecoder
		 * MessageToByteEncoder
		 * 
			ByteToMessageDecoder是ChannelInboundHandler的具体实现，该类使得处理分片变得更容易。
			ByteToMessageDecoder 调用decode()方法来处理接收缓存中的数据。
			当累积缓存中的数据不满足处理要求是decode()不需要向out中添加任何东西。当有更多数据到来时ByteToMessageDecoder会再次调用decode()方法。
			当decode()向out添加数据时，表明decoder已成功处理一个消息；这时候ByteToMessageDecoder 会丢弃缓冲区中已处理成功的数据。
			请记住你不需要处理多个消息，ByteToMessageDecoder 会多次调用decode()方法直到没有消息可以处理。
		 * 
		 * 基于分隔符的帧解码器。参数有两个，一个是最大帧长度，另外一个是定义分隔符
		 * 
		 * \r\n  -->  LineBasedFrameDecoder
		 */
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

		// 字符串解码 和 编码
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());

		// 自己的逻辑Handler
		pipeline.addLast("handler", new HelloServerHandler());
	}
}
