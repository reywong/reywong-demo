package cn.com.yto.reywong.tool.server;

import io.netty.channel.Channel;

/**
 * Created by wangrui on 2017/5/3.
 */
public class ChannelManagerBean {
    //客户端Id
    private String clientId;
    //客户端通道
    private Channel channel;
    //客户端最后一次登录时间
    private Long connectionTime;
    //客户端发送注册信息时间
    private Long loginTime;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Long getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(Long connectionTime) {
        this.connectionTime = connectionTime;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }
}
