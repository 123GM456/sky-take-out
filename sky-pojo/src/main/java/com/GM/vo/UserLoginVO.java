package com.GM.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 用户登录 VO（视图对象），微信小程序登录成功后返回给前端。
 * <p>包含用户基本信息 + JWT 令牌，前端收到后将令牌存入本地存储供后续接口鉴权。</p>
 */
@Data
@Builder  // 使用 Builder 模式链式构造：UserLoginVO.builder().id(1).openid("xxx").token("yyy").build()
public class UserLoginVO {

    /** 用户 ID（数据库主键） */
    private Long id;

    /** 微信用户唯一标识（OpenID，同一用户在同一小程序下固定不变） */
    private String openid;

    /** JWT 令牌（前端后续请求需放入请求头 user-token 中携带） */
    private String token;
}