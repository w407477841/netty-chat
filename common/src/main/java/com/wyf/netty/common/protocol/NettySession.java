package com.wyf.netty.common.protocol;

import lombok.Data;

@Data
public class NettySession {

    private String token;

    private String sn;

    private boolean ok;

}
