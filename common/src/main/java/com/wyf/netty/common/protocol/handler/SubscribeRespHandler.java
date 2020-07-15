package com.wyf.netty.common.protocol.handler;

import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.enums.MessageType;
import com.wyf.netty.common.protocol.enums.ResultCode;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class SubscribeRespHandler extends ChannelHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        NettyMessage nettyMessage = (NettyMessage) msg;
        String sn = ((NettyMessage) msg).getHeader().getSn();
        if(nettyMessage.getHeader().getType()== MessageType.SUBSCRIBE_REQ.value()){
            Map<String,Object> body = nettyMessage.getBody();
            String topic = (String) body.get("topic");
            if(Const.subscribeMap.containsKey(topic)){
                // 主题中缓存客户端
                Const.subscribeMap.get(topic).add(sn);
                // session中缓存该主题
                ctx.attr(Const.NETTY_SESSION).get().getTopic().add(topic);
                log.info("[{}]订阅[{}]成功",sn,topic);
                ctx.writeAndFlush(buildSubscribe(nettyMessage.getHeader(),nettyMessage.getBody(),ResultCode.SUCCESS));
            }else{
                log.info("[{}]订阅[{}]失败,未定义",sn,topic);
                ctx.writeAndFlush(buildSubscribe(nettyMessage.getHeader(),nettyMessage.getBody(),ResultCode.ERROR));
            }
        }else{
            ctx.fireChannelRead(msg);
        }

    }

    private NettyMessage buildSubscribe( Header header, Map<String,Object> body,ResultCode resultCode){
        NettyMessage nettyMessage = new NettyMessage();
        header.setType(MessageType.SUBSCRIBE_RESP.value());
        body.put("code", resultCode.code());
        nettyMessage.setHeader(header);
        nettyMessage.setBody(body);
        return nettyMessage;
    }

}
