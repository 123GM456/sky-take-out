package com.GM.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置属性类（Config 层），绑定 application.yml 中 {@code sky.jwt} 前缀的配置。
 * <p>管理端和用户端使用独立的密钥、过期时间和请求头名称，避免令牌跨端滥用。</p>
 */
@Component                                   // 注册为 Spring Bean
@ConfigurationProperties(prefix = "sky.jwt") // 自动读取 yml 中 sky.jwt.* 配置注入到同名字段
@Data
public class JwtProperties {

    /** 管理端 JWT 签名密钥（字符串，JwtUtil 内部转为 HMAC-SHA256 SecretKey） */
    private String adminSecretKey;

    /** 管理端令牌有效期（毫秒，yml 中默认 7200000 = 2 小时） */
    private long adminTtl;

    /** 管理端令牌请求头名称（前端发请求时携带此请求头） */
    private String adminTokenName;

    /** 用户端 JWT 签名密钥（微信小程序登录用，与管理端隔离） */
    private String userSecretKey;

    /** 用户端令牌有效期（毫秒，yml 中默认 7200000 = 2 小时） */
    private long userTtl;

    /** 用户端令牌请求头名称（小程序前端发请求时携带此请求头） */
    private String userTokenName;

}