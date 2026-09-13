package com.GM;

// Lombok：为当前类生成 log 日志对象（LoggerFactory.getLogger(类名.class)）
import lombok.extern.slf4j.Slf4j;
// Spring Boot：run() 方法所在类，负责启动 Spring 应用上下文和内嵌服务器
import org.springframework.boot.SpringApplication;
// Spring Boot：@SpringBootApplication 注解所在包，包含自动配置、组件扫描、额外配置
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 苍穹外卖后端服务启动入口（Spring Boot 引导类）。
 *
 * <p>位于 base package {@code com.sky} 下，使组件扫描能覆盖所有子包
 * （controller、service、mapper、config 等），无需手动配置 scanBasePackages。</p>
 */
// Lombok：为当前类生成 log 日志对象（LoggerFactory.getLogger(SkyApplication.class)）
@Slf4j
// Spring Boot：标识为启动引导类，内含 @ComponentScan + @EnableAutoConfiguration + @Configuration
@SpringBootApplication
// Spring Boot 启动类：启动内嵌 Tomcat，自动装配所有 Bean，初始化 Spring 容器
public class SkyApplication {

    public static void main(String[] args) {
        // 启动 Spring 应用上下文、内嵌 Web 服务器、自动装配
        SpringApplication.run(SkyApplication.class, args);
        log.info("苍穹外卖后端服务启动成功");
    }

}