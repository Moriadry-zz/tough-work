package com.dp.netty.Telnet;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author dp
 * @create 2016/7/4
 */
public class TelnetServer {
    static final int PORT = Integer.parseInt(System.getProperty("port", "8968"));

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss  = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new TelnetServerInitializer());

            //绑定端口
            ChannelFuture f = b.bind(PORT).sync();
            System.out.println("server boot success!");
            //阻塞直到服务器端口关闭
            f.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
