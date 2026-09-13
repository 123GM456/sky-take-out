package com.sky.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 员工登录 VO（视图对象），返回给前端的登录成功信息。
 * 包含员工基本信息和 JWT 令牌。
 */
@Data
@Builder
public class EmployeeLoginVO {

    /** 员工 ID */
    private Long id;

    /** 员工真实姓名 */
    private String name;

    /** 登录账号 */
    private String username;

    /** JWT 令牌（前端后续请求需在请求头中携带） */
    private String token;

}