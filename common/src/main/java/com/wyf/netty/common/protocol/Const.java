package com.wyf.netty.common.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Const {
    /**===== 服务端属性 =====*/
    public static AttributeKey<NettySession> NETTY_SESSION = AttributeKey.valueOf("netty.session");
    /** 客户端列表  */
    public static Map<String, ChannelHandlerContext> nodeCheck = new ConcurrentHashMap<>();
    /** 白名单 */
    public static String [] whiteList = { "127.0.0.1" };

    /**===== 客户端属性 =====*/
    /** 客户端上下文  */
    public static  ChannelHandlerContext client_ctx = null;

}
