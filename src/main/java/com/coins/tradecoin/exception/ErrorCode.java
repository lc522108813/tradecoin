package com.coins.tradecoin.exception;

import java.io.Serializable;

public class ErrorCode implements Serializable  {

    private static final long serialVersionUID = -1L;

    private final int code;

    private final String msg;


    public ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
