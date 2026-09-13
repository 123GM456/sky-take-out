package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.config.JwtProperties;
import com.sky.utils.JwtUtil;
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
 * 管理端 JWT 令牌校验拦截器（HandlerInterceptor）。
 * <p>拦截 /admin/** 请求，从请求头中提取 token 并验证签名和有效期。
 * 验证通过后将员工 ID 存入线程上下文（BaseContext），供后续业务层使用。</p>
 *
 * <p>注意：登录接口 /admin/employee/login 已在 WebMvcConfiguration 中排除，
 * 不会被此拦截器拦截。</p>
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    /**
     * 请求处理前的 JWT 校验。
     *
     * @return true=放行，false=拒绝并设置 HTTP 401
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        // 非 HandlerMethod（如静态资源、预检请求）直接放行，不校验令牌
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从请求头中获取 token，key 由 application.yml 中 sky.jwt.admin-token-name 指定
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        // 请求头中无 token → 返回 401
        if (token == null || token.isEmpty()) {
            log.warn("管理端请求缺少令牌：{} {}", request.getMethod(), request.getRequestURI());
            response.setStatus(401);
            return false;
        }

        try {
            // 解析并验证 JWT 签名，同时获取过期时间
            Claims claims = JwtUtil.parseToken(jwtProperties.getAdminSecretKey(), token);
            Long employeeId = claims.get(JwtClaimsConstant.EMPLOYEE_ID, Long.class);

            // 将当前登录员工 ID 存入 ThreadLocal，后续 Controller/Service 中可直接通过 BaseContext 获取
            BaseContext.setCurrentId(employeeId);
            log.info("管理端令牌验证通过，employeeId={}", employeeId);

            return true;

        } catch (ExpiredJwtException e) {
            // 令牌已过期（过期时间在生成时由 sky.jwt.admin-ttl 控制）
            log.warn("管理端令牌已过期");
            response.setStatus(401);
            return false;
        } catch (Exception e) {
            // 签名不匹配、令牌格式错误等情况
            log.warn("管理端令牌验证失败：{}", e.getMessage());
            response.setStatus(401);
            return false;
        }
    }

    /**
     * 请求处理完成后清理线程上下文，防止 ThreadLocal 内存泄漏。
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        BaseContext.removeCurrentId();
    }

}