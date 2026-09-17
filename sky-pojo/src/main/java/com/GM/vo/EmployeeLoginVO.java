package com.GM.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 员工登录 VO（视图对象），登录成功后返回给前端。
 * <p>包含员工基本信息 + JWT 令牌，前端收到后将令牌存入请求头供后续接口鉴权。</p>
 */
@Data
@Builder  // 使用 Builder 模式链式构造：EmployeeLoginVO.builder().id(1).build()
public class EmployeeLoginVO {

    /** 员工 ID */
    private Long id;

    /** 员工真实姓名 */
    private String name;

    /** 登录账号 */
    private String username;

    /** JWT 令牌（前端后续请求需放入请求头 Authorization 中携带） */
    private String token;
}