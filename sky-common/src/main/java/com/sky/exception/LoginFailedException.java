package com.sky.exception;

/**
 * 登录失败异常。
 * 用户名不存在、密码错误或账号被禁用时抛出。
 */
public class LoginFailedException extends BaseException {

    public LoginFailedException(String message) {
        super(message);
    }

}