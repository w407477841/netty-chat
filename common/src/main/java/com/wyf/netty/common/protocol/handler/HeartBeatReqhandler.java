package com.wyf.netty.common.protocol.handler;

import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.enums.MessageType;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HeartBeatReqhandler extends ChannelHandlerAdapter {

    private volatile ScheduledFuture<?> heartbeat;

    private String sn;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        NettyMessage nettyMessage = (NettyMessage) msg;


            if(nettyMessage.getHeader().getType() == MessageType.LOGIN_RESP.value()){
                sn = nettyMessage.getHeader().getSn();
                heartbeat = ctx.executor().scheduleAtFixedRate(new HeartbeatTask(ctx,sn),0,20000, TimeUnit.MILLISECONDS);
            }else if(nettyMessage.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()){
                log.info("收到心跳回复");
            }else{
                ctx.fireChannelRead(msg);
            }



    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        if(heartbeat!=null){
            heartbeat.cancel(true);
            heartbeat = null;
        }

        ctx.fireExceptionCaught(cause);

    }

    private class HeartbeatTask implements Runnable {

        private final ChannelHandlerContext ctx;
        private final String sn;

        public HeartbeatTask(ChannelHandlerContext ctx, String sn) {
            this.ctx = ctx;
            this.sn = sn;
        }

        @Override
        public void run() {
            ctx.writeAndFlush(buildHeartbeat());
        }

        private NettyMessage buildHeartbeat(){
            NettyMessage nettyMessage = new NettyMessage();
            Header header = new Header();
            header.setSn(sn);
            header.setType(MessageType.HEARTBEAT_REQ.value());
            nettyMessage.setHeader(header);
            return nettyMessage;
        }

    }

}
