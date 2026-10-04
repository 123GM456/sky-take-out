package com.GM.config;

import com.GM.interceptor.JwtTokenAdminInterceptor;
import com.GM.json.JacksonObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

/**
 * Web MVC 配置类（Config 层）。
 * <p>集中管理 Spring MVC 的三大核心配置：</p>
 * <ul>
 *   <li>JWT 拦截器注册（控制哪些接口需要登录认证）</li>
 *   <li>JSON 序列化器（统一时间格式、空值处理等）</li>
 *   <li>URL 路径匹配规则（尾斜杠兼容）</li>
 * </ul>
 */
@Configuration                   // 标记为 Spring 配置类，启动时自动加载
@RequiredArgsConstructor          // 为 final 字段生成构造器注入（替代 @Autowired）
public class WebMvcConfiguration implements WebMvcConfigurer {

    /** JWT 管理端令牌校验拦截器（用于验证请求是否携带有效 token）*/
    private final JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册拦截器并配置拦截规则。
     * <p>所有 /admin/** 开头的请求都需要 JWT 认证，
     * 但排除登录接口（否则无法获取 token）。</p>
     *
     * @param registry 拦截器注册表（Spring 自动注入）
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 JWT 拦截器
        registry.addInterceptor(jwtTokenAdminInterceptor)
                // 拦截所有 /admin/** 路径的请求（需要登录才能访问）
                .addPathPatterns("/admin/**")
                // 排除登录接口（未登录时必须能访问，否则无法获取 token）
                .excludePathPatterns("/admin/employee/login");
    }

    /**
     * 扩展 Spring MVC 的消息转换器列表。
     * <p>在默认转换器之前插入自定义的 Jackson JSON 转换器，
     * 用于统一处理 JSON 序列化/反序列化的格式（如时间格式、null 值处理等）。</p>
     *
     * @param converters Spring 默认的消息转换器列表（按优先级排序）
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建自定义的 Jackson JSON 转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 使用项目定制的 ObjectMapper（封装了时间格式、空值处理等配置）
        converter.setObjectMapper(new JacksonObjectMapper());
        // 插入到列表首位（最高优先级），确保优先使用自定义转换器
        converters.add(0, converter);
    }

    /**
     * 配置 URL 路径匹配规则。
     * <p>启用尾斜杠匹配：访问 /admin/employee 和 /admin/employee/
     * 都能正确映射到同一个 Controller 方法。</p>
     *
     * @param configurer 路径匹配配置器（Spring 自动注入）
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 启用尾斜杠匹配（true = /path 和 /path/ 等价）
        configurer.setUseTrailingSlashMatch(true);
    }
}