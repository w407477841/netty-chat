package com.wyf.netty.client.api;

import cn.hutool.core.util.RandomUtil;
import com.wyf.netty.client.properties.NettyProperties;
import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.enums.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/")
public class MessageAPI {
    @Autowired
    private NettyProperties nettyProperties;
    @GetMapping("send")
    public String send(String sn){
        if(nettyProperties.getSn().equals(sn)){
            return "no";
        }
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setSn(nettyProperties.getSn());
        header.setType(MessageType.MESSAGE_REQ.value());
        header.setSerialNumber(RandomUtil.randomLong());
        Map<String,Object> body = new HashMap<>();
        body.put("content","nihao");
        body.put("to",sn);
        nettyMessage.setHeader(header);
        nettyMessage.setBody(body);
        if(Const.client_ctx!=null){
            Const.client_ctx.writeAndFlush(nettyMessage);
        }
        return "ok";
    }

}
