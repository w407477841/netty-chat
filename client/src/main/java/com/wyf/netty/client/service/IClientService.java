package com.wyf.netty.client.service;

public interface IClientService {

    void send(String topic,String content);

    void sub(String topic);

}
