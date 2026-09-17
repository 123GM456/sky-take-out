package com.GM.exception;

/**
 * 账号不存在异常。
 * <p>登录时用户名在数据库中不存在时抛出。</p>
 */
public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException(String message) {
        super(message);
    }

}