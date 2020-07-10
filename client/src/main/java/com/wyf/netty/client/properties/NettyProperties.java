package com.wyf.netty.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "netty")
@Data
public class NettyProperties {

    private  String sn = "124";
    private  Integer localPort = 9998;
    private  String localHost = "127.0.0.1";
    private  Integer remotePort = 19999;
    private  String remoteHost = "127.0.0.1";

}
