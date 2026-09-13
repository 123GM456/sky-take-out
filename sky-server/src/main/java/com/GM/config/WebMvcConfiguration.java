package com.GM.config;

// 项目拦截器：自定义的管理端 JWT 校验拦截器，需要注册到拦截器链中
import com.GM.interceptor.JwtTokenAdminInterceptor;
// Lombok：为 final 字段生成构造器注入（等价于手动 @Autowired）
import lombok.RequiredArgsConstructor;
// Spring：@Configuration 注解所在包，标记当前类为配置类
import org.springframework.context.annotation.Configuration;
// Spring MVC：InterceptorRegistry，用于向 Spring MVC 注册自定义拦截器
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
// Spring MVC：WebMvcConfigurer 接口，通过实现它来扩展 Spring MVC 配置
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置类，注册拦截器、静态资源映射等全局组件。
 * <p>管理端所有 /admin/** 路径（登录接口除外）需要 JWT 令牌校验。</p>
 */
// Spring：标记为配置类，等价于 XML 配置中的 <beans/>
@Configuration
// Lombok：为 final 字段生成构造器注入
@RequiredArgsConstructor
// 配置类：自定义 Spring MVC 全局配置（注册拦截器、路径映射等）
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册管理端 JWT 校验拦截器。
     * 拦截 /admin/** 下的所有请求，但排除 /admin/employee/login（登录不需要令牌）。
     */
    // Spring：标记当前方法重写/实现接口方法，编译器会验证签名是否匹配
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    }

}