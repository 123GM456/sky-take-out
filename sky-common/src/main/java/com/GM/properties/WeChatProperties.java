package com.GM.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序配置属性类（Config 层），绑定 application.yml 中 {@code sky.wechat} 前缀的配置。
 * <p>包含小程序登录、微信支付相关的所有配置项。</p>
 */
@Component                                      // 注册为 Spring Bean
@ConfigurationProperties(prefix = "sky.wechat") // 自动读取 yml 中 sky.wechat.* 配置注入到同名字段
@Data
public class WeChatProperties {

    /** 小程序的 AppID（在微信公众平台 → 开发管理 → 开发设置 中获取） */
    private String appid;

    /** 小程序的密钥（AppSecret，用于服务端调用微信接口，必须保密） */
    private String appSecret;

    /** 商户号（微信支付商户平台 → 账户信息 中获取） */
    private String mchid;

    /** 商户 API 证书的证书序列号（微信支付商户平台 → 账户中心 → API安全 中获取） */
    private String mchSerialNo;

    /** 商户私钥文件路径（用于签名请求，通常放在 resources/cert 目录下） */
    private String privateKeyFilePath;

    /** APIv3 密钥（证书解密的密钥，在微信支付商户平台 → 账户中心 → API安全 中设置） */
    private String apiV3Key;

    /** 微信支付平台证书文件路径（用于验证微信回调签名） */
    private String weChatPayCertFilePath;

    /** 支付成功的回调地址（微信支付完成后，微信服务器会向此地址发送通知） */
    private String notifyUrl;

    /** 退款成功的回调地址（退款处理完成后，微信服务器会向此地址发送通知） */
    private String refundNotifyUrl;
}