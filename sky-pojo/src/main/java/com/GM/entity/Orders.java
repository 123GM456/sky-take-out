package com.GM.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体，与数据库 orders 表一一对应（ORM 映射）。
 * <p>存储订单主信息，包括订单状态、金额、收货地址、配送信息等核心数据。</p>
 * <p>使用 Orders（复数）作为类名，避免与 SQL 关键字 order 冲突。</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 订单号（唯一，格式：时间戳+随机数） */
    private String number;

    /** 订单状态：1=待付款，2=待接单，3=已接单，4=派送中，5=已完成，6=已取消 */
    private Integer status;

    /** 用户 ID（下单人） */
    private Long userId;

    /** 地址簿 ID（收货地址） */
    private Long addressBookId;

    /** 下单时间 */
    private LocalDateTime orderTime;

    /** 支付/结账时间 */
    private LocalDateTime checkoutTime;

    /** 支付方式：1=微信支付，2=支付宝 */
    private Integer payMethod;

    /** 支付状态：0=未支付，1=已支付，2=已退款 */
    private Integer payStatus;

    /** 订单总金额（所有商品金额之和 + 打包费等） */
    private BigDecimal amount;

    /** 订单备注（用户填写的特殊要求） */
    private String remark;

    /** 收货人手机号（冗余存储，方便查询） */
    private String phone;

    /** 收货地址（冗余存储，方便查询） */
    private String address;

    /** 收货人姓名 */
    private String consignee;

    /** 取消原因（用户取消时填写） */
    private String cancelReason;

    /** 拒单原因（商家拒单时填写） */
    private String rejectionReason;

    /** 取消时间 */
    private LocalDateTime cancelTime;

    /** 预计送达时间 */
    private LocalDateTime estimatedDeliveryTime;

    /** 配送状态：1=立即送出，0=暂不送出 */
    private Integer deliveryStatus;

    /** 实际送达时间 */
    private LocalDateTime deliveryTime;

    /** 打包费 */
    private Integer packAmount;

    /** 餐具数量 */
    private Integer tablewareNumber;

    /** 餐具状态：1=已需要，0=不需要 */
    private Integer tablewareStatus;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}