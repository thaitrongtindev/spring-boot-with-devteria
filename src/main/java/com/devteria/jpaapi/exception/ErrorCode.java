package com.devteria.jpaapi.exception;

public enum ErrorCode {
    USER_EXSITED(101, "User existed"),
    USER_NOT_EXSITED(105, "User not existed"),
    PASSWORD_INVALID(103, "Password must be at least 3 characters"),
    INVALID_KEY(103, "Invalid Key"),
    UNAUTHENTICATED(106, "Unauthenticated"),
    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
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
