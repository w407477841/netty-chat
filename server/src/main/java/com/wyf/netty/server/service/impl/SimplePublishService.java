package com.wyf.netty.server.service.impl;

import com.wyf.netty.server.service.IPublishService;

public class SimplePublishService implements IPublishService {


    @Override
    public String[] topics() {
        return new String[]{"abc","def"};
    }

    @Override
    public int port() {
        return 19999;
    }

    @Override
    public String host() {
        return "127.0.0.1";
    }


}
