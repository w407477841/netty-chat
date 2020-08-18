package com.wyf.netty.common.protocol.handler;

import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.enums.MessageType;
import com.wyf.netty.common.protocol.enums.ResultCode;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息发送的应答
 */
@Slf4j
public class MessageReqHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;


        if(nettyMessage.getHeader().getType() == MessageType.MESSAGE_RESP.value()){
            if(ResultCode.SUCCESS.code().equals(nettyMessage.getBody().get("code"))){
                log.info("topic:[{}] sn:[{}] 消息[{}]发送成功",nettyMessage.getBody().get("topic"),nettyMessage.getBody().get("sn"),nettyMessage.getHeader().getSerialNumber());
            }else{
                log.info("topic:[{}] 消息[{}]发送失败",nettyMessage.getBody().get("topic"),nettyMessage.getHeader().getSerialNumber());
            }
        }else{
            ctx.fireChannelRead(msg);
        }

    }
}