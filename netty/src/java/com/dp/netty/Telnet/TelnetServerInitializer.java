package com.dp.netty.Telnet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author dp
 * @create 2016/7/4
 */
public class TelnetServerInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    public void initChannel(SocketChannel ch){
        ChannelPipeline pipeline = ch.pipeline();

        //设置为用换行符拆分接收到的ByteBufs
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        pipeline.addLast(new StringDecoder());//Decodes a received ByteBuf into a String
        pipeline.addLast(new StringEncoder());//Encodes the requested String into a ByteBuf
        pipeline.addLast(new TelnetServerHandler());
    }
}
