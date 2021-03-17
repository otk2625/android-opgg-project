package com.cos.javagg.dto;


public class CMRespDto<T> {
    private int resultCode;
    private T data;

    public CMRespDto(int resultCode, T data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
