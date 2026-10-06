package com.GM.interceptor;

import com.GM.constant.JwtClaimsConstant;
import com.GM.context.BaseContext;
import com.GM.properties.JwtProperties;
import com.GM.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用户端 JWT 令牌校验拦截器（HandlerInterceptor / 拦截器层）。
 * <p>拦截 /user/** 请求（登录接口除外），校验请求头中的 JWT 令牌，
 * 验证通过后将用户 ID 存入 ThreadLocal 供 Service 层使用。</p>
 *
 * <p>在 {@code WebMvcConfiguration} 中注册，排除 {@code /user/user/login}。</p>
 */
@Component      // 注册为 Spring Bean，由 WebMvcConfiguration 注入
@Slf4j
@RequiredArgsConstructor
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    /**
     * 请求处理前的 JWT 校验。
     *
     * @return true=放行该请求，false=拒绝并设置 HTTP 401
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        // 非 HandlerMethod（如静态资源、OPTIONS 预检请求）直接放行，不做校验
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从请求头中获取 token，key 由 application.yml 中 sky.jwt.user-token-name 指定
        String token = request.getHeader(jwtProperties.getUserTokenName());

        // 请求头中无 token → 返回 401
        if (token == null || token.isEmpty()) {
            log.warn("用户端请求缺少令牌：{} {}", request.getMethod(), request.getRequestURI());
            response.setStatus(401);
            return false;
        }

        try {
            // 解析并验证 JWT 签名和有效期（使用用户端独立密钥）
            Claims claims = JwtUtil.parseToken(jwtProperties.getUserSecretKey(), token);
            Long userId = claims.get(JwtClaimsConstant.USER_ID, Long.class);

            // 将当前登录用户 ID 存入 ThreadLocal，供 Service/Controller 通过 BaseContext 获取
            BaseContext.setCurrentId(userId);
            log.info("用户端令牌验证通过，userId={}", userId);

            return true;

        } catch (ExpiredJwtException e) {
            // 令牌已过期（有效期由 sky.jwt.user-ttl 控制）
            log.warn("用户端令牌已过期");
            response.setStatus(401);
            return false;
        } catch (Exception e) {
            // 签名不匹配、令牌格式错误等情况
            log.warn("用户端令牌验证失败：{}", e.getMessage());
            response.setStatus(401);
            return false;
        }
    }

    /**
     * 请求处理完成后清理线程上下文，防止 Tomcat 线程池复用导致数据污染。
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        BaseContext.removeCurrentId();
    }
}