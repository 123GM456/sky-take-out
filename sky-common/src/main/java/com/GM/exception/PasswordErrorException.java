package com.GM.exception;

/**
 * 密码错误异常。
 * <p>登录时密码比对不一致时抛出。</p>
 */
public class PasswordErrorException extends BaseException {

    public PasswordErrorException(String message) {
        super(message);
    }

}