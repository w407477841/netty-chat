package com.wyf.netty.client.auto;

import com.wyf.netty.client.properties.NettyProperties;
import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.coder.NettyMessageDecoder;
import com.wyf.netty.common.protocol.coder.NettyMessageEncoder;
import com.wyf.netty.common.protocol.handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NettyClient implements ApplicationRunner {


    private ScheduledExecutorService executorService;


    public NettyClient(int port, String host, String sn, int cport, String chost) {
        this.port = port;
        this.host = host;
        this.sn = sn;
        this.cport = cport;
        this.chost = chost;
        executorService = new ScheduledThreadPoolExecutor(1);
    }

    private int port ;
    private String host;
    private String sn;
    private int cport;
    private String chost;


    EventLoopGroup group  = new NioEventLoopGroup();



    public void connect() throws Exception{
        try {
            Bootstrap b = new Bootstrap();
            b
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("DelimiterBasedFrameDecoder",new DelimiterBasedFrameDecoder(10240, Unpooled.copiedBuffer(Header.END.getBytes())))
                                    .addLast("NettyMessageDecoder",new NettyMessageDecoder())
                                    .addLast("NettyMessageEncoder", new NettyMessageEncoder())
                                    .addLast("ReadTimeoutHandler", new ReadTimeoutHandler(60))
                                    .addLast("LoginAuthReqHandler", new LoginAuthReqHandler(sn))
                                    .addLast("HeartBeatReqhandler", new HeartBeatReqhandler())
                                    .addLast("MessageReqHandler", new MessageReqHandler())
                                    .addLast("MessageRespHandler", new MessageRespHandler())
                                    .addLast("ExceptionReqHandler",new ExceptionReqHandler())
                                    ;
                        }
                    });

            ChannelFuture future = b.connect(
                    // 设置远端ip+端口
                    new InetSocketAddress(host, port),
                    // 设置本机IP+端口
                    new InetSocketAddress(chost, cport)
            ).sync();
            future.channel().closeFuture().sync();
        }finally {
                executorService.execute(()->{
                    try {
                        TimeUnit.SECONDS.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        connect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        }

    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        connect();
    }
}
