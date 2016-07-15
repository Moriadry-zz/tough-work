package com.dp.netty.Factorial;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigInteger;

/**
 * @author dp
 * @create 2016/7/4
 */
public class FactorialServerHandler extends SimpleChannelInboundHandler<BigInteger>{

    private BigInteger lastMultiplier = new BigInteger("1");
    private BigInteger factorial = new BigInteger("1");

    @Override
    public void messageReceived(ChannelHandlerContext ctx, BigInteger msg){
        // Calculate the cumulative factorial and send it to the client.
        lastMultiplier = msg;
        factorial = factorial.multiply(msg);
        ctx.writeAndFlush(factorial);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        System.err.printf("Factorial of %,d is: %,d%n", lastMultiplier, factorial);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        cause.printStackTrace();
        ctx.close();
    }
}
