package com.dp.netty.Factorial;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author dp
 * @create 2016/7/4
 */
public class FactorialServer {
    private static final int PORT = Integer.parseInt(System.getProperty("port", "8968"));
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch){
                            ChannelPipeline pipeline = ch.pipeline();

                            //Enable stream compression (you can remove these two if unnecessary)
//                            pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
//                            pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));

                            pipeline.addLast(new BigIntegerDecoder());
                            pipeline.addLast(new NumberEncoder());

                            pipeline.addLast(new FactorialServerHandler());
                        }
                    });

            ChannelFuture f = b.bind(PORT).sync();
            f.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
