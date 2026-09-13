package com.GM.vo;

// Lombok：提供 Builder 模式构造对象，支持链式调用（.builder().id(1).name("xxx").build()）
import lombok.Builder;
// Lombok：自动生成 getter/setter、toString、equals、hashCode
import lombok.Data;

/**
 * 员工登录 VO（视图对象），返回给前端的登录成功信息。
 * 包含员工基本信息和 JWT 令牌。
 */
// Lombok：自动生成 getter/setter、toString、equals、hashCode、canEqual
@Data
// Lombok：提供 Builder 模式构造对象（如 EmployeeLoginVO.builder().id(1).build()）
@Builder
// VO：登录成功后返回给前端的视图对象，包含员工基本信息 + JWT 令牌
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