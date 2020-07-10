package com.wyf.netty.common.protocol;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author root
 */
@Data
public final class NettyMessage {

    private Header header;

    private Map<String,Object> body = new HashMap<>();

}
