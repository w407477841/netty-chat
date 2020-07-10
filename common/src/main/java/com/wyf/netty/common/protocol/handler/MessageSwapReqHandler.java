package com.wyf.netty.common.protocol.handler;

import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.enums.MessageType;
import com.wyf.netty.common.protocol.enums.ResultCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MessageSwapReqHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;

        if(nettyMessage.getHeader().getType()== MessageType.MESSAGE_REQ.value()){
            String to = (String) nettyMessage.getBody().get("to");
            ChannelHandlerContext toChannel =  Const.nodeCheck.get(to);
            if(toChannel==null){
                ctx.writeAndFlush(buildMessage(nettyMessage.getHeader(),ResultCode.ERROR));
            }else{
                NettyMessage toMessage = nettyMessage;
                toMessage.getHeader().setType(MessageType.MESSAGE_SWAP_REQ.value());
                toChannel.writeAndFlush(toMessage);
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
