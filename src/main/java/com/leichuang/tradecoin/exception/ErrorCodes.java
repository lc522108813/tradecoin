package com.leichuang.tradecoin.exception;

public class ErrorCodes {

    /** 成功 **/
    public static final ErrorCode SUCCESS=new ErrorCode(200,"success");

    /** 货币种类无效 **/
    public static final ErrorCode INVALID_COIN_TYPE=new ErrorCode(13001,"wrong coin type");

    /** 参数错误 **/
    public static final ErrorCode INVALID_PARAM_ERROR=new ErrorCode(9999,"wrong params");


}
