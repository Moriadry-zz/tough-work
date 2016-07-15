package com.dp.netty.MultiUserCommunicateDemo.Server;

import com.dp.netty.MultiUserCommunicateDemo.Message.*;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.sql.Timestamp;

/**
 * 服务器端事件处理
 */
public class NettyServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyChannelMap.remove((SocketChannel)ctx.channel());
    }
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg){
        BaseMsg baseMsg = (BaseMsg)msg;
        if(MsgType.LOGIN.equals(baseMsg.getType())){
            LoginMsg loginMsg=(LoginMsg)baseMsg;
            if("robin".equals(loginMsg.getUserName())&&"dp".equals(loginMsg.getPassword())){
                //登录成功,把channel存到服务端的map中
                NettyChannelMap.add(loginMsg.getClientId(),(SocketChannel)channelHandlerContext.channel());
                System.out.println("client"+loginMsg.getClientId()+" 登录成功");
            }
        }else{
            if(NettyChannelMap.get(baseMsg.getClientId())==null){
                    //说明未登录，或者连接断了，服务器向客户端发起登录请求，让客户端重新登录
                    LoginMsg loginMsg=new LoginMsg();
                    channelHandlerContext.channel().writeAndFlush(loginMsg);
            }
        }
        System.out.println("Receive Client Message Type is " + baseMsg.getType().toString());
        switch (baseMsg.getType()){
            case PING:{
                PingMsg pingMsg=(PingMsg)baseMsg;
                PingMsg replyPing=new PingMsg();
                NettyChannelMap.get(pingMsg.getClientId()).writeAndFlush(replyPing);
            }break;
            case ASK:{
                //收到客户端的请求
                AskMsg askMsg=(AskMsg)baseMsg;
                if("authToken".equals(askMsg.getParams().getAuth())){
                    ReplyServerBody replyBody=new ReplyServerBody("server reply info $$$$ !!!");
                    ReplyMsg replyMsg=new ReplyMsg();
                    replyMsg.setBody(replyBody);
                    NettyChannelMap.get(askMsg.getClientId()).writeAndFlush(replyMsg);
                }
            }break;
            case REPLY:{
                //收到客户端回复
                ReplyMsg replyMsg=(ReplyMsg)baseMsg;
                ReplyClientBody clientBody=(ReplyClientBody)replyMsg.getBody();
                System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"receive client msg: "+clientBody.getClientInfo());
            }break;
            default:break;
        }
        ReferenceCountUtil.release(baseMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        cause.printStackTrace();
        ctx.close();
    }
}
