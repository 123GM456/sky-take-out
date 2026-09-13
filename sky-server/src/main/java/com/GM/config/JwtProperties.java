package com.GM.config;

// Lombok：自动生成 getter/setter、toString、equals、hashCode（配合 @ConfigurationProperties 必须要有 setter）
import lombok.Data;
// Spring Boot：将 application.yml 中指定前缀的配置项绑定到当前类的同名字段
import org.springframework.boot.context.properties.ConfigurationProperties;
// Spring：将当前类注册为 Bean，让 Spring 容器管理其生命周期
import org.springframework.stereotype.Component;

/**
 * JWT 配置属性类，绑定 application.yml 中 "sky.jwt" 前缀的配置。
 * <p>管理端（admin）和用户端（user）使用独立的密钥和过期时间，
 * 避免令牌跨端滥用。</p>
 */
// Spring：将当前类注册为 Bean，交给 Spring 容器管理
@Component
// Spring Boot：将 application.yml 中以 "sky.jwt" 为前缀的配置项自动注入到当前类的同名字段
@ConfigurationProperties(prefix = "sky.jwt")
// Lombok：自动生成 getter/setter、toString、equals、hashCode
@Data
// 配置属性：读取 application.yml 中 sky.jwt 开头的配置项，注入到同名字段供全局使用
public class JwtProperties {

    /** 管理端员工登录的 JWT 签名密钥（字符串，由 JwtUtil 转为 SecretKey） */
    private String adminSecretKey;

    /** 管理端令牌过期时间（单位：毫秒，当前默认 2 小时 = 7200000） */
    private long adminTtl;

    /** 管理端令牌名称，对应前端请求头中的 key */
    private String adminTokenName;

}