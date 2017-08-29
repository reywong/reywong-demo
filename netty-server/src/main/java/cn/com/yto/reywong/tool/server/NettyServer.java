package cn.com.yto.reywong.tool.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by wangrui on 2017/4/13.
 */
public class NettyServer {

    public static void main(String[] args) {
        //启动80端口
        System.out.println("[Demo-Netty-Server]开启88端口");
        new NettyServer().bind(88);
    }

    public void bind(int port) {
        //boss 用来接收进来的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //worker boss收到连接，会把连接信息注册到“worker”上
        EventLoopGroup workGroup = new NioEventLoopGroup();

        //启动NIO服务的辅助启动类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //注册通道
        serverBootstrap.group(bossGroup, workGroup);
        //频道类型
        serverBootstrap.channel(NioServerSocketChannel.class);

        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        //channel
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                //字符串类解析
//                socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                //设置解码为UTF-8
//                socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                //设置编码为UTF-8
//                socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
                //添加心跳
//        socketChannel.pipeline().addLast("timeout", new IdleStateHandler(5, 5, 4, TimeUnit.SECONDS));
                // 在管道中添加我们自己的接收数据实现方法
                socketChannel.pipeline().addLast(new NettyServerHandler());
            }
        });
        //绑定端口
        ChannelFuture channelFuture = serverBootstrap.bind(port);


        try {
            //等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //退出
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
