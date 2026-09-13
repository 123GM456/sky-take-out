package com.GM.interceptor;

// 项目常量：JWT 声明中键名（EMPLOYEE_ID），用于从 token 中提取员工 ID
import com.GM.constant.JwtClaimsConstant;
// 项目工具：线程上下文（ThreadLocal），暂存当前登录员工 ID 供 Controller/Service 获取
import com.GM.context.BaseContext;
// 项目配置：读取 application.yml 中 sky.jwt 的配置项（密钥、过期时间、令牌名）
import com.GM.config.JwtProperties;
// 项目工具：JWT 令牌的创建和解析
import com.GM.utils.JwtUtil;
// JJWT：解析 token 后得到的声明体，从中提取自定义数据（如 employeeId）
import io.jsonwebtoken.Claims;
// JJWT：令牌过期时抛出的特定异常，需单独处理并返回 401
import io.jsonwebtoken.ExpiredJwtException;
// Jakarta Servlet：HTTP 请求对象，用于读取请求头中的 token
import jakarta.servlet.http.HttpServletRequest;
// Jakarta Servlet：HTTP 响应对象，用于设置 401 状态码
import jakarta.servlet.http.HttpServletResponse;
// Lombok：为 final 字段生成构造器注入
import lombok.RequiredArgsConstructor;
// Lombok：为当前类生成 log 日志对象
import lombok.extern.slf4j.Slf4j;
// Spring：将当前类注册为 Bean，使其可被注入到 WebMvcConfiguration 中使用
import org.springframework.stereotype.Component;
// Spring MVC：封装了处理方法和方法所属 Controller 的信息，用于区分静态资源
import org.springframework.web.method.HandlerMethod;
// Spring MVC：拦截器接口，实现 preHandle / afterCompletion 方法
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 管理端 JWT 令牌校验拦截器（HandlerInterceptor）。
 * <p>拦截 /admin/** 请求，从请求头中提取 token 并验证签名和有效期。
 * 验证通过后将员工 ID 存入线程上下文（BaseContext），供后续业务层使用。</p>
 *
 * <p>注意：登录接口 /admin/employee/login 已在 WebMvcConfiguration 中排除，
 * 不会被此拦截器拦截。</p>
 */
// Spring：将拦截器注册为 Bean，方便在 WebMvcConfiguration 中注入
@Component
// Lombok：生成 log 日志对象
@Slf4j
// Lombok：为 final 字段生成构造器注入
@RequiredArgsConstructor
// 拦截器：校验管理端请求的 JWT 令牌有效性，通过后将用户 ID 存入 ThreadLocal 供后续使用
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    /**
     * 请求处理前的 JWT 校验。
     *
     * @return true=放行，false=拒绝并设置 HTTP 401
     */
    // @Override：通知编译器覆写 HandlerInterceptor.preHandle 方法
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
    // @Override：通知编译器覆写 HandlerInterceptor.afterCompletion 方法
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        BaseContext.removeCurrentId();
    }

}