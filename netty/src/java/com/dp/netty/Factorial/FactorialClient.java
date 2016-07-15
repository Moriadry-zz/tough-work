package com.dp.netty.Factorial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 *
 * @author dp
 * @create 2016/7/4
 */
public class FactorialClient {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8968"));
    static final int COUNT = Integer.parseInt(System.getProperty("count", "10"));

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    public void initChannel(SocketChannel ch){
                        ChannelPipeline pipeline = ch.pipeline();

                        // Enable stream compression (you can remove these two if unnecessary)
//                        pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
//                        pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));

                        // Add the number codec first,
                        pipeline.addLast(new BigIntegerDecoder());
                        pipeline.addLast(new NumberEncoder());

                        // and then business logic.
                        pipeline.addLast(new FactorialClientHandler());
                    }
                });

            // Make a new connection.
            ChannelFuture f = b.connect(HOST, PORT).sync();

            // Get the handler instance to retrieve the answer.
            FactorialClientHandler handler = (FactorialClientHandler) f.channel().pipeline().last();

            // Print out the answer.
            System.err.format("Factorial of %,d is: %,d", COUNT, handler.getFactorial());
        } finally {
                group.shutdownGracefully();
        }
    }
}
