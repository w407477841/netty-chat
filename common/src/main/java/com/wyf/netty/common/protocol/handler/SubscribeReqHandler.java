package com.wyf.netty.common.protocol.handler;

import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.enums.MessageType;
import com.wyf.netty.common.protocol.enums.ResultCode;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 订阅消息
 */
@Slf4j
public class SubscribeReqHandler extends ChannelHandlerAdapter {



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        NettyMessage nettyMessage = (NettyMessage) msg;


            if(nettyMessage.getHeader().getType() == MessageType.SUBSCRIBE_RESP.value()){
                Map<String,Object>  body = nettyMessage.getBody();
                if(ResultCode.SUCCESS.code().equals(body.get("code"))){
                    log.info("订阅[{}]成功",body.get("topic"));
                }else{
                    log.info("订阅[{}]失败",body.get("topic"));
                }

            }else{
                ctx.fireChannelRead(msg);
            }



    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.fireExceptionCaught(cause);

    }



}
