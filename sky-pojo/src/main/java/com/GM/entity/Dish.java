package com.GM.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 菜品实体，与数据库 dish 表一一对应（ORM 映射）。
 */
@Data
@Builder
@NoArgsConstructor          // 无参构造器（MyBatis ORM 反射创建对象时必需）
@AllArgsConstructor         // 全参构造器（@Builder 内部需要）
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 最近修改时间 */
    private LocalDateTime updateTime;

    /** 创建人 ID */
    private Long createUser;

    /** 最近修改人 ID */
    private Long updateUser;
}