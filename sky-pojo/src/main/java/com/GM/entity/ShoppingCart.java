package com.GM.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车实体，与数据库 shopping_cart 表一一对应（ORM 映射）。
 * <p>存储用户加入购物车的商品（菜品或套餐），支持数量修改。</p>
 * <p>注意：dishId 和 setmealId 二选一，不能同时为空或同时有值。</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 商品名称（菜品名或套餐名） */
    private String name;

    /** 商品图片 URL */
    private String image;

    /** 用户 ID */
    private Long userId;

    /** 菜品 ID（与 setmealId 二选一） */
    private Long dishId;

    /** 套餐 ID（与 dishId 二选一） */
    private Long setmealId;

    /** 口味（如"微辣、不要葱"等，仅菜品有） */
    private String dishFlavor;

    /** 数量 */
    private Integer number;

    /** 金额（单价） */
    private BigDecimal amount;

    /** 创建时间 */
    private LocalDateTime createTime;
}