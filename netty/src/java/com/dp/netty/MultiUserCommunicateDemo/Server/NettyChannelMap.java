package com.dp.netty.MultiUserCommunicateDemo.Server;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器端存储的<客户端id，客户端channel>映射管理
 */
public class NettyChannelMap {
    private  static Map<String,SocketChannel> map=new ConcurrentHashMap<String, SocketChannel>();
    public static void add(String clientId,SocketChannel socketChannel){
        map.put(clientId,socketChannel);
    }
    public static SocketChannel get(String clientId){
       return map.get(clientId);
    }
    public static void remove(SocketChannel socketChannel){
        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue()==socketChannel){
                map.remove(entry.getKey());
            }
        }
    }
    public static List<String> getAllKeys(){
        List<String> list = new ArrayList<String>();
        for (String key:map.keySet()){
            list.add(key);
        }
        return list;
    }

}
