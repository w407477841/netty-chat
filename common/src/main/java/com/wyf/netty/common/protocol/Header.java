package com.wyf.netty.common.protocol;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author root
 */
@Data
public final class Header {
    /** 结束符 */
    public static String END = "#@#";
    /** 设备号 */
    private String sn;
    /** 流水号*/
    private long serialNumber;
    /** 消息类型 */
    private int type;
    /** 附件 */
    private Map<String,Object> attachment = new HashMap<>();

}
