package com.GM.constant;

/**
 * 业务提示信息常量。
 * 统一管理异常/成功提示文案，避免在各个 Service 中硬编码字符串。
 */
public class MessageConstant {

    /** 登录成功 */
    public static final String LOGIN_SUCCESS = "登录成功";

    /** 登录失败 */
    public static final String LOGIN_FAILED = "登录失败";

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

    /** 地址簿不存在或不属于当前用户 */
    public static final String ADDRESS_BOOK_IS_NULL = "地址簿不存在，请重新选择收货地址";

    /** 购物车为空 */
    public static final String SHOPPING_CART_IS_NULL = "购物车为空，不能下单";

    /** 订单不存在或不属于当前用户 */
    public static final String ORDER_NOT_FOUND = "订单不存在";

    /** 订单状态异常（当前状态不允许该操作） */
    public static final String ORDER_STATUS_ERROR = "订单状态异常，无法执行该操作";

}