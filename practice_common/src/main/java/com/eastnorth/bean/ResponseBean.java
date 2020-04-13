package com.eastnorth.utils;

/**
 * @author zuojianhou
 * @date   2020/4/9
 * @Description:
 */
public class ResponseBean<T> {

    private int status;

    private String msg;

    private boolean result;

    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
