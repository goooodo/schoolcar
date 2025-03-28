package com.snnubus.entity;

public class Result {
    private Object data; // 返回的数据
    private Integer code; // 状态码
    private String msg; // 返回消息

    public Result(Object data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Object data, Integer code) {
        this.data = data;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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

}