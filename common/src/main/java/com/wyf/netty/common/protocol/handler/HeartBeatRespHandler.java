package com.wyf.netty.common.protocol.handler;

import cn.hutool.core.util.RandomUtil;
import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.NettySession;
import com.wyf.netty.common.protocol.enums.MessageType;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

public class HeartBeatRespHandler extends ChannelHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        NettyMessage nettyMessage = (NettyMessage) msg;
        String sn = ((NettyMessage) msg).getHeader().getSn();
        if(nettyMessage.getHeader().getType()== MessageType.HEARTBEAT_REQ.value()){
            ctx.writeAndFlush(buildHeartbeat(sn));
        }else{
            ctx.fireChannelRead(msg);
        }

    }
    private NettyMessage buildHeartbeat(String sn){
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setSn(sn);
        header.setType(MessageType.HEARTBEAT_RESP.value());
        nettyMessage.setHeader(header);
        return nettyMessage;
    }
}
