package com.dp.netty.Factorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.math.BigInteger;
import java.util.List;

/**
 * Decodes the binary representation of a {@link BigInteger} prepended
 * with a magic number ('F' or 0x46) and a 32-bit integer length prefix into a
 * {@link BigInteger} instance.  For example, { 'F', 0, 0, 0, 1, 42 } will be
 * decoded into new BigInteger("42").
 * @author dp
 * @create 2016/7/4
 */


public class BigIntegerDecoder extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out){
        if(in.readableBytes() < 5){
            return;
        }
        in.markReaderIndex();

        int magicNumber = in.readUnsignedByte();
        if(magicNumber != 'F'){
            in.resetReaderIndex();
            throw new CorruptedFrameException("Invalid magic number: " + magicNumber);
        }

        int dataLength = in.readInt();
        if(in.readableBytes() < dataLength){
            in.resetReaderIndex();
            return;
        }

        // Convert the received data into a new BigInteger.
        byte[] decoded = new byte[dataLength];
        in.readBytes(decoded);

        out.add(new BigInteger(decoded));
    }
}
