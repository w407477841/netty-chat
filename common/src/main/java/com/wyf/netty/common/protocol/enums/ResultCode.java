package com.wyf.netty.common.protocol.enums;

import cn.hutool.core.util.StrUtil;

public enum ResultCode {

    SUCCESS("200","成功"),
    ERROR("400","失败"),
    IP_FORBID("401","IP被禁止"),
    DUPLICATE_LOGIN("402","重复登录"),
    ;

    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code(){
        return this.code;
    }
    public String msg(){
        return this.msg;
    }
    public String msg(String code){
        if(StrUtil.isNotBlank(code)){
            for (ResultCode value : values()) {
                    if(value.code().equals(code)){
                        return value.msg();
                    }
            }
        }
        return "";

    }

}
