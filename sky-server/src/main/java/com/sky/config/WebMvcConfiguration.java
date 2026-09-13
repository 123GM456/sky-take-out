package com.sky.config;

import com.sky.interceptor.JwtTokenAdminInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置类，注册拦截器、静态资源映射等全局组件。
 * <p>管理端所有 /admin/** 路径（登录接口除外）需要 JWT 令牌校验。</p>
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册管理端 JWT 校验拦截器。
     * 拦截 /admin/** 下的所有请求，但排除 /admin/employee/login（登录不需要令牌）。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    }

}