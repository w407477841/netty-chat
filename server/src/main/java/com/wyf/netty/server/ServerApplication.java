package com.wyf.netty.server;

import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.server.auto.NettyServer;
import com.wyf.netty.server.service.IPublishService;
import com.wyf.netty.server.service.impl.SimplePublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
@Slf4j
public class ServerApplication {


    /**
     * 默认实现
     * 初始化 topic
     * 设置连接ip、端口
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = IPublishService.class)
    public IPublishService publishService(){
        return new SimplePublishService();
    }

    @Bean
    public NettyServer nettyServer(){
        // 初始化 topic列表
        String [] topics = publishService().topics();
        log.info("topic:{}",topics.toString());
        for (String topic : topics) {
            Const.subscribeMap.put(topic,new HashSet<>());
        }
        NettyServer nettyServer = new NettyServer(publishService().port(),publishService().host());
        return nettyServer;
    }


}
