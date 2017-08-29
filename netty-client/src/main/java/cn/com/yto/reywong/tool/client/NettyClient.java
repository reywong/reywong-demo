package cn.com.yto.reywong.tool.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by wangrui on 2017/4/13.
 */
public class NettyClient {
    public static void main(String[] args) {
        String ipAddr = "10.129.221.158";
        ipAddr = "localhost";
        int port = 8888;
        EventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                //字符串类解析
//                socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                //设置解码为UTF-8
//                socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
//                设置编码为UTF-8
//                socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
                //添加心跳
//               socketChannel.pipeline().addLast("timeout", new IdleStateHandler(5, 5, 4, TimeUnit.SECONDS));
                //客户端处理工具类
                socketChannel.pipeline().addLast(new NettyClientHandler());
            }
        });

        try {
            //启动客户端
            ChannelFuture channelFuture = bootstrap.connect(ipAddr, port).sync();

            Channel channel = channelFuture.channel();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String str = bufferedReader.readLine();
                ByteBuf buf = Unpooled.buffer(str.getBytes().length);
                buf.writeBytes(str.getBytes());
                channel.writeAndFlush(buf);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
