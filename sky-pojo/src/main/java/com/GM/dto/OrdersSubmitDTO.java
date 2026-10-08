package com.GM.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 提交订单传输对象（DTO），接收用户端下单请求的参数。
 * <p>只包含客户端可提供的字段；user_id、订单号、订单状态、下单时间等
 * 服务端生成字段由 Service 层填充，不出现在此对象中。</p>
 */
@Data
public class OrdersSubmitDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 地址簿 ID（收货地址） */
    private Long addressBookId;

    /** 支付方式：1=微信，2=支付宝 */
    private Integer payMethod;

    /** 订单备注 */
    private String remark;

    /** 预计送达时间 */
    private LocalDateTime estimatedDeliveryTime;

    /** 配送状态：1=立即送出，0=暂不送出 */
    private Integer deliveryStatus;

    /** 餐具状态：1=需要，0=不需要 */
    private Integer tablewareStatus;

    /** 餐具数量 */
    private Integer tablewareNumber;

    /** 打包费 */
    private Integer packAmount;

    /** 订单金额（客户端按购物车计算后传入） */
    private BigDecimal amount;
}