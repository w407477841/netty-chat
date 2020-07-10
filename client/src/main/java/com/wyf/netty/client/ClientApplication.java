package com.wyf.netty.client;

import com.wyf.netty.client.auto.NettyClient;
import com.wyf.netty.client.properties.NettyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(value= NettyProperties.class)
public class ClientApplication {


    @Bean()
    NettyClient nettyClient(NettyProperties p){
        NettyClient  nettyClient =new NettyClient(p.getRemotePort(),p.getRemoteHost(),p.getSn(),p.getLocalPort(),p.getLocalHost());
        return nettyClient;
    }

    public static void main(String[] args) {

        SpringApplication.run(ClientApplication.class,args);

    }

}
