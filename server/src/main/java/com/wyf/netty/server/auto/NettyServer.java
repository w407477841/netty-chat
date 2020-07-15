package com.wyf.netty.server.auto;

import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.coder.NettyMessageDecoder;
import com.wyf.netty.common.protocol.coder.NettyMessageEncoder;
import com.wyf.netty.common.protocol.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

    public void bind(int port ,String host) throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap
                .group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast("DelimiterBasedFrameDecoder",new DelimiterBasedFrameDecoder(10240, Unpooled.copiedBuffer(Header.END.getBytes())))
                                .addLast("NettyMessageDecoder",new NettyMessageDecoder())
                                .addLast("NettyMessageEncoder", new NettyMessageEncoder())
                                .addLast("ReadTimeoutHandler", new ReadTimeoutHandler(60))
                                .addLast("LoginAuthRespHandler", new LoginAuthRespHandler())
                                .addLast("HeartBeatRespHandler", new HeartBeatRespHandler())
                                .addLast("SubscribeRespHandler",new SubscribeRespHandler())
                                .addLast("UnSubscribeRespHandler",new UnSubscribeRespHandler())
                                .addLast("MessageSwapReqHandler", new MessageSwapReqHandler())
                                .addLast("MessageSwapRespHandler", new MessageSwapRespHandler())
                                .addLast("ExceptionRespHandler",new ExceptionRespHandler())
                                ;
                    }
                });

        serverBootstrap.bind(host,port).sync();

        log.info("服务器启动成功");
    }

}
