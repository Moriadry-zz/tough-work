package com.dp.netty.EchoHTTPDemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.CharsetUtil;
import io.netty.util.ResourceLeakDetector;

import java.net.InetSocketAddress;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author dp
 * @create 2016/7/1
 */
public class EchoHTTPDemo {

    public ChannelFuture server(EventLoopGroup workerGroup) {
        ServerBootstrap b = new ServerBootstrap();
        b.group(workerGroup).channel(NioServerSocketChannel.class)
                //参数为0，InetSocketAddress会在区间[0，65535]随机挑选一个端口
                .localAddress(new InetSocketAddress(0))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new HttpServerCodec());

                        //HttpObjectAggregator helps collect chunked HttpRequest pieces into
                        //a single FullHttpRequest. If you don't make use of streaming, this is
                        //much simpler to work with.
                        pipeline.addLast(new HttpObjectAggregator(1048576));

                        pipeline.addLast(new SimpleChannelInboundHandler<FullHttpRequest>() {
                            @Override
                            public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                ctx.flush();
                                ctx.close();
                            }
                            @Override
                            protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
                                //构建默认的HTTP响应报文
                                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, msg.content().copy());
                                ctx.write(response);
                            }
                        });
                    }
                });

        return b.bind();
    }

    public Bootstrap client() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new HttpClientCodec());
                        pipeline.addLast(new HttpObjectAggregator(1048576));
                        pipeline.addLast(new SimpleChannelInboundHandler<FullHttpResponse>() {
                            @Override
                            protected void messageReceived(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
                                final String echo = msg.content().toString(CharsetUtil.UTF_8);
                                System.out.println("Response: {"+echo+"}");
                            }
                        });
                    }
                });
        return b;
    }

    public static void main(String[] args) throws Exception {
        //I find while learning Netty to keep resource leak detecting at Paranoid,
        //however *DO NOT* ship with this level.
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
        EventLoopGroup serverWorkgroup = new NioEventLoopGroup();
        EventLoopGroup clientWorkgroup = new NioEventLoopGroup();
        EchoHTTPDemo app = new EchoHTTPDemo();
        try {
            Channel serverChannel = app.server(serverWorkgroup).sync().channel();
            int PORT = ((InetSocketAddress) serverChannel.localAddress()).getPort();
            System.err.println("Open your web browser and navigate to " +
                    "://127.0.0.1:" + PORT + '/');
            System.err.println("Echo back any TEXT with POST HTTP requests");

            Bootstrap clientBootstrap = app.client();
            final ByteBuf content = Unpooled.copiedBuffer("Hello World! EchoHTTP.", CharsetUtil.UTF_8);
            clientBootstrap
                    .connect(serverChannel.localAddress())
                    .addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture future) throws Exception {
                            // Prepare the HTTP request.
                            HttpRequest request = new DefaultFullHttpRequest(
                                    HTTP_1_1, HttpMethod.POST, "/", content);
                            // If we don't set a content length from the client, HTTP RFC
                            // dictates that the body must be be empty then and Netty won't read it.
                            request.headers().set("Content-Length", content.readableBytes());
                            future.channel().writeAndFlush(request);
                        }
                    });

            serverChannel.closeFuture().sync();
        }finally {
            //优雅关闭，释放线程池资源
            serverWorkgroup.shutdownGracefully();
            clientWorkgroup.shutdownGracefully();
        }
    }


}
