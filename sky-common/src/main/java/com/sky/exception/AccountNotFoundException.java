package com.sky.exception;

/**
 * 账号不存在异常。
 */
public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException(String message) {
        super(message);
    }

}