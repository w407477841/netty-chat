package com.wyf.netty.common.protocol.handler;

import cn.hutool.core.util.RandomUtil;
import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.NettySession;
import com.wyf.netty.common.protocol.enums.MessageType;
import com.wyf.netty.common.protocol.enums.ResultCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 验证客户端有效性
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        NettyMessage nettyMessage = (NettyMessage) msg;
        String sn = ((NettyMessage) msg).getHeader().getSn();
        if(nettyMessage.getHeader().getType()==MessageType.LOGIN_RESQ.value()){
            if(Const.nodeCheck.containsKey(sn)){
                // 重复登录
                ctx.writeAndFlush(buildResponse(sn,ResultCode.DUPLICATE_LOGIN));
            }else{
                InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = inetSocketAddress.getAddress().getHostAddress();
                boolean isOK = false;
                // 白名单
                for (String s : Const.whiteList) {
                    if(ip.equals(s)){
                        isOK = true;
                        break;
                    }
                }
                ctx.writeAndFlush(buildResponse(sn,isOK? ResultCode.SUCCESS :ResultCode.IP_FORBID));
                if(isOK){
                    // 保存连接
                    NettySession nettySession = new NettySession();
                    nettySession.setOk(true);
                    nettySession.setSn(sn);
                    nettySession.setToken(RandomUtil.randomString(16));
                    ctx.channel().attr(Const.NETTY_SESSION).set(nettySession);
                    Const.nodeCheck.put(sn,ctx);

                }
            }
        }else{
            ctx.fireChannelRead(msg);
        }





    }

    private NettyMessage buildResponse(String sn,ResultCode code){

        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setSn(sn);
        header.setType(MessageType.LOGIN_RESP.value());
        nettyMessage.setHeader(header);
        nettyMessage.getBody().put("code",code.code());
        nettyMessage.getBody().put("msg",code.msg());
        return nettyMessage;

    }

}
