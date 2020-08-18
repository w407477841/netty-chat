package com.wyf.netty.common.protocol.handler;

import com.wyf.netty.common.protocol.Const;
import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import com.wyf.netty.common.protocol.enums.MessageType;
import com.wyf.netty.common.protocol.enums.ResultCode;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 *  登录请求
 */
@Slf4j
public class LoginAuthReqHandler extends ChannelHandlerAdapter {

    private final String sn;

    public LoginAuthReqHandler(String sn) {
        this.sn = sn;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("发送登录");
        ctx.writeAndFlush(buildLoginReq());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;
        if(nettyMessage.getHeader()!=null){

            if(nettyMessage.getHeader().getType() == MessageType.LOGIN_RESP.value()){
                Map<String,Object> body = nettyMessage.getBody();
                String code = (String) body.get("code");
                if(ResultCode.SUCCESS.code().equals(code)){
                    Const.client_ctx = ctx;
                    ctx.fireChannelRead(msg);
                }else{
                    // 登录失败,关闭握手
                    ctx.close();
                }
            }else{
                ctx.fireChannelRead(msg);
            }
        }else{
            throw new Exception("缺失头部");
        }
    }

    private NettyMessage buildLoginReq(){
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setSn(sn);
        header.setType(MessageType.LOGIN_RESQ.value());
        nettyMessage.setHeader(header);
        return nettyMessage;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
