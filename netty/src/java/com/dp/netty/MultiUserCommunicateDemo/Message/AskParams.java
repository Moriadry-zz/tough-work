package com.dp.netty.MultiUserCommunicateDemo.Message;

import java.io.Serializable;

/**
 * 请求参数
 */
public class AskParams implements Serializable {
    private static final long serialVersionUID = 1L;
    private String auth;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
