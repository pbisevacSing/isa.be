package com.example.demo.exceptions.user;

import com.example.demo.exceptions.BaseException;

public class UserAlreadyExistException extends BaseException {
    public UserAlreadyExistException(String msg) {
        super(msg);
    }

    public UserAlreadyExistException() {
        super();
    }
}
