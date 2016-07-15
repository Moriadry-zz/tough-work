package com.dp.netty.MultiUserCommunicateDemo.Message;

/**
 * 回复服务器端消息实体
 */
public class ReplyServerBody extends ReplyBody {
    private String serverInfo;
    public ReplyServerBody(String serverInfo) {
        this.serverInfo = serverInfo;
    }
    public String getServerInfo() {
        return serverInfo;
    }
    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }
}
