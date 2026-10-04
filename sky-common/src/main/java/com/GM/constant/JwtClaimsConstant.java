package com.GM.constant;

/**
 * JWT 声明（Claims）键名常量。
 * <p>统一管理 JWT payload 中的 key，避免各处硬编码字符串。</p>
 */
public class JwtClaimsConstant {

    /** 员工用户 ID（管理端 token 中存储的 key） */
    public static final String EMPLOYEE_ID = "employeeId";

    /** 微信用户 ID（用户端 token 中存储的 key） */
    public static final String USER_ID = "userId";

    public static final String PHONE = "phone";

    public static final String USERNAME = "username";

    public static final String NAME = "name";

}