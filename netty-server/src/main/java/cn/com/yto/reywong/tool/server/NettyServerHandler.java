package cn.com.yto.reywong.tool.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;

/**
 * Created by wangrui on 2017/4/13.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端连接，添加handler
     *
     * @param ctx
     * @throws Exception
     */
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[Demo-Netty-Server]执行handlerAdded");
    }

    /*
     * channelAction
     *
     * channel 通道
     * action  活跃的
     *
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     *
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String clientId = String.valueOf(new Date().getTime());
        System.out.println("[Demo-Netty-Server]执行channelActive,客户端id为" + clientId);
        NettyChannelManager.addChannel(clientId, ctx.channel());
        System.out.println(ctx.channel().remoteAddress());
    }


/*
     * channelRead
	 *
	 * channel 通道
	 * Read    读
	 *
	 * 简而言之就是从通道中读取数据，也就是服务端接收客户端发来的数据
	 * 但是这个数据在不进行解码时它是ByteBuf类型的后面例子我们在介绍
	 *
	 */

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        String datas = (String) msg;

        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        result.release();
        String datas = new String(result1, "UTF-8");
        System.out.println("[Demo-Netty-Server]执行channelRead，接收个客户端值:" + datas);
        ctx.channel().writeAndFlush("yes\r\n");
    }

    /*
     * channelReadComplete
     *
     * channel  通道
     * Read     读取
     * Complete 完成
     *
     * 在通道读取完成后会在这个方法里通知，对应可以做刷新操作
     * ctx.flush()
     *
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
        System.out.println("[Demo-Netty-Server]执行channelReadComplete");
    }


    /*
     * exceptionCaught
     *
     * exception	异常
     * Caught		抓住
     *
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     *
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
        System.out.println("[Demo-Netty-Server]执行exceptionCaught");
    }

    /*
     * channelInactive
     *
     * channel 	通道
     * Inactive 不活跃的
     *
     * 这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     *
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
        NettyChannelManager.removeByChannel(ctx.channel());
        System.out.println("[Demo-Netty-Server]执行channelInactive");
    }

    /**
     * 当客户端主动断开服务端的链接后，移除handler
     *
     * @param ctx
     * @throws Exception
     */
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[Demo-Netty-Server]执行handlerRemoved");
    }


    /**
     * IDLE 事件
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            String hearStr = "USERSTATE";
            ByteBuf buf = Unpooled.buffer(hearStr.getBytes().length);
            buf.writeBytes(hearStr.getBytes());
            if (e.state() == IdleState.READER_IDLE) {
                System.out.println("[发送心跳探测消息READER_IDLE][" + hearStr + "]");
                ctx.channel().writeAndFlush(buf);
            } else if (e.state() == IdleState.WRITER_IDLE) {
                System.out.println("[发送心跳探测消息WRITER_IDLE][" + hearStr + "]");
                ctx.channel().writeAndFlush(buf);
            } else if (e.state() == IdleState.ALL_IDLE) {
                System.out.println("[定时向客户端发送数据]");
                //获取在线停车场数据


            }

        }
    }

}
