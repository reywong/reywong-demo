package cn.com.yto.reywong.tool.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.util.*;

/**
 * Created by wangrui on 2017/4/13.
 */
public class NettyChannelManager {
    //当前连接数
    private static int connectionNum = 0;
    //最大连接数
    private static int connectionPool = 2;
    //临时client存活时间单位 毫秒
    private static int tempTime = 30000;
    //管理channel
    private static List<ChannelManagerBean> channelManagerBeanList = new Vector<ChannelManagerBean>();

    /**
     * 添加channel
     *
     * @param clientId
     * @param channel
     */
    public static void addChannel(String clientId, Channel channel) {
        connectionNum++;
        if (connectionNum > connectionPool) {
            sendPrompt(channel, "已超过服务器连接上限！");
        } else {
            //如果存在相同clientId则关闭channel
            if (channelManagerBeanList != null) {
                for (int i = 0; i < channelManagerBeanList.size(); i++) {
                    if (channelManagerBeanList.get(i).getClientId().equals(clientId)) {
                        sendPrompt(channelManagerBeanList.get(i).getChannel(), null);
                        channelManagerBeanList.remove(i);
                        break;
                    }
                }
            }

            //添加channel
            ChannelManagerBean channelManagerBean = new ChannelManagerBean();
            channelManagerBean.setClientId(clientId);
            channelManagerBean.setChannel(channel);
            channelManagerBean.setConnectionTime(new Date().getTime());
            channelManagerBeanList.add(channelManagerBean);

        }

    }

    /**
     * 更新channel
     *
     * @param channel
     */
    public static void updateByChannel(String clientId, Channel channel) {
        if (channelManagerBeanList != null) {
            for (int i = 0; i < channelManagerBeanList.size(); i++) {
                ChannelManagerBean channelManagerBean = channelManagerBeanList.get(i);
                Channel tempChannel = channelManagerBean.getChannel();
                if (tempChannel == channel) {
                    channelManagerBean.setClientId(clientId);
                    channelManagerBean.setLoginTime(new Date().getTime());
                    channelManagerBeanList.set(i, channelManagerBean);
                    break;
                }
            }
        }
    }

    /**
     * 更新client
     *
     * @param clientId
     * @param channel
     */
    public static void updateByClientId(String clientId, Channel channel) {
        if (channelManagerBeanList != null) {
            for (int i = 0; i < channelManagerBeanList.size(); i++) {
                ChannelManagerBean channelManagerBean = channelManagerBeanList.get(i);
                String tempClientId = channelManagerBean.getClientId();
                if (tempClientId.equals(clientId)) {
                    sendPrompt(channelManagerBean.getChannel(), null);
                    channelManagerBean.setChannel(channel);
                    channelManagerBeanList.set(i, channelManagerBean);
                    break;
                }
            }
        }
    }


    /**
     * 获取channel
     *
     * @param clientId
     * @return
     */
    public static Channel getChannelByClientId(String clientId) {
        if (channelManagerBeanList != null) {
            for (int i = 0; i < channelManagerBeanList.size(); i++) {
                if (channelManagerBeanList.get(i).getClientId().equals(clientId)) {
                    return channelManagerBeanList.get(i).getChannel();
                }
            }
        }
        return null;
    }

    /**
     * 移除channel
     *
     * @param channel
     */
    public static String removeByChannel(Channel channel) {
        connectionNum--;
        String result = "";
        if (channelManagerBeanList != null) {
            for (int i = 0; i < channelManagerBeanList.size(); i++) {
                if (channelManagerBeanList.get(i).getChannel() == channel) {
                    sendPrompt(channelManagerBeanList.get(i).getChannel(), "您已被服务器关闭连接");
                    result = channelManagerBeanList.get(i).getClientId();
                    channelManagerBeanList.remove(i);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 通过Id去删除连接
     *
     * @param clientId
     * @return
     */
    public static void removeByClientId(String clientId) {
        if (channelManagerBeanList != null) {
            for (int i = 0; i < channelManagerBeanList.size(); i++) {
                if (channelManagerBeanList.get(i).getClientId().equals(clientId)) {
                    sendPrompt(channelManagerBeanList.get(i).getChannel(), "您已被服务器关闭连接");
                    channelManagerBeanList.remove(i);
                    connectionNum--;
                    break;
                }
            }
        }
    }

    /**
     * 发送信息并关闭channel
     *
     * @param channel
     * @param msg
     */
    public static void sendPrompt(Channel channel, String msg) {
        if (channel != null) {
            //返回提示信息
            Map result = new HashMap();
            //提示
            result.put("interface", "ytoMsgService.prompt");
            Map response = new HashMap();
            response.put("resultFlag", false);
            if (msg != null && !"".equals(msg)) {
                response.put("resultMessage", msg);
            } else {
                response.put("resultMessage", "您的账号已在另一个地方登陆,您已和服务器断开！");
            }
            result.put("response", response);
            String json = DataTypeChangeTool.mapToJson(result);
            ByteBuf buf = Unpooled.buffer(json.getBytes().length);
            buf.writeBytes(json.getBytes());
            channel.writeAndFlush(buf);
            channel.close();
        }

    }


    /**
     * 去掉超时未注册的channel
     *
     * @param currentDate 当前时间
     */
    public static void removeTempChannel(Date currentDate) {
        if (channelManagerBeanList != null) {
            for (int i = 0; i < channelManagerBeanList.size(); i++) {
                if (channelManagerBeanList.get(i).getClientId().startsWith("temp_")
                        && (currentDate.getTime() - channelManagerBeanList.get(i).getConnectionTime()) * 1000 > tempTime) {
                    sendPrompt(channelManagerBeanList.get(i).getChannel(), "您长时间未注册，服务器已与您断开连接");
                    channelManagerBeanList.remove(i);
                    connectionNum--;
                }
            }
        }
    }
}
