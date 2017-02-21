package com.mvw.netty.pojo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class DiscardClient {
	

	public static void main(String[] args) throws Exception {
		for(int i=0;i<50;i++){
			TestClient();
		}
    }

	//正常速度是本地测试:  5M/s   10000/0.738=13550=1.4W
	private static void TestClient() throws InterruptedException {
		String host = "127.0.0.1";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                    		 /*
                    		 new ObjectEncoder(),
                             new ObjectDecoder(Integer.MAX_VALUE ,ClassResolvers.cacheDisabled(null)),
                             */
							 new MyEncoder(),
							 new MyDecoder(),
                             new ChannelInboundHandlerAdapter(){
                    			    long s=0;
                    				int callNum=0;
                    			 	@Override
                    			    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                    			        super.channelActive(ctx);
                    			        s=System.currentTimeMillis();
                    			        ctx.writeAndFlush(new BeanDTO());
                    			    }

                    			    @Override
                    			    public void channelRead(ChannelHandlerContext ctx, Object msg)
                    			            throws Exception {
                    			        ctx.writeAndFlush(msg);
                    			        callNum++;
                    			        if(callNum>5){//每次调用5次
                    			        	ctx.close();
                    			        	System.out.println("5 次  耗时="+(System.currentTimeMillis()-s));
                    			        }
                    			    }

                    			    @Override
                    			    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                    			            throws Exception {
                    			        ctx.close();
                    			    }	 
                    });
                }
            });

            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
	}
}
