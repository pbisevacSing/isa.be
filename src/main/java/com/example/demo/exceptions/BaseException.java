package com.example.demo.exceptions;

public class BaseException extends RuntimeException {
    public BaseException(String msg) {
        super(msg);
    }

    public BaseException() {
        super();
    }
}
