package com.wyf.test.api;

import com.wyf.netty.client.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class MessageAPI {

    @Autowired
    private IClientService clientService;
    @RequestMapping("send")
    public String send(String topic){

        clientService.send(topic,"hello");

        return "ok";
    }
    @RequestMapping("sub")
    public String sub(String topic){

        clientService.sub(topic);

        return "ok";

    }


}
