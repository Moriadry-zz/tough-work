package com.dp.netty.Echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author dp
 * @create 2016/7/1
 */
public class EchoClient {
    static final String HOST = System.getProperty("host","127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port","8968"));
    static final int SIZE = Integer.parseInt(System.getProperty("size","128"));

    public static void main(String[] args) throws InterruptedException {
        //配置客户端
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)// TODO: 2016/7/1
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch){
                            ChannelPipeline cp = ch.pipeline();
                            cp.addLast(new com.dp.netty.Echo.EchoClientHandler());
                        }
                    });

            //启动客户端
            ChannelFuture cf = b.connect(HOST,PORT).sync();
            //阻塞直到关闭连接
            cf.channel().closeFuture().sync();
        }finally {
            //优雅关闭，释放所有线程池资源
            group.shutdownGracefully();
        }
    }
}
