package com.GM.service.impl;

import com.GM.constant.MessageConstant;
import com.GM.entity.User;
import com.GM.exception.LoginFailedException;
import com.GM.mapper.UserMapper;
import com.GM.properties.WeChatProperties;
import com.GM.service.UserService;
import com.GM.dto.UserLoginDTO;
import com.GM.utils.HttpClientUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务实现。
 */
@Service                         // 标记为 Service 层 Bean，Spring 自动注册
@RequiredArgsConstructor         // 为 final 字段生成构造器注入（替代 @Autowired）
@Slf4j                           // 生成 log 对象（log.info / log.warn）
public class UserServiceImpl implements UserService {

    // 微信小程序登录接口
    public static final String WeChatLogin = "https://api.weixin.qq.com/sns/jscode2session";

    private final WeChatProperties weChatProperties;

    private final UserMapper userMapper;

    /**
     * 微信小程序登录。
     */
    @Override
    public User WeChatLogin(UserLoginDTO userLoginDTO){
        // 从 code 中获取 openid
        String openid = getOpenid(userLoginDTO.getCode());
        // 校验 openid 是否为空
        if(openid == null){
            log.warn("微信登录失败，openid 为空");
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        /**
         * 新用户自动注册。
         */
        User user = userMapper.getByOpenid(openid);
        if(user == null){
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        //返回用户对象
        return user;
    }

    /**
     * 从 code 中获取 openid。
     */
    private String getOpenid(String code){
        // 构建请求参数
        Map<String,String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getAppSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WeChatLogin,map);
        log.info("微信登录接口响应：{}", json);
        // 解析 JSON 响应，提取 openid（用 Jackson，项目已自带无需额外依赖）
        String openid = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode openidNode = jsonNode.get("openid");
            if (openidNode != null) {
                openid = openidNode.asText();
            } else {
                log.warn("微信响应中没有 openid 字段，可能是 code 无效或配置错误");
            }
        } catch (Exception e) {
            log.error("解析微信登录响应失败：json={}", json, e);
        }
        return openid;
    }

}