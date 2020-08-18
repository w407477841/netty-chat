package com.wyf.test.impl;

import com.wyf.netty.server.service.IPublishService;
import org.springframework.stereotype.Service;

@Service(value = "publishService")
public class ServerImpl implements IPublishService {
    @Override
    public String[] topics() {
        return new String[]{"abc","def","zxc"};
    }

    @Override
    public int port() {
        return 19999;
    }

    @Override
    public String host() {
        return "localhost";
    }
}
