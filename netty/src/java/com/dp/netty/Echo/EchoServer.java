package com.dp.netty.Echo;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 返回从客户端接受到的任何数据
 *
 * @author dp
 * @create 2016/7/1
 */
public class EchoServer {
    static final int PORT = Integer.parseInt(System.getProperty("port","8968"));

    public static void main(String[] args) throws InterruptedException {
        //配置服务器
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch){
                            ChannelPipeline cp = ch.pipeline();
                            cp.addLast(new EchoServerHandler());
                        }
                    });

            //启动服务器
            ChannelFuture cf = sb.bind(PORT).sync();

            //阻塞直到服务器端口关闭
            cf.channel().closeFuture().sync();
        }finally {
            //优雅退出，释放所有线程池资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
