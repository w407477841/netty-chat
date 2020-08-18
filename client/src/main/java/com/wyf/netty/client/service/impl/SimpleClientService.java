package com.wyf.netty.client.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.wyf.netty.client.properties.NettyProperties;
import com.wyf.netty.client.service.IClientService;
import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.enums.MessageType;

import java.util.HashMap;
import java.util.Map;

public class SimpleClientService  implements IClientService {

    public SimpleClientService(NettyProperties nettyProperties) {
        this.nettyProperties = nettyProperties;
    }

    private NettyProperties nettyProperties;

    @Override
    public void send(String topic, String content) {
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setSn(nettyProperties.getSn());
        header.setType(MessageType.MESSAGE_REQ.value());
        header.setSerialNumber(RandomUtil.randomLong());
        Map<String,Object> body = new HashMap<>();
        body.put("content",content);
        body.put("topic",topic);
        nettyMessage.setHeader(header);
        nettyMessage.setBody(body);
        if(Const.client_ctx!=null){
            Const.client_ctx.writeAndFlush(nettyMessage);
        }
    }

    @Override
    public void sub(String topic) {
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setSn(nettyProperties.getSn());
        header.setType(MessageType.SUBSCRIBE_REQ.value());
        Map<String,Object> body = new HashMap<>();
        body.put("topic",topic);
        nettyMessage.setHeader(header);
        nettyMessage.setBody(body);
        if(Const.client_ctx!=null){
            Const.client_ctx.writeAndFlush(nettyMessage);
        }
    }
}
