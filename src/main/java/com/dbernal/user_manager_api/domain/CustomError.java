package com.dbernal.user_manager_api.domain;

import java.io.Serializable;

public class CustomError implements Serializable {

    private int code;
    private String message;

    public CustomError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}