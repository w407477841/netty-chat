package com.wyf.netty.common.protocol.handler;

import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.common.protocol.NettySession;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionRespHandler extends ChannelHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
               throw new Exception("心跳超时");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                /*写超时*/
                // System.out.println("===服务端===(WRITER_IDLE 写超时)");
            } else if (event.state() == IdleState.ALL_IDLE) {
                /*总超时*/
                // System.out.println("===服务端===(ALL_IDLE 总超时)");
            }
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        NettySession nettySession = ctx.channel().attr(Const.NETTY_SESSION).get();
        if(nettySession!=null){
            log.error("设备[{}][{}]异常,[{}]",nettySession.getSn(),ctx.channel().remoteAddress().toString(),cause.getMessage(),cause);
        }else{
            log.error("匿名设备[{}]异常,[{}]",ctx.channel().remoteAddress().toString(),cause.getMessage(),cause);
        }

        ctx.close();

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettySession nettySession = ctx.channel().attr(Const.NETTY_SESSION).get();
        if(nettySession!=null){
            Const.nodeCheck.remove(nettySession.getSn());
            log.error("设备[{}][{}]离线",nettySession.getSn(),ctx.channel().remoteAddress().toString());
        }else{
            log.error("匿名设备[{}]离线",ctx.channel().remoteAddress().toString());
        }

    }
}
