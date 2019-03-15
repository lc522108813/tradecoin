package com.leichuang.tradecoin.exception;

public class CoinException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    private int code;
    private String msg;
    private Object data;

    public CoinException(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CoinException(ErrorCode errorCode) {
        this.code=errorCode.getCode();
        this.msg=errorCode.getMsg();
    }


    public CoinException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
