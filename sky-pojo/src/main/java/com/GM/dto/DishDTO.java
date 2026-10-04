package com.GM.dto;

import com.GM.entity.DishFlavor;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜品 DTO，接收前端菜品表单数据（新增/编辑共用）。
 */
@Data
public class DishDTO implements Serializable {

    /** 菜品 ID（新增时为 null，编辑时必填） */
    private Long id;

    /** 菜品名称 */
    private String name;

    /** 所属分类 ID */
    private Long categoryId;

    /** 价格 */
    private BigDecimal price;

    /** 菜品图片 URL */
    private String image;

    /** 菜品描述 */
    private String description;

    /** 状态：1=起售，0=停售 */
    private Integer status;

    /** 菜品口味列表（包含口味名和可选项，如"辣度：微辣/中辣/特辣"） */
    private List<DishFlavor> flavors = new ArrayList<>();
}