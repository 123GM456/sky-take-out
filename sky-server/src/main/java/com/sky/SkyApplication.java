package com.sky;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 苍穹外卖后端服务启动入口（Spring Boot 引导类）。
 *
 * <p>位于 base package {@code com.sky} 下，使组件扫描能覆盖所有子包
 * （controller、service、mapper、config 等），无需手动配置 scanBasePackages。</p>
 */
@Slf4j
@SpringBootApplication
public class SkyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyApplication.class, args);
        log.info("苍穹外卖后端服务启动成功");
    }

}