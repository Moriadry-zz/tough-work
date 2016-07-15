package com.dp.netty.MultiUserCommunicateDemo.Server;

import com.dp.netty.MultiUserCommunicateDemo.Message.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.TimeUnit;

/**
 * 服务器端启动代码
 */
public class NettyServerBootstrap {
    private int port;
    private SocketChannel socketChannel;
    public NettyServerBootstrap(int port) throws InterruptedException {
        this.port = port;
    }

    private void bind() throws InterruptedException {
        EventLoopGroup boss=new NioEventLoopGroup();
        EventLoopGroup worker=new NioEventLoopGroup();
        try{
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(boss,worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline p = socketChannel.pipeline();
                p.addLast(new ObjectEncoder());
                p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                p.addLast(new NettyServerHandler());
            }
        });
        //Start the server
        ChannelFuture f= bootstrap.bind(port).sync();
        if(f.isSuccess()){
            System.out.println("server start---------------");
        }

        //Wait until the server socket is closed
        f.channel().closeFuture().sync();
        }finally{
            //优雅的退出
            boss.shutdownGracefully();
            worker.shutdownGracefully();

        }
    }
    public static void main(String []args) throws InterruptedException {
        Thread thread = new Thread(){
            public void run(){
                while(true){
                    for (String clientID:NettyChannelMap.getAllKeys()){
                        if(clientID != null){
                            AskMsg askMsg=new AskMsg();
                            AskParams ap = new AskParams();
                            ap.setAuth(clientID);
                            askMsg.setParams(ap);
                            NettyChannelMap.get(clientID).writeAndFlush(askMsg);
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

        new NettyServerBootstrap(9999).bind();
        //为什么执行不到这里？
        System.out.println("Netty Server Bootstrap");

//        //为什么这里会阻塞？
//        while(true){
//            System.out.println("Netty Server Bootstrap1");
//            for (String clientID:NettyChannelMap.getAllKeys()){
//                System.out.println("Netty Server Bootstrap2");
//                if(clientID != null){
//                    AskMsg askMsg=new AskMsg();
//                    AskParams ap = new AskParams();
//                    ap.setAuth(clientID);
//                    askMsg.setParams(ap);
//                    NettyChannelMap.get(clientID).writeAndFlush(askMsg);
//                }
//            }
//            TimeUnit.SECONDS.sleep(3);
//        }
    }

}
