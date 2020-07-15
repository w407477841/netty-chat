package com.wyf.netty.server;

import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.server.auto.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class,args);
        NettyServer nettyServer = new NettyServer();
        try {
            // 初始化 topic列表
            String [] topics = {"abc","def"};

            for (String topic : topics) {
                Const.subscribeMap.put(topic,new HashSet<>());
            }
            nettyServer.bind(19999,"127.0.0.1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
