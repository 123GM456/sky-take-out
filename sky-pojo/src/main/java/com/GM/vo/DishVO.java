package com.GM.vo;

import com.GM.entity.DishFlavor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜品视图对象（VO），用于组装前端展示所需的数据。
 * <p>包含菜品基本信息 + 关联的分类名称 + 口味列表。</p>
 */
@Data
public class DishVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long categoryId;

    /** 分类名称（冗余，前端展示用） */
    private String categoryName;

    private String name;

    private BigDecimal price;

    private String image;

    private String description;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
    
    private List<DishFlavor> flavors=new ArrayList<>();
}