package com.dp.netty.Telnet;

import io.netty.channel.*;

import java.net.InetAddress;
import java.util.Date;

/**
 * @author dp
 * @create 2016/7/4
 */
public class TelnetServerHandler extends SimpleChannelInboundHandler<String>{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName()+"!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg){
        String response;
        boolean close = false;

        if(msg.length() == 0){
            response = "Please type something.\r\n";
        }else if ("bye".equals(msg.toLowerCase())){
            response = "Have a good day!\r\n";
            close = true;
        }else{
            response = "You say: " + msg + "!\r\n";
        }

        ChannelFuture f = ctx.write(response);

        if(close){
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
