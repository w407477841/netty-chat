package com.wyf.netty.common.protocol.handler;

import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.enums.MessageType;
import com.wyf.netty.common.protocol.enums.ResultCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.Set;

public class MessageSwapReqHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;

        if(nettyMessage.getHeader().getType()== MessageType.MESSAGE_REQ.value()){
            String topic = (String) nettyMessage.getBody().get("topic");

            if(!Const.subscribeMap.containsKey(topic)){
                ctx.writeAndFlush(buildMessage(nettyMessage.getHeader(),nettyMessage.getBody(),ResultCode.ERROR));
            }else{
                Set<String> sns = Const.subscribeMap.get(topic);
                for (String to : sns) {
                    if(!to.equals(nettyMessage.getHeader().getSn())){
                        // 剔除自己
                        ChannelHandlerContext toChannel =  Const.nodeCheck.get(to);
                        if(toChannel!=null){
                            NettyMessage toMessage = nettyMessage;
                            toMessage.getHeader().setType(MessageType.MESSAGE_SWAP_REQ.value());
                            toChannel.writeAndFlush(toMessage);
                        }
                    }

                }
            }

        }else{
            ctx.fireChannelRead(msg);
        }
    }


    private NettyMessage buildMessage(Header header, Map<String, Object> body, ResultCode code){
        NettyMessage toMessage = new NettyMessage();
        Header toHeader = header;
        toHeader.setType(MessageType.MESSAGE_RESP.value());
        toMessage.setHeader(toHeader);
        body.put("code",code.code());
        body.put("msg",code.msg());
        toMessage.setBody(body);
        return toMessage;
    }

}
