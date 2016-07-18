package com.dp.netty.Qotm;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author dp
 * @create 2016/7/4
 */
public class QuoteOfTheMomentServer {
    private static final int PORT = Integer.parseInt(System.getProperty("port", "8968"));
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new QuoteOfTheMomentServerHandler());

            ChannelFuture f = b.bind(PORT).sync();
            f.channel().closeFuture().await();
        }finally {
            group.shutdownGracefully();
        }
    }
}
