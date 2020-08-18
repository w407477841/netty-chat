package com.wyf.netty.common.protocol;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author root
 */
@Data
public final class NettyMessage {
    /** 消息头 */
    private Header header;
    /** 消息体 */
    private Map<String,Object> body = new HashMap<>();

}
