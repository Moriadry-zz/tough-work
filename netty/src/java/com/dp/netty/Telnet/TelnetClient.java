package com.dp.netty.Telnet;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author dp
 * @create 2016/7/4
 */
public class TelnetClient {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8968"));

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new TelnetClientInitializer());

            //启动连接
            Channel ch = b.connect(HOST, PORT).channel();

            //从标准输入流读取内容
            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader((System.in)));
            for(;;){
                String line = in.readLine();
                if(line == null){
                    break;
                }
                //发送接受到的内容
                lastWriteFuture = ch.writeAndFlush(line+"\r\n");

                //结束标志"bye"
                if("bye".equalsIgnoreCase(line)){
                    ch.closeFuture().sync();
                    break;
                }
            }

            //在关闭通道前阻塞直到发送完所有的消息
            if(lastWriteFuture != null){
                lastWriteFuture.sync();
            }
        }finally {
            group.shutdownGracefully();
        }
    }
}
