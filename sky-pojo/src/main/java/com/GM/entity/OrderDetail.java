package com.GM.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单明细实体，与数据库 order_detail 表一一对应（ORM 映射）。
 * <p>存储订单中的每个商品详情（菜品或套餐），支持"再来一单"功能。</p>
 * <p>一张订单对应多条订单明细（一对多关系）。</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 商品名称（菜品/套餐名称） */
    private String name;

    /** 订单 ID（关联 orders 表） */
    private Long orderId;

    /** 菜品 ID（与 setmealId 二选一） */
    private Long dishId;

    /** 套餐 ID（与 dishId 二选一） */
    private Long setmealId;

    /** 菜品口味（仅菜品有） */
    private String dishFlavor;

    /** 数量 */
    private Integer number;

    /** 单价（下单时的价格快照） */
    private BigDecimal amount;

    /** 商品图片 */
    private String image;

    /** 创建时间 */
    private LocalDateTime createTime;
}