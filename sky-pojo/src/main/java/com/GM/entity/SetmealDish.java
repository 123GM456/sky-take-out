package com.GM.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 套餐菜品关系实体，对应数据库 setmeal_dish 表。
 * <p>记录套餐中包含哪些菜品及份数，name/price 为冗余字段。</p>
 */
@Data
public class SetmealDish implements Serializable {

    /** 主键 ID */
    private Long id;

    /** 套餐 ID */

    private Long setmealId;

    /** 菜品 ID */
    private Long dishId;

    /** 菜品名称（冗余） */
    private String name;

    /** 菜品单价（冗余，下单时用） */
    private BigDecimal price;

    /** 份数 */
    private Integer copies;
}