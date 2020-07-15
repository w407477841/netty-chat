package com.wyf.netty.common.protocol;

import lombok.Data;

import java.util.Set;

@Data
public class NettySession {

    private String token;

    private String sn;

    private Set<String> topic;

    private boolean ok;

}
