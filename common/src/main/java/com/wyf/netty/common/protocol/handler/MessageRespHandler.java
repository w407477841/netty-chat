package com.wyf.netty.common.protocol.handler;

import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.enums.MessageType;
import com.wyf.netty.common.protocol.enums.ResultCode;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MessageRespHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;

        log.info("收到[{}]消息[{}],内容[{}]",nettyMessage.getHeader().getSn(),nettyMessage.getHeader().getSerialNumber(),nettyMessage.getBody().get("content"));
        if(nettyMessage.getHeader().getType() == MessageType.MESSAGE_SWAP_REQ.value()){

                 nettyMessage.getHeader().setType(MessageType.MESSAGE_SWAP_RESP.value());
                 ctx.writeAndFlush(nettyMessage);
        }else{
            ctx.fireChannelRead(msg);
        }
    }


}