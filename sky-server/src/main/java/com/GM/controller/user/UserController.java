package com.GM.controller.user;

import com.GM.constant.JwtClaimsConstant;
import com.GM.dto.UserLoginDTO;
import com.GM.entity.User;
import com.GM.properties.JwtProperties;
import com.GM.result.Result;
import com.GM.service.UserService;
import com.GM.utils.JwtUtil;
import com.GM.vo.UserLoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器。
 */
@RestController                     // 将返回值自动序列化为 JSON 写入 HTTP 响应体
@RequestMapping("/user/user")  // 所有方法共用该 URL 前缀
@RequiredArgsConstructor            // 为 final 字段生成构造器注入
@Slf4j
public class UserController {

    private final UserService userService;

    private final JwtProperties jwtProperties;

    /**
     * 用户登录。
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信登录请求：code={}", userLoginDTO.getCode());
        // 调用服务层方法进行登录
        User user = userService.WeChatLogin(userLoginDTO);
        // 生成 JWT 令牌
        Map<String, Object> claims=new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createToken(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        UserLoginVO userLoginVO=UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }
}

