package com.GM;

import com.GM.constant.JwtClaimsConstant;
import com.GM.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * 用户端 JWT 令牌生成器（仅供本地联调使用）。
 * <p>微信登录接口需要真实的微信授权 code，无法在 Apifox 中直接调用，
 * 故通过本测试手动签发一个用户端令牌：复制打印结果，填入 Apifox 请求头
 * {@code authentication} 中，即可测试 /user/** 下的接口。</p>
 *
 * <p>密钥与有效期须与 application.yml 中 sky.jwt 的用户端配置一致，否则后端验签失败返回 401。</p>
 */
public class JwtTokenGeneratorTest {

    // 用户端签名密钥，与 application.yml 的 sky.jwt.user-secret-key 一致
    private static final String USER_SECRET_KEY = "sky-take-out-user-secret-key-GM666";

    // 令牌有效期（毫秒），与 application.yml 的 sky.jwt.user-ttl 一致
    private static final long USER_TTL = 720000000L;

    // 令牌所属用户 ID，须为 user 表中真实存在且已有测试数据的记录
    private static final Long USER_ID = 1L;

    /**
     * 生成用户端 JWT 令牌并打印到控制台。
     * <p>打印的令牌可直接粘贴到 Apifox 请求头 authentication 中使用。</p>
     */
    @Test
    public void generateUserToken() {
        Map<String, Object> claims = new HashMap<>();
        // 拦截器解析后取出该值写入 BaseContext，作为当前登录用户 ID
        claims.put(JwtClaimsConstant.USER_ID, USER_ID);

        String token = JwtUtil.createToken(USER_SECRET_KEY, USER_TTL, claims);

        System.out.println("userId = " + USER_ID);
        System.out.println("token  = " + token);
    }
}