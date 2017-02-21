package com.mvw.netty.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码
 * 
 * @author gaotingping
 *
 * 2016年8月1日 下午2:12:34
 */
public class MyEncoder extends MessageToByteEncoder<BeanDTO> {

	@Override  //将要传输的对象转换为byte数组
	protected void encode(ChannelHandlerContext ctx, BeanDTO msg, ByteBuf out) throws Exception {
		byte[] body = KryoUtil.serialize(msg); // 将对象转换为byte
		int dataLength = body.length; // 读取消息的长度
		out.writeInt(dataLength); // 先将消息长度写入，也就是消息头
		out.writeBytes(body); // 消息体中包含我们要发送的数据
	}
}
