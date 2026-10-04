package com.GM.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import java.time.Duration;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis 配置类（Config 层）。
 * <p>集中管理 Redis 的三大核心配置：</p>
 * <ul>
 *   <li>Redis 连接与序列化方式（统一使用 JSON 序列化，避免乱码）</li>
 *   <li>RedisTemplate Bean 注册（提供 String-Object 类型的操作模板）</li>
 *   <li>Spring Cache 缓存管理器配置（支持 @Cacheable 等注解）</li>
 * </ul>
 */
@Configuration                   // 标记为 Spring 配置类
@EnableCaching                  // 启用 Spring Cache 注解支持（@Cacheable、@CacheEvict 等）
public class RedisConfiguration {

    /**
     * 配置 RedisTemplate（String-Object 类型）。
     * <p>默认的 RedisTemplate 使用 JDK 序列化（二进制），可读性差且跨语言兼容性差。
     * 这里改用 JSON 序列化，使 Redis 中的数据以 JSON 格式存储，便于调试和查看。</p>
     *
     * @param connectionFactory Redis 连接工厂（Spring Boot 自动注入）
     * @return 配置好的 RedisTemplate 实例
     */
    @Bean                       // 将方法返回值注册为 Spring Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置 Redis 连接工厂（负责创建 Redis 连接）
        template.setConnectionFactory(connectionFactory);

        // 使用 StringRedisSerializer 序列化 key（key 通常是字符串，如 "SHOP_STATUS"）
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);

        // 使用 GenericJackson2JsonRedisSerializer 序列化 value（value 是对象，需要转 JSON）
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        // 使配置生效
        template.afterPropertiesSet();

        return template;
    }

    /**
     * 配置 Redis 缓存管理器（CacheManager）。
     * <p>用于支持 Spring Cache 注解（@Cacheable、@CachePut、@CacheEvict），
     * 统一管理缓存的过期时间、序列化方式等。</p>
     *
     * @param connectionFactory Redis 连接工厂
     * @return 配置好的 RedisCacheManager 实例
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // 默认缓存配置：TTL=1小时，JSON序列化
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                // 设置 key 的序列化方式为 String（避免 key 出现乱码）
                .entryTtl(Duration.ofHours(1))                    // 默认缓存有效期 1 小时
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置 value 的序列化方式为 JSON（便于查看缓存内容）
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 禁用缓存空值（防止缓存穿透攻击）
                .disableCachingNullValues();

        // 针对不同缓存名称设置不同的过期时间（按需扩展）
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        // 示例：店铺状态缓存 30 分钟过期
        cacheConfigurations.put("shop-status", defaultConfig.entryTtl(Duration.ofMinutes(30)));

        // 创建缓存管理器并返回
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)                      // 应用默认配置
                .withInitialCacheConfigurations(cacheConfigurations) // 应用特定缓存配置
                .build();
    }

    /**
     * 自定义缓存 Key 生成器。
     * <p>默认的 Key 生成器可能生成过长的 Key（包含完整类名+方法名+参数），
     * 这里自定义生成规则：类名::方法名::参数值，更简洁易读。</p>
     *
     * @return 自定义的 KeyGenerator 实例
     */
    @Bean
    public KeyGenerator customKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                // 添加类名（简短形式，去掉包名）
                sb.append(target.getClass().getSimpleName());
                sb.append("::");
                // 添加方法名
                sb.append(method.getName());
                sb.append("::");
                // 添加参数值（如果有）
                for (Object param : params) {
                    sb.append(param.toString());
                }
                return sb.toString();
            }
        };
    }
}