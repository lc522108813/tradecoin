package com.coins.tradecoin.entity;

import com.coins.tradecoin.exception.ErrorCode;
import com.coins.tradecoin.exception.ErrorCodes;

import java.io.Serializable;


/** 标准返回体 **/
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer code;

    private String msg;

    private T data;


    public static Result buildSuccess(){
        return new Result(ErrorCodes.SUCCESS);
    }

    public static<T> Result<T> buildSuccess(T data){
        return new Result<T>(200,"success",data);
    }


    public static Result buildFailure(ErrorCode errorCode){
        return new Result(errorCode);
    }

    public Result(ErrorCode errorCode){
        this.code=errorCode.getCode();
        this.msg=errorCode.getMsg();
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code) {
        this.code = code;
    }



    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
