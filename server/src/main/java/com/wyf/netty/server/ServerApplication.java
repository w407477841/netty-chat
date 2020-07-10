package com.wyf.netty.server;

import com.wyf.netty.server.auto.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class,args);
        NettyServer nettyServer = new NettyServer();
        try {
            nettyServer.bind(19999,"127.0.0.1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
