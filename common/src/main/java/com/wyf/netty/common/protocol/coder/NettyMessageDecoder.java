package com.wyf.netty.common.protocol.coder;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONUtil;
import com.wyf.netty.common.protocol.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class NettyMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        int length = byteBuf.readableBytes();
        if(length>4){
            byte [] bytes = new byte[length];
            byteBuf.readBytes(bytes);
            String json = new String(bytes, CharsetUtil.CHARSET_UTF_8);
            log.info("NettyMessageDecoder[{}]",json);
            NettyMessage nettyMessage = JSONUtil.toBean(json,NettyMessage.class);
            list.add(nettyMessage);
        }


    }
}
