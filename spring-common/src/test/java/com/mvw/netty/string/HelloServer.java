package com.mvw.netty.string;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {
	
	public static void main(String[] args) throws Exception {
		
		/**
		 * EventLoopGroup 是在4.x版本中提出来的一个新概念。用于channel的管理。
		 * 服务端需要两个。和3.x版本一样，一个是boss线程一个是worker线程。
		 * 
		 * NioEventLoopGroup是一个多线程的I/O操作事件循环池，Netty为各种传输方式提供了多种EventLoopGroup的实现。
		 * 我们可以像上面的例子一样来实现一个服务器应用，代码中的两个NioEventLoopGroup都会被使用到。第一个NioEventLoopGroup
		 * 通常被称为'boss'，用于接收所有连接到服务器端的客户端连接。第二个被称为'worker',当有新的连接进来时将会被注册到worker中。
		 * 至于要在EventLoopGroup创建多少个线程，映射多少个Channel可以在EventLoopGroup的构造方法中进行配置。
		 */
		EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        
        try {
            ServerBootstrap b = new ServerBootstrap();
            
            b.group(bossGroup, workerGroup);
            
            b.channel(NioServerSocketChannel.class);//nio
            
            b.childHandler(new HelloServerInitializer());//用于添加相关的Handler
            
            /**
             * option() 方法用于设置监听套接字。
             * childOption()则用于设置连接到服务器的客户端套接字
             */
            b.option(ChannelOption.SO_BACKLOG, 128);          //设置参数
            b.childOption(ChannelOption.SO_KEEPALIVE, true); //

            // 服务器绑定端口监听
            ChannelFuture f = b.bind(9988).sync();
            
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();

            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
        	//释放资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}
}
