package com.wyf.netty.common.protocol.enums;

public enum MessageType {
    /** 登录 */
    LOGIN_RESQ(1),
    /** 登录应答 */
    LOGIN_RESP(2),

    HEARTBEAT_REQ(3),
    HEARTBEAT_RESP(4),

    MESSAGE_REQ(5),
    MESSAGE_RESP(6),
    MESSAGE_SWAP_REQ(7),
    MESSAGE_SWAP_RESP(8),


    SUBSCRIBE_REQ(9),
    SUBSCRIBE_RESP(10),
    UNSUBSCRIBE_REQ(11),
    UNSUBSCRIBE_RESP(12),
    ;

    private  int type;

    MessageType(int type) {
        this.type = type;
    }

    public int value(){
        return type;
    }

}
