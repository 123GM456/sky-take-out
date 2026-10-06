package com.GM;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 苍穹外卖后端服务启动入口（Spring Boot 引导类）。
 *
 * <p>包名 com.GM，Spring Boot 自动扫描其下所有子包（controller、service、mapper、config 等），
 * 无需手动配置 scanBasePackages。</p>
 */
// @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication
// @Slf4j：Lombok 注解，自动生成 log（LoggerFactory.getLogger(SkyApplication.class)）
@Slf4j
// @EnableCaching：开启缓存功能
@EnableCaching
public class SkyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyApplication.class, args);
        log.info("苍穹外卖后端服务启动成功");
    }

}