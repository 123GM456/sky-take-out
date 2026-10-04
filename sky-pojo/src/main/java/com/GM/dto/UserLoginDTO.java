package com.GM.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 用户登录 DTO，接收微信小程序前端传来的临时授权码。
 * <p>前端调用 {@code wx.login()} 后获取临时授权码 code，
 * 后端收到后使用该 code + appid + secret 调用微信接口换取用户的 openid。</p>
 */
@Data
public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 微信登录临时授权码（有效期5分钟，只能使用一次）。
     * <p>前端通过 {@code wx.login()} 获取，后端用于调用微信 {@code jscode2session} 接口换取 openid。</p>
     */
    private String code;
}