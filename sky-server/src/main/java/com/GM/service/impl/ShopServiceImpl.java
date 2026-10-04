package com.GM.service.impl;

import com.GM.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

@Service                         // 标记为 Service 层 Bean，Spring 自动注册
@RequiredArgsConstructor         // 为 final 字段生成构造器注入（替代 @Autowired）
@Slf4j                           // 生成 log 对象（log.info / log.warn）
public class ShopServiceImpl implements ShopService {

    private final RedisTemplate<String, Object> redisTemplate;

    // 店铺营业状态 Redis 键名
    private static final String KEY = "shopStatus";

    /**
     * 设置店铺营业状态。
     * @param status 目标状态：1=营业，0=打烊
     */
    @Override
    public void setStatus(Integer status) {
        log.info("设置店铺营业状态为：{}", status);
        redisTemplate.opsForValue().set(KEY, status);
    }

    /**
     * 获取店铺营业状态。
     * @return 店铺营业状态：1=营业，0=打烊
     */
    @Override
    public Integer getStatus() {
        Object value = redisTemplate.opsForValue().get(KEY);
        return value == null ? 1 : (Integer) value;
    }

}