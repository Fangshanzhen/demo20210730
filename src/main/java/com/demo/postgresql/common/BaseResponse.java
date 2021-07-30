package com.demo.postgresql.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;

public class BaseResponse {
    private int code;
    private String msg;
    private Object data;


    public BaseResponse(int code, String msg, Object data) {
        this.code = 0;
        this.msg = null;
        this.data = null;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static BaseResponse createErrorResponse(int code, String msg, Object... args) {
        return new BaseResponse(code, msg, Arrays.asList(args));
    }

    public void setData(Object data) {
        this.data = data;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccessful() {
        return this.code == 0;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public Object getData() {
        return this.data;
    }
}
