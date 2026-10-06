package com.GM.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 购物车 DTO，接收前端加入购物车的数据。
 * <p>dishId 与 setmealId 二选一，二者均为空或均有值时数据非法。</p>
 */
@Data
public class ShoppingCartDTO implements Serializable {

    /** 菜品 ID（与 setmealId 二选一） */
    private Long dishId;

    /** 套餐 ID（与 dishId 二选一） */
    private Long setmealId;

    /** 菜品口味（仅菜品有，如"微辣""中辣"） */
    private String dishFlavor;
}