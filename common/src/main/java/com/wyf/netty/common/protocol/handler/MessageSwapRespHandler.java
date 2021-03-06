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
public class MessageSwapRespHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;


        if(nettyMessage.getHeader().getType() == MessageType.MESSAGE_SWAP_RESP.value()){
            String toSn =  nettyMessage.getHeader().getSn();
            ChannelHandlerContext toCtx = Const.nodeCheck.get(toSn);
            if(toCtx == null){

            }else{
                nettyMessage.getHeader().setType(MessageType.MESSAGE_SWAP_RESP.value());
                toCtx.writeAndFlush(buildMessage(nettyMessage.getHeader(), ResultCode.SUCCESS));
            }
        }else{
            ctx.fireChannelRead(msg);
        }

    }

    private NettyMessage buildMessage(Header header , ResultCode code){
        NettyMessage toMessage = new NettyMessage();
        Header toHeader = header;
        toHeader.setType(MessageType.MESSAGE_RESP.value());
        toMessage.setHeader(toHeader);
        toMessage.getBody().put("code",code.code());
        toMessage.getBody().put("msg",code.msg());

        return toMessage;
    }



}
