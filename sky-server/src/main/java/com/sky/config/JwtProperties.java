package com.sky.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置属性类，绑定 application.yml 中 "sky.jwt" 前缀的配置。
 * <p>管理端（admin）和用户端（user）使用独立的密钥和过期时间，
 * 避免令牌跨端滥用。</p>
 */
@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data
public class JwtProperties {

    /** 管理端员工登录的 JWT 签名密钥（字符串，由 JwtUtil 转为 SecretKey） */
    private String adminSecretKey;

    /** 管理端令牌过期时间（单位：毫秒，当前默认 2 小时 = 7200000） */
    private long adminTtl;

    /** 管理端令牌名称，对应前端请求头中的 key */
    private String adminTokenName;

}