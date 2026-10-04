package com.GM.dto;

import com.GM.entity.SetmealDish;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 套餐 DTO，接收前端套餐表单数据（新增/编辑共用）。
 */
@Data
public class SetmealDTO implements Serializable {

    /** 套餐 ID（新增时为 null，编辑时必填） */
    private Long id;

    /** 所属分类 ID */
    private Long categoryId;

    /** 套餐名称 */
    private String name;

    /** 套餐价格 */
    private BigDecimal price;

    /** 套餐图片 URL */
    private String image;

    /** 套餐描述 */
    private String description;

    /** 状态：1=起售，0=停售 */
    private Integer status;

    /** 套餐包含的菜品列表（含菜品 ID、份数等） */
    private List<SetmealDish> setmealDishes = new ArrayList<>();
}