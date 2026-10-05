package com.GM.vo;

import com.GM.entity.SetmealDish;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 套餐视图对象（VO），用于组装前端展示所需的数据。
 * <p>包含套餐基本信息 + 关联的分类名称 + 包含的菜品列表。</p>
 */
@Data
public class SetmealVO implements Serializable {

    /** 套餐 ID */
    private Long id;

    /** 分类 ID */
    private Long categoryId;

    /** 分类名称（冗余，前端展示用） */
    private String categoryName;

    /** 套餐名称 */
    private String name;

    /** 套餐价格 */
    private BigDecimal price;

    /** 套餐描述 */
    private String description;

    /** 套餐图片 URL */
    private String image;

    /** 状态：1=起售，0=停售 */
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /** 套餐包含的菜品列表 */
    private List<SetmealDish> setmealDishes = new ArrayList<>();
}