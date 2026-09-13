package com.sky.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT 令牌工具，使用 HMAC-SHA256 算法（对称加密）对令牌进行签名和验签。
 * <p>生成时传入 claims（如 employeeId）、密钥和过期时间，返回 compact 格式的 JWT 字符串。
 * 解析时使用相同密钥验签，若签名不匹配或已过期则抛出异常。</p>
 *
 * <p>注意：secretKey 需满足 HMAC-SHA256 最低密钥长度要求（256 bits，即 32 字节），
 * 配置中应使用足够长的字符串。</p>
 */
public class JwtUtil {

    /**
     * 生成 JWT 令牌。
     *
     * @param secretKey  签名密钥（字符串形式，内部转为 SecretKey）
     * @param ttlMillis  令牌有效期（毫秒），从当前时间开始计算
     * @param claims     放入令牌 payload 的自定义声明，如 {"employeeId": 1}
     * @return 签名的 JWT 字符串（compact 格式）
     */
    public static String createToken(String secretKey, long ttlMillis, Map<String, Object> claims) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(claims)                               // payload 自定义数据（如 employeeId）
                .issuedAt(new Date())                         // 签发时间
                .expiration(new Date(System.currentTimeMillis() + ttlMillis))  // 到期时间
                .signWith(key)                                // 用 HMAC-SHA256 签名
                .compact();
    }

    /**
     * 解析 JWT 令牌，验证签名并提取 claims。
     *
     * @param secretKey 与生成时相同的密钥
     * @param token     JWT 字符串
     * @return 解析后的 claims（含自定义数据和标准声明）
     * @throws io.jsonwebtoken.ExpiredJwtException     令牌已过期
     * @throws io.jsonwebtoken.security.SecurityException 签名不匹配
     */
    public static Claims parseToken(String secretKey, String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()
                .verifyWith(key)                              // 设置验证密钥
                .build()
                .parseSignedClaims(token)                     // 验签并解析
                .getPayload();                                // 获取 claims
    }

}