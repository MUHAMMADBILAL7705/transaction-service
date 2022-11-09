package com.transaction.config.advice;

public class ApiError {

    public static final int ERR_GENERIC = 0;
    public static final int ERR_AUTHENTICATION_FAILED = 30;
    public static final int ERR_AUTHORIZATION_FAILED = 31;
    public static final int ERR_ENTITY_NOT_FOUND = 40;
    public static final int ERR_RELATED_ENTITY_NOT_FOUND = 41;
    public static final int ERR_PARENT_ENTITY_NOT_FOUND = 42;
    public static final int ERR_DUPLICATE_ENTRY = 43;
    public static final int ERR_INVALID_DATA = 44;
    public static final int ERR_DATABASE = 50;
    public static final int ERR_IO = 51;
    public static final int ERR_ENDPOINT_DOWN = 60;
    private String message;
    private int code;

    public ApiError(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
