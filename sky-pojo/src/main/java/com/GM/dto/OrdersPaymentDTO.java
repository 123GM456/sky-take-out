package com.GM.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 订单支付传输对象（DTO），接收用户端支付请求的参数。
 */
@Data
public class OrdersPaymentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 订单号 */
    private String orderNumber;

    /** 支付方式：1=微信，2=支付宝 */
    private Integer payMethod;
}