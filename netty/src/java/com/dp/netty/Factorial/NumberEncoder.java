package com.dp.netty.Factorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.math.BigInteger;

/**
 * @author dp
 * @create 2016/7/4
 */
public class NumberEncoder extends MessageToByteEncoder<Number>{
    @Override
    protected void encode(ChannelHandlerContext ctx, Number msg, ByteBuf out){
        // Convert to a BigInteger first for easier implementation.
        BigInteger bi;
        if(msg instanceof BigInteger){
            bi = (BigInteger)msg;
        }else{
            bi = new BigInteger(String.valueOf(msg));
        }

        // Convert the number into a byte array.
        byte[] data = bi.toByteArray();
        int dataLength = data.length;

        //Encodes a number Prepended with a magic number 'F' or 0x46
        out.writeByte((byte) 'F');
        out.writeInt(dataLength);
        out.writeBytes(data);
    }
}
