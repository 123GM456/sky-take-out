package com.GM.constant;

/**
 * 业务提示信息常量。
 * 统一管理异常/成功提示文案，避免在各个 Service 中硬编码字符串。
 */
public class MessageConstant {

    /** 登录成功 */
    public static final String LOGIN_SUCCESS = "登录成功";

    /** 密码错误 */
    public static final String PASSWORD_ERROR = "密码错误";

    /** 账号不存在 */
    public static final String ACCOUNT_NOT_FOUND = "账号不存在";

    /** 账号已被禁用 */
    public static final String ACCOUNT_LOCKED = "账号已被禁用";

    /** 未知错误 */
    public static final String UNKNOWN_ERROR = "未知错误";

    /** 用户名重复 */
    public static final String USERNAME_DUPLICATE = "已存在，请重新输入";

}