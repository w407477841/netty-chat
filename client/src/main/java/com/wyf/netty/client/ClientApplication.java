package com.wyf.netty.client;

import com.wyf.netty.client.auto.NettyClient;
import com.wyf.netty.client.properties.NettyProperties;
import com.wyf.netty.client.service.IClientService;
import com.wyf.netty.client.service.impl.SimpleClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value= NettyProperties.class)
public class ClientApplication {



    @Bean
    NettyClient nettyClient(NettyProperties p){
        NettyClient  nettyClient =new NettyClient(p.getRemotePort(),p.getRemoteHost(),p.getSn(),p.getLocalPort(),p.getLocalHost());
        return nettyClient;
    }
    @Bean
    IClientService clientService(NettyProperties p){

        return new SimpleClientService(p);

    }



}
