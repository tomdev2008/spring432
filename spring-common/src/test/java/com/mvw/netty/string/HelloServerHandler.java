package com.mvw.netty.string;

import java.net.InetAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;


//消息入站处理
public class HelloServerHandler extends ChannelInboundHandlerAdapter{
	
	@Override //收到消息
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		/**
		 * ByteBuf采用引用计数
		 */
		try{
			// 收到消息直接打印输出
			System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);

			// 返回客户端消息 - 我已经接收到了你的消息
			ctx.writeAndFlush("Received your message !\n\r\n123456789\r\n");
			//ctx.write(msg);//不会立即写出
			
		}finally{
			ReferenceCountUtil.release(msg); //释放资源
		}
	}
	
	@Override //建立连接
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

		ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

		super.channelActive(ctx);
	}
	
	//异常
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		System.out.println("挂了");
		super.exceptionCaught(ctx, e);
		e.printStackTrace();
		ctx.close();//异常关闭它
	}
}
