package com.dp.netty.Echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;

/**
 * @author dp
 * @create 2016/7/1
 */
public class EchoClientHandler extends ChannelHandlerAdapter{
    private final ByteBuf firstMessage;

    public EchoClientHandler(){
        //设置要发送的第一条消息内容
        firstMessage = Unpooled.buffer(EchoClient.SIZE);
        for(int i=0; i<firstMessage.capacity(); i++){
            firstMessage.writeByte((byte)i);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        //连接建立后，立刻发一条消息给服务器
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Receive Server timeStamp:" + body);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        //打印异常信息，然后关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
