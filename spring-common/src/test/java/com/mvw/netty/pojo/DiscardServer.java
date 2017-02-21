package com.mvw.netty.pojo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

//初步测试kryo没有默认的好
public class DiscardServer {
	
	private int port;
	
	public DiscardServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					
					ch.pipeline().addLast(
							/*
							//jdk自带的测试速度反而整体快???
							new ObjectEncoder(),
                            new ObjectDecoder(Integer.MAX_VALUE,ClassResolvers.cacheDisabled(null)),
                            */
							new MyEncoder(),
							new MyDecoder(),
                            new ChannelInboundHandlerAdapter(){
									@Override
								    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
								        System.out.println(msg);
										ctx.writeAndFlush(msg);
								    }
									
								    @Override
								    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
								            throws Exception {
								        ctx.close();
								    }
							});
				}
			});

			ChannelFuture f = b.bind(port).sync();

			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new DiscardServer(8080).run();
	}
}
