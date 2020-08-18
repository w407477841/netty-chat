package com.wyf.netty.common.protocol.handler;

import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.enums.MessageType;
import com.wyf.netty.common.protocol.enums.ResultCode;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 接受消息
 */
@Slf4j
public class MessageRespHandler extends ChannelHandlerAdapter {

    private String sn;

    public MessageRespHandler(String sn) {
        this.sn = sn;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;

        log.info("收到[{}]消息[{}],主题[{}],内容[{}]",nettyMessage.getHeader().getSn(),nettyMessage.getHeader().getSerialNumber(),nettyMessage.getBody().get("topic"),nettyMessage.getBody().get("content"));
        if(nettyMessage.getHeader().getType() == MessageType.MESSAGE_SWAP_REQ.value()){
                 nettyMessage.getHeader().setType(MessageType.MESSAGE_SWAP_RESP.value());
                 nettyMessage.getBody().put("sn",sn);
                 ctx.writeAndFlush(nettyMessage);
        }else{
            ctx.fireChannelRead(msg);
        }
    }


}