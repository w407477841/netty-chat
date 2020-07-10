package com.wyf.netty.common.protocol.handler;

import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.enums.MessageType;
import com.wyf.netty.common.protocol.enums.ResultCode;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MessageReqHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;


        if(nettyMessage.getHeader().getType() == MessageType.MESSAGE_RESP.value()){
            if(ResultCode.SUCCESS.code().equals(nettyMessage.getBody().get("code"))){
                log.info("消息[{}]发送成功",nettyMessage.getHeader().getSerialNumber());
            }else{
                log.info("消息[{}]发送失败",nettyMessage.getHeader().getSerialNumber());
            }
        }else{
            ctx.fireChannelRead(msg);
        }

    }
}