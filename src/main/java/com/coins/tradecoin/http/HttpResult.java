package com.coins.tradecoin.http;

public class HttpResult {

    /** http status */
    private Integer code;
    /** http response content */
    private String body;


    public HttpResult(Integer code) {
        this.code = code;
    }

    public HttpResult(Integer code, String body) {
        this.code = code;
        this.body = body;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
