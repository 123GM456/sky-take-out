package com.GM.config;

import com.GM.interceptor.JwtTokenAdminInterceptor;
import com.GM.json.JacksonObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

/**
 * Web MVC 全局配置类（Config 层）。
 * <p>注册管理端 JWT 校验拦截器，以及自定义的 Jackson 日期格式消息转换器。</p>
 */
@Configuration  // 标记为配置类，Spring 自动加载其中的 @Bean 和方法配置
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册管理端 JWT 校验拦截器。
     * <p>拦截 /admin/** 所有请求，排除登录接口（/admin/employee/login），
     * 因为登录时还没有 token。</p>
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    }

    /**
     * 拓展 Spring MVC 消息转换器：将自定义的 JacksonObjectMapper 放在转换器链首位。
     * <p>这样所有 Controller 返回的 LocalDateTime/LocalDate 等 Java 8 时间类型
     * 都会按 yyyy-MM-dd HH:mm:ss 格式序列化，而不是默认的时间戳数组。</p>
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0, converter);  // 放到首位，优先级最高
    }
}