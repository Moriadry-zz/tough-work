/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.dp.netty.Snoop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;

import java.net.URI;

/**
 * 简单的Http应用，打印从服务器端接收到的消息
 * @author dp
 * @create 2016/7/5
 */
public final class HttpSnoopClient {

    static final String URL = System.getProperty("url", "http://127.0.0.1:8968/");

    public static void main(String[] args) throws Exception {
        URI uri = new URI(URL);
        String scheme = uri.getScheme() == null? "http" : uri.getScheme();
        String host = uri.getHost() == null? "127.0.0.1" : uri.getHost();
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }

        if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
            System.err.println("Only HTTP(S) is supported.");
            return;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch){
                    ChannelPipeline p = ch.pipeline();

                    p.addLast(new HttpClientCodec());

                    // Remove the following line if you don't want automatic content decompression.
                    p.addLast(new HttpContentDecompressor());

                    // Uncomment the following line if you don't want to handle HttpContents.
                    //p.addLast(new HttpObjectAggregator(1048576));

                    p.addLast(new HttpSnoopClientHandler());
                }
             });

            // 连接服务器并阻塞
            Channel ch = b.connect(host, port).sync().channel();

            // 构造HTTP请求
            HttpRequest request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
            request.headers().set(Names.HOST, host);
            request.headers().set(Names.CONNECTION, Values.CLOSE);
            request.headers().set(Names.ACCEPT_ENCODING, Values.GZIP);

            // 设置一些简单的Cookie键值对
            request.headers().set(
                    Names.COOKIE,
                    ClientCookieEncoder.encode(
                            new DefaultCookie("my-cookie", "foo"),
                            new DefaultCookie("another-cookie", "bar")));

            // 发送HTTP请求
            ch.writeAndFlush(request);

            // 等待服务器关闭连接.
            ch.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
