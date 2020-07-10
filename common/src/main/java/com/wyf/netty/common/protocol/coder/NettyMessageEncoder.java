package com.wyf.netty.common.protocol.coder;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONUtil;
import com.wyf.netty.common.protocol.Header;
import com.wyf.netty.common.protocol.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage nettyMessage, ByteBuf byteBuf) throws Exception {

            String jsonStr = JSONUtil.toJsonStr(nettyMessage);
            log.info("NettyMessageEncoder[{}]",jsonStr);
            byte[] bytes = jsonStr.getBytes(CharsetUtil.CHARSET_UTF_8);
            byteBuf.writeBytes(bytes);
            byteBuf.writeBytes(Header.END.getBytes());
    }
}
